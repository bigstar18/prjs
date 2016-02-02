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

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

public interface BOCCapitalProcessorRMI extends CapitalProcessorRMI {
	/**
	 * 
	 * 通讯检测
	 * @param bankID
	 * @return
	 * @throws RemoteException
	 * @author : taog
	 * @Date :2011-12-28上午10:08:25
	 */
	public ReturnValue CommunicationsTest(String bankID) throws RemoteException;
	/**
	 * 银行发起预签约
	 * @param card
	 * @param account
	 * @param cardtype
	 * @param bankid
	 * @return
	 * @throws RemoteException
	 */
	public CorrespondValue getFirmIDbyCardAndAccount(String card, String cardtype, String accountname) throws RemoteException ;
	/**
	 * 客户端判定连通处理器方法
	 * @return String
	 */
	public ReturnValue checkSigning(CorrespondValue cv) throws RemoteException;
}
