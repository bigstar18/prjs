package gnnt.MEBS.delivery.workflow.regStockApply.handle.behaviour;

import gnnt.MEBS.delivery.model.workflow.RegStockApply;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.RegStockApplyService;
import gnnt.MEBS.delivery.workflow.Behaviour;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AddRegStockApplyBehaviour
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(AddRegStockApplyBehaviour.class);
  @Autowired
  @Qualifier("w_regStockApplyService")
  private RegStockApplyService regStockApplyService;
  
  public void deal(WorkFlowClone paramWorkFlowClone)
  {
    this.regStockApplyService.addRegStockApply((RegStockApply)paramWorkFlowClone);
    this.logger.debug("添加注册仓单申请表AddStandardRegStockBehaviour");
  }
}
