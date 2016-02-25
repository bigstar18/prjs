package gnnt.MEBS.delivery.command.settleBehavior.behavior;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Behaviour;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.SettleMatchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ReturnPayForDefault
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(ReturnPayForDefault.class);
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  
  public void deal(SettleObject paramSettleObject)
  {
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    double d1 = localSettleMatch.getBuyPayout();
    if (d1 != 0.0D)
    {
      double d2 = -d1;
      this.settleMatchService.inoutSettleMoney(localSettleMatch, d2, true);
    }
  }
}
