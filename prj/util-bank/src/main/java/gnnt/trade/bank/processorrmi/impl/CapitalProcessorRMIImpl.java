package gnnt.trade.bank.processorrmi.impl;

import gnnt.trade.bank.CapitalProcessor;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
import gnnt.trade.bank.vo.BankCompareInfoValue;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.ChargeAgainstValue;
import gnnt.trade.bank.vo.CitysValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmTradeStatus;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.QSChangeResult;
import gnnt.trade.bank.vo.QSRresult;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.RgstCapitalValue;
import gnnt.trade.bank.vo.TradeDetailAccount;
import gnnt.trade.bank.vo.TradeResultValue;
import gnnt.trade.bank.vo.bankdz.boc.ClientState;
import gnnt.trade.bank.vo.bankdz.gs.sent.BankFirmRightValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRights;
import gnnt.trade.bank.vo.bankdz.gs.sent.OpenOrDelFirmValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.ProperBalanceValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.TradingDetailsValue;
import gnnt.trade.bank.vo.bankdz.hx.sent.HXSentQSMsgValue;
import gnnt.trade.bank.vo.bankdz.pf.sent.FundsMarg;
import gnnt.trade.bank.vo.bankdz.pf.sent.Margins;
import gnnt.trade.bank.vo.bankdz.pf.sent.TradeList;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzBChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzFailChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatFailResultChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.KXHfailChild;
import gnnt.trade.bank.vo.bankdz.xy.XYMarketMoney;
import gnnt.trade.bank.vo.bankdz.xy.resave.FFHDValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.ZFPHValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
public class CapitalProcessorRMIImpl extends UnicastRemoteObject implements CapitalProcessorRMI {
	
	private static final long serialVersionUID = 1L;

	/**
	 * public String testRmi() throws RemoteException;
	 * test
	 */
	public String testRmi() throws RemoteException {
		return "test successfully!";
	}
	public CapitalProcessorRMIImpl() throws RemoteException {
		super();
	}
	CapitalProcessor cp = new CapitalProcessor();

