package gnnt.MEBS.bankadded.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class QueryBankInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(QueryBankInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute(ActionConstant.GNNT_ + "primary.bankCode[=]", request.getParameter("original_bankCode"));
    request.setAttribute(ActionConstant.GNNT_ + "primary.transType[=]", "F");
    String result = invocation.invoke();
    return result;
  }
}
