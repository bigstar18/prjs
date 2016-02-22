package gnnt.MEBS.report.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class BrokerageQueryInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(BrokerageQueryInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute(ActionConstant.GNNT_ + "primary.brokerageno[>][String]", "0");
    String result = invocation.invoke();
    return result;
  }
}
