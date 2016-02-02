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
public class CapitalProcessorRMIImpl extends UnicastRemoteObject implements CapitalProcessorRMI {
	private static final long serialVersionUID = 1L;
	CapitalProcessor cp = new CapitalProcessor();
	public CapitalProcessorRMIImpl() throws RemoteException {
		super();
	}
	public String testRmi() throws RemoteException {
		cp.log("-----------------------------------------testRmi  客户端判定连通处理器方法");
		String result = "test successfully!";
		cp.log("=========================================testRmi  客户端判定连通处理器方法");
		return result;
	}
	public long getMktActionID() throws RemoteException {
		cp.log("-----------------------------------------getMktActionID  取得市场业务流水号");
		long result = cp.getMktActionID();
		cp.log("=========================================getMktActionID  取得市场业务流水号");
		return result;
	}
	public Vector<BankValue> getBankList(String filter) throws RemoteException{
		cp.log("-----------------------------------------getBankList  取得银行信息列表");
		Vector<BankValue> result = cp.getBankList(filter);
		cp.log("=========================================getBankList  取得银行信息列表");
		return result;
	}
	public BankValue getBank(String bankID) throws RemoteException{
		cp.log("-----------------------------------------getBank  取得银行信息");
		BankValue result = cp.getBank(bankID);
		cp.log("=========================================getBank  取得银行信息");
		return result;
	}
	public ReturnValue modBank(BankValue bankValue) throws RemoteException{
		cp.log("-----------------------------------------modBank  修改银行信息");
		ReturnValue result = cp.modBank(bankValue);
		cp.log("=========================================modBank  修改银行信息");
		return result;
	}
	public ReturnValue changeBankTradeType(Vector<String> bankID,int type,int value) throws RemoteException{
		cp.log("-----------------------------------------changeBankTradeType  银行交易禁用/恢复");
		ReturnValue result = cp.changeBankTradeType(bankID,type,value);
		cp.log("=========================================changeBankTradeType  银行交易禁用/恢复");
		return result;
	}
	public Hashtable<String, String> getBankInfo(String bankID) throws RemoteException {
		cp.log("-----------------------------------------getBankInfo  根据银行ID获取市场银行信息");
		Hashtable<String, String> result = cp.getBankInfo(bankID);
		cp.log("=========================================getBankInfo  根据银行ID获取市场银行信息");
		return result;
	}
	public Map<String,CorrespondValue> getCorrespondValue(Vector<String> contacts,String bankID)  throws RemoteException{
		cp.log("-----------------------------------------getCorrespondValue  获取交易账号银行对应关系");
		Map<String,CorrespondValue> result = cp.getCorrespondValue(contacts, bankID);
		cp.log("=========================================getCorrespondValue  获取交易账号银行对应关系");
		return result;
	}
	public Vector<CorrespondValue> getCorrespondValue(String filter) throws RemoteException {
		cp.log("-----------------------------------------getCorrespondValue  获取交易账号银行对应关系");
		Vector<CorrespondValue> result = cp.getCorrespondValue(filter);
		cp.log("=========================================getCorrespondValue  获取交易账号银行对应关系");
		return result;
	}
	public Vector<FirmValue> getFirmUser(String filter) throws RemoteException {
		cp.log("-----------------------------------------getFirmUser  取得交易账号信息列表");
		Vector<FirmValue> result = cp.getFirmUser(filter);
		cp.log("=========================================getFirmUser  取得交易账号信息列表");
		return result;
	}
	public long isPassword(String firmID, String password) throws RemoteException{
		cp.log("-----------------------------------------isPassword  验证密码正确性");
		long result = cp.isPassword(firmID, password);
		cp.log("=========================================isPassword  验证密码正确性");
		return result;
	}
	public long modPwd(String firmID, String password, String chpassword) throws RemoteException{
		cp.log("-----------------------------------------modPwd  修改交易账号密码");
		long result = cp.modPwd(firmID, password, chpassword);
		cp.log("=========================================modPwd  修改交易账号密码");
		return result;
	}
	public ReturnValue initializeFrimPwd(String firmID,String password) throws RemoteException{
		cp.log("-----------------------------------------initializeFrimPwd  初始化交易账号密码");
		ReturnValue result = cp.initializeFrimPwd(firmID,password);
		cp.log("=========================================initializeFrimPwd  初始化交易账号密码");
		return result;
	}
	public ReturnValue initializeFrimPwd(String firmID) throws RemoteException{
		cp.log("-----------------------------------------initializeFrimPwd  初始化交易账号密码");
		ReturnValue result = cp.initializeFrimPwd(firmID);
		cp.log("=========================================initializeFrimPwd  初始化交易账号密码");
		return result;
	}
	public ReturnValue modCorrespondStatus(CorrespondValue cav) throws RemoteException{
		cp.log("-----------------------------------------modCorrespondStatus  禁用、恢复交易账号绑定表状态");
		ReturnValue result = cp.modCorrespondStatus(cav);
		cp.log("=========================================modCorrespondStatus  禁用、恢复交易账号绑定表状态");
		return result;
	}
	public long rgstAccount(CorrespondValue correspondValue) throws RemoteException {
		cp.log("-----------------------------------------rgstAccount  银行账号注册");
		long result = cp.rgstAccount(correspondValue);
		cp.log("=========================================rgstAccount  银行账号注册");
		return result;
	}
	public ReturnValue openAccount(CorrespondValue cv) throws RemoteException {
		cp.log("-----------------------------------------openAccount  银行开户方法");
		ReturnValue result = cp.openAccount(cv);
		cp.log("=========================================openAccount  银行开户方法");
		return result;
	}
	public ReturnValue openAccountMarket(CorrespondValue cv) throws RemoteException{
		cp.log("-----------------------------------------openAccountMarket  市场开户方法");
		ReturnValue result = cp.openAccountMarket(cv);
		cp.log("=========================================openAccountMarket  市场开户方法");
		return result;
	}
	public long delAccount(CorrespondValue correspondValue) throws RemoteException {
		cp.log("-----------------------------------------delAccount  银行端发起客户解约");
		long result = cp.delAccount(correspondValue);
		cp.log("=========================================delAccount  银行端发起客户解约");
		return result;
	}
	public ReturnValue delAccountMaket(CorrespondValue correspondValue) throws RemoteException{
		cp.log("-----------------------------------------delAccountMaket  市场端发起客户解约");
		ReturnValue result = cp.delAccountMaket(correspondValue);
		cp.log("=========================================delAccountMaket  市场端发起客户解约");
		return result;
	}
	public ReturnValue modAccount(CorrespondValue cv1, CorrespondValue cv2) throws RemoteException {
		cp.log("-----------------------------------------modAccount  变更账户方法 变更 交易账号代码与银行账号对应关系 的签约状态");
		ReturnValue result = cp.modAccount(cv1, cv2);
		cp.log("=========================================modAccount  变更账户方法 变更 交易账号代码与银行账号对应关系 的签约状态");
		return result;
	}
	public ReturnValue modAccountMarket(CorrespondValue cv1,CorrespondValue cv2) throws RemoteException{
		cp.log("-----------------------------------------modAccountMarket  市场变更账户方法 变更 交易账号代码与银行账号对应关系 的签约状态");
		ReturnValue result = cp.modAccountMarket(cv1, cv2);
		cp.log("=========================================modAccountMarket  市场变更账户方法 变更 交易账号代码与银行账号对应关系 的签约状态");
		return result;
	}
	public long tradeDate(String bankID) throws RemoteException{
		cp.log("-----------------------------------------tradeDate  判断是否可以发生转账交易");
		long result = cp.tradeDate(bankID);
		cp.log("=========================================tradeDate  判断是否可以发生转账交易");
		return result;
	}
	public ReturnValue isTradeDate(java.util.Date tradeDate) throws RemoteException{
		cp.log("-----------------------------------------isTradeDate  判断某一天是否是交易日");
		ReturnValue result = cp.isTradeDate(tradeDate);
		cp.log("=========================================isTradeDate  判断某一天是否是交易日");
		return result;
	}
	public boolean getTraderStatus() throws RemoteException{
		cp.log("-----------------------------------------getTraderStatus  取交易系统结算状态");
		boolean result = cp.getTraderStatus();
		cp.log("=========================================getTraderStatus  取交易系统结算状态");
		return result;
	}
	public FirmMessageVo getFirmMSG(String contact)  throws RemoteException{
		cp.log("-----------------------------------------getFirmMSG  查询交易账号名下交易员信息");
		FirmMessageVo result = cp.getFirmMSG(contact);
		cp.log("=========================================getFirmMSG  查询交易账号名下交易员信息");
		return result;
	}
	public ReturnValue ifbankTrade(String bankID,String firmID,String contact,int type,int launcher) throws RemoteException{
		cp.log("-----------------------------------------ifbankTrade  判断当前是否可以发生转账");
		ReturnValue result = cp.ifbankTrade(bankID, firmID,contact, type, launcher);
		cp.log("=========================================ifbankTrade  判断当前是否可以发生转账");
		return result;
	}
	public long inMoney(String bankID, String firmID, String account, Timestamp bankTime, double money, String funID, long actionID, int funFlag, String remark) throws RemoteException {
		cp.log("-----------------------------------------inMoney  入金(适配器调用)");
		long result = cp.inMoney(bankID, firmID, account, bankTime, money, funID, actionID, funFlag, remark);
		cp.log("=========================================inMoney  入金(适配器调用)");
		return result;
	}
	public ReturnValue inMoneyMarket(InMoneyMarket imm) throws RemoteException {
		cp.log("-----------------------------------------inMoneyMarket  入金(市场方调用)");
		ReturnValue result = cp.inMoneyMarket(imm);
		cp.log("=========================================inMoneyMarket  入金(市场方调用)");
		return result;
	}
	public ReturnValue outMoney(String bankID, double money, String firmID, String bankAccount, String funid,String operator, int express, int type) throws RemoteException {
		cp.log("-----------------------------------------outMoney  出金(适配器调用)");
		ReturnValue result = cp.outMoney(bankID, money, firmID, bankAccount,funid, operator, express, type);
		cp.log("=========================================outMoney  出金(适配器调用)");
		return result;
	}
	public ReturnValue outMoneyMarket(OutMoneyMarket omm)throws RemoteException{
		cp.log("-----------------------------------------outMoneyMarket  出金 (市场方调用)");
		ReturnValue result = cp.outMoneyMarket(omm);
		cp.log("=========================================outMoneyMarket  出金 (市场方调用)");
		return result;
	}
	public ReturnValue moneyInAudit(long actionID,String funID,boolean funFlag) throws RemoteException{
		cp.log("-----------------------------------------moneyInAudit  审核处理中的信息");
		ReturnValue result = cp.moneyInAudit(actionID,funID, funFlag);
		cp.log("=========================================moneyInAudit  审核处理中的信息");
		return result;
	}
	public ReturnValue chargeAgainst(ChargeAgainstValue cav) throws RemoteException {
		cp.log("-----------------------------------------chargeAgainst  冲证");
		ReturnValue result = cp.chargeAgainst(cav);
		cp.log("=========================================chargeAgainst  冲证");
		return result;
	}
	public FirmBalanceValue getMarketBalance(String contact,String bankID) throws RemoteException {
		cp.log("-----------------------------------------getMarketBalance  查询交易账号市场资金");
		FirmBalanceValue result = cp.getMarketBalance(contact,bankID);
		cp.log("=========================================getMarketBalance  查询交易账号市场资金");
		return result;
	}
	public FirmBalanceValue getMarketBalance(String firmID,String contact,String bankID) throws RemoteException{
		cp.log("-----------------------------------------getMarketBalance  查询交易账号市场资金");
		FirmBalanceValue result = cp.getMarketBalance(firmID, contact,bankID);
		cp.log("=========================================getMarketBalance  查询交易账号市场资金");
		return result;
	}
	public Vector<FirmBankMsg> getfirmBankMsg(String filter) throws RemoteException{
		cp.log("-----------------------------------------getfirmBankMsg  查询交易商银行信息");
		Vector<FirmBankMsg> result = cp.getfirmBankMsg(filter);
		cp.log("=========================================getfirmBankMsg  查询交易商银行信息");
		return result;
	}
	public FirmBalanceValue getFirmBalance(String bankid, String firmid, String pwd) throws RemoteException {
		cp.log("-----------------------------------------getFirmBalance  查询交易账号市场可用资金和银行账号余额");
		FirmBalanceValue result = cp.getFirmBalance(bankid, firmid, pwd);
		cp.log("=========================================getFirmBalance  查询交易账号市场可用资金和银行账号余额");
		return result;
	}
	public Vector<CapitalValue> getCapitalList(String filter) throws RemoteException {
		cp.log("-----------------------------------------getCapitalList  获取资金流水数据");
		Vector<CapitalValue> result = cp.getCapitalList(filter);
		cp.log("=========================================getCapitalList  获取资金流水数据");
		return result;
	}
	public int getBankCompareInfo(String bankID, Date date) throws RemoteException {
		cp.log("-----------------------------------------getBankCompareInfo  获取银行对账信息");
		int result = cp.getBankCompareInfo(bankID, date);
		cp.log("=========================================getBankCompareInfo  获取银行对账信息");
		return result;
	}
	public int insertBankCompareInfo(Vector<MoneyInfoValue> list) throws RemoteException {
		cp.log("-----------------------------------------insertBankCompareInfo  写入银行对账信息");
		int result = cp.insertBankCompareInfo(list);
		cp.log("=========================================insertBankCompareInfo  写入银行对账信息");
		return result;
	}
	public long insertBankMoneyInfo(Vector<MoneyInfoValue> mv, int ready) throws RemoteException {
		cp.log("-----------------------------------------insertBankMoneyInfo  提供给适配器的保存银行数据的方法");
		long result = cp.insertBankMoneyInfo(mv, ready);
		cp.log("=========================================insertBankMoneyInfo  提供给适配器的保存银行数据的方法");
		return result;
	}
	public ReturnValue roughInfo(String bankID,java.util.Date tradeDate) throws RemoteException{
		cp.log("-----------------------------------------roughInfo  处理日间产生的对账不平数据");
		ReturnValue result = cp.roughInfo(bankID, tradeDate);
		cp.log("=========================================roughInfo  处理日间产生的对账不平数据");
		return result;
	}
	public Vector<CompareResult> getBankNoInfo(String bankID,java.util.Date tradeDate) throws RemoteException{
		cp.log("-----------------------------------------getBankNoInfo  查询市场有，银行没有流水的记录信息");
		Vector<CompareResult> result = cp.getBankNoInfo(bankID, tradeDate);
		cp.log("=========================================getBankNoInfo  查询市场有，银行没有流水的记录信息");
		return result;
	}
	public Vector<CompareResult> getMarketNoInfo(String bankID,java.util.Date tradeDate) throws RemoteException{
		cp.log("-----------------------------------------getMarketNoInfo  查询银行有，市场没有流水的记录信息");
		Vector<CompareResult> result = cp.getMarketNoInfo(bankID, tradeDate);
		cp.log("=========================================getMarketNoInfo  查询银行有，市场没有流水的记录信息");
		return result;
	}
	public Vector<CapitalCompare> sumResultInfo(String bankID,String[] firmIDs,java.util.Date date) throws RemoteException{
		cp.log("-----------------------------------------sumResultInfo  查询交易账号当天出入金求和数据");
		Vector<CapitalCompare> result = cp.sumResultInfo(bankID, firmIDs, date);
		cp.log("=========================================sumResultInfo  查询交易账号当天出入金求和数据");
		return result;
	}
	public ReturnValue refuseCapitalInfo(Vector<Long> actionIDs) throws RemoteException{
		cp.log("-----------------------------------------refuseCapitalInfo  批量冲正成功流水");
		ReturnValue result = cp.refuseCapitalInfo(actionIDs);
		cp.log("=========================================refuseCapitalInfo  批量冲正成功流水");
		return result;
	}
	public ReturnValue refuseCapitalInfo(long actionID) throws RemoteException {
		cp.log("-----------------------------------------refuseCapitalInfo  冲正银行没有成功的流水");
		ReturnValue result = cp.refuseCapitalInfo(actionID);
		cp.log("=========================================refuseCapitalInfo  冲正银行没有成功的流水");
		return result;
	}
	public ReturnValue supplyCapitalInfo(Vector<CapitalValue> vector) throws RemoteException{
		cp.log("-----------------------------------------supplyCapitalInfo  批量导入银行成功流水");
		ReturnValue result = cp.supplyCapitalInfo(vector);
		cp.log("=========================================supplyCapitalInfo  批量导入银行成功流水");
		return result;
	}
	public ReturnValue supplyCapitalInfo(CapitalValue value) throws RemoteException{
		cp.log("-----------------------------------------supplyCapitalInfo  添加成功资金流水");
		ReturnValue result = cp.supplyCapitalInfo(value);
		cp.log("=========================================supplyCapitalInfo  添加成功资金流水");
		return result;
	}
	public Vector<InterfaceLog> interfaceLogList(String filter,int[] pageinfo) throws RemoteException{
		cp.log("-----------------------------------------interfaceLogList  查询银行接口和银行通讯信息");
		Vector<InterfaceLog> result = cp.interfaceLogList(filter,pageinfo);
		cp.log("=========================================interfaceLogList  查询银行接口和银行通讯信息");
		return result;
	}
	public int interfaceLog(InterfaceLog log) throws RemoteException{
		cp.log("-----------------------------------------interfaceLog  录入银行接口和银行通讯信息");
		int result = cp.interfaceLog(log);
		cp.log("=========================================interfaceLog  录入银行接口和银行通讯信息");
		return result;
	}
}
