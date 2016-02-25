package gnnt.MEBS.delivery.workflow.regStockApply.handle;

import gnnt.MEBS.delivery.model.workflow.RegStockApply;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.RegStockApplyService;
import gnnt.MEBS.delivery.workflow.StatusOperate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class StatusOperateImpl
  implements StatusOperate
{
  private final transient Log logger = LogFactory.getLog(StatusOperateImpl.class);
  @Autowired
  @Qualifier("w_regStockApplyService")
  private RegStockApplyService regStockApplyService;
  
  public void updateStatus(WorkFlowClone paramWorkFlowClone, int paramInt)
  {
    this.logger.debug("传入的状态StatusOperateImpl" + paramInt);
    RegStockApply localRegStockApply = (RegStockApply)paramWorkFlowClone;
    localRegStockApply.setStatus(paramInt);
    this.regStockApplyService.updateRegStockApply((RegStockApply)paramWorkFlowClone);
  }
}
