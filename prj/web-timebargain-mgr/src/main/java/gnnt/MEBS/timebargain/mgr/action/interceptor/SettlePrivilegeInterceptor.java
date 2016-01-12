package gnnt.MEBS.timebargain.mgr.action.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.timebargain.mgr.statictools.CommonDictionary;
import javax.servlet.http.HttpServletRequest;

public class SettlePrivilegeInterceptor extends AbstractInterceptor
{
  public String intercept(ActionInvocation paramActionInvocation)
    throws Exception
  {
    ActionContext localActionContext = paramActionInvocation.getInvocationContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    localHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
    localHttpServletRequest.setAttribute("DELAYORDERTYPE", CommonDictionary.DELAYORDERTYPE);
    localHttpServletRequest.setAttribute("PRIVILEGECODE_B", CommonDictionary.PRIVILEGECODE_B);
    localHttpServletRequest.setAttribute("PRIVILEGECODE_S", CommonDictionary.PRIVILEGECODE_S);
    String str = paramActionInvocation.invoke();
    return str;
  }
}