package gnnt.bank.adapter.rmi.serice;
import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.bank.adapter.rmi.Impl.BankAdapterRMIImpl;
import gnnt.bank.adapter.util.Tool;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Timer;
import java.util.TimerTask;
public class AdapterServer {
	private static BankAdapterRMI server;
	/**
	 * 启动适配器 RMI 服务
	 * @param IpAddress 适配器机器IP地址
	 * @param PortNumber 适配器 RMI 服务端口
	 * @param AdapterName 适配器服务名
	 */
	public void server(String IpAddress, int PortNumber, String AdapterName){
		if(System.getSecurityManager()!=null){
			System.setSecurityManager(new RMISecurityManager());
		}
        try{    
            LocateRegistry.createRegistry(PortNumber);
            Naming.rebind("//"+IpAddress+":"+PortNumber+"/"+AdapterName , getServer());
            Tool.log("RMI服务启动成功!");
        }catch (Exception e) {
        	Tool.log("RMI服务启动失败!详细信息如下:");
			Tool.log(Tool.getExceptionTrace(e));
		} 
	}
	private BankAdapterRMI getServer()throws RemoteException{
		if(server != null){
			return server;
		}
		synchronized(this){
			if(server != null){
				return server;
			}
			try {
				server = new BankAdapterRMIImpl();
			} catch (RemoteException e) {
				throw e;
			}
		}
		return server;
	}
	/**
	 * 启动rmi服务
	 */
	public void startup(){
		String adapterName = Tool.getConfig(Tool.AdapterName);
		String adapterIP = Tool.getConfig(Tool.AdapterIP);//适配器ip
		String adapterPort = Tool.getConfig(Tool.AdapterPort);//适配器端口
		server(adapterIP, Integer.parseInt(adapterPort), adapterName);
	}
}