package gnnt.MEBS.timebargain.tradeweb.webapp.listener;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
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
  
  public void contextInitialized(ServletContextEvent sce)
  {
    ServletContext servletContext = sce.getServletContext();
    if (ctx == null) {
      ctx = 
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    }
  }
  
  public void contextDestroyed(ServletContextEvent sce) {}
  
  public void attributeAdded(HttpSessionBindingEvent se) {}
  
  public void attributeRemoved(HttpSessionBindingEvent se) {}
  
  public void attributeReplaced(HttpSessionBindingEvent se) {}
  
  public void sessionCreated(HttpSessionEvent se) {}
  
  public void sessionDestroyed(HttpSessionEvent se)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("session destroying...");
    }
    rmiMap = getRMIMap();
    StringBuffer sb = new StringBuffer();
    sb.append("rmi://").append(rmiMap.get("host")).append(":").append(
      rmiMap.get("port")).append("/TradeRMI");
    try
    {
      this.tradeRMI = ((TradeRMI)Naming.lookup(sb.toString()));
      


      Privilege privilege = (Privilege)se.getSession().getAttribute(
        "privilege");
      if (privilege != null) {
        this.log.info("客户" + 
          privilege.getTraderID() + 
          "于" + 
          
          DateUtil.getCurDateTime() + "Session失效,IP地址" + 
          privilege.getLogonIP() + " cookies sessionID=" + 
          se.getSession().getId());
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.log.error("session destroy error:" + e.getMessage());
    }
  }
  
  public Object getBean(String name)
  {
    return ctx.getBean(name);
  }
  
  private Map getRMIMap()
  {
    if (rmiMap == null) {
      rmiMap = LogonManager.getRMIConfig("2", 
        (DataSource)getBean("dataSource"));
    }
    return rmiMap;
  }
}
