package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.SettleMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CheckTakePenalty
  implements Filtering
{
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    String str = paramSettleObject.getMatchId();
    double d1 = paramSettleObject.getAmount();
    boolean bool = paramSettleObject.getSign();
    SettleMatch localSettleMatch = this.settleMatchService.getSettleMatchLock(str);
    double d2 = 0.0D;
    if (bool) {
      d2 = localSettleMatch.getPenalty_B();
    } else {
      d2 = localSettleMatch.getPenalty_S();
    }
    d2 += d1;
    if (d2 < 0.0D) {
      i = -5;
    }
    return i;
  }
}
