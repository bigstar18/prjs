package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.RegStockService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CheckRegStockType
  implements Filtering
{
  private List<String> regStockTypeList;
  @Autowired
  @Qualifier("w_regStockService")
  private RegStockService regStockService;
  
  public void setRegStockTypeList(List<String> paramList)
  {
    this.regStockTypeList = paramList;
  }
  
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    RegStock localRegStock = this.regStockService.getRegStockById(localSettleMatch.getRegStockId());
    int j = localRegStock.getType();
    if (!this.regStockTypeList.contains(j + "")) {
      i = -13;
    }
    return i;
  }
}
