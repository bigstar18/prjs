package gnnt.MEBS.timebargain.tradeweb.webapp.listener;

import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
import gnnt.MEBS.timebargain.server.util.SysConfig;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.util.DateUtil;
import java.rmi.Naming;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SessionListener
  extends HttpServlet
  implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener
{
  private static final long serialVersionUID = -774139866063503112L;
  private final transient Log log = LogFactory.getLog(SessionListener.class);
  private static ApplicationContext ctx = null;
  private TradeRMI tradeRMI = null;
  private static Map rmiMap = null;
  static ServletContext sc = null;
  
  public void contextInitialized(ServletContextEvent paramServletContextEvent)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("SessionListener initializing context...");
    }
    sc = paramServletContextEvent.getServletContext();
  }
  
  public void contextDestroyed(ServletContextEvent paramServletContextEvent)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("SessionListener destroying context...");
    }
  }
  
  public void attributeAdded(HttpSessionBindingEvent paramHttpSessionBindingEvent)
  {
    if (this.log.isDebugEnabled()) {}
  }
  
  public void attributeRemoved(HttpSessionBindingEvent paramHttpSessionBindingEvent)
  {
    if (this.log.isDebugEnabled()) {}
  }
  
  public void attributeReplaced(HttpSessionBindingEvent paramHttpSessionBindingEvent)
  {
    if (this.log.isDebugEnabled()) {}
  }
  
  public void sessionCreated(HttpSessionEvent paramHttpSessionEvent)
  {
    if (this.log.isDebugEnabled()) {}
  }
  
  public void sessionDestroyed(HttpSessionEvent paramHttpSessionEvent)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("session destroying...");
    }
    rmiMap = getRMIMap();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("rmi://").append(rmiMap.get("host")).append(":").append(rmiMap.get("port")).append("/TradeRMI");
    try
    {
      this.tradeRMI = ((TradeRMI)Naming.lookup(localStringBuffer.toString()));
      String str1 = paramHttpSessionEvent.getSession().getAttribute("LOGON_TYPE").toString();
      String str2 = this.tradeRMI.getUserID(((Privilege)paramHttpSessionEvent.getSession().getAttribute("privilege")).getSessionID().longValue(), str1);
      this.tradeRMI.logoff(((Privilege)paramHttpSessionEvent.getSession().getAttribute("privilege")).getSessionID().longValue(), str1);
      if (str2 != null) {
        this.log.info("客户" + str2 + "于" + DateUtil.getCurDateTime() + "退出系统");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("session destroy error:" + localException.getMessage());
    }
  }
  
  public Object getBean(String paramString)
  {
    if (ctx == null) {
      ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
    }
    return ctx.getBean(paramString);
  }
  
  private Map getRMIMap()
  {
    if (rmiMap == null) {
      rmiMap = SysConfig.getRMIConfig((DataSource)getBean("dataSource"));
    }
    return rmiMap;
  }
}
