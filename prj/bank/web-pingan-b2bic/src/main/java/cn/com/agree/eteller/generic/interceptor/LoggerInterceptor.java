package cn.com.agree.eteller.generic.interceptor;

import cn.com.agree.eteller.generic.action.GenericAction;
import cn.com.agree.eteller.generic.vo.LoginUser;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class LoggerInterceptor
  extends AbstractInterceptor
{
  private static final long serialVersionUID = 1777316199384840567L;
  private static final Logger logger = Logger.getLogger(GenericAction.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    String result = "error";
    
    LoginUser user = (LoginUser)ServletActionContext.getRequest().getSession().getAttribute("user");
    String actionName = invocation.getProxy().getActionName();
    String actionMethod = invocation.getProxy().getMethod();
    
    logger.debug("用户: " + user.getUsername() + "  开始执行  Action: " + actionName + " 中的 " + actionMethod + " 方法");
    try
    {
      result = invocation.invoke();
      logger.debug("用户: " + user.getUsername() + "  结束执行  Action: " + actionName + " 中的 " + actionMethod + " 方法");
    }
    catch (Exception e)
    {
      logger.error(ExceptionUtils.getStackTrace(e));
      e.printStackTrace();
    }
    return result;
  }
}
