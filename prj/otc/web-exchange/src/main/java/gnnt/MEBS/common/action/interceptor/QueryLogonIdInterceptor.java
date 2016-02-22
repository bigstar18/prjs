package gnnt.MEBS.common.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class QueryLogonIdInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(QueryLogonIdInterceptor.class);
  private String queryCondition;
  
  public void setQueryCondition(String queryCondition)
  {
    this.queryCondition = queryCondition;
  }
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute(ActionConstant.GNNT_ + this.queryCondition, AclCtrl.getUser(request).getId());
    this.logger.debug("userId:" + AclCtrl.getUser(request).getId());
    this.logger.debug("queryCondition:" + this.queryCondition);
    String result = invocation.invoke();
    return result;
  }
}
