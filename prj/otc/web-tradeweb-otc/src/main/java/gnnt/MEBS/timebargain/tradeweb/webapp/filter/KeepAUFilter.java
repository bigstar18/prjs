package gnnt.MEBS.timebargain.tradeweb.webapp.filter;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.servlet.Login_syn;
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

public class KeepAUFilter
  implements Filter
{
  private final transient Log logger = LogFactory.getLog(KeepAUFilter.class);
  private String noRight_url = null;
  
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException
  {
    boolean sign = true;
    
    String contextPath = ((HttpServletRequest)req).getContextPath();
    
    HttpServletRequest request = (HttpServletRequest)req;
    
    HttpServletResponse response = (HttpServletResponse)res;
    
    String uriStr = request.getRequestURI();
    if ((uriStr.indexOf("/public/") > -1) || 
      (uriStr.indexOf("image.jsp") > -1) || 
      (uriStr.indexOf("login_syn.jsp") > -1) || 
      (uriStr.indexOf("/logon.jsp") > -1) || 
      (uriStr.indexOf("/logon/logon.action") > -1) || 
      (uriStr.indexOf("/noRight.jsp") > -1) || 
      (uriStr.indexOf(".gif") > -1) || 
      (uriStr.indexOf(".jpg") > -1) || 
      (uriStr.indexOf(".css") > -1) || 
      (uriStr.endsWith(".js")) || 
      (uriStr.indexOf(".png") > -1) || 
      (uriStr.indexOf(".htc") > -1) || 
      (uriStr.indexOf(".ico") > -1) || 
      (uriStr.indexOf(".bmp") > -1) || 
      (uriStr.indexOf(".zip") > -1) || 
      (uriStr.indexOf(".cab") > -1))
    {
      this.logger.debug(" enter filter:" + uriStr);
      chain.doFilter(req, res);
      return;
    }
    LogonManager logonManager = LogonManager.getInstance();
    Long sessionId = (Long)request.getSession().getAttribute("LOGINID");
    String traderId = (String)request.getSession().getAttribute("username");
    if ((sessionId == null) || (traderId == null))
    {
      String loginIdForCookie = request.getParameter("LOGINID");
      String userNameForCookie = request.getParameter("username");
      if ((loginIdForCookie != null) && (userNameForCookie != null))
      {
        long session = Login_syn.checkUser(userNameForCookie, Long.parseLong(loginIdForCookie), request, response);
        if (session <= 0L) {
          sign = false;
        }
      }
      else
      {
        sign = false;
      }
    }
    this.logger.debug("sign:" + sign);
    if (sign) {
      chain.doFilter(req, res);
    } else {
      ((HttpServletResponse)res).sendRedirect(contextPath + this.noRight_url);
    }
  }
  
  public void destroy() {}
  
  public void init(FilterConfig arg0)
    throws ServletException
  {
    this.noRight_url = arg0.getInitParameter("NORIGHT_URL");
  }
}
