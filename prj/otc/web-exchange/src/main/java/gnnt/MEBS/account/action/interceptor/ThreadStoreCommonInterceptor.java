package gnnt.MEBS.account.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class ThreadStoreCommonInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(ThreadStoreCommonInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter   ThreadStoreCommonInterceptor");
    HttpServletRequest request = ServletActionContext.getRequest();
    ThreadStore.put(ThreadStoreConstant.AUTHOR, AclCtrl.getLogonID(request));
    String result = invocation.invoke();
    return result;
  }
}
