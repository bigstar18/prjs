package gnnt.MEBS.packaging.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class QueryLogonInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(QueryLogonInterceptor.class);
  private String queryCondition;
  
  public void setQueryCondition(String queryCondition)
  {
    this.queryCondition = queryCondition;
  }
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute(ActionConstant.GNNT_ + this.queryCondition, request.getSession().getAttribute("username"));
    this.logger.debug("userId:" + request.getSession().getAttribute("username"));
    this.logger.debug("queryCondition:" + this.queryCondition);
    String result = invocation.invoke();
    return result;
  }
}
