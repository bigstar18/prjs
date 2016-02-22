package gnnt.MEBS.account.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import java.io.PrintStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class QueryWXCustomerInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(QueryWXCustomerInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter queryCustomerInterceptor");
    HttpServletRequest request = ServletActionContext.getRequest();
    String memberIds = request.getParameter(ActionConstant.GNNT_ + "memberIds");
    System.out.println("memberIds >>>>>>>>>>>>>" + memberIds);
    if ((memberIds != null) && (memberIds.length() > 0)) {
      request.setAttribute(ActionConstant.GNNT_ + "primary.memberno[in]", "(" + memberIds + ")");
    }
    this.logger.debug("sqlString:" + memberIds);
    String result = invocation.invoke();
    return result;
  }
}
