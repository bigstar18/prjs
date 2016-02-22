package gnnt.MEBS.broke.action;

import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.broke.service.BrokeragePWService;
import gnnt.MEBS.common.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class BrokeragePwdAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(BrokeragePwdAction.class);
  @Autowired
  @Qualifier("brokeragePWService")
  private BrokeragePWService brokeragePwdService;
  
  public InService getService()
  {
    return this.brokeragePwdService;
  }
  
  public String update()
  {
    Brokerage bro = (Brokerage)this.obj;
    int resultValue = getService().update(bro);
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
}
