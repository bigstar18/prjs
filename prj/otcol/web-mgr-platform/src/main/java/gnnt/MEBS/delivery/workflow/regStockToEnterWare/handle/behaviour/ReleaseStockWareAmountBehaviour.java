package gnnt.MEBS.delivery.workflow.regStockToEnterWare.handle.behaviour;

import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.model.workflow.RegStockToEnterWare;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.RegStockService;
import gnnt.MEBS.delivery.workflow.Behaviour;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ReleaseStockWareAmountBehaviour
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(ReleaseStockWareAmountBehaviour.class);
  @Autowired
  @Qualifier("w_regStockService")
  private RegStockService regStockService;
  
  public void deal(WorkFlowClone paramWorkFlowClone)
  {
    RegStockToEnterWare localRegStockToEnterWare = (RegStockToEnterWare)paramWorkFlowClone;
    RegStock localRegStock = this.regStockService.getRegStockById(localRegStockToEnterWare.getRegStockId());
    double d = localRegStockToEnterWare.getRelTurnToWeight();
    this.regStockService.releaseAmount(localRegStock, d, 2);
    this.logger.debug("添加标准注册仓单转入库单申请表AddEnterWareBehaviour");
  }
}
