package gnnt.bank.adapter.rmi.serice;
import gnnt.bank.adapter.Test;
public class StartUpAdapter {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AdapterServer as = new AdapterServer();
		as.startup();//启动RMI服务端
		AdapterClient ac = new AdapterClient();
		ac.initAdapter();//启动RMI客户端
//		Test.inputMethod();
	}
}
