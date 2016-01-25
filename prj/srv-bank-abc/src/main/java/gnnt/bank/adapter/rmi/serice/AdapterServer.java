package gnnt.bank.adapter.rmi.serice;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.bank.adapter.rmi.Impl.BankAdapterRMIImpl;
import gnnt.bank.adapter.util.Configuration;
import java.io.PrintStream;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

public class AdapterServer
{
  public void server(String IpAddress, int PortNumber, String AdapterName)
  {
    if (System.getSecurityManager() != null)
      System.setSecurityManager(new RMISecurityManager());
    try
    {
      LocateRegistry.createRegistry(PortNumber);
      BankAdapterRMI server = new BankAdapterRMIImpl();
      Naming.rebind("//" + IpAddress + ":" + PortNumber + "/" + AdapterName, server);
      System.out.println("RMI服务启动成功!");
    } catch (Exception e) {
      System.err.println("RMI服务启动失败!详细信息如下:");
      e.printStackTrace();
    }
  }

  public void startup()
  {
    Properties props = new Configuration().getSection("BANK.Adapter");
    String adapterName = props.getProperty("rmiName");
    String adapterIP = props.getProperty("rmiAdress");
    String adapterPort = props.getProperty("rmiPort");
    System.out.println("//" + adapterIP + ":" + Integer.parseInt(adapterPort) + "/" + adapterName);
    server(adapterIP, Integer.parseInt(adapterPort), adapterName);
  }
}