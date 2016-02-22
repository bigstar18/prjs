package gnnt.MEBS.query.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class FundOutInFirmTypeQueryInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(FundOutInFirmTypeQueryInterceptor.class);
  private String type;
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter   FundOutInFirmTypeQueryInterceptor");
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute(ActionConstant.GNNT_ + "primary.firmType[=][String]", this.type);
    String result = invocation.invoke();
    return result;
  }
}
