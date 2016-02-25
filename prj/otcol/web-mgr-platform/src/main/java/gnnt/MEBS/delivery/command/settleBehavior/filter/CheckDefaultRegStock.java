package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CheckDefaultRegStock
  implements Filtering
{
  private final transient Log logger = LogFactory.getLog(CheckDefaultRegStock.class);
  
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    String str = paramSettleObject.getChangeRegStockId();
    if (str != null) {
      i = -17;
    }
    return i;
  }
}
