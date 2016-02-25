package gnnt.MEBS.delivery.command.aop;

import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.model.LogValue;
import gnnt.MEBS.delivery.services.LogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AddLog
{
  private final transient Log logger = LogFactory.getLog(AddLog.class);
  @Autowired
  @Qualifier("w_logService")
  private LogService logService;
  
  public Object logAround(ProceedingJoinPoint paramProceedingJoinPoint)
    throws Throwable
  {
    Object localObject = paramProceedingJoinPoint.proceed();
    int i = ((Integer)localObject).intValue();
    this.logger.debug("result:" + i);
    if (i == 1)
    {
      Object[] arrayOfObject = paramProceedingJoinPoint.getArgs();
      Information localInformation = (Information)arrayOfObject[0];
      LogValue localLogValue = localInformation.getLogValue();
      if (localLogValue != null) {
        this.logService.addLog(localLogValue);
      }
    }
    return localObject;
  }
}
