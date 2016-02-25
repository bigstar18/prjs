package gnnt.MEBS.delivery.workflow.outWare;

import gnnt.MEBS.delivery.model.workflow.OutWare;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.OutWareService;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.Precondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PreconditionImpl
  implements Precondition
{
  @Autowired
  @Qualifier("w_outWareService")
  private OutWareService outWareService;
  
  public int doPrecondition(OriginalModel paramOriginalModel)
  {
    int i = 1;
    WorkFlowClone localWorkFlowClone = paramOriginalModel.getObject();
    OutWare localOutWare1 = (OutWare)localWorkFlowClone;
    String str = localOutWare1.getId();
    OutWare localOutWare2 = this.outWareService.getOutWareForUpdate(str);
    if (localOutWare1.getCurrentStatus() != localOutWare2.getCurrentStatus()) {
      i = -4;
    }
    return i;
  }
}
