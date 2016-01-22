package gnnt.bank.adapter.rmi.service;

import gnnt.bank.adapter.BankAdapter;
import gnnt.bank.adapter.rmi.Impl.BankAdapterRMIImpl;
import gnnt.bank.adapter.rmi.RmiDataSocket;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;

public class RmiServer
{
  public static void start()
    throws RemoteException, MalformedURLException, AlreadyBoundException
  {
    String IpAddress = BankAdapter.getConfig("rimAdress");
    int PortNumber = Integer.parseInt(BankAdapter.getConfig("rimPort"));
    int PortDataNumber = Integer.parseInt(BankAdapter.getConfig("rimDataPort"));
    String ServiceName = BankAdapter.getConfig("rimName");
    if (System.getSecurityManager() != null) {
      System.setSecurityManager(new RMISecurityManager());
    }

    try
    {
      RMISocketFactory.setSocketFactory(new RmiDataSocket(PortDataNumber));
      LocateRegistry.createRegistry(PortNumber);
      String fullRmiPath = "//" + IpAddress + ":" + PortNumber + "/" + ServiceName;
      Naming.bind(fullRmiPath, new BankAdapterRMIImpl());
      BankAdapter.log("处理器监听启动成功!");
    } catch (RemoteException e) {
      BankAdapter.log("处理器监听启动失败!");
      e.printStackTrace();
      throw e;
    } catch (MalformedURLException e) {
      BankAdapter.log("处理器监听启动失败!");
      e.printStackTrace();
      throw e;
    } catch (AlreadyBoundException e) {
      BankAdapter.log("处理器监听启动失败!");
      e.printStackTrace();
      throw e;
    } catch (Exception e) {
      BankAdapter.log("处理器监听启动失败!");
      e.printStackTrace();
    }
  }
}