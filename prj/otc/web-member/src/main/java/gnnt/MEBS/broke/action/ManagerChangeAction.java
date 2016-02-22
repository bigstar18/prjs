package gnnt.MEBS.broke.action;

import gnnt.MEBS.broke.model.Manager;
import gnnt.MEBS.broke.service.ManagerChangeService;
import gnnt.MEBS.broke.service.ManagerService;
import gnnt.MEBS.broke.service.OrganizationService;
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
public class ManagerChangeAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(ManagerChangeAction.class);
  @Autowired
  @Qualifier("managerChangeService")
  private ManagerChangeService managerChangeService;
  @Autowired
  @Qualifier("managerService")
  private ManagerService managerService;
  @Autowired
  @Qualifier("organizationService")
  private OrganizationService organizationService;
  
  public InService getService()
  {
    return this.managerService;
  }
  
  public String viewById()
  {
    this.obj = getService().get(this.obj);
    Manager manager = (Manager)this.obj;
    manager.setManagerNoChange(manager.getManagerNo());
    this.obj = manager;
    HttpSession session = this.request.getSession();
    List resultList = this.organizationService.getByMemberNoList(null, (String)session.getAttribute(ActionConstant.REGISTERID));
    this.request.setAttribute("resultList", resultList);
    return getReturnValue();
  }
  
  public String update()
  {
    int returnValue = this.managerChangeService.update((Manager)this.obj);
    addResultMsg(this.request, returnValue);
    return getReturnValue();
  }
}
