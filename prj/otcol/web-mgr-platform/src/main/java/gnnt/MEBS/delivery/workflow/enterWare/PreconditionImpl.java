package gnnt.MEBS.delivery.workflow.enterWare;

import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.EnterWareService;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.Precondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PreconditionImpl
  implements Precondition
{
  @Autowired
  @Qualifier("w_enterWareService")
  private EnterWareService enterWareService;
  
  public int doPrecondition(OriginalModel paramOriginalModel)
  {
    int i = 1;
    WorkFlowClone localWorkFlowClone = paramOriginalModel.getObject();
    EnterWare localEnterWare1 = (EnterWare)localWorkFlowClone;
    String str = localEnterWare1.getId();
    EnterWare localEnterWare2 = this.enterWareService.getEnterWareLock(str);
    if (localEnterWare1.getCurrentStatus() != localEnterWare2.getCurrentStatus()) {
      i = -4;
    }
    return i;
  }
}
