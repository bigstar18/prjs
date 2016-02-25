package gnnt.MEBS.delivery.action.interceptor;

import gnnt.MEBS.delivery.action.BaseController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AddConditionInterceptor
  extends HandlerInterceptorAdapter
{
  private final transient Log logger = LogFactory.getLog(AddConditionInterceptor.class);
  
  public boolean preHandle(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Object paramObject)
    throws Exception
  {
    String str = paramHttpServletRequest.getRequestURI();
    paramHttpServletRequest.setAttribute(BaseController.AttributeForCommodityList + "ability[=]", "0");
    return true;
  }
}
