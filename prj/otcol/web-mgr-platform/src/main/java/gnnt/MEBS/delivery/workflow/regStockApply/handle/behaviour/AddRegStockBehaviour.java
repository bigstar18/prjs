package gnnt.MEBS.delivery.workflow.regStockApply.handle.behaviour;

import gnnt.MEBS.base.copy.CopyObjectParamUtil;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.model.workflow.RegStockApply;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.RegStockService;
import gnnt.MEBS.delivery.workflow.Behaviour;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AddRegStockBehaviour
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(AddRegStockBehaviour.class);
  @Autowired
  @Qualifier("w_regStockService")
  private RegStockService regStockService;
  
  public void deal(WorkFlowClone paramWorkFlowClone)
  {
    RegStockApply localRegStockApply = (RegStockApply)paramWorkFlowClone;
    RegStock localRegStock = new RegStock();
    CopyObjectParamUtil.bindData(localRegStockApply, localRegStock);
    localRegStock.setCreateTime(localRegStockApply.getAuditTime());
    localRegStock.setInitWeight(localRegStockApply.getWeight());
    this.regStockService.addRegStock(localRegStock);
    this.logger.debug("添加注册仓单申请表AddRegStockBehaviour");
  }
}
