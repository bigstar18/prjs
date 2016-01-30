package gnnt.MEBS.broker.mgr.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

public class CrudTypeInterceptor extends AbstractInterceptor
{
  private String crud;

  public void setCrud(String paramString)
  {
    this.crud = paramString;
  }

  public String intercept(ActionInvocation paramActionInvocation)
    throws Exception
  {
    HttpServletRequest localHttpServletRequest = ServletActionContext.getRequest();
    localHttpServletRequest.setAttribute("crud", this.crud);
    String str = paramActionInvocation.invoke();
    return str;
  }
}