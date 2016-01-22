package gnnt.bank.platform.rmi.server;

import gnnt.bank.platform.rmi.impl.CapitalProcessorRMIImpl;
import gnnt.bank.platform.rmi.impl.PlatformProcessorRMIImpl;
import gnnt.bank.platform.util.Tool;
import java.io.PrintStream;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;

public class CapitalProcessorRMIServer
{
  private static CapitalProcessorRMIImpl cpRmi = null;
  private static PlatformProcessorRMIImpl cpRmiPT = null;

  public CapitalProcessorRMIServer() throws RemoteException
  {
    System.out.println("启动本地RMI服务...");
  }

  public void startRMI(String IpAddress, int PortNumber, int DataPortNumber, String BankServiceName, String TradeServiceName)
  {
    if (System.getSecurityManager() != null)
      System.setSecurityManager(new RMISecurityManager());
    try
    {
      RMISocketFactory.setSocketFactory(new RmiDataSocket(DataPortNumber));
      cpRmi = new CapitalProcessorRMIImpl();
      cpRmiPT = new PlatformProcessorRMIImpl();

      LocateRegistry.createRegistry(PortNumber);
      Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + BankServiceName, cpRmi);
      Tool.log("对银行RMI服务启动成功!");
      Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + TradeServiceName, cpRmiPT);
      Tool.log("对交易系统RMI服务启动成功!");
    } catch (Exception e) {
      Tool.log("cpRMI服务启动失败!详细信息如下:" + Tool.getExceptionTrace(e));
    }
  }

  public static void main(String[] args)
  {
    try
    {
      CapitalProcessorRMIServer server = new CapitalProcessorRMIServer();
      server.startRMI("172.16.0.24", 12404, 12405, "BankRmi", "TradeRmi");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}