package gnnt.MEBS.delivery.workflow.outWare.handle.behaviour;

import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.model.workflow.OutWare;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.EnterWareService;
import gnnt.MEBS.delivery.services.OutWareService;
import gnnt.MEBS.delivery.workflow.Behaviour;
import java.math.BigDecimal;
import java.util.Date;
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
  @Autowired
  @Qualifier("w_outWareService")
  private OutWareService outWareService;
  
  public void deal(WorkFlowClone paramWorkFlowClone)
  {
    OutWare localOutWare = (OutWare)paramWorkFlowClone;
    EnterWare localEnterWare = this.enterWareService.getEnterWareById(localOutWare.getEnterWareId());
    BigDecimal localBigDecimal1 = new BigDecimal(localEnterWare.getFrozenAmount()).subtract(new BigDecimal(localOutWare.getWeight()));
    BigDecimal localBigDecimal2 = new BigDecimal(localEnterWare.getExistAmount()).subtract(new BigDecimal(localOutWare.getWeight()));
    localEnterWare.setFrozenAmount(localBigDecimal1.doubleValue());
    localEnterWare.setExistAmount(localBigDecimal2.doubleValue());
    localOutWare.setOutDate(new Date());
    this.enterWareService.updateEnterWare(localEnterWare);
    this.outWareService.updateOutEnter(localOutWare);
    this.logger.debug("添加仓库申请表addOutWareBehaviour");
  }
}
