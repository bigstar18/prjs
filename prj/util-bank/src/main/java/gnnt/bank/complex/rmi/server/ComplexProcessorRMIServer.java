package gnnt.bank.complex.rmi.server;

import gnnt.bank.complex.rmi.ComplexProcessorRMIImpl;
import gnnt.bank.complex.util.Util;
import java.io.PrintStream;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;

public class ComplexProcessorRMIServer
{
  private static ComplexProcessorRMIImpl cpRmi = null;

  public ComplexProcessorRMIServer() throws RemoteException {
    System.out.println("启动本地RMI服务...");
  }

  public void startRMI(String IpAddress, int PortNumber, int DataPortNumber, String ServiceName)
  {
    if (System.getSecurityManager() != null)
      System.setSecurityManager(new RMISecurityManager());
    try
    {
      RMISocketFactory.setSocketFactory(new RmiDataSocket(DataPortNumber));
      cpRmi = new ComplexProcessorRMIImpl();

      LocateRegistry.createRegistry(PortNumber);
      Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + ServiceName, cpRmi);
      Util.log("RMI服务启动成功!");
    } catch (Exception e) {
      Util.log("cpRMI服务启动失败!详细信息如下:" + Util.getExceptionTrace(e));
    }
  }
}