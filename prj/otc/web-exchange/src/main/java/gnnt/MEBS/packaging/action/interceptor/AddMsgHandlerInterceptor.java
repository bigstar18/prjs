package gnnt.MEBS.packaging.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class AddMsgHandlerInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(AddMsgHandlerInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    if (request.getSession().getAttribute(ActionConstant.RESULTMSG) != null)
    {
      String resultMsg = (String)request.getSession().getAttribute(ActionConstant.RESULTMSG);
      int resultValue = ((Integer)request.getSession().getAttribute(ActionConstant.RESULTVAULE)).intValue();
      this.logger.debug("resultMsg:" + resultMsg);
      this.logger.debug("resultValue:" + resultValue);
      request.setAttribute(ActionConstant.RESULTMSG, resultMsg);
      request.getSession().removeAttribute(ActionConstant.RESULTMSG);
      request.setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(resultValue));
      request.getSession().removeAttribute(ActionConstant.RESULTVAULE);
    }
    String result = invocation.invoke();
    return result;
  }
}
