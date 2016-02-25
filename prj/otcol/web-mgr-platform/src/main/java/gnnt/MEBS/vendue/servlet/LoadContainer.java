package gnnt.MEBS.vendue.servlet;

import gnnt.MEBS.vendue.server.GlobalContainer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;

public class LoadContainer
  extends HttpServlet
{
  private static final long serialVersionUID = 1000L;
  
  public void destroy()
  {
    super.destroy();
  }
  
  public void init()
    throws ServletException
  {
    super.init();
    Logger localLogger = Logger.getLogger("Kernellog");
    localLogger.debug("=========== start to load container ====================================");
    try
    {
      GlobalContainer.loadContainer();
      localLogger.debug("=========== succeed to load container ==================================");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      localLogger.debug("=========== fail to load container ====================================");
    }
  }
}
