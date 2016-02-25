package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CheckSettleFinshForCompliance
  implements Filtering
{
  private final transient Log logger = LogFactory.getLog(CheckSettleFinshForCompliance.class);
  
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    double d1 = localSettleMatch.getSellIncome_Ref();
    double d2 = localSettleMatch.getBuyPayout_Ref();
    double d3 = d1 - d2;
    double d4 = localSettleMatch.getBuyPayout();
    double d5 = localSettleMatch.getSellIncome();
    double d6 = localSettleMatch.getHL_Amount();
    if (Arith.compareTo(Arith.format(Arith.add(d1, d6), 2), Arith.format(d5, 2)) != 0) {
      i = -8;
    } else if (Arith.compareTo(Arith.format(d5, 2), Arith.format(Arith.add(Arith.add(d2, d6), d3), 2)) != 0) {
      i = -9;
    } else if (Arith.compareTo(Arith.format(Arith.add(d4, d3), 2), Arith.format(d5, 2)) < 0) {
      i = -9;
    }
    return i;
  }
}
