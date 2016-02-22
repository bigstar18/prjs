package gnnt.MEBS.config.filter;

import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RoleFilter
  implements Filter
{
  private FilterConfig filterConfig;
  private String LOGIN_PAGE_URI = "/noSession.jsp";
  private final transient Log logger = LogFactory.getLog(RoleFilter.class);
  
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException
  {
    boolean sign = true;
    ThreadStore.clear();
    ThreadStore.put(ThreadStoreConstant.REQUEST, (HttpServletRequest)req);
    HttpServletRequest request = (HttpServletRequest)req;
    HttpServletResponse response = (HttpServletResponse)res;
    
    String uriStr = request.getRequestURI();
    if (request.getParameter("funcflg") != null) {
      uriStr = uriStr + "?funcflg=" + request.getParameter("funcflg");
    }
    this.logger.debug("uriStr:" + uriStr);
    if ((uriStr.indexOf("/public/") > -1) || (uriStr.indexOf("image.jsp") > -1) || 
      (uriStr.indexOf("logon.jsp") > -1) || (uriStr.indexOf("noSession.jsp") > -1) || 
      (uriStr.indexOf("/common/skinstyle/") > -1) || (uriStr.indexOf("/common/logon.action") > -1))
    {
      this.logger.debug(" enter filter:" + uriStr);
      chain.doFilter(req, res);
      return;
    }
    String currenUserId = (String)request.getSession().getAttribute("CURRENUSERID");
    this.logger.debug("currenUserId:" + currenUserId);
    if (currenUserId == null) {
      sign = false;
    }
    this.logger.debug("sign:" + sign);
    if (sign) {
      chain.doFilter(req, res);
    } else {
      response.sendRedirect(request.getContextPath() + this.LOGIN_PAGE_URI);
    }
  }
  
  public void init(FilterConfig filterconfig)
    throws ServletException
  {
    this.filterConfig = this.filterConfig;
  }
  
  public void destroy() {}
}
