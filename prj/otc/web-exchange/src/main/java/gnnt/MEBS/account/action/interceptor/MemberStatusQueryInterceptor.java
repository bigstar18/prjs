package gnnt.MEBS.account.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class MemberStatusQueryInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(MemberStatusQueryInterceptor.class);
  private String status;
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter   CustomerStatusQueryInterceptor");
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute(ActionConstant.GNNT_ + "compMember.status[in]", this.status);
    String result = invocation.invoke();
    return result;
  }
}
