package gnnt.MEBS.commodity.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class OrderDefaultCommodityInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(OrderDefaultCommodityInterceptor.class);
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
    request.setAttribute("sortString", this.sortString);
    String result = invocation.invoke();
    return result;
  }
}
