package gnnt.MEBS.delivery.workflow.regStockApply.handle.behaviour;

import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.model.workflow.RegStockApply;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.EnterWareService;
import gnnt.MEBS.delivery.workflow.Behaviour;
import java.math.BigDecimal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class UpdateToBeforeAmountBehaviour
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(UpdateToBeforeAmountBehaviour.class);
  @Autowired
  @Qualifier("w_enterWareService")
  private EnterWareService enterWareService;
  
  public void deal(WorkFlowClone paramWorkFlowClone)
  {
    RegStockApply localRegStockApply = (RegStockApply)paramWorkFlowClone;
    EnterWare localEnterWare = this.enterWareService.getEnterWareById(localRegStockApply.getStockId());
    BigDecimal localBigDecimal = new BigDecimal(localEnterWare.getFrozenAmount()).subtract(new BigDecimal(localRegStockApply.getWeight()));
    localEnterWare.setFrozenAmount(localBigDecimal.doubleValue());
    this.enterWareService.updateEnterWare(localEnterWare);
  }
}
