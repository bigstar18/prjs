package gnnt.MEBS.delivery.workflow.outWare.handle;

import gnnt.MEBS.delivery.model.workflow.OutWare;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.OutWareService;
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
  @Qualifier("w_outWareService")
  private OutWareService outWareService;
  
  public void updateStatus(WorkFlowClone paramWorkFlowClone, int paramInt)
  {
    this.logger.debug("传入的状态StatusOperateImpl" + paramInt);
    OutWare localOutWare = (OutWare)paramWorkFlowClone;
    localOutWare.setAbility(paramInt);
    this.outWareService.updateOutEnter((OutWare)paramWorkFlowClone);
  }
}
