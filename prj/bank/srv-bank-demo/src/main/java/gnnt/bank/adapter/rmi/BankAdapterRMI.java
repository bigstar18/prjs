package gnnt.bank.adapter.rmi;

import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Vector;

public interface BankAdapterRMI extends Remote{

	public String testRmi() throws RemoteException;
	/**
	 * 入金
	 * @param inMoneyInfo 入金信息
	 * @return ReturnValue
	 */
	public ReturnValue inMoneyQueryBank(InMoneyVO inMoneyVO) throws RemoteException;
	
	/**
     * 出金
     * @param outMoneyInfo 出金对象
     * @return ReturnValue
     */
	public ReturnValue outMoneyMarketDone(OutMoneyVO outMoneyVO) throws RemoteException;
	/**
     * 银行账户注册申请
     * @param correspondValue 交易商代码银行账户对应关系对象
     * @return
     */
	public ReturnValue rgstAccountQuery(CorrespondValue correspondValue) throws RemoteException;
	
	/**
	 * 银行账户注销申请
	 * @param correspondValue 交易商代码银行账户对应关系对象
	 * @return 
	 */
	public ReturnValue delAccountQuery(CorrespondValue correspondValue) throws RemoteException;
	
	/**
     * 查询帐户余额
     * @return 
     * @throws 
     */
	public double accountQuery(CorrespondValue correspondValue,String password) throws RemoteException;

	/**
     * 返回银行对帐信息
     * @return Vector 每项数据为 MoneyInfoValue
     * @throws 
     */
	public Vector<MoneyInfoValue> getBankMoneyInfo(Date date,Vector v) throws RemoteException;
	/**
	 * 模拟银行的超时测试
	 * @return String
	 */
	public String getStr(long second) throws RemoteException;
		
}
