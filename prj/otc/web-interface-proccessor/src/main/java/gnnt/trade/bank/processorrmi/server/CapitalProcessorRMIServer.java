package gnnt.trade.bank.processorrmi.server;

import gnnt.trade.bank.processorrmi.impl.ABCCapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.ABCZLCapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.BOCCapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.CCBCapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.CEBCapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.CIBCapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.CITICCapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.CMBCCapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.CMBCapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.CapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.GFBCapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.HXBCapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.HZCapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.ICBCCapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.ICBCECapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.JSBCapitalProcessorRMIImpl;
import gnnt.trade.bank.processorrmi.impl.YjfCapitalProcessorRMIImpl;
import gnnt.trade.bank.util.Configuration;
import gnnt.trade.bank.util.Tool;
import java.io.PrintStream;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;
import org.apache.log4j.Logger;

public class CapitalProcessorRMIServer
{
  private static CapitalProcessorRMIImpl cpRmi = null;
  private static ABCCapitalProcessorRMIImpl ABCcpRmi = null;
  private static YjfCapitalProcessorRMIImpl YJFcpRmi = null;
  private static BOCCapitalProcessorRMIImpl BOCcpRmi = null;
  private static JSBCapitalProcessorRMIImpl JSBcpRmi = null;
  private static CCBCapitalProcessorRMIImpl CCBcpRmi = null;
  private static HZCapitalProcessorRMIImpl HZcpRmi = null;
  private static GFBCapitalProcessorRMIImpl GFBcpRmi = null;
  private static ICBCCapitalProcessorRMIImpl ICBCcpRmi = null;
  private static CMBCapitalProcessorRMIImpl CMBcpRmi = null;
  private static ABCZLCapitalProcessorRMIImpl ABCZLcpRmi = null;
  private static CEBCapitalProcessorRMIImpl CEBcpRmi = null;
  private static HXBCapitalProcessorRMIImpl HXBcpRmi = null;
  private static CMBCCapitalProcessorRMIImpl CMBCcpRmi = null;
  private static ICBCECapitalProcessorRMIImpl ICBCEcpRmi = null;
  private static CITICCapitalProcessorRMIImpl CITICcpRmi = null;
  private static CIBCapitalProcessorRMIImpl CIBcpRmi = null;
  
  public CapitalProcessorRMIServer()
    throws RemoteException
  {
    System.out.println("启动本地BANKRMI服务器...");
  }
  
