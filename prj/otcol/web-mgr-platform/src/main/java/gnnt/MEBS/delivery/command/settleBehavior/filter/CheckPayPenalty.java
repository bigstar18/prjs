package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.MoneyDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CheckPayPenalty
  implements Filtering
{
  @Autowired
  @Qualifier("w_moneyDoService")
  private MoneyDoService moneyDoService;
  
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    double d = paramSettleObject.getAmount();
    boolean bool = paramSettleObject.getSign();
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    int j = localSettleMatch.getResult();
    if ((j == 2) && (bool))
    {
      i = -2;
    }
    else if ((j == 3) && (!bool))
    {
      i = -2;
    }
    else if (j == 4)
    {
      i = -2;
    }
    else
    {
      String str = null;
      if (j == 2) {
        str = localSettleMatch.getFirmID_S();
      } else if (j == 3) {
        str = localSettleMatch.getFirmID_B();
      }
      if (!this.moneyDoService.checkFirmFunds(str, -d)) {
        i = -14;
      }
    }
    return i;
  }
}
