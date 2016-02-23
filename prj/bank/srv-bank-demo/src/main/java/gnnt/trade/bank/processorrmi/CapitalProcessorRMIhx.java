package gnnt.trade.bank.processorrmi;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.ChargeAgainstValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmBankMsg;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyMarket;
import gnnt.trade.bank.vo.ReturnValue;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

public interface CapitalProcessorRMIhx extends CapitalProcessorRMI{
/*******************************************适配器调用接口方法******************************************************/
	/**
	 * 银行发起绑定，市场将银行子账号和子账号名称等添加进去
	 * @param cv
	 * @return
	 */
	public ReturnValue relevanceAccount(CorrespondValue cv)throws RemoteException;
	/**
	 * 子账户同步
	 * @author tongyu
	 * @param correspondValue 交易账号和银行对应关系
	 * @return  long 操作结果：0 成功  非0 失败
	 */
	public ReturnValue synchroAccount(CorrespondValue correspondValue)throws RemoteException;	
	/**
	 * 修改银行流水号
	 */
	public ReturnValue modCapitalInfoStatus(long id, String funID, int status,Timestamp bankTime) throws RemoteException;
	
	/**
	 * 判断是否可以解约
	 */
	public ReturnValue ifQuitFirm(String firmID,String bankID) throws RemoteException;
	/**
	 * 查询交易流水信息
	 */
	public Map<String,CapitalValue> getCapitalValue(Vector<String> contacts,String bankID)throws RemoteException ;
}
