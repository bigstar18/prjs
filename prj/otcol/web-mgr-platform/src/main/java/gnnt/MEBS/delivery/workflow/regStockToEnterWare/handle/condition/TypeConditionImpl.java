package gnnt.MEBS.delivery.workflow.regStockToEnterWare.handle.condition;

import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.model.workflow.RegStockToEnterWare;
import gnnt.MEBS.delivery.services.RegStockService;
import gnnt.MEBS.delivery.workflow.Condition;
import gnnt.MEBS.delivery.workflow.Handle;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TypeConditionImpl
  implements Condition
{
  @Autowired
  @Qualifier("w_regStockService")
  private RegStockService regStockService;
  private List<String> type;
  
  public void setType(List<String> paramList)
  {
    this.type = paramList;
  }
  
  public boolean check(OriginalModel paramOriginalModel, Handle paramHandle)
  {
    boolean bool = false;
    RegStockToEnterWare localRegStockToEnterWare = (RegStockToEnterWare)paramOriginalModel.getObject();
    RegStock localRegStock = this.regStockService.getRegStockById(localRegStockToEnterWare.getRegStockId());
    if (this.type.contains(localRegStock.getType() + "")) {
      bool = true;
    }
    return bool;
  }
}
