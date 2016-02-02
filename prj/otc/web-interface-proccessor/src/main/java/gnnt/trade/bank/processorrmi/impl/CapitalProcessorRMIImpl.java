package gnnt.trade.bank.processorrmi.impl;

import gnnt.trade.bank.CapitalProcessor;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
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

public class CapitalProcessorRMIImpl
  extends UnicastRemoteObject
  implements CapitalProcessorRMI
{
  private static final long serialVersionUID = 1L;
  CapitalProcessor cp = new CapitalProcessor();
  
  public CapitalProcessorRMIImpl()
    throws RemoteException
  {}
  
  public String testRmi()
    throws RemoteException
  {
    this.cp.log("-----------------------------------------testRmi  客户端判定连通处理器方法");
    String result = "test successfully!2222";
    this.cp.log("=========================================testRmi  客户端判定连通处理器方法");
    return result;
  }
  
  public long getMktActionID()
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getMktActionID  取得市场业务流水号");
    long result = this.cp.getMktActionID();
    this.cp.log("=========================================getMktActionID  取得市场业务流水号");
    return result;
  }
  
  public Vector<BankValue> getBankList(String filter)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getBankList  取得银行信息列表");
    Vector<BankValue> result = this.cp.getBankList(filter);
    this.cp.log("=========================================getBankList  取得银行信息列表");
    return result;
  }
  
  public BankValue getBank(String bankID)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getBank  取得银行信息");
    BankValue result = this.cp.getBank(bankID);
    this.cp.log("=========================================getBank  取得银行信息");
    return result;
  }
  
  public ReturnValue modBank(BankValue bankValue)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------modBank  修改银行信息");
    ReturnValue result = this.cp.modBank(bankValue);
    this.cp.log("=========================================modBank  修改银行信息");
    return result;
  }
  
  public ReturnValue changeBankTradeType(Vector<String> bankID, int type, int value)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------changeBankTradeType  银行交易禁用/恢复");
    ReturnValue result = this.cp.changeBankTradeType(bankID, type, value);
    this.cp.log("=========================================changeBankTradeType  银行交易禁用/恢复");
    return result;
  }
  
  public Hashtable<String, String> getBankInfo(String bankID)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getBankInfo  根据银行ID获取市场银行信息");
    Hashtable<String, String> result = this.cp.getBankInfo(bankID);
    this.cp.log("=========================================getBankInfo  根据银行ID获取市场银行信息");
    return result;
  }
  
  public Map<String, CorrespondValue> getCorrespondValue(Vector<String> contacts, String bankID)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getCorrespondValue  获取交易账号银行对应关系");
    Map<String, CorrespondValue> result = this.cp.getCorrespondValue(contacts, bankID);
    this.cp.log("=========================================getCorrespondValue  获取交易账号银行对应关系");
    return result;
  }
  
  public Vector<CorrespondValue> getCorrespondValue(String filter)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getCorrespondValue  获取交易账号银行对应关系");
    Vector<CorrespondValue> result = this.cp.getCorrespondValue(filter);
    this.cp.log("=========================================getCorrespondValue  获取交易账号银行对应关系");
    return result;
  }
  
  public Vector<FirmValue> getFirmUser(String filter)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getFirmUser  取得交易账号信息列表");
    Vector<FirmValue> result = this.cp.getFirmUser(filter);
    this.cp.log("=========================================getFirmUser  取得交易账号信息列表");
    return result;
  }
  
  public long isPassword(String firmID, String password)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------isPassword  验证密码正确性");
    long result = this.cp.isPassword(firmID, password);
    this.cp.log("=========================================isPassword  验证密码正确性");
    return result;
  }
  
  public long modPwd(String firmID, String password, String chpassword)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------modPwd  修改交易账号密码");
    long result = this.cp.modPwd(firmID, password, chpassword);
    this.cp.log("=========================================modPwd  修改交易账号密码");
    return result;
  }
  
  public ReturnValue initializeFrimPwd(String firmID, String password)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------initializeFrimPwd  初始化交易账号密码");
    ReturnValue result = this.cp.initializeFrimPwd(firmID, password);
    this.cp.log("=========================================initializeFrimPwd  初始化交易账号密码");
    return result;
  }
  
  public ReturnValue initializeFrimPwd(String firmID)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------initializeFrimPwd  初始化交易账号密码");
    ReturnValue result = this.cp.initializeFrimPwd(firmID);
    this.cp.log("=========================================initializeFrimPwd  初始化交易账号密码");
    return result;
  }
  
  public ReturnValue modCorrespondStatus(CorrespondValue cav)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------modCorrespondStatus  禁用、恢复交易账号绑定表状态");
    ReturnValue result = this.cp.modCorrespondStatus(cav);
    this.cp.log("=========================================modCorrespondStatus  禁用、恢复交易账号绑定表状态");
    return result;
  }
  
  public long rgstAccount(CorrespondValue correspondValue)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------rgstAccount  银行账号注册");
    long result = this.cp.rgstAccount(correspondValue);
    this.cp.log("=========================================rgstAccount  银行账号注册");
    return result;
  }
  
  public ReturnValue openAccount(CorrespondValue cv)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------openAccount  银行开户方法");
    ReturnValue result = this.cp.openAccount(cv);
    this.cp.log("=========================================openAccount  银行开户方法");
    return result;
  }
  
  public ReturnValue openAccountMarket(CorrespondValue cv)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------openAccountMarket  市场开户方法");
    ReturnValue result = this.cp.openAccountMarket(cv);
    this.cp.log("=========================================openAccountMarket  市场开户方法");
    return result;
  }
  
  public long delAccount(CorrespondValue correspondValue)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------delAccount  银行端发起客户解约");
    long result = this.cp.delAccount(correspondValue);
    this.cp.log("=========================================delAccount  银行端发起客户解约");
    return result;
  }
  
  public ReturnValue delAccountMaket(CorrespondValue correspondValue)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------delAccountMaket  市场端发起客户解约");
    ReturnValue result = this.cp.delAccountMaket(correspondValue);
    this.cp.log("=========================================delAccountMaket  市场端发起客户解约");
    return result;
  }
  
  public ReturnValue modAccount(CorrespondValue cv1, CorrespondValue cv2)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------modAccount  变更账户方法 变更 交易账号代码与银行账号对应关系 的签约状态");
    ReturnValue result = this.cp.modAccount(cv1, cv2);
    this.cp.log("=========================================modAccount  变更账户方法 变更 交易账号代码与银行账号对应关系 的签约状态");
    return result;
  }
  
  public ReturnValue modAccountMarket(CorrespondValue cv1, CorrespondValue cv2)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------modAccountMarket  市场变更账户方法 变更 交易账号代码与银行账号对应关系 的签约状态");
    ReturnValue result = this.cp.modAccountMarket(cv1, cv2);
    this.cp.log("=========================================modAccountMarket  市场变更账户方法 变更 交易账号代码与银行账号对应关系 的签约状态");
    return result;
  }
  
  public long tradeDate(String bankID)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------tradeDate  判断是否可以发生转账交易");
    long result = this.cp.tradeDate(bankID);
    this.cp.log("=========================================tradeDate  判断是否可以发生转账交易");
    return result;
  }
  
  public ReturnValue isTradeDate(Date tradeDate)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------isTradeDate  判断某一天是否是交易日");
    ReturnValue result = this.cp.isTradeDate(tradeDate);
    this.cp.log("=========================================isTradeDate  判断某一天是否是交易日");
    return result;
  }
  
  public boolean getTraderStatus()
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getTraderStatus  取交易系统结算状态");
    boolean result = this.cp.getTraderStatus();
    this.cp.log("=========================================getTraderStatus  取交易系统结算状态");
    return result;
  }
  
  public FirmMessageVo getFirmMSG(String contact)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getFirmMSG  查询交易账号名下交易员信息");
    FirmMessageVo result = this.cp.getFirmMSG(contact);
    this.cp.log("=========================================getFirmMSG  查询交易账号名下交易员信息");
    return result;
  }
  
  public ReturnValue ifbankTrade(String bankID, String firmID, String contact, int type, int launcher)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------ifbankTrade  判断当前是否可以发生转账");
    ReturnValue result = this.cp.ifbankTrade(bankID, firmID, contact, type, launcher);
    this.cp.log("=========================================ifbankTrade  判断当前是否可以发生转账");
    return result;
  }
  
  public long inMoney(String bankID, String firmID, String account, Timestamp bankTime, double money, String funID, long actionID, int funFlag, String remark)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------inMoney  入金(适配器调用)");
    long result = this.cp.inMoney(bankID, firmID, account, bankTime, money, funID, actionID, funFlag, remark);
    this.cp.log("=========================================inMoney  入金(适配器调用)");
    return result;
  }
  
  public ReturnValue inMoneyMarket(InMoneyMarket imm)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------inMoneyMarket  入金(市场方调用)");
    ReturnValue result = this.cp.inMoneyMarket(imm);
    this.cp.log("=========================================inMoneyMarket  入金(市场方调用)");
    return result;
  }
  
  public ReturnValue outMoney(String bankID, double money, String firmID, String bankAccount, String funid, String operator, int express, int type)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------outMoney  出金(适配器调用)");
    ReturnValue result = this.cp.outMoney(bankID, money, firmID, bankAccount, funid, operator, express, type);
    this.cp.log("=========================================outMoney  出金(适配器调用)");
    return result;
  }
  
  public ReturnValue outMoneyMarket(OutMoneyMarket omm)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------outMoneyMarket  出金 (市场方调用)");
    ReturnValue result = this.cp.outMoneyMarket(omm);
    this.cp.log("=========================================outMoneyMarket  出金 (市场方调用)");
    return result;
  }
  
  public ReturnValue moneyInAudit(long actionID, String funID, boolean funFlag)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------moneyInAudit  审核处理中的信息");
    ReturnValue result = this.cp.moneyInAudit(actionID, funID, funFlag);
    this.cp.log("=========================================moneyInAudit  审核处理中的信息");
    return result;
  }
  
  public ReturnValue chargeAgainst(ChargeAgainstValue cav)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------chargeAgainst  冲证");
    ReturnValue result = this.cp.chargeAgainst(cav);
    this.cp.log("=========================================chargeAgainst  冲证");
    return result;
  }
  
  public FirmBalanceValue getMarketBalance(String contact, String bankID)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getMarketBalance  查询交易账号市场资金");
    FirmBalanceValue result = this.cp.getMarketBalance(contact, bankID);
    this.cp.log("=========================================getMarketBalance  查询交易账号市场资金");
    return result;
  }
  
  public FirmBalanceValue getMarketBalance(String firmID, String contact, String bankID)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getMarketBalance  查询交易账号市场资金");
    FirmBalanceValue result = this.cp.getMarketBalance(firmID, contact, bankID);
    this.cp.log("=========================================getMarketBalance  查询交易账号市场资金");
    return result;
  }
  
  public Vector<FirmBankMsg> getfirmBankMsg(String filter)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getfirmBankMsg  查询交易商银行信息");
    Vector<FirmBankMsg> result = this.cp.getfirmBankMsg(filter);
    this.cp.log("=========================================getfirmBankMsg  查询交易商银行信息");
    return result;
  }
  
  public FirmBalanceValue getFirmBalance(String bankid, String firmid, String pwd)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getFirmBalance  查询交易账号市场可用资金和银行账号余额");
    FirmBalanceValue result = this.cp.getFirmBalance(bankid, firmid, pwd);
    this.cp.log("=========================================getFirmBalance  查询交易账号市场可用资金和银行账号余额");
    return result;
  }
  
  public Vector<CapitalValue> getCapitalList(String filter)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getCapitalList  获取资金流水数据");
    Vector<CapitalValue> result = this.cp.getCapitalList(filter);
    this.cp.log("=========================================getCapitalList  获取资金流水数据");
    return result;
  }
  
  public int getBankCompareInfo(String bankID, Date date)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getBankCompareInfo  获取银行对账信息");
    int result = this.cp.getBankCompareInfo(bankID, date);
    this.cp.log("=========================================getBankCompareInfo  获取银行对账信息");
    return result;
  }
  
  public int insertBankCompareInfo(Vector<MoneyInfoValue> list)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------insertBankCompareInfo  写入银行对账信息");
    int result = this.cp.insertBankCompareInfo(list);
    this.cp.log("=========================================insertBankCompareInfo  写入银行对账信息");
    return result;
  }
  
  public long insertBankMoneyInfo(Vector<MoneyInfoValue> mv, int ready)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------insertBankMoneyInfo  提供给适配器的保存银行数据的方法");
    long result = this.cp.insertBankMoneyInfo(mv, ready);
    this.cp.log("=========================================insertBankMoneyInfo  提供给适配器的保存银行数据的方法");
    return result;
  }
  
  public ReturnValue roughInfo(String bankID, Date tradeDate)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------roughInfo  处理日间产生的对账不平数据");
    ReturnValue result = this.cp.roughInfo(bankID, tradeDate);
    this.cp.log("=========================================roughInfo  处理日间产生的对账不平数据");
    return result;
  }
  
  public Vector<CompareResult> getBankNoInfo(String bankID, Date tradeDate)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getBankNoInfo  查询市场有，银行没有流水的记录信息");
    Vector<CompareResult> result = this.cp.getBankNoInfo(bankID, tradeDate);
    this.cp.log("=========================================getBankNoInfo  查询市场有，银行没有流水的记录信息");
    return result;
  }
  
  public Vector<CompareResult> getMarketNoInfo(String bankID, Date tradeDate)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getMarketNoInfo  查询银行有，市场没有流水的记录信息");
    Vector<CompareResult> result = this.cp.getMarketNoInfo(bankID, tradeDate);
    this.cp.log("=========================================getMarketNoInfo  查询银行有，市场没有流水的记录信息");
    return result;
  }
  
  public Vector<CapitalCompare> sumResultInfo(String bankID, String[] firmIDs, Date date)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------sumResultInfo  查询交易账号当天出入金求和数据");
    Vector<CapitalCompare> result = this.cp.sumResultInfo(bankID, firmIDs, date);
    this.cp.log("=========================================sumResultInfo  查询交易账号当天出入金求和数据");
    return result;
  }
  
  public ReturnValue refuseCapitalInfo(Vector<Long> actionIDs)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------refuseCapitalInfo  批量冲正成功流水");
    ReturnValue result = this.cp.refuseCapitalInfo(actionIDs);
    this.cp.log("=========================================refuseCapitalInfo  批量冲正成功流水");
    return result;
  }
  
  public ReturnValue refuseCapitalInfo(long actionID)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------refuseCapitalInfo  冲正银行没有成功的流水");
    ReturnValue result = this.cp.refuseCapitalInfo(actionID);
    this.cp.log("=========================================refuseCapitalInfo  冲正银行没有成功的流水");
    return result;
  }
  
  public ReturnValue supplyCapitalInfo(Vector<CapitalValue> vector)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------supplyCapitalInfo  批量导入银行成功流水");
    ReturnValue result = this.cp.supplyCapitalInfo(vector);
    this.cp.log("=========================================supplyCapitalInfo  批量导入银行成功流水");
    return result;
  }
  
  public ReturnValue supplyCapitalInfo(CapitalValue value)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------supplyCapitalInfo  添加成功资金流水");
    ReturnValue result = this.cp.supplyCapitalInfo(value);
    this.cp.log("=========================================supplyCapitalInfo  添加成功资金流水");
    return result;
  }
  
  public Vector<InterfaceLog> interfaceLogList(String filter, int[] pageinfo)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------interfaceLogList  查询银行接口和银行通讯信息");
    Vector<InterfaceLog> result = this.cp.interfaceLogList(filter, pageinfo);
    this.cp.log("=========================================interfaceLogList  查询银行接口和银行通讯信息");
    return result;
  }
  
  public int interfaceLog(InterfaceLog log)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------interfaceLog  录入银行接口和银行通讯信息");
    int result = this.cp.interfaceLog(log);
    this.cp.log("=========================================interfaceLog  录入银行接口和银行通讯信息");
    return result;
  }
  
  public ReturnValue relevanceAccount(CorrespondValue cv)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------supplyCapitalInfo 银行发起绑定");
    ReturnValue result = this.cp.relevanceAccount(cv);
    this.cp.log("=========================================supplyCapitalInfo  添加成功银行发起绑定");
    return result;
  }
  
  public ReturnValue synchroAccount(CorrespondValue correspondValue)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------synchroAccount  子账户同步");
    ReturnValue result = this.cp.synchroAccount(correspondValue);
    this.cp.log("=========================================synchroAccount  子账户同步");
    return result;
  }
  
  public Vector<FirmValue> getFirmUserList(String filter, int[] pageinfo, String type, String key, String bankid)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getCorrespondList  查询会员信息表会员信息");
    Vector<FirmValue> result = this.cp.getFirmUserList(filter, pageinfo, type, key, bankid);
    this.cp.log("=========================================getCorrespondList  查询会员信息表会员信息");
    return result;
  }
  
  public int[] getPageinfo()
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getPageinfo  获取建行新增页面页码信息");
    int[] result = this.cp.getPageinfo();
    this.cp.log("-----------------------------------------getPageinfo  获取建行新增页面页码信息");
    return result;
  }
  
  public ReturnValue outMoneyAudit(long actionID, boolean funFlag)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------outMoneyAudit  出金审核");
    ReturnValue result = this.cp.outMoneyAudit(actionID, funFlag);
    this.cp.log("=========================================outMoneyAudit  出金审核");
    return result;
  }
  
  public String getfirmid(String contact)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------getfirmid  获取交易商firmid");
    String ss = this.cp.getfirmid(contact);
    this.cp.log("-----------------------------------------getfirmid  获取交易商firmid");
    return ss;
  }
  
  public ReturnValue ifQuitFirm(String firmID, String bankID)
    throws RemoteException
  {
    this.cp.log("-----------------------------------------ifQuitFirm  判断是否可以解约");
    ReturnValue result = this.cp.ifQuitFirm(firmID, bankID);
    this.cp.log("-----------------------------------------ifQuitFirm  判断是否可以解约");
    return result;
  }
  
  public ReturnValue sendOldQS(Date date)
    throws RemoteException
  {
    return null;
  }
}
