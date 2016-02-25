package gnnt.MEBS.delivery.aop;

import gnnt.MEBS.delivery.model.OperateLog;
import gnnt.MEBS.delivery.services.OperateLogService;
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
  @Qualifier("w_operateLogService")
  private OperateLogService operateLogService;
  
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
        this.logger.debug("args:" + arrayOfObject1.length);
        if (arrayOfObject1.length > 1) {
          for (Object localObject2 : arrayOfObject1) {
            if ((localObject2 instanceof OperateLog))
            {
              OperateLog localOperateLog = (OperateLog)localObject2;
              try
              {
                this.operateLogService.addOprLog(localOperateLog);
              }
              catch (Exception localException)
              {
                this.logger.error("add operateLog error");
              }
            }
          }
        }
      }
    }
    this.logger.debug(toString() + " 环绕通知 方法名：" + paramProceedingJoinPoint.getSignature().getName() + " return: " + localObject1.toString());
    return localObject1;
  }
}
