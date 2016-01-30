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
  
  public void contextInitialized(ServletContextEvent paramServletContextEvent)
  {
    if (log.isDebugEnabled()) {
      log.debug("initializing context...");
    }
    super.contextInitialized(paramServletContextEvent);
    ServletContext localServletContext = paramServletContextEvent.getServletContext();
    localServletContext.setAttribute("rmi", localServletContext.getInitParameter("rmi"));
    localServletContext.setAttribute("yzm", localServletContext.getInitParameter("yzm"));
    localServletContext.setAttribute("ClientName", localServletContext.getInitParameter("ClientName"));
    log.debug("---前台下单启动初始化完成！---");
  }
}
