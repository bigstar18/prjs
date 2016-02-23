package gnnt.bank.adapter.rmi.Impl;
import gnnt.bank.adapter.BankAdapter;
import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Vector;
public class BankAdapterRMIImpl extends UnicastRemoteObject implements BankAdapterRMI{
	private BankAdapter adapter = null;
	public BankAdapterRMIImpl() throws RemoteException {
		super();		
		this.adapter = BankAdapter.getInstance();
	}	
	private static final long serialVersionUID = 3800004680225048743L;
	public double accountQuery(CorrespondValue correspondValue, String password) throws RemoteException {
		return adapter.accountQuery(correspondValue, password);
	}
	public ReturnValue delAccountQuery(CorrespondValue correspondValue) throws RemoteException {
		return adapter.delAccountQuery(correspondValue);
	}
	public ReturnValue inMoneyQueryBank(InMoneyVO inMoneyVO) throws RemoteException {
		return adapter.inMoneyQueryBank(inMoneyVO);
	}
	public ReturnValue outMoneyMarketDone(OutMoneyVO outMoneyVO) throws RemoteException {
		return adapter.outMoneyMarketDone(outMoneyVO);
	}
	public ReturnValue rgstAccountQuery(CorrespondValue correspondValue) throws RemoteException {
		return adapter.rgstAccountQuery(correspondValue);
	}
	public Vector<MoneyInfoValue> getBankMoneyInfo(Date date,Vector v) throws RemoteException{
		return adapter.getBankMoneyInfo(date,v);
	}
	public String testRmi() throws RemoteException {
		return "message come from adapter!";
	}
	public String getStr(long second) throws RemoteException {
		return adapter.getStr(second);
	}
}
