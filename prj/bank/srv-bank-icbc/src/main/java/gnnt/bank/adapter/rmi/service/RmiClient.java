package gnnt.bank.adapter.rmi.service;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import gnnt.bank.adapter.BankAdapter;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;

public class RmiClient {
	private String rimIp_Processor = null;
	private int rimPort_Processor;
	private String rimName_Processor = null;
	public CapitalProcessorRMI capitalProcessorRMI = null;

	public RmiClient() {
		this.rimIp_Processor = BankAdapter.getConfig("rimIp_Processor");
		this.rimPort_Processor = Integer.parseInt(BankAdapter.getConfig("rimPort_Processor"));
		this.rimName_Processor = BankAdapter.getConfig("rimName_Processor");
	}

	public boolean connProcessor() {
		boolean result = true;
		String fullRmiPath = "//" + this.rimIp_Processor + ":" + this.rimPort_Processor + "/" + this.rimName_Processor;
		try {
			this.capitalProcessorRMI = ((CapitalProcessorRMI) Naming.lookup(fullRmiPath));
			result = true;
		} catch (MalformedURLException e) {
			BankAdapter.log("连接处理器失败,处理器地址不正确");

			result = false;
		} catch (RemoteException e) {
			BankAdapter.log("连接处理器失败,处理器远程服务未打开");

			result = false;
		} catch (NotBoundException e) {
			BankAdapter.log("连接处理器失败,处理器远程服务未绑定");

			result = false;
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			System.out.println(Naming.lookup("//172.18.100.17:2118/CapitalProcessorRMI").getClass());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}