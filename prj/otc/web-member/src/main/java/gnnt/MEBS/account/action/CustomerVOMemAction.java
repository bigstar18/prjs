package gnnt.MEBS.account.action;

import gnnt.MEBS.account.service.CustomerVOService;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.broke.service.MemberInfoTreeService;
import gnnt.MEBS.broke.service.OrganizationService;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.service.InService;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("customerVOAction")
@Scope("request")
public class CustomerVOMemAction
  extends CustomerVOAction
{
  private final transient Log logger = LogFactory.getLog(CustomerVOAction.class);
  @Resource(name="openstatusMap")
  private Map openstatusMap;
  @Autowired
  @Qualifier("customerVOService")
  private CustomerVOService customerVOService;
  @Resource(name="papersTypeMap")
  private Map papersTypeMap;
  @Resource(name="papersTypeUpdateMap")
  private Map papersTypeUpdateMap;
  @Resource(name="firmStatusMap")
  private Map firmStatusMap;
  @Autowired
  @Qualifier("memberInfoTreeService")
  private MemberInfoTreeService memberInfoTreeService;
  @Autowired
  @Qualifier("organizationService")
  public OrganizationService organizationService;
  
  public Map getOpenstatusMap()
  {
    return this.openstatusMap;
  }
  
  public Map getPapersTypeMap()
  {
    return this.papersTypeMap;
  }
  
  public Map getPapersTypeUpdateMap()
  {
    return this.papersTypeUpdateMap;
  }
  
  public Map getFirmStatusMap()
  {
    return this.firmStatusMap;
  }
  
  public InService getService()
  {
    return this.customerVOService;
  }
  
  public String viewById()
  {
    String organizationNO = (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID);
    if ((organizationNO != null) && (!"".equals(organizationNO)))
    {
      Organization organization = (Organization)this.organizationService.getById(organizationNO);
      this.request.setAttribute("organization", organization);
    }
    this.request.setAttribute("parentOrgNo", organizationNO);
    return super.viewById();
  }
  
  public String getOrganizationTree()
  {
    String tree = "";
    String organizationNo = (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID);
    String memberNo = (String)this.request.getSession().getAttribute(ActionConstant.REGISTERID);
    if ((organizationNo != null) && (!"".equals(organizationNo))) {
      tree = this.memberInfoTreeService.treeRightForOrg(organizationNo);
    } else {
      tree = this.memberInfoTreeService.treeForRight(memberNo);
    }
    this.request.setAttribute("parentOrgNo", this.request.getParameter("parentOrgNo"));
    this.request.setAttribute("tree", tree);
    return getReturnValue();
  }
}
