package gnnt.MEBS.audit.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

public class ChangeResultInterceptor
  extends AbstractInterceptor
{
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute("type", "applyAndAudit");
    
    return invocation.invoke();
  }
}
