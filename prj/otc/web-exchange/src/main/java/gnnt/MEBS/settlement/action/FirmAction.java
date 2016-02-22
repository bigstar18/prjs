package gnnt.MEBS.settlement.action;

import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.settlement.service.FirmService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class FirmAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(FirmAction.class);
  @Autowired
  @Qualifier("firmService")
  private FirmService firmService;
  
  public InService getService()
  {
    return this.firmService;
  }
}
