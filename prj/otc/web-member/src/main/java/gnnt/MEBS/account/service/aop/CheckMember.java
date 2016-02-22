package gnnt.MEBS.account.service.aop;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.util.CloneParameterValue;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

public class CheckMember
{
  private final transient Log logger = LogFactory.getLog(CheckMember.class);
  
  public Object around(ProceedingJoinPoint pjp)
    throws Throwable
  {
    this.logger.debug("enter around   " + pjp.getSignature().getName());
    Object retVal = pjp.proceed();
    if ((ThreadStore.get(ThreadStoreConstant.MEMBERNO) != null) && (ThreadStore.get(ThreadStoreConstant.INACTION) == null))
    {
      String memberNo = (String)ThreadStore.get(ThreadStoreConstant.MEMBERNO);
      this.logger.debug("memberNo:" + memberNo);
      if (retVal != null)
      {
        Clone clone = (Clone)retVal;
        if (CloneParameterValue.judgeParameter(clone, "memberNo"))
        {
          Object value = CloneParameterValue.getParameter(clone, "memberNo");
          this.logger.debug("value:" + value);
          if ((value == null) || (!memberNo.equals(value.toString()))) {
            throw new Exception("illegality operate");
          }
          this.logger.debug("检查无误");
        }
        else
        {
          this.logger.debug("无参数");
        }
      }
    }
    return retVal;
  }
}
