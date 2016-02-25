package gnnt.MEBS.delivery.command.aop;

import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.SettleReceive;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleExtra.SettleLegalExamine;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;

public class CheckSettleLegal
{
  private final transient Log logger = LogFactory.getLog(CheckSettleLegal.class);
  
  public Object around(ProceedingJoinPoint paramProceedingJoinPoint)
    throws Throwable
  {
    this.logger.debug("进入合法性检查");
    SettleReceive localSettleReceive = (SettleReceive)paramProceedingJoinPoint.getTarget();
    Object localObject = null;
    int i = 0;
    SettleLegalExamine localSettleLegalExamine = localSettleReceive.getSettleLegalExamine();
    if (localSettleLegalExamine != null)
    {
      Object[] arrayOfObject = paramProceedingJoinPoint.getArgs();
      Information localInformation = (Information)arrayOfObject[0];
      SettleObject localSettleObject = (SettleObject)localInformation.getObject();
      SettleMatch localSettleMatch = localSettleObject.getSettleMatch();
      int j = localSettleMatch.getResult();
      int k = localSettleMatch.getStatus();
      if (!localSettleLegalExamine.getStatusList().contains(Integer.valueOf(k))) {
        i = -1;
      } else if (!localSettleLegalExamine.getTypeList().contains(Integer.valueOf(j))) {
        i = -12;
      } else if ((localSettleLegalExamine.isCheckRegStock()) && (localSettleMatch.getRegStockId() == null)) {
        i = -13;
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
