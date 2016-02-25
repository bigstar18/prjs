package gnnt.MEBS.member.firm.aop;

import gnnt.MEBS.member.firm.services.FirmService;
import gnnt.MEBS.member.firm.unit.FirmLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AddLog
{
  private final transient Log logger = LogFactory.getLog(AddLog.class);
  @Autowired
  @Qualifier("m_firmService")
  private FirmService firmService;
  
  public Object logAround(ProceedingJoinPoint paramProceedingJoinPoint)
    throws Throwable
  {
    this.logger.debug(toString() + "  环绕通知 方法名：" + paramProceedingJoinPoint.getSignature().getName());
    Object localObject1 = paramProceedingJoinPoint.proceed();
    this.logger.debug("retVal:" + localObject1 + "   " + paramProceedingJoinPoint.getSignature().getName());
    if ((localObject1 instanceof Integer))
    {
      int i = ((Integer)localObject1).intValue();
      if (i == 1)
      {
        Object[] arrayOfObject1 = paramProceedingJoinPoint.getArgs();
        for (Object localObject2 : arrayOfObject1) {
          if ((localObject2 instanceof FirmLog))
          {
            FirmLog localFirmLog = (FirmLog)localObject2;
            this.firmService.addFirmLog(localFirmLog);
          }
        }
      }
    }
    this.logger.debug(toString() + " 环绕通知 方法名：" + paramProceedingJoinPoint.getSignature().getName() + " return: " + localObject1.toString());
    return localObject1;
  }
}
