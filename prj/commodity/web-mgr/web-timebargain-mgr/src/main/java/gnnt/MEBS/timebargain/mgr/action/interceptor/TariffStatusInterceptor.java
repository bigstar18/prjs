package gnnt.MEBS.timebargain.mgr.action.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import javax.servlet.http.HttpServletRequest;

public class TariffStatusInterceptor extends AbstractInterceptor
{
  public String intercept(ActionInvocation paramActionInvocation)
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    localHttpServletRequest.setAttribute("gnnt_primary.status", Integer.valueOf(0));
    return paramActionInvocation.invoke();
  }
}