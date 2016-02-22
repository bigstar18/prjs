package gnnt.MEBS.report.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class OrderDefaultReportInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(OrderDefaultReportInterceptor.class);
  private String sortString;
  
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
    if ((queryType == null) || (queryType == "D"))
    {
      queryType = "D";
      orderString = this.sortString;
    }
    request.setAttribute("orderString", orderString);
    String result = invocation.invoke();
    return result;
  }
}