  public void startRMI(String IpAddress, int PortNumber, String ServiceNameS)
  {
    Properties props = new Configuration().getSection("BANK.Processor");
    log("ServiceNameS=" + ServiceNameS);
    if (System.getSecurityManager() != null) {
      System.setSecurityManager(new RMISecurityManager());
    }
    try
    {
      LocateRegistry.createRegistry(PortNumber);
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("ABCRmiServiceName")))
      {
        log("rminame=" + props.getProperty("ABCRmiServiceName"));
        ABCcpRmi = new ABCCapitalProcessorRMIImpl();
        log("abcrmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("ABCRmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("ABCRmiServiceName"), ABCcpRmi);
        
        log("abcRMI服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("YJFRmiServiceName")))
      {
        YJFcpRmi = new YjfCapitalProcessorRMIImpl();
        log("YJFrmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("YJFRmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("YJFRmiServiceName"), YJFcpRmi);
        
        log("YJFRMI服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("BOCRmiServiceName")))
      {
        BOCcpRmi = new BOCCapitalProcessorRMIImpl();
        log("BOCrmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("BOCRmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("BOCRmiServiceName"), BOCcpRmi);
        
        log("BOCRMI服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("JSBRmiServiceName")))
      {
        JSBcpRmi = new JSBCapitalProcessorRMIImpl();
        log("JSBrmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("JSBRmiServiceName"));
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("JSBRmiServiceName"), JSBcpRmi);
        
        log("JSBRMI服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("CCBRmiServiceName")))
      {
        CCBcpRmi = new CCBCapitalProcessorRMIImpl();
        log("CCBrmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("CCBRmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("CCBRmiServiceName"), CCBcpRmi);
        
        log("CCBRMI服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("HZRmiServiceName")))
      {
        HZcpRmi = new HZCapitalProcessorRMIImpl();
        log("HZcpRmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("HZRmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("HZRmiServiceName"), HZcpRmi);
        
        log("CCBRMI服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("GFBRmiServiceName")))
      {
        GFBcpRmi = new GFBCapitalProcessorRMIImpl();
        log("GFBrmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("GFBRmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("GFBRmiServiceName"), GFBcpRmi);
        
        log("GFBRMI服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("ICBCRmiServiceName")))
      {
        ICBCcpRmi = new ICBCCapitalProcessorRMIImpl();
        log("ICBCrmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("ICBCRmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("ICBCRmiServiceName"), ICBCcpRmi);
        
        log("ICBCRMI服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("ABCZLRmiServiceName")))
      {
        ABCZLcpRmi = new ABCZLCapitalProcessorRMIImpl();
        log("abc直连rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("ABCZLRmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("ABCZLRmiServiceName"), ABCZLcpRmi);
        
        log("abc直连RMI服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("BaseRmiServiceName")))
      {
        cpRmi = new CapitalProcessorRMIImpl();
        log("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("BaseRmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("BaseRmiServiceName"), cpRmi);
        
        log("基础RMI服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("CEBRmiServiceName")))
      {
        CEBcpRmi = new CEBCapitalProcessorRMIImpl();
        log("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("CEBRmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("CEBRmiServiceName"), CEBcpRmi);
        
        log("CEBRMI服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("CMBRmiServiceName")))
      {
        log("rminame=" + props.getProperty("CMBRmiServiceName"));
        CMBcpRmi = new CMBCapitalProcessorRMIImpl();
        
        log("CMBcpRmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("CMBRmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("CMBRmiServiceName"), CMBcpRmi);
        
        log("CMBcpRmi服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("CIBRmiServiceName")))
      {
        CIBcpRmi = new CIBCapitalProcessorRMIImpl();
        log("CMBcpRmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("CIBRmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("CIBRmiServiceName"), CIBcpRmi);
        
        log("CIBRMI服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("HXBRmiServiceName")))
      {
        HXBcpRmi = new HXBCapitalProcessorRMIImpl();
        log("HXBcpRmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("HXBRmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("HXBRmiServiceName"), HXBcpRmi);
        
        log("HXBcpRmi服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("CMBCRmiServiceName")))
      {
        CMBCcpRmi = new CMBCCapitalProcessorRMIImpl();
        log("CMBCcpRmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("CMBCRmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("CMBCRmiServiceName"), CMBCcpRmi);
        
        log("CMBCcpRmi服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("ICBCERmiServiceName")))
      {
        ICBCEcpRmi = new ICBCECapitalProcessorRMIImpl();
        log("ICBCEcpRmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("ICBCERmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("ICBCERmiServiceName"), ICBCEcpRmi);
        
        log("ICBCEcpRmi服务启动成功!");
      }
      if (Tool.isContainRMIServer(ServiceNameS, props.getProperty("CITICRmiServiceName")))
      {
        CITICcpRmi = new CITICCapitalProcessorRMIImpl();
        log("CITICcpRmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("CITICRmiServiceName"));
        
        Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + props.getProperty("CITICRmiServiceName"), CITICcpRmi);
        
        log("CITICcpRmi服务启动成功!");
      }
    }
    catch (Exception e)
    {
      log("RMI服务启动失败!详细信息如下:");
      log(Tool.getExceptionTrace(e));
    }
  }
  
  public void log(String content)
  {
    Logger plog = Logger.getLogger("Processorlog");
    plog.debug(content);
  }
}
