package gnnt.MEBS.delivery.workflow.enterWare.handle;

import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.EnterWareService;
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
  @Qualifier("w_enterWareService")
  private EnterWareService enterWareService;
  
  public void updateStatus(WorkFlowClone paramWorkFlowClone, int paramInt)
  {
    this.logger.debug("传入的状态StatusOperateImpl" + paramInt);
    EnterWare localEnterWare = (EnterWare)paramWorkFlowClone;
    localEnterWare.setAbility(paramInt);
    this.enterWareService.updateEnterWare((EnterWare)paramWorkFlowClone);
  }
}
