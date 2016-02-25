package gnnt.trade.bank.processorrmi.impl;

import gnnt.trade.bank.PACapitalProcessor;
import gnnt.trade.bank.data.sfz.vo.BatCustDzBChild;
import gnnt.trade.bank.data.sfz.vo.BatCustDzFailChild;
import gnnt.trade.bank.data.sfz.vo.BatFailResultChild;
import gnnt.trade.bank.data.sfz.vo.KXHfailChild;
import gnnt.trade.bank.processorrmi.PACapitalProcessorRMI;
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
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

public class PACapitalProcessorRMIImpl
  extends UnicastRemoteObject
  implements PACapitalProcessorRMI
{
  private static final long serialVersionUID = 2L;
  PACapitalProcessor cp = new PACapitalProcessor();
  
  public PACapitalProcessorRMIImpl()
    throws RemoteException
  {}
  
  public ReturnValue getBankFileStatus(Date tradeDate, int type, String bankID)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getBankFileStatus  查看银行生成文件的状态");
    ReturnValue result = this.cp.getBankFileStatus(tradeDate, type, bankID);
    this.cp.log("=========================================getBankFileStatus  查看银行生成文件的状态");
    return result;
  }
  
  public ReturnValue getBatCustDz(Vector<BatCustDzFailChild> bcd, Date date, String bankID)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getBatCustDz  获取银行发送来的对账不平文件");
    ReturnValue result = this.cp.getBatCustDz(bcd, date, bankID);
    this.cp.log("=========================================getBatCustDz  获取银行发送来的对账不平文件");
    return result;
  }
  
  public ReturnValue getFirmBalanceError(Date date, String bankID)
    throws RemoteException
  {
    return null;
  }
  
  public ReturnValue getFirmBalanceError(Vector<BatFailResultChild> fbe, Date date, String bankID)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getFirmBalanceError  获取银行交易商对账失败文件");
    ReturnValue result = this.cp.getFirmBalanceError(fbe, date, bankID);
    this.cp.log("=========================================getFirmBalanceError  获取银行交易商对账失败文件");
    return result;
  }
  
  public ReturnValue getfirmBalanceFile(Date date, String bankID)
    throws RemoteException
  {
    return null;
  }
  
  public ReturnValue getfirmBalanceFile(Vector<BatCustDzBChild> fbf, Date date, String bankID)
    throws RemoteException
  {
    return this.cp.getfirmBalanceFile(fbf, date, bankID);
  }
  
  public ReturnValue relevanceAccount(CorrespondValue cv)
    throws RemoteException
  {
    return this.cp.relevanceAccount(cv);
  }
  
  public ReturnValue saveFirmKXH(Vector<KXHfailChild> vector, String bankID)
    throws RemoteException
  {
    return this.cp.saveFirmKXH(vector, bankID);
  }
  
  public ReturnValue saveFirmKXH(Date date, String bankID)
    throws RemoteException
  {
    return null;
  }
  
  public ReturnValue sentMaketQS(Date date, String bankID)
    throws RemoteException
  {
    return this.cp.sentMaketQS(date, bankID);
  }
  
  public ReturnValue changeBankTradeType(Vector<String> bankID, int type, int value)
    throws RemoteException
  {
    return this.cp.changeBankTradeType(bankID, type, value);
  }
  
  public ReturnValue chargeAgainst(ChargeAgainstValue cav)
    throws RemoteException
  {
    return this.cp.chargeAgainst(cav);
  }
  
  public long delAccount(CorrespondValue correspondValue)
    throws RemoteException
  {
    return this.cp.delAccount(correspondValue);
  }
  
  public ReturnValue delAccountMaket(CorrespondValue correspondValue)
    throws RemoteException
  {
    return this.cp.delAccountMaket(correspondValue);
  }
  
  public BankValue getBank(String bankID)
    throws RemoteException
  {
    return this.cp.getBank(bankID);
  }
  
  public int getBankCompareInfo(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.getBankCompareInfo(bankID, date);
  }
  
  public Hashtable<String, String> getBankInfo(String bankID)
    throws RemoteException
  {
    return this.cp.getBankInfo(bankID);
  }
  
  public Vector<BankValue> getBankList(String filter)
    throws RemoteException
  {
    return this.cp.getBankList(filter);
  }
  
  public Vector<CompareResult> getBankNoInfo(String bankID, Date tradeDate)
    throws RemoteException
  {
    return this.cp.getBankNoInfo(bankID, tradeDate);
  }
  
  public Vector<CapitalValue> getCapitalList(String filter)
    throws RemoteException
  {
    return this.cp.getCapitalList(filter);
  }
  
  public Map<String, CapitalValue> getCapitalValue(Vector<String> contacts, String bankID)
    throws RemoteException
  {
    return this.cp.getCapitalValue(contacts, bankID);
  }
  
  public Map<String, CorrespondValue> getCorrespondValue(Vector<String> contacts, String bankID)
    throws RemoteException
  {
    return this.cp.getCorrespondValue(contacts, bankID);
  }
  
  public Vector<CorrespondValue> getCorrespondValue(String filter)
    throws RemoteException
  {
    return this.cp.getCorrespondValue(filter);
  }
  
  public FirmBalanceValue getFirmBalance(String bankid, String firmid, String pwd)
    throws RemoteException
  {
    return this.cp.getFirmBalance(bankid, firmid, pwd);
  }
  
  public FirmMessageVo getFirmMSG(String contact)
    throws RemoteException
  {
    return this.cp.getFirmMSG(contact);
  }
  
  public Vector<FirmValue> getFirmUser(String filter)
    throws RemoteException
  {
    return this.cp.getFirmUser(filter);
  }
  
  public FirmBalanceValue getMarketBalance(String contact, String bankID)
    throws RemoteException
  {
    return this.cp.getMarketBalance(contact, bankID);
  }
  
  public FirmBalanceValue getMarketBalance(String firmID, String contact, String bankID)
    throws RemoteException
  {
    return this.cp.getMarketBalance(firmID, contact, bankID);
  }
  
  public Vector<CompareResult> getMarketNoInfo(String bankID, Date tradeDate)
    throws RemoteException
  {
    return this.cp.getMarketNoInfo(bankID, tradeDate);
  }
  
  public long getMktActionID()
    throws RemoteException
  {
    return this.cp.getMktActionID();
  }
  
  public boolean getTraderStatus()
    throws RemoteException
  {
    return this.cp.getTraderStatus();
  }
  
  public Vector<FirmBankMsg> getfirmBankMsg(String filter)
    throws RemoteException
  {
    return this.cp.getfirmBankMsg(filter);
  }
  
  public ReturnValue ifQuitFirm(String firmID, String bankID)
    throws RemoteException
  {
    return this.cp.ifQuitFirm(firmID, bankID);
  }
  
  public ReturnValue ifbankTrade(String bankID, String firmID, String contact, int type, int launcher)
    throws RemoteException
  {
    return this.cp.ifbankTrade(bankID, firmID, contact, type, launcher);
  }
  
  public long inMoney(String bankID, String firmID, String account, Timestamp bankTime, double money, String funID, long actionID, int funFlag, String remark)
    throws RemoteException
  {
    return this.cp.inMoney(bankID, firmID, account, bankTime, money, funID, actionID, funFlag, remark);
  }
  
  public ReturnValue inMoneyMarket(InMoneyMarket imm)
    throws RemoteException
  {
    return this.cp.inMoneyMarket(imm);
  }
  
  public ReturnValue initializeFrimPwd(String firmID, String password)
    throws RemoteException
  {
    return this.cp.initializeFrimPwd(firmID, password);
  }
  
  public ReturnValue initializeFrimPwd(String firmID)
    throws RemoteException
  {
    return this.cp.initializeFrimPwd(firmID);
  }
  
  public int insertBankCompareInfo(Vector<MoneyInfoValue> list)
    throws RemoteException
  {
    return this.cp.insertBankCompareInfo(list);
  }
  
  public long insertBankMoneyInfo(Vector<MoneyInfoValue> mv, int ready)
    throws RemoteException
  {
    return this.cp.insertBankMoneyInfo(mv, ready);
  }
  
  public int interfaceLog(InterfaceLog log)
    throws RemoteException
  {
    return this.cp.interfaceLog(log);
  }
  
  public Vector<InterfaceLog> interfaceLogList(String filter, int[] pageinfo)
    throws RemoteException
  {
    return this.cp.interfaceLogList(filter, pageinfo);
  }
  
  public long isPassword(String firmID, String password)
    throws RemoteException
  {
    return this.cp.isPassword(firmID, password);
  }
  
  public ReturnValue isTradeDate(Date tradeDate)
    throws RemoteException
  {
    return this.cp.isTradeDate(tradeDate);
  }
  
  public ReturnValue modAccount(CorrespondValue cv1, CorrespondValue cv2)
    throws RemoteException
  {
    return this.cp.modAccount(cv1, cv2);
  }
  
  public ReturnValue modAccountMarket(CorrespondValue cv1, CorrespondValue cv2)
    throws RemoteException
  {
    return this.cp.modAccountMarket(cv1, cv2);
  }
  
  public ReturnValue modBank(BankValue bankValue)
    throws RemoteException
  {
    return this.cp.modBank(bankValue);
  }
  
  public ReturnValue modCapitalInfoStatus(long id, String funID, int status, Timestamp bankTime)
    throws RemoteException
  {
    return this.cp.modCapitalInfoStatus(id, funID, status, bankTime);
  }
  
  public ReturnValue modCorrespondStatus(CorrespondValue cav)
    throws RemoteException
  {
    return this.cp.modCorrespondStatus(cav);
  }
  
  public long modPwd(String firmID, String password, String chpassword)
    throws RemoteException
  {
    return this.cp.modPwd(firmID, password, chpassword);
  }
  
  public ReturnValue moneyInAudit(long actionID, String funID, boolean funFlag)
    throws RemoteException
  {
    return this.cp.moneyInAudit(actionID, funID, funFlag);
  }
  
  public ReturnValue openAccount(CorrespondValue cv)
    throws RemoteException
  {
    return this.cp.openAccount(cv);
  }
  
  public ReturnValue openAccountMarket(CorrespondValue cv)
    throws RemoteException
  {
    return this.cp.openAccountMarket(cv);
  }
  
  public ReturnValue outMoney(String bankID, double money, String firmID, String bankAccount, String funid, String operator, int express, int type)
    throws RemoteException
  {
    return this.cp.outMoney(bankID, money, firmID, bankAccount, funid, operator, express, type);
  }
  
  public ReturnValue outMoneyAudit(long actionID, boolean funFlag)
    throws RemoteException
  {
    return this.cp.outMoneyAudit(actionID, funFlag);
  }
  
  public ReturnValue outMoneyMarket(OutMoneyMarket omm)
    throws RemoteException
  {
    return this.cp.outMoneyMarket(omm);
  }
  
  public ReturnValue refuseCapitalInfo(Vector<Long> actionIDs)
    throws RemoteException
  {
    return this.cp.refuseCapitalInfo(actionIDs);
  }
  
  public ReturnValue refuseCapitalInfo(long actionID)
    throws RemoteException
  {
    return this.cp.refuseCapitalInfo(actionID);
  }
  
  public long rgstAccount(CorrespondValue correspondValue)
    throws RemoteException
  {
    return this.cp.rgstAccount(correspondValue);
  }
  
  public ReturnValue roughInfo(String bankID, Date tradeDate)
    throws RemoteException
  {
    return this.cp.roughInfo(bankID, tradeDate);
  }
  
  public Vector<CapitalCompare> sumResultInfo(String bankID, String[] firmIDs, Date date)
    throws RemoteException
  {
    return this.cp.sumResultInfo(bankID, firmIDs, date);
  }
  
  public ReturnValue supplyCapitalInfo(Vector<CapitalValue> vector)
    throws RemoteException
  {
    return this.cp.supplyCapitalInfo(vector);
  }
  
  public ReturnValue supplyCapitalInfo(CapitalValue value)
    throws RemoteException
  {
    return this.cp.supplyCapitalInfo(value);
  }
  
  public String testRmi()
    throws RemoteException
  {
    return null;
  }
  
  public long tradeDate(String bankID)
    throws RemoteException
  {
    return this.cp.tradeDate(bankID);
  }
  
  public ReturnValue destroyAccount(CorrespondValue cv)
    throws RemoteException
  {
    return this.cp.destroyAccount(cv);
  }
  
  public FirmBalanceValue getMarketBalance(String firmid)
    throws RemoteException
  {
    return this.cp.getMarketBalance(firmid);
  }
  
  public String getfirmid(String contact)
    throws RemoteException
  {
    return this.cp.getfirmid(contact);
  }
  
  public Vector<FirmValue> getFirmUserList(String filter, int[] pageinfo, String type, String key, String bankid)
    throws RemoteException
  {
    return null;
  }
  
  public int[] getPageinfo()
    throws RemoteException
  {
    return null;
  }
  
  public ReturnValue sendOldQS(Date date)
    throws RemoteException
  {
    return null;
  }
  
  public ReturnValue synchroAccount(CorrespondValue correspondValue)
    throws RemoteException
  {
    return null;
  }
  
  public ReturnValue modAccount(CorrespondValue cv1, CorrespondValue cv2, String bankID)
    throws RemoteException
  {
    return null;
  }
}
