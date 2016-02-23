package gnnt.bank.adapter;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.bank.adapter.util.Common;
import gnnt.bank.adapter.util.Tool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Test {
	public static void main(String args[]){
		inputMethod();
	}
	public static void inputMethod(){
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		String str = "help";
		while(!"exit".equalsIgnoreCase(str)){
			try{
				if("help".equalsIgnoreCase(str)){
					help();
				}
				if("getStr".equalsIgnoreCase(str)){
					System.out.println("消息发出时间："+Common.df5.format(new java.util.Date()));
					try{
						System.out.println(getAdapter().getStr(getSleepTime()));
					}catch(Exception e){
						e.printStackTrace();
					}
					System.out.println("接收返回时间："+Common.df5.format(new java.util.Date()));
				}
				str = buf.readLine();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.exit(0);
	}
	private static long getSleepTime(){
		long result = 60;
		try{
			result = Long.parseLong(Tool.getConfig(Tool.sleeptime));
		}catch(Exception e){
		}
		return result;
	}
	private static void help(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("-----------------------"+str);
		sb.append("   help       查看命令"+str);
		sb.append("   getStr     查看联通情况"+str);
		sb.append("   exit       退出系统"+str);
		sb.append("-----------------------"+str);
		System.out.println(sb.toString());
	}
	private static BankAdapterRMI getAdapter(){
		BankAdapterRMI result = null;
		String adapterIP = Tool.getConfig(Tool.AdapterIP);
		String adapterPort = Tool.getConfig(Tool.AdapterPort);
		String serviceName = Tool.getConfig(Tool.AdapterName);
		String adapterURL = "//"+adapterIP+":"+adapterPort+"/"+serviceName;
		try {
			result = (BankAdapterRMI) Naming.lookup(adapterURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return result;
	}
}
