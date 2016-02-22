package gnnt.MEBS.account.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.base.util.ThreadStore;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CloseThreadStoreCommonInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(CloseThreadStoreCommonInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter   ThreadStoreCommonInterceptor");
    ThreadStore.clear();
    String result = invocation.invoke();
    return result;
  }
}
