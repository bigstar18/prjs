package gnnt.MEBS.query.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.LogConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class GlobalLogQueryInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(GlobalLogQueryInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute(ActionConstant.GNNT_ + "OperatorType[like]", LogConstant.OPERATORTYPE);
    String memberNo = (String)request.getAttribute(ActionConstant.GNNT_ + "primary.s_memberNo[=]");
    request.removeAttribute(ActionConstant.GNNT_ + "primary.s_memberNo[=]");
    request.setAttribute(ActionConstant.GNNT_ + "mark[=]", memberNo);
    String result = invocation.invoke();
    return result;
  }
}
