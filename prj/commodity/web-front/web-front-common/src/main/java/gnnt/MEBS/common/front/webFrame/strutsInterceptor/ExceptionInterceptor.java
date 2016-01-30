package gnnt.MEBS.common.front.webFrame.strutsInterceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.common.front.action.StandardAction;
import gnnt.MEBS.common.front.common.ReturnValue;
import gnnt.MEBS.common.front.model.OperateLog;
import gnnt.MEBS.common.front.service.StandardService;
import gnnt.MEBS.common.front.statictools.Tools;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class ExceptionInterceptor
  extends AbstractInterceptor
{
  private static final long serialVersionUID = 6390831317255258653L;
  private final transient Log logger = LogFactory.getLog(ExceptionInterceptor.class);
  
  public String intercept(ActionInvocation paramActionInvocation)
    throws Exception
  {
    this.logger.debug("ExceptionInterceptor enter");
    HttpServletRequest localHttpServletRequest = ServletActionContext.getRequest();
    String str = "";
    try
    {
      str = paramActionInvocation.invoke();
    }
    catch (Exception localException)
    {
      str = "SysException";
      ReturnValue localReturnValue = new ReturnValue();
      localReturnValue.setResult(-1);
      localReturnValue.addInfo(9930091L);
      localHttpServletRequest.setAttribute("ReturnValue", localReturnValue);
      this.logger.error(localReturnValue.getInfo() + ";详细信息：" + Tools.getExceptionTrace(localException));
      OperateLog localOperateLog = (OperateLog)localHttpServletRequest.getAttribute("operateLog");
      if (localHttpServletRequest.getAttribute("operateLog") != null)
      {
        localOperateLog.setOperateResult(Integer.valueOf(0));
        localOperateLog.setLogType(Integer.valueOf(2));
        localOperateLog.setMark("失败原因:" + localException.getMessage());
        StandardAction localStandardAction = (StandardAction)paramActionInvocation.getAction();
        StandardService localStandardService = localStandardAction.getService();
        localStandardService.add(localOperateLog);
      }
    }
    return str;
  }
}
