package gnnt.MEBS.announcement.action;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.account.service.CustomerService;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.announcement.model.BrokerageNoticeTree;
import gnnt.MEBS.announcement.model.MemberNoticeTree;
import gnnt.MEBS.announcement.model.Notice;
import gnnt.MEBS.announcement.service.BrokerageNoticeTreeService;
import gnnt.MEBS.announcement.service.MemberNoticeTreeService;
import gnnt.MEBS.announcement.service.NoticeService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.broke.model.Manager;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.broke.service.BrokerageService;
import gnnt.MEBS.broke.service.ManagerService;
import gnnt.MEBS.broke.service.OrganizationService;
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
  @Qualifier("noticeService")
  private NoticeService noticeService;
  @Autowired
  @Qualifier("memberNoticeTreeService")
  private MemberNoticeTreeService memberNoticeTreeService;
  @Autowired
  @Qualifier("organizationService")
  private OrganizationService organizationService;
  @Autowired
  @Qualifier("brokerageService")
  private BrokerageService brokerageService;
  @Autowired
  @Qualifier("managerService")
  private ManagerService managerService;
  @Autowired
  @Qualifier("customerService")
  private CustomerService customerService;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  @Autowired
  @Qualifier("brokerageNoticeTreeService")
  private BrokerageNoticeTreeService brokerageNoticeTreeService;
  @Autowired
  @Qualifier("roleService")
  private RoleService roleService;
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
  private List<Organization> organizationList = new ArrayList();
  private List<Manager> managerList = new ArrayList();
  private List<Brokerage> brokerageList = new ArrayList();
  private List<Customer> customerList = new ArrayList();
  private List<Role> roleList = new ArrayList();
  
  public List<Role> getRoleList()
  {
    return this.roleList;
  }
  
  public List<Customer> getCustomerList()
  {
    return this.customerList;
  }
  
  public List<Notice> getNoticeList()
  {
    return this.noticeList;
  }
  
  public List<Organization> getOrganizationList()
  {
    return this.organizationList;
  }
  
  public List<Manager> getManagerList()
  {
    return this.managerList;
  }
  
  public List<Brokerage> getBrokerageList()
  {
    return this.brokerageList;
  }
  
  public InService getService()
  {
    return this.noticeService;
  }
  
  public String getUserName()
  {
    this.user = AclCtrl.getUser(this.request);
    String result = "success";
    if ((this.notice != null) && (this.notice.getSendType().intValue() == 2)) {
      result = "transmit";
    }
    this.request.setAttribute("memberName", ((MemberInfo)this.memberInfoService.getById(this.user.getMemberNo())).getName());
    return result;
  }
  
  public String addNotices()
  {
    this.user = AclCtrl.getUser(this.request);
    String tradersMember = this.request.getParameter("traderManagerShow");
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
    if ((tradersMember != null) && ((tradersMember.lastIndexOf(",") > 0) || (tradersMember.lastIndexOf("，") > 0)))
    {
      tradersMember = tradersMember.substring(0, tradersMember.length() - 1);
      
      tradersMember = tradersMember.replaceAll(" ", "");
    }
    Document document = DocumentHelper.createDocument();
    document.setXMLEncoding("utf-8");
    Element rootElement = document.addElement("root");
    


    Element specialRange = rootElement.addElement("range");
    String specialType = this.request.getParameter("specialType");
    if (specialType != null)
    {
      specialRange.addAttribute("type", "brokerage");
      Element group = specialRange.addElement("group");
      group.addAttribute("isNotAllBrokerage", isAllSpecial);
      if ("Y".equals(isAllSpecial))
      {
        Element specialMemeber = group.addElement("brokerage");
        specialMemeber.addCDATA(specialMembersAppointStr);
        Element relationElement = group.addElement("relation");
        relationElement.addCDATA("Y".equals(this.request.getParameter("underlingBrokerage")) ? "Y" : "N");
      }
      else
      {
        Element memberId = specialRange.addElement("memberId");
        memberId.addCDATA(this.user.getMemberNo());
      }
    }
    Element memberRange = rootElement.addElement("range");
    String memberType = this.request.getParameter("memberType");
    if (memberType != null)
    {
      memberRange.addAttribute("type", "organization");
      Element group = memberRange.addElement("group");
      group.addAttribute("isNotAllOrganization", isAllMember);
      if ("Y".equals(isAllMember))
      {
        Element memberElement = group.addElement("organization");
        memberElement.addCDATA(membersAppointStr);
      }
      else
      {
        Element memberId = memberRange.addElement("memberId");
        memberId.addCDATA(this.user.getMemberNo());
      }
      Element relationElement = memberRange.addElement("relation");
      relationElement.addCDATA("Y".equals(this.request.getParameter("underling")) ? "Y" : "N");
    }
    Element traderRange = rootElement.addElement("range");
    String traderType = this.request.getParameter("traderType");
    if (traderType != null)
    {
      traderRange.addAttribute("type", "traderMemberNotice");
      Element group = traderRange.addElement("group");
      group.addAttribute("isNotAllCustomers", isAllTrader);
      if ("Y".equals(isAllTrader))
      {
        Element memberElement = group.addElement("trader");
        memberElement.addCDATA(tradersMember);
      }
      Element memberId = traderRange.addElement("memberId");
      memberId.addCDATA(this.user.getMemberNo());
    }
    String recipientRules = document.asXML();
    
    this.notice.setRecipientRules(recipientRules);
    this.notice.setCreateTime(new Date());
    this.notice.setSendTime(new Date());
    this.notice.setSendType(Integer.valueOf(1));
    this.notice.setNoticeType("2");
    this.notice.setSource("M");
    int resultValue = this.noticeService.add(this.notice);
    if (resultValue == 2) {
      resultValue = 6;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String specialMemberTreeList()
  {
    String treeString = "";
    BrokerageNoticeTree brokerageNoticeTree = (BrokerageNoticeTree)this.brokerageNoticeTreeService.getById(AclCtrl.getUser(this.request).getMemberNo());
    if (brokerageNoticeTree != null) {
      treeString = brokerageNoticeTree.forTree();
    }
    this.request.setAttribute("treeString", treeString);
    return getReturnValue();
  }
  
  public String memberTreeList()
  {
    this.logger.debug("enter list");
    String treeString = "";
    MemberNoticeTree memberNoticeTree = (MemberNoticeTree)this.memberNoticeTreeService.getById(AclCtrl.getUser(this.request).getMemberNo());
    if (memberNoticeTree != null) {
      treeString = memberNoticeTree.forTree();
    }
    this.request.setAttribute("treeString", treeString);
    return getReturnValue();
  }
  
  public String getAllList()
  {
    if (this.request.getParameter("id") != null)
    {
      this.notice = ((Notice)this.noticeService.getById(Long.valueOf(Long.parseLong(this.request.getParameter("id")))));
      this.notice.setSendType(Integer.valueOf(2));
    }
    this.logger.debug("id:" + AclCtrl.getUser(this.request).getMemberInfo().getId());
    






    return getReturnValue();
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
    qc.addCondition("primary.noticeType", "=", "2");
    qc.addCondition("primary.author", "=", AclCtrl.getUser(this.request).getName());
    this.noticeList = this.noticeService.getList(qc, pageInfo);
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    return getReturnValue();
  }
  
  public String noticesShowList()
  {
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "id";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "true";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    if (qc == null) {
      qc = new QueryConditions("source", "=", "M");
    } else {
      qc.addCondition("source", "=", "M");
    }
    qc.addCondition("author", "=", AclCtrl.getUser(this.request).getId());
    this.noticeList = this.noticeService.getList(qc, pageInfo);
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    return getReturnValue();
  }
}
