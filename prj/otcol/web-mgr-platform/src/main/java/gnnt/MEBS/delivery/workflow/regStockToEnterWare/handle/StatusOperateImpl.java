package gnnt.MEBS.delivery.workflow.regStockToEnterWare.handle;

import gnnt.MEBS.delivery.model.workflow.RegStockToEnterWare;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.RegStockToEnterWareService;
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
  @Qualifier("w_regStockToEnterWareService")
  private RegStockToEnterWareService regStockToEnterWareService;
  
  public void updateStatus(WorkFlowClone paramWorkFlowClone, int paramInt)
  {
    this.logger.debug("传入的状态StatusOperateImpl: " + paramInt);
    RegStockToEnterWare localRegStockToEnterWare = (RegStockToEnterWare)paramWorkFlowClone;
    localRegStockToEnterWare.setStatus(paramInt);
    this.regStockToEnterWareService.updateRegStockToEnterWare((RegStockToEnterWare)paramWorkFlowClone);
  }
}
