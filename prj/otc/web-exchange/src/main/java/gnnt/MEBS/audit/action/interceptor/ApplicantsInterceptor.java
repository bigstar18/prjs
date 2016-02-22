package gnnt.MEBS.audit.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class ApplicantsInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(ApplicantsInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute(ActionConstant.GNNT_ + "proposer[=]", AclCtrl.getLogonID(request));
    this.logger.debug("queryCondition:" + ActionConstant.GNNT_ + "proposer");
    this.logger.debug("queryWord:" + AclCtrl.getLogonID(request));
    String result = invocation.invoke();
    return result;
  }
}
