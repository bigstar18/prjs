package gnnt.trade.bank;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
public class Test {
	public static void main(String[] args) {
		try {
			CapitalProcessorRMI cp = (CapitalProcessorRMI)Naming.lookup("//172.16.2.247:2007/CapitalProcessorRMI");
			System.out.println(cp==null);
			Hashtable<String,String> m = cp.getBankIdAndAdapterClassname();
			System.out.println(m==null);
			System.out.println(m.size());
			for (int a=0;a<m.size();a++)
			{
				System.out.println(m.get("ICBCBankImpl"));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}
}
