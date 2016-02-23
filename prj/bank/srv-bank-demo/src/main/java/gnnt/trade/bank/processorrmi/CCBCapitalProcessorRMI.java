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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

public interface CCBCapitalProcessorRMI extends CapitalProcessorRMI {
	/**
	 * 查询交易商和账号对应信息
	 * @param firmid
	 * @return
	 * @throws RemoteException
	 */
//	public Vector<CorrespondValue> getCorrespondValueAndfirmUser(String firmid,String bankid) throws RemoteException ;
	/**
	 * 修改银行流水号
	 */
	public ReturnValue modCapitalInfoStatus(long id, String funID, int status,Timestamp bankTime) throws RemoteException;
	/**
	 * 判断是否可以解约
	 */
	public ReturnValue ifQuitFirm(String firmID, String bankID)throws RemoteException ;
	/**
	 * 查询交易流水信息
	 */
	public Map<String,CapitalValue> getCapitalValue(Vector<String> contacts,String bankID)throws RemoteException ;
//	/**
//	 * 建行修改交易商信息（改约）
//	 * @param cv1
//	 * @param cv2
//	 * @return
//	 * @throws RemoteException
//	 */
//	public ReturnValue modAccountbyfirmuser(CorrespondValue cv1, CorrespondValue cv2) throws RemoteException ;
	
	/**
	 * 通过contact获取firmid
	 * @param contact
	 * @return
	 * @throws RemoteException
	 */
	public String getfirmid(String contact) throws RemoteException;

	/**
	 * 修改客户信息，主要用于新增建行账号（两个表同步进行）
	 * @param value 交易账号对象类
	 * @param bankid 银行ID（该项暂时不用，之后可能会用到）
	 * @return
	 */
	public int modfirmuser(FirmValue value,String bankid)throws RemoteException;
	
	/**
	 * 获得符合条件的客户资料
	 * @param filter
	 * @param pageinfo
	 * @param type  资料类型（member为会员页面显示信息，Backstage为管理后台页面显示信息）
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Vector<FirmValue> getFirmUserList(String filter,int[] pageinfo,String type,String key,String bankid)throws RemoteException;
	/**
	 * 获得该客户的信息
	 * @param firmID
	 * @return
	 * @throws RemoteException
	 */
	public FirmValue getFirmValue(FirmValue value)throws RemoteException;
	/**
	 * 获取某个交易商信息
	 * @return
	 * @throws RemoteException
	 */
	public Vector<CorrespondValue> getCorrespondList(String filter)throws RemoteException;
	
	/**
	 * 获取建行新增页面的页码信息
	 * @return
	 * @throws RemoteException
	 */
	public int[] getPageinfo()throws RemoteException;
	/**预签约验证*/
	public ReturnValue checkSigning(FirmValue value)throws RemoteException;
	/**
	 * 同步数据到扩展表
	 * @param firmid
	 * @param bankid
	 * @param key
	 * @param value
	 * @return
	 * @throws RemoteException
	 */
	public int updateFirmInfo(String firmid,String bankid,String key,String value)throws RemoteException;
	public void send(Date tradeDate)throws RemoteException;
}
