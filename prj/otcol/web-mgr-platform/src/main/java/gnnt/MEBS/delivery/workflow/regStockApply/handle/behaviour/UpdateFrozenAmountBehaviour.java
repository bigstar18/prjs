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

public class UpdateFrozenAmountBehaviour
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(UpdateFrozenAmountBehaviour.class);
  @Autowired
  @Qualifier("w_enterWareService")
  private EnterWareService enterWareService;
  
  public void deal(WorkFlowClone paramWorkFlowClone)
  {
    RegStockApply localRegStockApply = (RegStockApply)paramWorkFlowClone;
    EnterWare localEnterWare = this.enterWareService.getEnterWareById(localRegStockApply.getStockId());
    BigDecimal localBigDecimal1 = new BigDecimal(localEnterWare.getFrozenAmount()).subtract(new BigDecimal(localRegStockApply.getWeight()));
    BigDecimal localBigDecimal2 = new BigDecimal(localEnterWare.getExistAmount()).subtract(new BigDecimal(localRegStockApply.getWeight()));
    localEnterWare.setFrozenAmount(localBigDecimal1.doubleValue());
    localEnterWare.setExistAmount(localBigDecimal2.doubleValue());
    this.enterWareService.updateEnterWare(localEnterWare);
    this.logger.debug("添加仓库申请表addOutWareBehaviour");
  }
}
