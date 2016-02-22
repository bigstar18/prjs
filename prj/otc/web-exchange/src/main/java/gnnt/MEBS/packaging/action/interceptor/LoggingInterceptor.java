package gnnt.MEBS.packaging.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggingInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(LoggingInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    String result = invocation.invoke();
    

























































    return result;
  }
}
