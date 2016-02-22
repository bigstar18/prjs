package gnnt.MEBS.broke.action;

import gnnt.MEBS.common.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class ManagerAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(ManagerAction.class);
  private InService inService;
  
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
}
