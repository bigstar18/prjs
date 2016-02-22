package gnnt.MEBS.broke.action;

import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.broke.service.BrokerageDivertService;
import gnnt.MEBS.broke.service.BrokerageService;
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
public class BrokerageDivertAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(BrokerageDivertAction.class);
  @Autowired
  @Qualifier("brokerageService")
  private BrokerageService brokerageService;
  @Autowired
  @Qualifier("brokerageDivertService")
  private BrokerageDivertService brokerageDivertService;
  
  public InService getService()
  {
    return this.brokerageService;
  }
  
  public String update()
  {
    this.logger.debug("entered the update method of BrokerageDivertAction");
    Brokerage brokerage = (Brokerage)this.obj;
    
    int resultValue = -1;
    resultValue = this.brokerageDivertService.checkBrokerageDivert(brokerage.getBrokerageNo(), brokerage.getMemberNo());
    if (resultValue == 1)
    {
      this.brokerageDivertService.add(brokerage.getBrokerageNo(), brokerage.getMemberNo());
      resultValue = 3;
    }
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
}
