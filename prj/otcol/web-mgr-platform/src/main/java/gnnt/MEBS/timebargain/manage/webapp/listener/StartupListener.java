package gnnt.MEBS.timebargain.manage.webapp.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
    log.debug("---后台管理启动初始化完成！---");
  }
  
  public static void setupContext(ServletContext paramServletContext)
  {
    WebApplicationContext localWebApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(paramServletContext);
    try
    {
      if (log.isDebugEnabled()) {
        log.debug("drop-down initialization complete [OK]");
      }
    }
    catch (Exception localException)
    {
      log.error(localException.getMessage());
    }
  }
}
