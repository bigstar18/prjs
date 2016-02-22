package gnnt.MEBS.query.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class firmTypeQueryInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(firmTypeQueryInterceptor.class);
  private String type;
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter   firmTypeQueryInterceptor");
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute(ActionConstant.GNNT_ + "firm.firmType[=][String]", this.type);
    String result = invocation.invoke();
    return result;
  }
}
