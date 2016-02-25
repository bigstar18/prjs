package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;

public class CheckMarginForSell
  implements Filtering
{
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    double d1 = paramSettleObject.getAmount();
    double d2 = localSettleMatch.getSellMargin();
    if (Arith.compareTo(d1, d2) > 0) {
      i = -3;
    }
    return i;
  }
}
