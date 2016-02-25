package gnnt.MEBS.delivery.workflow.enterApply.handle;

import gnnt.MEBS.delivery.model.workflow.EnterApply;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.EnterApplyService;
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
  @Qualifier("w_enterApplyService")
  private EnterApplyService enterApplyService;
  
  public void updateStatus(WorkFlowClone paramWorkFlowClone, int paramInt)
  {
    this.logger.debug("传入的状态StatusOperateImpl:" + paramInt);
    EnterApply localEnterApply = (EnterApply)paramWorkFlowClone;
    localEnterApply.setAbility(paramInt);
    this.enterApplyService.updateEnterApply((EnterApply)paramWorkFlowClone);
  }
}
