package gnnt.MEBS.broke.action;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.broke.service.BrokerageAndOrganizationService;
import gnnt.MEBS.broke.service.BrokerageService;
import gnnt.MEBS.broke.service.BrokerageVOService;
import gnnt.MEBS.broke.service.MemberInfoTreeService;
import gnnt.MEBS.broke.service.OrganizationService;
import gnnt.MEBS.common.action.BaseAction;
import gnnt.MEBS.config.constant.ActionConstant;
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

public class BrokerageAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(BrokerageAction.class);
  private InService inService;
  @Autowired
  @Qualifier("brokerageService")
  public BrokerageService brokerageService;
  @Autowired
  @Qualifier("brokerageVOService")
  public BrokerageVOService brokerageVOService;
  @Autowired
  @Qualifier("organizationService")
  public OrganizationService organizationService;
  @Autowired
  @Qualifier("brokerageAndOrganizationService")
  public BrokerageAndOrganizationService brokerageAndOrganizationService;
  @Autowired
  @Qualifier("memberInfoTreeService")
  private MemberInfoTreeService memberInfoTreeService;
  
  public InService getInService()
  {
    return this.inService;
  }
  
  public void setInService(InService inService)
  {
    this.inService = inService;
  }
  
  public InService getService()
  {
    return this.inService;
  }
  
  public String forwardAdd()
  {
    String seqString = this.brokerageService.getBrokerageSeq();
    int size = seqString.length();
    if (size == 4) {
      seqString = "00" + seqString;
    } else if (size == 5) {
      seqString = "0" + seqString;
    }
    String brokerageNo = (String)this.request.getSession().getAttribute(ActionConstant.REGISTERID) + seqString;
    String organization = (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID);
    String memberNo = (String)this.request.getSession().getAttribute(ActionConstant.REGISTERID);
    List<Organization> organizationList = this.organizationService.getOrganizationBYOrgNOMemNo(organization, memberNo);
    this.request.setAttribute("brokerageNo", brokerageNo);
    this.request.setAttribute("organizationList", organizationList);
    this.request.setAttribute("parentOrgNo", organization);
    return getReturnValue();
  }
  
  public String add()
  {
    this.logger.debug("enter add");
    Brokerage bro = (Brokerage)this.obj;
    String reqOrganizationNO = this.request.getParameter("organizationNo");
    String organizationNo = (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID);
    if ((reqOrganizationNO != null) && (!"".equals(reqOrganizationNO))) {
      organizationNo = reqOrganizationNO;
    }
    if ((organizationNo != null) && (!"".equals(organizationNo)))
    {
      Organization organization = new Organization();
      organization.setOrganizationNO(organizationNo);
      Set<Organization> set = new HashSet();
      set.add(organization);
      bro.setOrganizationSet(set);
    }
    int resultValue = getService().add(bro);
    this.obj = bro;
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String viewById()
  {
    this.obj = getService().get(this.obj);
    String organization = (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID);
    String memberNo = (String)this.request.getSession().getAttribute(ActionConstant.REGISTERID);
    List<Organization> organizationList = this.organizationService.getOrganizationBYOrgNOMemNo(organization, memberNo);
    this.request.setAttribute("organizationList", organizationList);
    this.request.setAttribute("parentOrgNo", organization);
    return getReturnValue();
  }
  
  public String update()
  {
    this.logger.debug("enter update");
    Brokerage bro = (Brokerage)this.obj;
    String reqOrganizationNO = this.request.getParameter("organizationNo");
    String organizationNo = (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID);
    if ((reqOrganizationNO != null) && (!"".equals(reqOrganizationNO))) {
      organizationNo = reqOrganizationNO;
    }
    if ((organizationNo != null) && (!"".equals(organizationNo)))
    {
      Organization organization = new Organization();
      organization.setOrganizationNO(organizationNo);
      Set<Organization> set = new HashSet();
      set.add(organization);
      bro.setOrganizationSet(set);
    }
    this.obj = bro;
    int resultValue = this.brokerageService.update(bro);
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String getOrganizationTree()
  {
    String tree = "";
    String organizationNo = (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID);
    String memberNo = (String)this.request.getSession().getAttribute(ActionConstant.REGISTERID);
    if ((organizationNo != null) && (!"".equals(organizationNo)))
    {
      tree = this.memberInfoTreeService.treeRightForOrgChildren(organizationNo);
      Organization organization = (Organization)this.organizationService.getById(organizationNo);
      tree = "<li><input type=\"radio\" name=\"checks\" id=\"checkM\" value=\"" + organization.getOrganizationNO() + "\"> " + "<label id=\"" + organization.getOrganizationNO() + "_memberInfoTree\">" + 
        organization.getName() + "</label><ul>" + tree + "</ul>";
      tree = tree + "</li>";
    }
    else
    {
      tree = this.memberInfoTreeService.treeForRight(memberNo);
    }
    this.request.setAttribute("parentOrgNo", this.request.getParameter("parentOrgNo"));
    this.request.setAttribute("tree", tree);
    return getReturnValue();
  }
  
  public String list()
  {
    this.logger.debug("enter list");
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "primary.id";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "false";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    

    QueryConditions qc = getQueryConditions(map);
    this.resultList = this.brokerageVOService.getList(qc, pageInfo);
    this.logger.debug("resultList  size:" + this.resultList.size());
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    this.obj = null;
    return getReturnValue();
  }
}
