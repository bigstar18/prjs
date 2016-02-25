package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;

public class CheckSettleHL
  implements Filtering
{
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    double d1 = localSettleMatch.getSellIncome_Ref();
    double d2 = localSettleMatch.getHL_Amount();
    double d3 = paramSettleObject.getAmount();
    double d4 = localSettleMatch.getSellIncome();
    if (d1 + d2 + d3 < d4) {
      i = -7;
    }
    return i;
  }
}
