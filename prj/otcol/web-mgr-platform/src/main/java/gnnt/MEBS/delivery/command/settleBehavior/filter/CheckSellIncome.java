package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CheckSellIncome
  implements Filtering
{
  private final transient Log logger = LogFactory.getLog(CheckSellIncome.class);
  
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    double d = localSettleMatch.getSellIncome();
    if (d > 0.0D) {
      i = -18;
    }
    return i;
  }
}
