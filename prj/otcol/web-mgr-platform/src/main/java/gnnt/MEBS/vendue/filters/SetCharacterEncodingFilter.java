package gnnt.MEBS.vendue.filters;

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
  protected String encoding = null;
  protected FilterConfig filterConfig = null;
  protected boolean ignore = true;
  
  public void destroy()
  {
    this.encoding = null;
    this.filterConfig = null;
  }
  
  public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain paramFilterChain)
    throws IOException, ServletException
  {
    if ((this.ignore) || (paramServletRequest.getCharacterEncoding() == null))
    {
      String str = selectEncoding(paramServletRequest);
      if (str != null) {
        paramServletRequest.setCharacterEncoding(str);
      }
    }
    paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
  }
  
  public void init(FilterConfig paramFilterConfig)
    throws ServletException
  {
    this.filterConfig = paramFilterConfig;
    this.encoding = paramFilterConfig.getInitParameter("encoding");
    String str = paramFilterConfig.getInitParameter("ignore");
    if (str == null) {
      this.ignore = true;
    } else if (str.equalsIgnoreCase("true")) {
      this.ignore = true;
    } else if (str.equalsIgnoreCase("yes")) {
      this.ignore = true;
    } else {
      this.ignore = false;
    }
  }
  
  protected String selectEncoding(ServletRequest paramServletRequest)
  {
    return this.encoding;
  }
}
