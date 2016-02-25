package gnnt.MEBS.delivery.workflow.enterApply;

import gnnt.MEBS.delivery.model.workflow.EnterApply;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.EnterApplyService;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.Precondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PreconditionImpl
  implements Precondition
{
  @Autowired
  @Qualifier("w_enterApplyService")
  private EnterApplyService enterApplyService;
  
  public int doPrecondition(OriginalModel paramOriginalModel)
  {
    int i = 1;
    WorkFlowClone localWorkFlowClone = paramOriginalModel.getObject();
    EnterApply localEnterApply1 = (EnterApply)localWorkFlowClone;
    String str = localEnterApply1.getId();
    EnterApply localEnterApply2 = this.enterApplyService.getEnterApplyLock(str);
    if (localEnterApply1.getCurrentStatus() != localEnterApply2.getCurrentStatus()) {
      i = -4;
    }
    return i;
  }
}
