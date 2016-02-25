package gnnt.MEBS.delivery.workflow.regStockToEnterWare;

import gnnt.MEBS.delivery.model.workflow.RegStockToEnterWare;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.RegStockToEnterWareService;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.Precondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PreconditionImpl
  implements Precondition
{
  @Autowired
  @Qualifier("w_regStockToEnterWareService")
  private RegStockToEnterWareService regStockToEnterWareService;
  
  public int doPrecondition(OriginalModel paramOriginalModel)
  {
    int i = 1;
    WorkFlowClone localWorkFlowClone = paramOriginalModel.getObject();
    RegStockToEnterWare localRegStockToEnterWare1 = (RegStockToEnterWare)localWorkFlowClone;
    String str = localRegStockToEnterWare1.getId();
    RegStockToEnterWare localRegStockToEnterWare2 = this.regStockToEnterWareService.getRegStockToEnterWareLock(str);
    if (localRegStockToEnterWare1.getCurrentStatus() != localRegStockToEnterWare2.getCurrentStatus()) {
      i = -4;
    }
    return i;
  }
}
