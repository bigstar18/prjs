package gnnt.MEBS.delivery.workflow.aop;

import gnnt.MEBS.delivery.model.OperateLog;
import gnnt.MEBS.delivery.services.OperateLogService;
import gnnt.MEBS.delivery.workflow.OriginalModel;
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
    Object localObject = paramProceedingJoinPoint.proceed();
    this.logger.debug(toString() + " 环绕通知 方法名：" + paramProceedingJoinPoint.getSignature().getName() + " return: " + localObject.toString());
    int i = ((Integer)localObject).intValue();
    this.logger.debug("result:" + i);
    if (i == 1)
    {
      Object[] arrayOfObject = paramProceedingJoinPoint.getArgs();
      OriginalModel localOriginalModel = (OriginalModel)arrayOfObject[0];
      OperateLog localOperateLog = localOriginalModel.getLog();
      if (localOperateLog != null) {
        this.operateLogService.addOprLog(localOperateLog);
      }
    }
    return localObject;
  }
}
