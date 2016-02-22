package gnnt.MEBS.announcement.action;

import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.account.model.SpecialMember;
import gnnt.MEBS.account.model.SpecialMemberRole;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.announcement.model.Notice;
import gnnt.MEBS.announcement.service.NoticeService;
import gnnt.MEBS.announcement.service.SpecialMemberRoleServie;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.common.service.RoleService;
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
  @Qualifier("noticeService")
  private NoticeService noticeService;
  @Autowired
  @Qualifier("roleService")
  private RoleService roleService;
  @Autowired
  @Qualifier("specialMemberRoleServie")
  private SpecialMemberRoleServie specialMemberRoleServie;
  private User user;
  private Notice notice;
  private List<Role> roleList = new ArrayList();
  
  public List<Role> getRoleList()
  {
    return this.roleList;
  }
  
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
  
  private List<MemberInfo> memberInfoList = new ArrayList();
  private List<Notice> noticeList = new ArrayList();
  
  public List<Notice> getNoticeList()
  {
    return this.noticeList;
  }
  
  public void setNoticeList(List<Notice> noticeList)
  {
    this.noticeList = noticeList;
  }
  
  public List<MemberInfo> getMemberInfoList()
  {
    return this.memberInfoList;
  }
  
  public void setMemberInfoList(List<MemberInfo> memberInfoList)
  {
    this.memberInfoList = memberInfoList;
  }
  
  public InService getService()
  {
    return this.memberInfoService;
  }
  
  public String getUserName()
  {
    this.user = AclCtrl.getUser(this.request);
    String result = "success";
    if ((this.notice != null) && (this.notice.getSendType().intValue() == 2)) {
      result = "transmit";
    }
    return result;
  }
  
  public String getAllList()
  {
    if (this.request.getParameter("id") != null)
    {
      this.notice = ((Notice)this.noticeService.getById(Long.valueOf(Long.parseLong(this.request.getParameter("id")))));
      this.notice.setSendType(Integer.valueOf(2));
    }
    this.memberInfoList = this.memberInfoService.getList(null, null);
    QueryConditions conditions = new QueryConditions("primary.s_memberNo", "=", AclCtrl.getUser(this.request).getMemberInfo().getS_memberNo());
    conditions.addCondition("primary.id", ">=", Long.valueOf(0L));
    this.roleList = this.roleService.getList(conditions, null);
    return getReturnValue();
  }
  
  public String addNotices()
  {
    this.user = AclCtrl.getUser(this.request);
    String[] members = this.request.getParameterValues("memberInfo");
    String[] sRoles = this.request.getParameterValues("role");
    String[] traders = this.request.getParameterValues("trader");
    String traderAppoint = this.request.getParameter("traderManager");
    String membersAppoint = this.request.getParameter("manager");
    String specialMembersAppoint = this.request.getParameter("specialsManager");
    
    String isAllSpecial = this.request.getParameter("isAllSpecial");
    if (isAllSpecial == null) {
      isAllSpecial = "Y";
    }
    String isAllMember = this.request.getParameter("isAllMember");
    if (isAllMember == null) {
      isAllMember = "Y";
    }
    String membersAppointStr = "";
    if ((membersAppoint != null) && (!"".equals(membersAppoint)))
    {
      String[] membersAppoints = membersAppoint.split(",");
      for (String string : membersAppoints) {
        membersAppointStr = membersAppointStr + "'" + string + "',";
      }
    }
    String specialMembersAppointStr = "";
    if ((specialMembersAppoint != null) && (!"".equals(specialMembersAppoint)))
    {
      String[] specialMembersAppoints = specialMembersAppoint.split(",");
      for (String string : specialMembersAppoints) {
        specialMembersAppointStr = specialMembersAppointStr + "'" + string + "',";
      }
    }
    String tradersMember = "";
    if (traders != null) {
      for (String string : traders) {
        tradersMember = tradersMember + "'" + string + "',";
      }
    }
    String specialRole = "";
    if (sRoles != null) {
      for (String string : sRoles) {
        specialRole = specialRole + "'" + string + "',";
      }
    }
    String traderAppointStr = "";
    if ((traderAppoint != null) && (!"".equals(traderAppoint)))
    {
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
    List<SpecialMemberRole> list;
    Long id;
    if (specialType != null)
    {
      specialRange.addAttribute("type", specialType);
      if ((!"Y".equals(isAllSpecial)) || (sRoles != null) || ((specialMembersAppoint != null) && (!"".equals(specialMembersAppoint))))
      {
        Element groups = specialRange.addElement("groups");
        groups.addAttribute("isNotAllDefault", "Y");
        Element group = groups.addElement("group");
        group.addAttribute("id", AclCtrl.getUser(this.request).getMemberInfo().getS_memberNo());
        Element isNotDefaultRole = group.addElement("isNotDefaultRole");
        if ("Y".equals(isAllSpecial))
        {
          isNotDefaultRole.addAttribute("flag", "Y");
          Element roleElement = isNotDefaultRole.addElement("role");
          if ("".equals(specialRole)) {
            roleElement.addCDATA(specialRole);
          } else {
            roleElement.addCDATA(specialRole.substring(0, specialRole.length() - 1));
          }
        }
        else
        {
          isNotDefaultRole.addAttribute("flag", "Y");
          Element roleElement = isNotDefaultRole.addElement("role");
          
          list = this.specialMemberRoleServie.getList(new QueryConditions("memberid", "=", AclCtrl.getUser(this.request).getMemberInfo().getS_memberNo()), null);
          Long id;
          if (list.size() == 0) {
            id = Long.valueOf(-1L);
          } else {
            id = ((SpecialMemberRole)list.get(0)).getId();
          }
          roleElement.addCDATA(id);
        }
      }
      Element specialElement = specialRange.addElement("appoint");
      if ("".equals(specialMembersAppointStr)) {
        specialElement.addCDATA(specialMembersAppointStr);
      } else {
        specialElement.addCDATA(specialMembersAppointStr.substring(0, specialMembersAppointStr.length() - 1));
      }
    }
    Element memberRange = rootElement.addElement("range");
    String memberType = this.request.getParameter("memberType");
    if (memberType != null)
    {
      memberRange.addAttribute("type", memberType);
      if (members != null)
      {
        Element groups = memberRange.addElement("groups");
        groups.addAttribute("isNotAllDefault", isAllMember);
        if (("Y".equals(isAllMember)) && 
          (members != null) && (members.length > 0))
        {
          String[] arrayOfString5;
          list = (arrayOfString5 = members).length;
          for (id = 0; id < list; id++)
          {
            String member = arrayOfString5[id];
            Element group = groups.addElement("group");
            group.addAttribute("id", member);
            Element isNotDefaultRole = group.addElement("isNotDefaultRole");
            
            isNotDefaultRole.addAttribute("flag", "N");
          }
        }
      }
      Element memberElement = memberRange.addElement("appoint");
      if ("".equals(membersAppointStr)) {
        memberElement.addCDATA(membersAppointStr);
      } else {
        memberElement.addCDATA(membersAppointStr.substring(0, membersAppointStr.length() - 1));
      }
    }
    String recipientRules = document.asXML();
    this.notice.setRecipientRules(recipientRules);
    this.notice.setCreateTime(new Date());
    if (this.notice.getSendTime() == null) {
      this.notice.setSendTime(new Date());
    }
    this.notice.setNoticeType("2");
    this.notice.setSource("S");
    String result = "success";
    if (2 == this.notice.getSendType().intValue())
    {
      this.notice.setAuthor(AclCtrl.getUser(this.request).getName());
      result = "transmit";
    }
    else
    {
      this.notice.setSendType(Integer.valueOf(1));
    }
    int resultValue = this.noticeService.addNotice(this.notice);
    if (resultValue == 1) {
      resultValue = 6;
    }
    addResultSessionMsg(this.request, resultValue);
    return result;
  }
  
  public String noticesList()
  {
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "id";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "true";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    if (qc == null) {
      qc = new QueryConditions();
    }
    qc.addCondition("primary.author", "=", AclCtrl.getUser(this.request).getName());
    this.noticeList = this.noticeService.getList(qc, pageInfo);
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    return getReturnValue();
  }
}
