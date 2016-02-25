package gnnt.MEBS.delivery.workflow.regStockToEnterWare.handle.filter;

import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.model.workflow.RegStockApply;
import gnnt.MEBS.delivery.model.workflow.RegStockToEnterWare;
import gnnt.MEBS.delivery.services.EnterWareService;
import gnnt.MEBS.delivery.services.RegStockApplyService;
import gnnt.MEBS.delivery.services.RegStockService;
import gnnt.MEBS.delivery.workflow.Filtering;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.common.ProcessContextImpl;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class RegStockToEnterWarefilterTypeAndStatus
  implements Filtering
{
  private final transient Log logger = LogFactory.getLog(RegStockToEnterWarefilterTypeAndStatus.class);
  @Autowired
  @Qualifier("w_regStockApplyService")
  private RegStockApplyService regStockApplyService;
  @Autowired
  @Qualifier("w_regStockService")
  private RegStockService regStockService;
  @Autowired
  @Qualifier("w_enterWareService")
  private EnterWareService enterWareService;
  @Autowired
  @Qualifier("regStockApplyProcessContext")
  private ProcessContextImpl regStockApplyProcessContext;
  
  public int checkFiler(OriginalModel paramOriginalModel)
  {
    int i = 1;
    RegStockToEnterWare localRegStockToEnterWare = (RegStockToEnterWare)paramOriginalModel.getObject();
    RegStock localRegStock = this.regStockService.getRegStockById(localRegStockToEnterWare.getRegStockId());
    RegStockApply localRegStockApply = this.regStockApplyService.getRegStockApplyById(localRegStock.getRegStockId());
    EnterWare localEnterWare = this.enterWareService.getEnterWareLock(localRegStock.getStockId());
    double d1 = localRegStock.getWeight();
    double d2 = localRegStock.getFrozenWeight();
    double d3 = Arith.sub(d1, d2);
    double d4 = localRegStockToEnterWare.getRelTurnToWeight();
    int j = ((Integer)this.regStockApplyProcessContext.getFinalStatus().get(0)).intValue();
    int k = localRegStock.getType();
    if (k == 0)
    {
      if ((Arith.compareTo(d3, d4) < 0) || (d4 <= 0.0D) || (Arith.format(localEnterWare.getFrozenAmount(), 4) < Arith.format(d4, 4))) {
        return -10;
      }
    }
    else if (k == 1)
    {
      if ((Arith.compareTo(d3, d4) < 0) || (d4 <= 0.0D)) {
        return -10;
      }
    }
    else if (k > 1) {
      i = -11;
    }
    return i;
  }
}
