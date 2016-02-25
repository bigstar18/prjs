package gnnt.MEBS.delivery.command.settleBehavior.behavior;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Behaviour;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.SettleMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ReceivePenalty
  implements Behaviour
{
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  
  public void deal(SettleObject paramSettleObject)
  {
    String str = paramSettleObject.getMatchId();
    SettleMatch localSettleMatch = this.settleMatchService.getSettleMatchLock(str);
    double d1 = paramSettleObject.getAmount();
    boolean bool = paramSettleObject.getSign();
    double d2 = 0.0D;
    if (bool) {
      d2 = localSettleMatch.getBuyMargin();
    } else {
      d2 = localSettleMatch.getSellMargin();
    }
    if (d2 > 0.0D) {
      this.settleMatchService.returnMargin(localSettleMatch, d2, bool);
    }
    this.settleMatchService.receiveOrPayPenalty(localSettleMatch, d1, bool, true);
  }
}
