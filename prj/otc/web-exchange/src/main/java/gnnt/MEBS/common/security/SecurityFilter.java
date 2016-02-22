package gnnt.MEBS.common.security;

import gnnt.MEBS.base.util.SpringContextHelper;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.handler.SecurityHandler;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.member.ActiveUser.ActiveUserManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecside.util.RequestUtils;

public class SecurityFilter
  implements Filter
{
  private final transient Log logger = LogFactory.getLog(SecurityFilter.class);
  private FilterConfig filterConfig = null;
  private String login_url = null;
  private String noRight_url = null;
  private List<String> noKeywordList = null;
  private List<String> methodList = null;
  
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException
  {
    if (RequestUtils.isAJAXRequest(req)) {
      req.setCharacterEncoding("UTF-8");
    }
    String contextPath = ((HttpServletRequest)req).getContextPath();
    String requestUri = ((HttpServletRequest)req).getRequestURI();
    ThreadStore.clear();
    String value;
    for (String parameter : this.methodList)
    {
      value = ((HttpServletRequest)req).getParameter(parameter);
      if (value != null) {
        requestUri = requestUri + "?" + parameter + "=" + value;
      }
    }
    requestUri = requestUri.replaceFirst(contextPath, "");
    boolean sign = true;
    for (String key : this.noKeywordList) {
      if ((requestUri + ",").indexOf(key + ",") > 0) {
        sign = false;
      }
    }
    this.logger.debug("sign:" + sign);
    if (sign)
    {
      if (this.logger.isDebugEnabled())
      {
        Cookie[] cookies2 = ((HttpServletRequest)req).getCookies();
        if (cookies2 != null) {
          for (int i = 0; i < cookies2.length; i++)
          {
            this.logger.debug("cookie name : " + cookies2[i].getName());
            this.logger.debug("cookie value : " + cookies2[i].getValue());
          }
        }
      }
      ThreadStore.put(ThreadStoreConstant.REQUEST, (HttpServletRequest)req);
      ThreadStore.put(ThreadStoreConstant.OPERATEIP, req.getRemoteAddr());
      this.logger.debug(requestUri);
      String userId = AclCtrl.getLogonID(req);
      User user = AclCtrl.getUser(req);
      SecurityHandler securityHandler = (SecurityHandler)SpringContextHelper.getBean("securityHandler");
      int result = securityHandler.handleRequest(requestUri, user);
      this.logger.debug("result:" + result);
      if (result == -1)
      {
        ((HttpServletRequest)req).getRequestDispatcher(this.noRight_url).forward(req, res);
      }
      else if (result == -99)
      {
        String invalidationSign = null;
        if (((HttpServletRequest)req).getSession().getAttribute("CURRENUSERID") != null)
        {
          String userIdFor = (String)((HttpServletRequest)req).getSession().getAttribute("CURRENUSERID");
          this.logger.debug("userIdFor:" + userIdFor);
          ActiveUserManager au = new ActiveUserManager();
          if ((au.getAllUsersSys(userIdFor) != null) && (au.getAllUsersSys(userIdFor).length > 0))
          {
            invalidationSign = "N";
            this.logger.info("被踢用户名：" + userIdFor);
            this.logger.info("ip：" + req.getRemoteAddr());
            String[] users = au.getAllUsersSys(userIdFor);
            this.logger.info("被踢于：" + users[0]);
            this.logger.info(req.getRealPath(""));
            this.logger.info(req.getLocalAddr());
            Cookie[] cookies = ((HttpServletRequest)req).getCookies();
            if ((cookies != null) && (cookies.length > 0)) {
              for (int i = 0; i < cookies.length; i++)
              {
                Cookie c = cookies[i];
                this.logger.info("cookie" + i + ":" + c.getName() + "  value:" + c.getValue() + "   age:" + c.getMaxAge());
              }
            }
            Enumeration e = ((HttpServletRequest)req).getHeaderNames();
            if (e != null) {
              while (e.hasMoreElements())
              {
                String key = (String)e.nextElement();
                this.logger.info("key:" + key);
                this.logger.info(((HttpServletRequest)req).getHeader(key));
              }
            }
          }
          else
          {
            this.logger.info("au失效用户名：" + userIdFor);
            this.logger.info("ip：" + req.getRemoteAddr());
            this.logger.info(req.getRealPath(""));
            this.logger.info(req.getLocalAddr());
            Cookie[] cookies = ((HttpServletRequest)req).getCookies();
            if ((cookies != null) && (cookies.length > 0)) {
              for (int i = 0; i < cookies.length; i++)
              {
                Cookie c = cookies[i];
                this.logger.info("cookie" + i + ":" + c.getName() + "  value:" + c.getValue() + "   age:" + c.getMaxAge());
              }
            }
            Enumeration e = ((HttpServletRequest)req).getHeaderNames();
            if (e != null) {
              while (e.hasMoreElements())
              {
                String key = (String)e.nextElement();
                this.logger.info("key:" + key);
                this.logger.info(((HttpServletRequest)req).getHeader(key));
              }
            }
            invalidationSign = "Y";
          }
        }
        else
        {
          this.logger.info("session失效用户名：");
          this.logger.info("ip：" + req.getRemoteAddr());
          this.logger.info(req.getRealPath(""));
          this.logger.info(req.getLocalAddr());
          Cookie[] cookies = ((HttpServletRequest)req).getCookies();
          if ((cookies != null) && (cookies.length > 0)) {
            for (int i = 0; i < cookies.length; i++)
            {
              Cookie c = cookies[i];
              this.logger.info("cookie" + i + ":" + c.getName() + "  value:" + c.getValue() + "   age:" + c.getMaxAge());
            }
          }
          Enumeration e = ((HttpServletRequest)req).getHeaderNames();
          if (e != null) {
            while (e.hasMoreElements())
            {
              String key = (String)e.nextElement();
              this.logger.info("key:" + key);
              this.logger.info(((HttpServletRequest)req).getHeader(key));
            }
          }
          invalidationSign = "S";
        }
        if (((HttpServletRequest)req).getSession().getAttribute("invalidationSign") == null)
        {
          ((HttpServletRequest)req).getSession().invalidate();
          ((HttpServletRequest)req).getSession().setAttribute("invalidationSign", invalidationSign);
          this.logger.debug("invalidationSign:" + invalidationSign);
        }
        ((HttpServletResponse)res).sendRedirect(contextPath + this.login_url + "?a=" + new Date());
      }
      else if (result == -98)
      {
        chain.doFilter(req, res);
      }
      else if (result >= 0)
      {
        String AusessionId = req.getParameter("AUsessionId");
        String sessionId = (String)((HttpServletRequest)req).getSession().getAttribute("LOGINIDS");
        String userIdFor = (String)((HttpServletRequest)req).getSession().getAttribute("CURRENUSERID");
        if ((AusessionId == null) || (sessionId.equals(AusessionId)))
        {
          chain.doFilter(req, res);
        }
        else
        {
          this.logger.info("au失效sessionId：" + AusessionId);
          this.logger.info("当前有效 用户名：" + userIdFor + "  sessionId:" + sessionId);
          this.logger.info("ip：" + req.getRemoteAddr());
          this.logger.info(req.getRealPath(""));
          this.logger.info(req.getLocalAddr());
          Cookie[] cookies = ((HttpServletRequest)req).getCookies();
          if ((cookies != null) && (cookies.length > 0)) {
            for (int i = 0; i < cookies.length; i++)
            {
              Cookie c = cookies[i];
              this.logger.info("cookie" + i + ":" + c.getName() + "  value:" + c.getValue() + "   age:" + c.getMaxAge());
            }
          }
          Enumeration e = ((HttpServletRequest)req).getHeaderNames();
          if (e != null) {
            while (e.hasMoreElements())
            {
              String key = (String)e.nextElement();
              this.logger.info("key:" + key);
              this.logger.info(((HttpServletRequest)req).getHeader(key));
            }
          }
          if (((HttpServletRequest)req).getSession().getAttribute("invalidationSign") == null) {
            ((HttpServletRequest)req).getSession().setAttribute("invalidationSign", "K");
          }
          ((HttpServletResponse)res).sendRedirect(contextPath + this.login_url + "?a=" + new Date());
        }
      }
    }
    else
    {
      chain.doFilter(req, res);
    }
  }
  
  public void destroy()
  {
    this.filterConfig = null;
    this.login_url = null;
    this.noRight_url = null;
    this.methodList = null;
    this.noKeywordList = null;
  }
  
  public void init(FilterConfig filterCfg)
    throws ServletException
  {
    this.filterConfig = filterCfg;
    this.login_url = this.filterConfig.getInitParameter("LOGIN_URL");
    this.noRight_url = this.filterConfig.getInitParameter("NORIGHT_URL");
    String noKeyword = this.filterConfig.getInitParameter("NOKEYWORDS");
    String[] noKeywords = noKeyword.split(",");
    this.noKeywordList = new ArrayList();
    for (int i = 0; i < noKeywords.length; i++) {
      this.noKeywordList.add(noKeywords[i]);
    }
    String method = this.filterConfig.getInitParameter("METHODS");
    String[] methods = method.split(",");
    this.methodList = new ArrayList();
    for (int i = 0; i < methods.length; i++) {
      this.methodList.add(methods[i]);
    }
  }
}
