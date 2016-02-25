package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.MoneyDoService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CheckReceivePenalty
  implements Filtering
{
  @Autowired
  @Qualifier("w_moneyDoService")
  private MoneyDoService moneyDoService;
  
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    double d1 = paramSettleObject.getAmount();
    boolean bool = paramSettleObject.getSign();
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    int j = localSettleMatch.getResult();
    if ((j == 2) && (!bool))
    {
      i = -2;
    }
    else if ((j == 3) && (bool))
    {
      i = -2;
    }
    else
    {
      String str = null;
      double d2 = 0.0D;
      if ((j == 2) || ((j == 4) && (bool)))
      {
        str = localSettleMatch.getFirmID_B();
        d2 = new BigDecimal(d1).subtract(new BigDecimal(localSettleMatch.getBuyMargin())).doubleValue();
      }
      else if ((j == 3) || ((j == 4) && (!bool)))
      {
        str = localSettleMatch.getFirmID_S();
        d2 = new BigDecimal(d1).subtract(new BigDecimal(localSettleMatch.getSellMargin())).doubleValue();
      }
      if (!this.moneyDoService.checkFirmFunds(str, d2)) {
        i = -14;
      }
    }
    return i;
  }
}
