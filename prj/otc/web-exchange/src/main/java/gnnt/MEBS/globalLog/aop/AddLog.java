package gnnt.MEBS.globalLog.aop;

import gnnt.MEBS.base.util.SpringContextHelper;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.config.constant.LogConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.ExecuteObject;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.globalLog.util.Compare;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

public class AddLog
{
  private final transient Log logger = LogFactory.getLog(AddLog.class);
  
  public Object logAround(ProceedingJoinPoint pjp)
    throws Throwable
  {
    this.logger.debug("方法之前");
    this.logger.debug(pjp.getSignature().getName());
    OperateLog operateLog = (OperateLog)ThreadStore.get(ThreadStoreConstant.OPERATELOGFORLOG);
    String operate = (String)ThreadStore.get(ThreadStoreConstant.OPERATE);
    Object retVal = pjp.proceed();
    this.logger.debug("operateLog" + operateLog);
    this.logger.debug("retVal" + ((Integer)retVal).toString());
    this.logger.debug("operateLog" + operateLog);
    if ((operateLog != null) && (((Integer)retVal).intValue() >= 1) && (ThreadStore.get(ThreadStoreConstant.ISLOG) != null) && 
      (((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue() > 0))
    {
      Object obj = operateLog.getObj();
      
      this.logger.debug("operateLog.getOperateContent():" + operateLog.getOperateContent());
      if (operateLog.getOperateContent() == null)
      {
        ExecuteObject executeObject = Compare.getDifferent(null, obj);
        this.logger.debug("executeObject.getPropertyList().size():" + executeObject.getPropertyList().size());
        String description = Compare.translate(executeObject);
        this.logger.debug("description:" + description);
        operateLog.setOperateContent(description);
      }
      operateLog.setOperateDate(new Date());
      operateLog.setOperateIp((String)ThreadStore.get(ThreadStoreConstant.OPERATEIP));
      operateLog.setOperateType((String)ThreadStore.get(ThreadStoreConstant.OPERATE));
      operateLog.setOperatorType(LogConstant.OPERATORTYPE);
      OperateLogService operateLogService = (OperateLogService)SpringContextHelper.getBean("globalLogService");
      operateLogService.add(operateLog);
    }
    this.logger.debug("方法之后");
    return retVal;
  }
}
