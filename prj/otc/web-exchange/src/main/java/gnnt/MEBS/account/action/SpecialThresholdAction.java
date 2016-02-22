package gnnt.MEBS.account.action;

import gnnt.MEBS.account.service.SpecialThresholdService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class SpecialThresholdAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(SpecialThresholdAction.class);
  @Autowired
  @Qualifier("specialThresholdService")
  private SpecialThresholdService specialThresholdService;
  
  public InService getService()
  {
    return this.specialThresholdService;
  }
}
