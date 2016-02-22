package gnnt.MEBS.trade.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.AnnouncementConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class OkNoticesQueryInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(OkNoticesQueryInterceptor.class);
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    this.logger.debug("enter   CustomerStatusQueryInterceptor");
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute(ActionConstant.GNNT_ + "okNotice.recipient[=]", AclCtrl.getUser(request).getId());
    request.setAttribute(ActionConstant.GNNT_ + "okNotice.recipientType[=]", AnnouncementConstant.EXCHANGE_ADMINISTRATOR);
    String result = invocation.invoke();
    return result;
  }
}
