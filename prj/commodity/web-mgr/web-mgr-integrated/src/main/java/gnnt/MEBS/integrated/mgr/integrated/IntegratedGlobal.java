package gnnt.MEBS.integrated.mgr.integrated;

import gnnt.MEBS.activeUserListener.kernel.ILogonService;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IntegratedGlobal
  implements ServletContextListener
{
  protected final transient Log logger = LogFactory.getLog(getClass());
  private static ILogonService logonService;
  public static Map<Integer, String> certificateTypeMap;
  private static Map<String, String> rmiMap = new HashMap();
  
  public void contextDestroyed(ServletContextEvent paramServletContextEvent) {}
  
  public void contextInitialized(ServletContextEvent paramServletContextEvent)
  {
    certificateTypeMap = getCertificateTypeMap();
    StandardService localStandardService = (StandardService)ApplicationContextInit.getBean("com_standardService");
    List localList1 = localStandardService.getListBySql("select * from l_dictionary l where l.key='getLogonUser_ip'");
    if ((localList1 != null) && (localList1.size() > 0)) {
      rmiMap.put("ip", ((Map)localList1.get(0)).get("VALUE").toString());
    }
    List localList2 = localStandardService.getListBySql("select * from l_dictionary l where l.key='getLogonUser_port'");
    if ((localList2 != null) && (localList2.size() > 0)) {
      rmiMap.put("port", ((Map)localList2.get(0)).get("VALUE").toString());
    }
    List localList3 = localStandardService.getListBySql("select * from l_dictionary l where l.key='getLogonUser_dataPort'");
    if ((localList3 != null) && (localList3.size() > 0)) {
      rmiMap.put("dataPort", ((Map)localList3.get(0)).get("VALUE").toString());
    }
    this.logger.info("-----rmiMap----------" + rmiMap);
    try
    {
      logonService = (ILogonService)Naming.lookup("rmi://" + (String)rmiMap.get("ip") + ":" + (String)rmiMap.get("port") + "/logonService");
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    catch (NotBoundException localNotBoundException)
    {
      localNotBoundException.printStackTrace();
    }
  }
  
  public static ILogonService getLogonService()
  {
    return logonService;
  }
  
  public static void clearLogonService()
    throws MalformedURLException, RemoteException, NotBoundException
  {
    logonService = (ILogonService)Naming.lookup("rmi://" + (String)rmiMap.get("ip") + ":" + (String)rmiMap.get("port") + "/" + (String)rmiMap.get("serverName"));
  }
  
  public static Map<Integer, String> getCertificateTypeMap()
  {
    StandardService localStandardService = (StandardService)ApplicationContextInit.getBean("com_standardService");
    List localList = localStandardService.getListBySql("select * from m_certificatetype");
    certificateTypeMap = new LinkedHashMap();
    if ((localList != null) && (localList.size() > 0))
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Map localMap = (Map)localIterator.next();
        certificateTypeMap.put(Integer.valueOf(((BigDecimal)localMap.get("CODE")).intValue()), (String)localMap.get("NAME"));
      }
    }
    return certificateTypeMap;
  }
}
