package gnnt.MEBS.delivery.command.aop;

import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.SettleReceive;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleExtra.SettleBalanceCheck;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.MoneyDoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CheckRealFunds
{
  private final transient Log logger = LogFactory.getLog(CheckRealFunds.class);
  @Autowired
  @Qualifier("w_moneyDoService")
  private MoneyDoService moneyDoService;
  
  public Object around(ProceedingJoinPoint paramProceedingJoinPoint)
    throws Throwable
  {
    this.logger.debug("进入交易商资金检查");
    SettleReceive localSettleReceive = (SettleReceive)paramProceedingJoinPoint.getTarget();
    int i = 0;
    Object localObject = null;
    SettleBalanceCheck localSettleBalanceCheck = localSettleReceive.getSettleBalanceCheck();
    if (localSettleBalanceCheck.isBalance())
    {
      Object[] arrayOfObject = paramProceedingJoinPoint.getArgs();
      Information localInformation = (Information)arrayOfObject[0];
      SettleObject localSettleObject = (SettleObject)localInformation.getObject();
      SettleMatch localSettleMatch = localSettleObject.getSettleMatch();
      double d1 = localSettleObject.getAmount();
      String str = "";
      if ("B".equals(localSettleBalanceCheck.getBuyOrSell())) {
        str = localSettleMatch.getFirmID_B();
      } else {
        str = localSettleMatch.getFirmID_S();
      }
      if (localSettleBalanceCheck.isDeduction()) {
        d1 = -d1;
      }
      double d2 = this.moneyDoService.getFirmFunds(str);
      if (Arith.sub(d2, d1) < 0.0D) {
        i = -14;
      }
    }
    if (i == 0) {
      localObject = paramProceedingJoinPoint.proceed();
    } else {
      localObject = Integer.valueOf(i);
    }
    return localObject;
  }
}
