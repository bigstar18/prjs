package gnnt.MEBS.broke.action;

import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.broke.service.OrganizationChangeService;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.List;
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
public class OrganizationChangeAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(OrganizationChangeAction.class);
  @Autowired
  @Qualifier("organizationChangeService")
  private OrganizationChangeService organizationChangeService;
  
  public InService getService()
  {
    return this.organizationChangeService;
  }
  
  public String viewById()
  {
    this.obj = getService().get(this.obj);
    Organization organization = (Organization)this.obj;
    organization.setOrganizationNoChange(organization.getOrganizationNO());
    List list = this.organizationChangeService.list((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID), (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID), organization.getOrganizationNO());
    this.request.setAttribute("resultList", list);
    this.obj = organization;
    return getReturnValue();
  }
  
  public String update()
  {
    int returnValue = this.organizationChangeService.update((Organization)this.obj);
    addResultMsg(this.request, returnValue);
    return getReturnValue();
  }
}
