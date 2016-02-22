package gnnt.MEBS.packaging.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class ForEcsideListInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(ForEcsideListInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    String type = "page";
    String fileName = "";
    HttpServletRequest request = ServletActionContext.getRequest();
    if ((request.getParameter("ec_ev") != null) && (!"".equals(request.getParameter("ec_ev")))) {
      type = request.getParameter("ec_ev");
    }
    if ((request.getParameter("ec_efn") != null) && (!"".equals(request.getParameter("ec_efn")))) {
      fileName = request.getParameter("ec_efn");
    }
    request.setAttribute("fileName", fileName);
    request.setAttribute("type", type);
    String result = invocation.invoke();
    return result;
  }
}
