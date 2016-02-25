package gnnt.MEBS.delivery.workflow.regStockApply.handle.filter;

import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.model.workflow.RegStockApply;
import gnnt.MEBS.delivery.services.EnterWareService;
import gnnt.MEBS.delivery.workflow.Filtering;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.common.ProcessContextImpl;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class FilteringEnterWareImpl
  implements Filtering
{
  private final transient Log logger = LogFactory.getLog(FilteringEnterWareImpl.class);
  @Autowired
  @Qualifier("w_enterWareService")
  private EnterWareService enterWareService;
  @Autowired
  @Qualifier("enterWareProcessContext")
  private ProcessContextImpl enterWareProcessContext;
  
  public int checkFiler(OriginalModel paramOriginalModel)
  {
    int i = 1;
    RegStockApply localRegStockApply = (RegStockApply)paramOriginalModel.getObject();
    EnterWare localEnterWare = this.enterWareService.getEnterWareById(localRegStockApply.getStockId());
    int j = ((Integer)this.enterWareProcessContext.getFinalStatus().get(0)).intValue();
    if (localEnterWare.getAbility() != j) {
      i = -13;
    }
    return i;
  }
}
