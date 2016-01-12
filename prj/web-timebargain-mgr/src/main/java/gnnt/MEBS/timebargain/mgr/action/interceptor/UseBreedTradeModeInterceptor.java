package gnnt.MEBS.timebargain.mgr.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

public class UseBreedTradeModeInterceptor extends AbstractInterceptor
{
  public String intercept(ActionInvocation paramActionInvocation)
    throws Exception
  {
    HttpServletRequest localHttpServletRequest = ServletActionContext.getRequest();
    String str = ServletActionContext.getServletContext().getInitParameter("useBreedTradeMode");
    localHttpServletRequest.setAttribute("useBreedTradeMode", str);
    return paramActionInvocation.invoke();
  }
}