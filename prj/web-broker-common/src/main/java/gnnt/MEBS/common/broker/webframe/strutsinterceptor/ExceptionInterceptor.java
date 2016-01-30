package gnnt.MEBS.common.broker.webframe.strutsinterceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.common.broker.action.StandardAction;
import gnnt.MEBS.common.broker.common.ReturnValue;
import gnnt.MEBS.common.broker.model.OperateLog;
import gnnt.MEBS.common.broker.service.StandardService;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class ExceptionInterceptor extends AbstractInterceptor
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
      localException.printStackTrace();
      str = "SysException";
      ReturnValue localReturnValue = new ReturnValue();
      localReturnValue.setResult(-1);
      localReturnValue.addInfo(139904L);
      localHttpServletRequest.setAttribute("ReturnValue", localReturnValue);
      this.logger.error(localReturnValue.getInfo() + ";详细信息：" + localException);
      OperateLog localOperateLog = (OperateLog)localHttpServletRequest.getAttribute("operateLog");
      if (localHttpServletRequest.getAttribute("operateLog") != null)
      {
        localOperateLog.setOperateResult(Integer.valueOf(0));
        localOperateLog.setMark("失败原因:" + localException.getMessage());
        StandardAction localStandardAction = (StandardAction)paramActionInvocation.getAction();
        StandardService localStandardService = localStandardAction.getService();
        localStandardService.add(localOperateLog);
      }
    }
    return str;
  }
}