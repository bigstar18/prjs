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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CheckUrlFilter
  implements Filter
{
  private FilterConfig filterConfig;
  private String LOGIN_PAGE_URI = "/noSession.jsp";
  private final transient Log logger = LogFactory.getLog(CheckUrlFilter.class);
  
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException
  {
    boolean sign = true;
    ThreadStore.clear();
    ThreadStore.put(ThreadStoreConstant.REQUEST, (HttpServletRequest)req);
    HttpServletRequest request = (HttpServletRequest)req;
    HttpServletResponse response = (HttpServletResponse)res;
    

















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
