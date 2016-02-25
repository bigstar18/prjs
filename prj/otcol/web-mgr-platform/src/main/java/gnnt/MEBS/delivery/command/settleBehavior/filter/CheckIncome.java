package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;

public class CheckIncome
  implements Filtering
{
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    double d1 = localSettleMatch.getSellIncome_Ref();
    double d2 = localSettleMatch.getBuyPayout_Ref();
    double d3 = d1 - d2;
    double d4 = localSettleMatch.getBuyPayout();
    double d5 = localSettleMatch.getSellIncome();
    double d6 = paramSettleObject.getAmount();
    if ((Arith.add(d4, d3) < Arith.add(d5, d6)) || (Arith.add(d5, d6) < 0.0D)) {
      i = -3;
    }
    return i;
  }
}
