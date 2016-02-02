package gnnt.trade.bank.processorrmi.server;

import gnnt.trade.bank.processorrmi.impl.CapitalProcessorRMIImpl;
import gnnt.trade.bank.util.Tool;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class CapitalProcessorRMIServer {
	private static CapitalProcessorRMIImpl cpRmi = null;

	public CapitalProcessorRMIServer() throws RemoteException {
		System.out.println("启动本地BANKRMI服务器...");
	}

	/**
	 * 初始化RMI对象,并将RMI对象绑定到名称
	 */
	public void startRMI(String IpAddress, int PortNumber, String ServiceName) {
		if (System.getSecurityManager() != null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		try {
			cpRmi = new CapitalProcessorRMIImpl();
			// 将该对象实例与名称CapitalProcessorRMI绑定
			LocateRegistry.createRegistry(PortNumber);
			System.out.println("rmi://" + IpAddress + ":" + PortNumber + "/" + ServiceName);
			Naming.bind("rmi://" + IpAddress + ":" + PortNumber + "/" + ServiceName, cpRmi);
			System.out.println("cpRMI服务启动成功!");
		} catch (Exception e) {
			System.err.println("cpRMI服务启动失败!详细信息如下:");
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CapitalProcessorRMIServer server = new CapitalProcessorRMIServer();
			server.startRMI(Tool.getConfig("RmiIpAddress"), Integer.parseInt(Tool.getConfig("RmiPortNumber")), Tool.getConfig("RmiServiceName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
