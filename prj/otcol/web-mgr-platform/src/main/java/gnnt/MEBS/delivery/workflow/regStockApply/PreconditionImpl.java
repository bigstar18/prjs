package gnnt.MEBS.delivery.workflow.regStockApply;

import gnnt.MEBS.delivery.model.workflow.RegStockApply;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.RegStockApplyService;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.Precondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PreconditionImpl
  implements Precondition
{
  @Autowired
  @Qualifier("w_regStockApplyService")
  private RegStockApplyService regStockApplyService;
  
  public int doPrecondition(OriginalModel paramOriginalModel)
  {
    int i = 1;
    WorkFlowClone localWorkFlowClone = paramOriginalModel.getObject();
    RegStockApply localRegStockApply1 = (RegStockApply)localWorkFlowClone;
    String str = localRegStockApply1.getApplyId();
    RegStockApply localRegStockApply2 = this.regStockApplyService.getRegStockApplyForUpdate(str);
    if (localRegStockApply1.getCurrentStatus() != localRegStockApply2.getCurrentStatus()) {
      i = -4;
    }
    return i;
  }
}
