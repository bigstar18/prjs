package gnnt.MEBS.delivery.workflow.enterInform;

import gnnt.MEBS.delivery.model.workflow.EnterApply;
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
    EnterApply localEnterApply1 = (EnterApply)paramOriginalModel.getObject();
    EnterApply localEnterApply2 = this.enterApplyService.getEnterApplyLock(localEnterApply1.getId());
    if (localEnterApply1.getCurrentStatus() != localEnterApply2.getCurrentStatus()) {
      i = -4;
    }
    return i;
  }
}
