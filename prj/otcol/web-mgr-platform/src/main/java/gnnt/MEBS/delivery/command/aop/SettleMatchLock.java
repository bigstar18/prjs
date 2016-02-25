package gnnt.MEBS.delivery.command.aop;

import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.SettleMatchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SettleMatchLock
{
  private final transient Log logger = LogFactory.getLog(SettleMatchLock.class);
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  
  public Object around(ProceedingJoinPoint paramProceedingJoinPoint)
    throws Throwable
  {
    Object[] arrayOfObject = paramProceedingJoinPoint.getArgs();
    Information localInformation = (Information)arrayOfObject[0];
    SettleObject localSettleObject = (SettleObject)localInformation.getObject();
    String str = localSettleObject.getMatchId();
    SettleMatch localSettleMatch = this.settleMatchService.getSettleMatchLock(str);
    localSettleObject.setSettleMatch(localSettleMatch);
    Object localObject = paramProceedingJoinPoint.proceed();
    return localObject;
  }
}
