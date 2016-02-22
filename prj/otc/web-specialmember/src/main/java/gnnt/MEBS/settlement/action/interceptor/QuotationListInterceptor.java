package gnnt.MEBS.settlement.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.common.service.UserService;
import gnnt.MEBS.config.constant.ActionConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class QuotationListInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(QuotationListInterceptor.class);
  @Autowired
  @Qualifier("userService")
  private UserService userService;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute(ActionConstant.GNNT_ + "quotepoint.m_FirmId[=]", ((User)this.userService.getById(AclCtrl.getLogonID(request))).getMemberNo());
    String result = invocation.invoke();
    return result;
  }
}
