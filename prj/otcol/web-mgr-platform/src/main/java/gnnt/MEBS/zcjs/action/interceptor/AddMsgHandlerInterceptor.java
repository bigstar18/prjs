package gnnt.MEBS.zcjs.action.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AddMsgHandlerInterceptor
  extends HandlerInterceptorAdapter
{
  private final transient Log logger = LogFactory.getLog(AddMsgHandlerInterceptor.class);
  
  public boolean preHandle(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Object paramObject)
    throws Exception
  {
    this.logger.debug("AddMsgHandlerInterceptor");
    if (paramHttpServletRequest.getSession().getAttribute("resultMsg") != null)
    {
      String str = (String)paramHttpServletRequest.getSession().getAttribute("resultMsg");
      this.logger.debug("resultMsg:" + str);
      paramHttpServletRequest.setAttribute("resultMsg", str);
      paramHttpServletRequest.getSession().removeAttribute("resultMsg");
    }
    return true;
  }
}