	public ReturnValue chargeAgainst(ChargeAgainstValue cav) throws RemoteException {
		return cp.chargeAgainst(cav);
	}
	public ReturnValue chargeAgainstMaket(ChargeAgainstValue cav) throws RemoteException{
		return cp.chargeAgainstMaket(cav);
	}
	public Vector<BankValue> getBankList(String filter) throws RemoteException{
		
		Vector<BankValue> v = null;
		try {
			 v = BankDAOFactory.getDAO().getBankList(filter);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return v;
	}
	public Vector<CompareResult> checkMoney(String bankID, Date date)
			throws RemoteException {
		return cp.checkMoney(bankID, date);
	}

	public Vector<CapitalCompare> sumResultInfo(String bankID,String[] firmIDs,java.util.Date date) throws RemoteException{
		return cp.sumResultInfo(bankID, firmIDs, date);
	}
	public Vector<CompareResult> checkMoney(Date date) throws RemoteException {
		return cp.checkMoney(date);
	}

	public int checkMoneyByNumber() throws RemoteException {
		return cp.checkMoneyByNumber();
	}


	public long delAccount(CorrespondValue correspondValue)
			throws RemoteException {
		return cp.delAccount(correspondValue);
	}

	public long delAccountMaket(CorrespondValue correspondValue) throws RemoteException{
		return cp.delAccountMaket(correspondValue);
	}

	public int getBankCompareInfo(String bankID, Date date)
			throws RemoteException {
		return cp.getBankCompareInfo(bankID, date);
	}

	public Hashtable<String, String> getBankInfo(String bankID)
			throws RemoteException {
		return cp.getBankInfo(bankID);
	}

	public Vector<CapitalValue> getCapitalList(String filter)
			throws RemoteException {
		return cp.getCapitalList(filter);
	}

	public String getConfig(String key) throws RemoteException {
		return cp.getConfig(key);
	}

	public Vector<CorrespondValue> getCorrespondValue(String filter)
			throws RemoteException {
		return cp.getCorrespondValue(filter);
	}
	public Map<String,CorrespondValue> getCorrespondValue(Vector<String> firmIDs,String bankID)  throws RemoteException{
		return cp.getCorrespondValue(firmIDs, bankID);
	}
	public Hashtable<String, Hashtable<String, String>> getFirmTradeBal(Date b_date) throws RemoteException {
		return cp.getFirmTradeBal(b_date);
	}

	public long getMktActionID() throws RemoteException {
		return cp.getMktActionID();
	}


	public long inMoney(String bankID, String firmID, String account,
			Timestamp bankTime, double money, String funID, long actionID,
			int funFlag, String remark) throws RemoteException {
		return cp.inMoney(bankID, firmID, account, bankTime, money, funID, actionID, funFlag, remark);
	}

	public long inMoneyMarket(String bankID, String firmID, String account,
			String accountPwd, double money, String remark, String inOutStart, String personName, String amoutDate, String bankName, String outAccount)
			throws RemoteException {
		return cp.inMoneyMarket(bankID, firmID, account, accountPwd, money, remark, inOutStart, personName, amoutDate, bankName, outAccount);
	}

	public int insertBankCompareInfo(Vector<MoneyInfoValue> list)
			throws RemoteException {
		return cp.insertBankCompareInfo(list);
	}

	public ReturnValue outMoney(String bankID, double money, String firmID,
			String bankAccount, String funid,String operator, int express, int type)
			throws RemoteException {
		return cp.outMoney(bankID, money, firmID, bankAccount,funid, operator, express, type);
	}


	public long rgstAccount(CorrespondValue correspondValue)
			throws RemoteException {
		return cp.rgstAccount(correspondValue);
	}

	public int setMoneyInfo(String bankID, Date date) throws RemoteException {
		return cp.setMoneyInfo(bankID, date);
	}

	public long setMoneyInfoAutoOrNo(String bankID, Date date, int moduleid)
			throws RemoteException {
		return cp.setMoneyInfoAutoOrNo(bankID, date, moduleid);
	}

	public long transferMoney(String bankID, int type, double money,
			String operator, String firmID) throws RemoteException {
		return cp.transferMoney(bankID, type, money, operator, firmID);
	}

	public long sendTotalMoneyToBank(String bankid, Hashtable<String, Double> ht)  throws RemoteException{
		return cp.sendTotalMoneyToBank(bankid, ht);
	}
	
	public long  outMoneyForAccess(int rvResult,String bankid,String firmid,String account,String actionid,String funcid) throws RemoteException
	{
		return cp.outMoneyForAccess(rvResult,bankid,firmid,account,actionid,funcid);
	}

	public ReturnValue destroyAccount(CorrespondValue cv) throws RemoteException {
		return cp.destroyAccount(cv);
	}

	public Hashtable<String, String> getBankIdAndAdapterClassname() throws RemoteException {
		return cp.getBankIdAndAdapterClassname();
	}

	public long insertBankMoneyInfo(Vector<MoneyInfoValue> mv, int ready) throws RemoteException {
		return cp.insertBankMoneyInfo(mv, ready);
	}

	public ReturnValue modAccount(CorrespondValue cv1, CorrespondValue cv2) throws RemoteException {
		return cp.modAccount(cv1, cv2);
	}

	public ReturnValue openAccount(CorrespondValue cv) throws RemoteException {
		return cp.openAccount(cv);
	}

	public FirmBalanceValue getFirmBalance(String bankid, String firmid, String pwd) throws RemoteException {
		return cp.getFirmBalance(bankid, firmid, pwd);
	}

	public long setMoneyInfoByHashtable(String bankID, Hashtable<String, TradeResultValue> ht) throws RemoteException {
		return cp.setMoneyInfoByHashtable(bankID, ht);
	}
	
	public FirmBalanceValue getMarketBalance(String firmid) throws RemoteException {
		return cp.getMarketBalance(firmid);
	}
	public long outMoneyAudit(long actionID, boolean funFlag) throws RemoteException {
		return cp.outMoneyAudit(actionID, funFlag);
	}
	public long outMoneySecondAudit(long actionID, boolean funFlag) throws RemoteException {
		return cp.outMoneySecondAudit(actionID, funFlag);
	}
	public Vector<BankCompareInfoValue> getBankCapList(String filter)  throws RemoteException{
		return cp.getBankCapList(filter);
	}
	public FirmMessageVo getFirmMSG(String firmid)  throws RemoteException{
		return cp.getFirmMSG(firmid);
	}
	public boolean getTraderStatus() throws RemoteException{
		return cp.getTraderStatus();
	}
	public long moneyInAudit(long actionID,boolean funFlag) throws RemoteException{
		return cp.moneyInAudit(actionID, funFlag);
	}
	public long tradeDate(String bankID) throws RemoteException{
		return cp.tradeDate(bankID);
	}
	public long isPassword(String firmID, String password) throws RemoteException{
		return cp.isPassword(firmID, password);
	}
	public long modPwd(String firmID, String password, String chpassword) throws RemoteException{
		return cp.modPwd(firmID, password, chpassword);
	}
	public ReturnValue openAccountMarket(CorrespondValue cv) throws RemoteException{
		return cp.openAccountMarket(cv);
	}
	/**同步子账号  lipj 20110311 */
	public ReturnValue synchroAccountMarket(CorrespondValue cv) throws RemoteException{
		return cp.synchroAccountMarket(cv);
	} 
	public BankValue getBank(String bankID) throws RemoteException{
		return cp.getBank(bankID);
	}
	public long inMoneyNoAdapter(String maketbankID, String firmID,String account,String bankName,double money ,String remark) throws RemoteException{
		return cp.inMoneyNoAdapter(maketbankID, firmID, account, bankName, money, remark);
	}
	
	public long outMoneyNoAdapter(String maketbankID,double money,String firmID,String bankName,String account,String operator,int express,int type) throws RemoteException{
		return cp.outMoneyNoAdapter(maketbankID, money, firmID, bankName, account, operator, express, type);
	}
	
	public FirmBalanceValue getBalanceNoAdapter(String firmid) throws RemoteException{
		return cp.getBalanceNoAdapter(firmid);
	}
	
	public long moneyAuditNoAdapter(long actionID,boolean funFlag) throws RemoteException{
		return cp.moneyAuditNoAdapter(actionID, funFlag);
	}

	public ReturnValue modAccountNoAdapter(CorrespondValue correspondValue) throws RemoteException{
		return cp.modAccountNoAdapter(correspondValue);
	}
	
	public long delAccountNoAdapter(CorrespondValue correspondValue) throws RemoteException{
		return cp.delAccountNoAdapter(correspondValue);
	}
	public boolean getTradeFlag() throws RemoteException{
		return cp.getTraderStatus();
	}
	public ReturnValue isTradeDate(java.util.Date tradeDate) throws RemoteException{
		return cp.isTradeDate(tradeDate);
	}
//----------------
	public ReturnValue relevanceAccount(CorrespondValue cv) throws RemoteException{
		return cp.relevanceAccount(cv);
	}
	public ReturnValue saveFirmKXH(Vector<KXHfailChild> vector,String bankID) throws RemoteException{
		return cp.saveFirmKXH(vector,bankID);
	}
	public ReturnValue saveFirmKXH(java.util.Date date,String bankID) throws RemoteException{
		return cp.saveFirmKXH(date, bankID);
	}
	public ReturnValue getBatCustDz(java.util.Date date,String bankID) throws RemoteException{
		return cp.getBatCustDz(date, bankID);
	}
	public ReturnValue getBatCustDz(Vector<BatCustDzFailChild> bcd,java.util.Date date,String bankID) throws RemoteException{
		return cp.getBatCustDz(bcd, date,bankID);
	}
	public ReturnValue getfirmBalanceFile(java.util.Date date,String bankID) throws RemoteException{
		return cp.getfirmBalanceFile(date, bankID);
	}

	public ReturnValue getfirmBalanceFile(Vector<BatCustDzBChild> fbf,java.util.Date date,String bankID) throws RemoteException{
		return cp.getfirmBalanceFile(fbf, date,bankID);
	}
	public ReturnValue getFirmBalanceError(java.util.Date date,String bankID) throws RemoteException{
		return cp.getFirmBalanceError(date, bankID);
	}
	public ReturnValue getFirmBalanceError(Vector<BatFailResultChild> fbe,java.util.Date date,String bankID) throws RemoteException{
		return cp.getFirmBalanceError(fbe, date,bankID);
	}
	public ReturnValue getBankFileStatus(java.util.Date tradeDate,int type,String bankID) throws RemoteException{
		return cp.getBankFileStatus(tradeDate, type, bankID);
	}
	public ReturnValue sentMaketQS(java.util.Date date,String bankID) throws RemoteException{
		return cp.sentMaketQS(date, bankID);
	}
	public ReturnValue ifQuitFirm(String firmID,String bankID) throws RemoteException{
		return cp.ifQuitFirm(firmID,bankID);
	}
//********************************建设银行特殊定制*************************************
	public ReturnValue modCapitalInfoStatus(long actionID,String funID) throws RemoteException{
		return cp.modCapitalInfoStatus(actionID, funID);
	}
	/**给银行发送手续费和资金变化量*/
	public ReturnValue sentFirmBalance(String bankID,java.util.Date date) throws RemoteException{
		return cp.sentFirmBalance(bankID, date);
	}
	public Map<String,CapitalValue> getCapitalValue(Vector<String> funids,String bankID) throws RemoteException{
		return cp.getCapitalValue(funids, bankID);
	}/**
	 * 保存银行清算结果信息
	 */
	public ReturnValue saveQSResult(String bankID,String tradeDate) throws RemoteException{
		return cp.saveQSResult(bankID,tradeDate);
	}
	/**
	 * 保存银行清算结果信息
	 */
	public ReturnValue saveQSResult(Vector<QSRresult> qsResult) throws RemoteException{
		return cp.saveQSResult(qsResult);
	}
//*******************************华夏银行特殊定制*************************************
	public ReturnValue synchronousFirms(String bankID,String[] firmIDs) throws RemoteException{
		return cp.synchronousFirms(bankID, firmIDs);
	}
	public ReturnValue hxSentQS(String bankID,java.util.Date date) throws RemoteException{
		return cp.hxSentQS(bankID, date);
	}
	public ReturnValue hxSentDZ(String bankID,java.util.Date date) throws RemoteException{
		return cp.hxSentDZ(bankID, date);
	}
	public Vector<HXSentQSMsgValue> hxGetQS(String bankID,java.util.Date date) throws RemoteException{
		return cp.hxGetQS(bankID, date);
	}
	public Vector<HXSentQSMsgValue> hxGetDZ(String bankID,java.util.Date date) throws RemoteException{
		return cp.hxGetQS(bankID, date);
	}
	public ReturnValue hxGetQSError(String bankID,java.util.Date tradeDate) throws RemoteException{
		return cp.hxGetQSError(bankID, tradeDate);
	}
	public ReturnValue hxGetDZError(String bankID,java.util.Date tradeDate) throws RemoteException{
		return cp.hxGetDZError(bankID, tradeDate);
	}
	public ReturnValue hxSaveQSError(Vector<QSChangeResult> vector) throws RemoteException{
		return cp.hxSaveQSError(vector);
	}
	public ReturnValue hxSaveDZError(Vector<QSRresult> vector) throws RemoteException{
		return cp.hxSaveDZError(vector);
	}
//*******************************浦发银行特殊定制************************************
	public ReturnValue pfdbQS(String bankID,java.util.Date date) throws RemoteException{
		return cp.pfdbQS(bankID, date);
	}
	public ReturnValue getTradesDateDetailsList(String bankID,java.util.Date date,int sendCount,int timeOutCount,int faileCount) throws RemoteException{
		return cp.getTradesDateDetailsList(bankID, date,sendCount,timeOutCount,faileCount);
	}
	public Vector<TradeList> getTradesDateDetailsList(String bankID,java.util.Date date,String[] flag)throws RemoteException{
		return cp.getTradesDateDetailsList(bankID, date, flag);
	}
	public ReturnValue getDongjieDetailList(String bankID,java.util.Date date,int sendCount,int timeOutCount,int faileCount) throws RemoteException{
		return cp.getDongjieDetailList(bankID, date, sendCount, timeOutCount, faileCount);
	}
	public Vector<Margins> getDongjieDetailList(String bankID,java.util.Date date,String[] flag) throws RemoteException{
		return cp.getDongjieDetailList(bankID, date, flag);
	}
	public Hashtable<String,FundsMarg> getFundsMarg(String bankID,java.util.Date date) throws RemoteException{
		return cp.getFundsMarg(bankID,date);
	}
//*********************************工商银行特殊定制***********************************

	public ReturnValue sendGHQS(String bankId,String firmId,java.util.Date qdate) throws RemoteException{
		return cp.sendGHQS(bankId,firmId,qdate);
	}
	public List<FirmRights> getRightsList(String bankId,String firmId,java.util.Date qdate) throws RemoteException{
		return cp.getRightsList(bankId,firmId,qdate);
	}
	public List<TradingDetailsValue> getChangeBalance(String bankId,String firmId,java.util.Date qdate) throws RemoteException{
		return cp.getChangeBalance(bankId,firmId,qdate);
	}
	public List<OpenOrDelFirmValue> getOpenOrDropMaket(String bankId,java.util.Date qdate) throws RemoteException{
		return cp.getOpenOrDropMaket(bankId, qdate);
	}
	/**交易商权益的分分核对*/
	public long setBankFirmRightValue(List<BankFirmRightValue> list) throws RemoteException{
		return cp.getBankFirmRightValue(list);
	}
	/**总分平衡监管*/
	public long setProperBalanceValue(ProperBalanceValue pbv) throws RemoteException{
		return cp.getProperBalanceValue(pbv);
	}
	public ReturnValue getBankFirmRightValue(String bankID, Date date)
			throws RemoteException {
		return cp.getBankFirmRightValue(bankID, date);
	}
	public ReturnValue getProperBalanceValue(String bankID, Date date)
			throws RemoteException {
		return cp.getProperBalanceValue(bankID, date);
	}
//******************************************************兴业银行订制****************************************************
	public ReturnValue updownBank(String bankID,int type) throws RemoteException{
		return cp.updownBank(bankID, type);
	}
	public ReturnValue sendXYQSValue(String bankID,String[] firmIDs,java.util.Date tradeDate) throws RemoteException{
		return cp.sendXYQSValue(bankID, firmIDs, tradeDate);
	}
	public ReturnValue sendHRBQSValue(String bankID, String[] firmIDs,
			Date tradeDate) throws RemoteException {
		return cp.sendHRBQSValue(bankID, firmIDs, tradeDate);
	}
	public RZQSValue getXYQSValue(String bankID,String[] firmIDs,java.util.Date tradeDate) throws RemoteException{
		return cp.getXYQSValue(bankID, firmIDs, tradeDate);
	}
	public RZDZValue getXYDZValue(String bankID,String[] firmIDs,java.util.Date tradeDate) throws RemoteException{
		return cp.getXYDZValue(bankID, firmIDs, tradeDate);
	}
	public ReturnValue getZFPH(String bankID,java.util.Date date) throws RemoteException{
		return cp.getZFPH(bankID, date);
	}
	public ReturnValue getFFHD(String bankID,java.util.Date date) throws RemoteException{
		return cp.getFFHD(bankID, date);
	}
	public ReturnValue saveZFPH(ZFPHValue zfph) throws RemoteException{
		return cp.saveZFPH(zfph);
	}
	public ReturnValue saveFFHD(FFHDValue ffhd) throws RemoteException{
		return cp.saveFFHD(ffhd);
	}
	public ReturnValue addMarketMoney(XYMarketMoney xymm) throws RemoteException{
		return cp.addMarketMoney(xymm);
	}
	public ReturnValue modMarketMoney(XYMarketMoney xymm) throws RemoteException{
		return cp.modMarketMoney(xymm);
	}
	
	public ReturnValue BankTransferResultNotice(long actionId, int optRst)
			throws RemoteException {
		// TODO Auto-gSSSSenerated method stub
		return null;
	}
	public long bankTransfer(long id, int optFlag) throws RemoteException {
		// TODO Auto-generated method stub
		return cp.bankTransfer(id, optFlag);
	}
	public ReturnValue marketTransfer(BankTransferValue bankTransferValue)
			throws RemoteException {
		// TODO Auto-generated method stub
		return cp.marketTransfer(bankTransferValue);
	}
	public long inMoneyMarket(String bankID, String firmID, String account,
			String accountPwd, double money, String remark)
			throws RemoteException {
		return cp.inMoneyMarket(bankID, firmID, account, accountPwd, money, remark);

	}
	//-----------------------------------招行订制  start---------------------------------------------------
	public ReturnValue preOpenAccountMarket(CorrespondValue cv)
			throws RemoteException {
		// TODO Auto-generated method stub
		return cp.preOpenAccount(cv);
	}
	public long insertFirmTradeStatus(Vector<FirmTradeStatus> veFirmStatus,
			int ready) throws RemoteException {
		// TODO Auto-generated method stub
		return cp.insertFirmTradeStatus(veFirmStatus, ready);
	}
	public long insertTradeDetailAccount(Vector<TradeDetailAccount> veDetail,
			int ready) throws RemoteException {
		// TODO Auto-generated method stub
		return cp.insertTradeDetailAccount(veDetail, ready);
	}
	public ReturnValue marketOpenAccount(CorrespondValue val) throws RemoteException {
		return cp.marketOpenAccount(val);
	}
	//-----------------------------------招行订制  end---------------------------------------------------
	//-----------------------------------民生订制  start------------------------------
	public ReturnValue sendCMBCQSValue(String bankID, Date date)
	throws RemoteException {
		ReturnValue result=new ReturnValue();
//		cp.log("-------------------------------sendCMBCQSValue民生接口调用处理器清算方法开始");
		result= cp.sendCMBCQSValue(bankID,date);
//		cp.log("==============================sendCMBCQSValue民生接口调用处理器清算方法结束");
		return result;


	}
	public long isLogPassword(String firmID, String password) throws RemoteException{
		return cp.isLogPassword(firmID, password);
	}
	//-----------------------------------民生订制  end------------------------------
	
	//------------------------------------国付宝 G商银通 begin------------------------------------
	public Vector<RgstCapitalValue> getRgstCapitalValue(String file)  throws RemoteException {
		// TODO Auto-generated method stub
		return cp.getRgstCapitalValue(file);
	}
	public ReturnValue modRgstCapitalValue(RgstCapitalValue rc)  throws RemoteException {
		// TODO Auto-generated method stub
		return cp.modRgstCapitalValue(rc);
	}
	public ReturnValue addRgstCapitalValue(CorrespondValue rc,int type)  throws RemoteException {
		// TODO Auto-generated method stub
		return cp.addRgstCapitalValue(rc,type);
	}
	public long modMoneyCapital(long actionID,String funID,boolean funFlag)  throws RemoteException {
		return cp.modMoneyCapital(actionID,funID,funFlag);
	}
	public long inMoneyMarketGS(String bankID, String firmID, String account,
			String accountPwd, double money, String remark)
			throws RemoteException {
		return cp.inMoneyMarketGS(bankID, firmID, account, accountPwd, money, remark);

	}
	public ReturnValue outMoneyGS(String bankID, double money, String firmID,
			String bankAccount, String funid,String operator, int express, int type)
			throws RemoteException {
		return cp.outMoneyGS(bankID, money, firmID, bankAccount,funid, operator, express, type);
	}
	
	public FirmBalanceValue getBankBalance(String bankid, String firmid, String pwd) throws RemoteException {
		return cp.getBankBalance(bankid, firmid, pwd);
	}
	//------------------------------------国付宝 G商银通 end   ------------------------------------
	//---------------------------------中行特殊定制 begin--------------------------------
	/**银行端发起的预指定存管银行确认*/
	public ReturnValue SpecifiedStorageTubeBankSure(){
		return cp.SpecifiedStorageTubeBankSure();
	}
	/**中行测试通讯*/
	public ReturnValue CommunicationsTest(String bankID) throws RemoteException {
		return cp.CommunicationsTest(bankID);
	}
	/**中行通过证件信息进行预签约*/
	public ReturnValue BankYuSigning(String bankid, String cardtype,String card, String account) throws RemoteException {
		return cp.BankYuSigning( bankid, cardtype, card, account);
	}
	public Hashtable<String, List> getZHQSValue(String bankID, Date tradeDate) throws RemoteException {
		
		return cp.getZHQSValue(bankID,tradeDate);
	}
	/**中行客户账号状态对账*/
	public long insertClientStates(Vector<ClientState> states, int ready)throws RemoteException {
		return cp.insertClientStates(states,ready);
	}
	/**发送清算信息*/
	public ReturnValue sendZHQS(String bankID, String[] firmIDs, Date tradeDate)
			throws RemoteException {
		return cp.sendZHQS(bankID, firmIDs, tradeDate);
	}
	//---------------------------------中行特殊定制 end--------------------------------
	//--------------通讯日志 start -----------------
	/**添加通讯日志 2013.01.05*/
	public int interfaceLog(InterfaceLog log) throws RemoteException{
		int result = cp.interfaceLog(log);
		return result;
	}
	//--------------通讯日志 end -----------------
	public Vector<CitysValue> getCitysValue(String filter) throws RemoteException{
		return cp.getCitysValue(filter);
	}
}
