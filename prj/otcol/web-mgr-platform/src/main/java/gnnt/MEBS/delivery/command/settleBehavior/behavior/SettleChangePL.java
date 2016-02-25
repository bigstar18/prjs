package gnnt.MEBS.delivery.command.settleBehavior.behavior;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Behaviour;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.SettleMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SettleChangePL
  implements Behaviour
{
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  
  public void deal(SettleObject paramSettleObject)
  {
    String str = paramSettleObject.getMatchId();
    SettleMatch localSettleMatch = this.settleMatchService.getSettleMatchLock(str);
    boolean bool = paramSettleObject.getSign();
    double d = paramSettleObject.getAmount();
    this.settleMatchService.addSettlePL(localSettleMatch, d, bool);
  }
}
