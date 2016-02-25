package gnnt.MEBS.delivery.command.settleBehavior.behavior;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Behaviour;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.SettleMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MarginTurnGoodsPayment
  implements Behaviour
{
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  
  public void deal(SettleObject paramSettleObject)
  {
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    double d = paramSettleObject.getAmount();
    this.settleMatchService.returnMargin(localSettleMatch, d, true);
    this.settleMatchService.inoutSettleMoney(localSettleMatch, d, true);
  }
}
