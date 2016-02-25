package gnnt.MEBS.delivery.command.settleBehavior.behavior;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Behaviour;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.SettleMatchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Payout
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(Payout.class);
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  
  public void deal(SettleObject paramSettleObject)
  {
    String str = paramSettleObject.getMatchId();
    double d = paramSettleObject.getAmount();
    SettleMatch localSettleMatch = this.settleMatchService.getSettleMatchLock(str);
    this.settleMatchService.inoutSettleMoney(localSettleMatch, d, true);
  }
}
