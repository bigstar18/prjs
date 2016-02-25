package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.SettleMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CheckSettleInvoice
  implements Filtering
{
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    String str = paramSettleObject.getMatchId();
    SettleMatch localSettleMatch = this.settleMatchService.getSettleMatchLock(str);
    int j = localSettleMatch.getRecvInvoice();
    if (j != 0) {
      i = -10;
    }
    return i;
  }
}
