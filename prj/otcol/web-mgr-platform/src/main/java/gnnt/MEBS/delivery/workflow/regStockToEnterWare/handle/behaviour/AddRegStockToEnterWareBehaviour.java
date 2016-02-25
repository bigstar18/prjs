package gnnt.MEBS.delivery.workflow.regStockToEnterWare.handle.behaviour;

import gnnt.MEBS.delivery.model.workflow.RegStockToEnterWare;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.RegStockToEnterWareService;
import gnnt.MEBS.delivery.workflow.Behaviour;
import gnnt.MEBS.delivery.workflow.enterWare.handle.behaviour.AddEnterWareBehaviour;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AddRegStockToEnterWareBehaviour
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(AddEnterWareBehaviour.class);
  @Autowired
  @Qualifier("w_regStockToEnterWareService")
  private RegStockToEnterWareService regStockToEnterWareService;
  
  public void deal(WorkFlowClone paramWorkFlowClone)
  {
    RegStockToEnterWare localRegStockToEnterWare = (RegStockToEnterWare)paramWorkFlowClone;
    this.regStockToEnterWareService.addRegStockToEnterWare(localRegStockToEnterWare);
    this.logger.debug("添加注册仓单转入库单申请表AddEnterWareBehaviour");
  }
}
