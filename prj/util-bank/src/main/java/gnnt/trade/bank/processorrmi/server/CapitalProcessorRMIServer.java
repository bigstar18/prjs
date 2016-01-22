package gnnt.trade.bank.processorrmi.server;

import gnnt.trade.bank.processorrmi.impl.CapitalProcessorRMIImpl;
import gnnt.trade.bank.util.SMRMISocket;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;

public class CapitalProcessorRMIServer {

	private static CapitalProcessorRMIImpl cpRmi = null;

	public CapitalProcessorRMIServer() throws RemoteException 
	{
		System.out.println("启动本地BANKRMI服务器...");
	}

	/**
	 * 初始化RMI对象,并将RMI对象绑定到名称
	 */
	public void startRMI(String IpAddress, int PortNumber,int dataPort, String ServiceName)
	{
		if(System.getSecurityManager()!=null){
			System.setSecurityManager(new RMISecurityManager());
		}
		try {
			cpRmi = new CapitalProcessorRMIImpl();
			RMISocketFactory.setSocketFactory(new SMRMISocket(dataPort));  //定义数据传输端口
			// 将该对象实例与名称CapitalProcessorRMI绑定
			LocateRegistry.createRegistry(PortNumber);
			String url = "rmi://" + IpAddress + ":" + PortNumber + "/" + ServiceName;
			Naming.bind(url, cpRmi);
			System.out.println("cpRMI服务 "+url+" 启动成功!");
		} catch (Exception e) {
			System.err.println("cpRMI服务启动失败!详细信息如下:");
			e.printStackTrace();
		} 
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			CapitalProcessorRMIServer server = new CapitalProcessorRMIServer();
			server.startRMI("127.0.0.1", 12404, 12405, "cpRmi");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
