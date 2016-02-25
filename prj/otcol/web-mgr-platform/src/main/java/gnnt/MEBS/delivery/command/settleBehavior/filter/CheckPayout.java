package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;

public class CheckPayout
  implements Filtering
{
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    double d1 = paramSettleObject.getAmount();
    double d2 = localSettleMatch.getSellIncome_Ref();
    double d3 = localSettleMatch.getBuyPayout_Ref();
    double d4 = d2 - d3;
    double d5 = localSettleMatch.getBuyPayout();
    double d6 = localSettleMatch.getSellIncome();
    if ((Arith.add(Arith.add(d5, d1), d4) < Arith.format(d6, 2)) || (Arith.add(d5, d1) < 0.0D)) {
      i = -3;
    }
    return i;
  }
}
