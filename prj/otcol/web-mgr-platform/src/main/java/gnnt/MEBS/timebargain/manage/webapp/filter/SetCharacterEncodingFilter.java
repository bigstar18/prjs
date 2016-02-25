package gnnt.MEBS.timebargain.manage.webapp.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SetCharacterEncodingFilter
  implements Filter
{
  public void init(FilterConfig paramFilterConfig)
    throws ServletException
  {}
  
  public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain paramFilterChain)
    throws IOException, ServletException
  {
    paramServletRequest.setCharacterEncoding("GBK");
    paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
  }
  
  public void destroy() {}
}
