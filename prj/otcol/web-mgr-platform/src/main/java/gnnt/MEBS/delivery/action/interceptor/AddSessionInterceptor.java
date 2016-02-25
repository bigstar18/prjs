package gnnt.MEBS.delivery.action.interceptor;

import gnnt.MEBS.common.security.AclCtrl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AddSessionInterceptor
  extends HandlerInterceptorAdapter
{
  private final transient Log logger = LogFactory.getLog(AddSessionInterceptor.class);
  
  public boolean preHandle(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Object paramObject)
    throws Exception
  {
    if (paramHttpServletRequest.getSession().getAttribute("logonUser") == null)
    {
      String str = AclCtrl.getLogonID(paramHttpServletRequest);
      paramHttpServletRequest.getSession().setAttribute("logonUser", str);
    }
    return true;
  }
}
