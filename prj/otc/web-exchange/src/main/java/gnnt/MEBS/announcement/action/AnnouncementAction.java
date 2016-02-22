package gnnt.MEBS.announcement.action;

import gnnt.MEBS.account.model.SpecialMember;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.account.service.SpecialMemberService;
import gnnt.MEBS.announcement.model.MemberNoticeTree;
import gnnt.MEBS.announcement.model.Notice;
import gnnt.MEBS.announcement.service.MemberNoticeTreeService;
import gnnt.MEBS.announcement.service.NoticeService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class AnnouncementAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(AnnouncementAction.class);
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  @Autowired
  @Qualifier("memberNoticeTreeService")
  private MemberNoticeTreeService memberNoticeTreeService;
  @Autowired
  @Qualifier("specialMemberService")
  private SpecialMemberService specialMemberService;
  @Autowired
  @Qualifier("noticeService")
  private NoticeService noticeService;
  private User user;
  private Notice notice;
  
  public Notice getNotice()
  {
    return this.notice;
  }
  
  public void setNotice(Notice notice)
  {
    this.notice = notice;
  }
  
  public User getUser()
  {
    return this.user;
  }
  
  private List<Notice> noticeList = new ArrayList();
  
  public List<Notice> getNoticeList()
  {
    return this.noticeList;
  }
  
  public void setNoticeList(List<Notice> noticeList)
  {
    this.noticeList = noticeList;
  }
  
  public InService getService()
  {
    return this.memberInfoService;
  }
  
  public String getUserName()
  {
    this.user = AclCtrl.getUser(this.request);
    return getReturnValue();
  }
  
  public String specialMemberTreeList()
  {
    List<SpecialMember> specialMemberList = this.specialMemberService.getList(null, null);
    String treeString = "";
    for (SpecialMember m : specialMemberList) {
      treeString = treeString + m.forTree();
    }
    this.request.setAttribute("treeString", treeString);
    return getReturnValue();
  }
  
  public String memberTreeList()
  {
    this.logger.debug("enter list");
    PageInfo pageInfo = new PageInfo(1, 100000, "id", false);
    List<MemberNoticeTree> list = this.memberNoticeTreeService.getList(null, pageInfo);
    this.logger.debug("list  size:" + list.size());
    String treeString = "";
    for (MemberNoticeTree m : list) {
      treeString = treeString + m.forTree();
    }
    this.request.setAttribute("treeString", treeString);
    return getReturnValue();
  }
  
  public String addNotices()
  {
    this.user = AclCtrl.getUser(this.request);
    String tradersMember = this.request.getParameter("gnnt_traderInfoIds");
    String traderAppoint = this.request.getParameter("traderManager");
    String membersAppointStr = this.request.getParameter("gnnt_memberInfoIds");
    String specialMembersAppointStr = this.request.getParameter("gnnt_specialMemberInfoIds");
    
    String isAllSpecial = this.request.getParameter("isAllSpecial");
    if (isAllSpecial == null) {
      isAllSpecial = "Y";
    }
    String isAllMember = this.request.getParameter("isAllMember");
    if (isAllMember == null) {
      isAllMember = "Y";
    }
    String isAllTrader = this.request.getParameter("isAllTrader");
    if (isAllTrader == null) {
      isAllTrader = "Y";
    }
    String traderAppointStr = "";
    if ((traderAppoint != null) && (!"".equals(traderAppoint)))
    {
      traderAppoint.replaceAll("，", ",");
      String[] traderAppoints = traderAppoint.split(",");
      for (String string : traderAppoints) {
        traderAppointStr = traderAppointStr + "'" + string + "',";
      }
    }
    Document document = DocumentHelper.createDocument();
    document.setXMLEncoding("utf-8");
    Element rootElement = document.addElement("root");
    


    Element specialRange = rootElement.addElement("range");
    String specialType = this.request.getParameter("specialType");
    if (specialType != null)
    {
      specialRange.addAttribute("type", specialType);
      Element group = specialRange.addElement("group");
      group.addAttribute("isNotAllSpecialMemeber", isAllSpecial);
      if ("Y".equals(isAllSpecial))
      {
        Element specialMemeber = group.addElement("specialMemeber");
        specialMemeber.addCDATA(specialMembersAppointStr);
      }
    }
    Element memberRange = rootElement.addElement("range");
    String memberType = this.request.getParameter("memberType");
    if (memberType != null)
    {
      memberRange.addAttribute("type", memberType);
      Element group = memberRange.addElement("group");
      group.addAttribute("isNotAllMember", isAllMember);
      if ("Y".equals(isAllMember))
      {
        Element memberElement = group.addElement("member");
        memberElement.addCDATA(membersAppointStr);
      }
      Element relationElement = memberRange.addElement("relation");
      relationElement.addCDATA("Y".equals(this.request.getParameter("underling")) ? "Y" : "N");
    }
    Element traderRange = rootElement.addElement("range");
    String traderType = this.request.getParameter("traderType");
    if (traderType != null)
    {
      traderRange.addAttribute("type", traderType);
      Element group = traderRange.addElement("group");
      group.addAttribute("isNotAllCustomers", isAllTrader);
      if ("Y".equals(isAllTrader))
      {
        Element memberElement = group.addElement("member");
        memberElement.addCDATA(tradersMember);
      }
    }
    if ((traderAppointStr != null) && (!"".equals(traderAppointStr)))
    {
      Element traderAppointElement = traderRange.addElement("appoint");
      traderAppointElement.addCDATA(traderAppointStr.substring(0, traderAppointStr.length() - 1));
    }
    String recipientRules = document.asXML();
    
    this.notice.setRecipientRules(recipientRules);
    this.notice.setCreateTime(new Date());
    this.notice.setSendTime(new Date());
    this.notice.setSendType(Integer.valueOf(1));
    this.notice.setNoticeType("2");
    this.notice.setSource("E");
    int resultValue = this.noticeService.add(this.notice);
    if (resultValue == 2) {
      resultValue = 6;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String noticesList()
  {
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "id";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "true";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    qc.addCondition("primary.noticeType", "=", "2");
    this.noticeList = this.noticeService.getList(qc, pageInfo);
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    return getReturnValue();
  }
}
