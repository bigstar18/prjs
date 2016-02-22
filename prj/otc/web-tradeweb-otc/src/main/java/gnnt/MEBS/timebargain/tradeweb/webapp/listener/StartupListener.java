package gnnt.MEBS.timebargain.tradeweb.webapp.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;

public class StartupListener
  extends ContextLoaderListener
  implements ServletContextListener
{
  private static final Log log = LogFactory.getLog(StartupListener.class);
  
  public void contextInitialized(ServletContextEvent event)
  {
    if (log.isDebugEnabled()) {
      log.debug("initializing context...");
    }
    super.contextInitialized(event);
    
    ServletContext context = event.getServletContext();
    
    context.setAttribute("rmi", context.getInitParameter("rmi"));
    context.setAttribute("yzm", context.getInitParameter("yzm"));
    context.setAttribute("ClientName", context.getInitParameter("ClientName"));
    

    log.debug("---前台下单启动初始化完成！---");
  }
}
