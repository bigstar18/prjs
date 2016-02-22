package gnnt.MEBS.query.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class OrderDefaultQueryInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(OrderDefaultQueryInterceptor.class);
  private String sortString;
  private String sortHString;
  
  public String getSortHString()
  {
    return this.sortHString;
  }
  
  public void setSortHString(String sortHString)
  {
    this.sortHString = sortHString;
  }
  
  public String getSortString()
  {
    return this.sortString;
  }
  
  public void setSortString(String sortString)
  {
    this.sortString = sortString;
  }
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    String queryType = request.getParameter("queryType");
    String orderString = "";
    if ((queryType == null) || ("D".equals(queryType)))
    {
      queryType = "D";
      orderString = this.sortString;
    }
    else if ("H".equals(queryType))
    {
      orderString = this.sortHString;
    }
    request.setAttribute("orderString", orderString);
    String result = invocation.invoke();
    return result;
  }
}
