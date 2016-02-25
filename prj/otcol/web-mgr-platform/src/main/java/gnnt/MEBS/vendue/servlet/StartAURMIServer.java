package gnnt.MEBS.vendue.servlet;

import gnnt.MEBS.vendue.server.rmi.ActiveUserRMIImpl;
import gnnt.MEBS.vendue.util.Configuration;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;

public class StartAURMIServer
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
    String str1 = getServletContext().getServletContextName();
    Logger localLogger = Logger.getLogger("Kernellog");
    try
    {
      Properties localProperties = new Configuration().getSection("MEBS.AURMIServer");
      String str2 = localProperties.getProperty("IP");
      int i = Integer.parseInt(localProperties.getProperty("Port"));
      boolean bool = Boolean.valueOf(localProperties.getProperty("IsRMIServer")).booleanValue();
      if (bool)
      {
        try
        {
          Registry localRegistry = LocateRegistry.getRegistry(i);
          if (localRegistry != null) {
            localRegistry = LocateRegistry.createRegistry(i);
          }
        }
        catch (Exception localException2) {}
        ActiveUserRMIImpl localActiveUserRMIImpl = new ActiveUserRMIImpl();
        try
        {
          Naming.rebind("//" + str2 + ":" + i + "/ActiveUserRMI" + str1, localActiveUserRMIImpl);
          localLogger.debug("==============>应用" + str1 + " ActiveUser RMI服务启动成功。");
        }
        catch (Exception localException3)
        {
          localLogger.debug("==============>应用" + str1 + " ActiveUser RMI服务启动失败。");
          localException3.printStackTrace();
        }
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
  }
}
