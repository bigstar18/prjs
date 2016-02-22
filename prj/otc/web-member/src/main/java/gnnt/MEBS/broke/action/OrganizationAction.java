package gnnt.MEBS.broke.action;

import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.broke.service.BrokerageService;
import gnnt.MEBS.broke.service.MemberInfoTreeService;
import gnnt.MEBS.broke.service.OrganizationChangeService;
import gnnt.MEBS.broke.service.OrganizationService;
import gnnt.MEBS.common.action.BaseAction;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class OrganizationAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(OrganizationAction.class);
  @Autowired
  @Qualifier("organizationService")
  private OrganizationService organizationService;
  @Autowired
  @Qualifier("organizationChangeService")
  private OrganizationChangeService organizationChangeService;
  @Autowired
  @Qualifier("brokerageService")
  private BrokerageService brokerageService;
  @Autowired
  @Qualifier("memberInfoTreeService")
  private MemberInfoTreeService memberInfoTreeService;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  private String strBrokerageNo;
  
  public BrokerageService getBrokerageService()
  {
    return this.brokerageService;
  }
  
  public String getStrBrokerageNo()
  {
    return this.strBrokerageNo;
  }
  
  public void setStrBrokerageNo(String strBrokerageNo)
  {
    this.strBrokerageNo = strBrokerageNo;
  }
  
  public InService getService()
  {
    return this.organizationService;
  }
  
  public String list()
  {
    String sortName = "primary.name";
    String sortOrder = "false";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    this.resultList = getService().getList(qc, pageInfo);
    this.logger.debug("resultList  size:" + this.resultList.size());
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    this.obj = null;
    return getReturnValue();
  }
  
  public String forwardAdd()
  {
    List list = this.organizationChangeService.list(
      (String)this.request.getSession().getAttribute(
      ActionConstant.REGISTERID), 
      (String)this.request.getSession().getAttribute(
      ActionConstant.ORGANIZATIONID), null);
    String seqString = this.organizationService.getOrganizationSeq();
    int size = seqString.length();
    if (size == 4) {
      seqString = "00" + seqString;
    } else if (size == 5) {
      seqString = "0" + seqString;
    }
    String organizationNo = 
      (String)this.request.getSession().getAttribute(ActionConstant.REGISTERID) + 
      seqString;
    this.request.setAttribute("memberInfoList", list);
    this.request.setAttribute("organizationNO", organizationNo);
    this.request.setAttribute("parentOrgNo", (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID));
    return getReturnValue();
  }
  
  public String add()
  {
    this.logger.debug("enter add");
    Organization org = (Organization)this.obj;
    if ((org.getParentOrganizationNO() == null) || 
      ("".equals(org.getParentOrganizationNO()))) {
      org.setParentOrganizationNO(
        (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID));
    }
    int resultValue = getService().add(org);
    this.obj = org;
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String viewById()
  {
    this.logger.debug("enter viewById");
    Organization organization = (Organization)this.obj;
    List list = this.organizationChangeService.list(
      (String)this.request.getSession().getAttribute(
      ActionConstant.REGISTERID), 
      (String)this.request.getSession().getAttribute(
      ActionConstant.ORGANIZATIONID), organization
      .getOrganizationNO());
    this.request.setAttribute("memberInfoList", list);
    this.request.setAttribute("parentOrgNo", (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID));
    return super.viewById();
  }
  
  public String update()
  {
    this.logger.debug("enter update");
    Organization org = (Organization)this.obj;
    if ((org.getParentOrganizationNO() == null) || 
      ("".equals(org.getParentOrganizationNO()))) {
      org.setParentOrganizationNO(
        (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID));
    }
    int resultValue = getService().update(org);
    this.obj = org;
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String updatePassword()
  {
    this.logger.debug("enter update");
    Organization organization = (Organization)this.obj;
    organization.setPassword(MD5.getMD5(organization.getId(), 
      organization.getPassword()));
    int resultValue = getService().update(organization);
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String getBrokerageList()
  {
    Organization organization = 
      (Organization)this.organizationService.get((Organization)this.obj);
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.memberNo", "=", this.request.getSession()
      .getAttribute("REGISTERID"));
    List brokerageAllList = this.brokerageService.getList(qc, null);
    QueryConditions qc1 = new QueryConditions();
    qc1.addCondition("primary.memberNo", "=", this.request.getSession()
      .getAttribute("REGISTERID"));
    qc1.addCondition("primary.brokerageNo", "in", 
      "(select brokerageNO from BrokerageAndOrganization)");
    List brokerageList = this.brokerageService.getList(qc1, null);
    if (brokerageList.size() != 0)
    {
      this.obj = organization;
      this.request.setAttribute("brokerageList", brokerageList);
      this.request.setAttribute("brokerageAllList", brokerageAllList);
      this.request.setAttribute("organization", organization);
      return getReturnValue();
    }
    this.request.setAttribute(ActionConstant.RESULTMSG, "没有合适的居间人，请先添加居间人!");
    this.request.setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(3));
    return getReturnValue();
  }
  
  public String updateCotactBroke()
  {
    String[] strBrokeNo = this.request.getParameterValues("checkId");
    this.logger.debug("enter update CotactBroke");
    Organization organization = 
      (Organization)this.organizationService.get((Organization)this.obj);
    List brokerageList = this.brokerageService.getList(null, null);
    this.request.setAttribute("brokerageList", brokerageList);
    this.request.setAttribute("organization", organization);
    Set set = new HashSet();
    if ((strBrokeNo != null) && (strBrokeNo.length > 0)) {
      for (String str : strBrokeNo)
      {
        this.logger.debug("brokeNo:" + str);
        Brokerage brokerage = new Brokerage();
        brokerage.setBrokerageNo(str);
        set.add(brokerage);
      }
    }
    organization.setBrokerageSet(set);
    this.obj = organization;
    int resultValue = this.organizationService.updateCotactBroke(organization);
    this.logger.debug("resultValue:" + resultValue);
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String getOrganizationTree()
  {
    String tree = "";
    String organizationNo = (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID);
    String parentOrgNo = this.request.getParameter("parentOrgNo");
    String thisOrganizationNo = this.request.getParameter("organizationNO");
    
    String memberNo = (String)this.request.getSession().getAttribute(ActionConstant.REGISTERID);
    if ((organizationNo != null) && (!"".equals(organizationNo)))
    {
      tree = this.memberInfoTreeService.organizationTreePart(organizationNo, thisOrganizationNo);
      Organization organization = (Organization)this.organizationService.getById(organizationNo);
      if (organizationNo.equals(thisOrganizationNo))
      {
        tree = "";
      }
      else
      {
        tree = 
          "<li><input type=\"radio\" name=\"checks\" id=\"checkM\" value=\"" + organization.getOrganizationNO() + "\"> " + "<label id=\"" + organization.getOrganizationNO() + "_memberInfoTree\">" + organization.getName() + "</label><ul>" + tree + "</ul>";
        tree = tree + "</li>";
      }
    }
    else
    {
      tree = this.memberInfoTreeService.memOrganizationTreePart(memberNo, thisOrganizationNo);
    }
    this.request.setAttribute("parentOrgNo", this.request.getParameter("parentOrgNo"));
    this.request.setAttribute("tree", tree);
    return getReturnValue();
  }
}
