package gnnt.MEBS.account.action;

import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class CustomerPWAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CustomerPWAction.class);
  private InService inService;
  @Resource(name="papersTypeMap")
  private Map papersTypeMap;
  @Resource(name="firmStatusMap")
  private Map firmStatusMap;
  
  public InService getService()
  {
    return this.inService;
  }
  
  public InService getInService()
  {
    return this.inService;
  }
  
  public void setInService(InService inService)
  {
    this.inService = inService;
  }
  
  public Map getPapersTypeMap()
  {
    return this.papersTypeMap;
  }
  
  public Map getFirmStatusMap()
  {
    return this.firmStatusMap;
  }
}
