package gnnt.MEBS.delivery.command.settleBehavior.behavior;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Behaviour;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.SettleMatchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ReturnMarginAll
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(ReturnMarginAll.class);
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  
  public void deal(SettleObject paramSettleObject)
  {
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    double d1 = localSettleMatch.getSellIncome_Ref();
    double d2 = localSettleMatch.getBuyPayout_Ref();
    double d3 = d1 - d2;
    double d4 = localSettleMatch.getBuyPayout();
    double d5 = localSettleMatch.getSellIncome();
    double d6 = localSettleMatch.getBuyMargin();
    double d7 = localSettleMatch.getHL_Amount();
    double d8 = localSettleMatch.getSellMargin();
    this.logger.debug("buyMargin:" + d6);
    if (d6 > 0.0D) {
      this.settleMatchService.returnMargin(localSettleMatch, d6, true);
    }
    this.logger.debug("sellMargin:" + d8);
    if (d8 > 0.0D) {
      this.settleMatchService.returnMargin(localSettleMatch, d8, false);
    }
  }
}
