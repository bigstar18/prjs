package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.MoneyDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CheckSettleChangePL
  implements Filtering
{
  @Autowired
  @Qualifier("w_moneyDoService")
  private MoneyDoService moneyDoService;
  
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    boolean bool = paramSettleObject.getSign();
    double d = paramSettleObject.getAmount();
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    String str = localSettleMatch.getFirmID_B();
    if (!bool) {
      str = localSettleMatch.getFirmID_S();
    }
    if (!this.moneyDoService.checkFirmFunds(str, -d)) {
      i = -14;
    }
    return i;
  }
}
