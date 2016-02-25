package gnnt.bank.platform.rmi.impl;

import gnnt.bank.adapter.bankBusiness.dayData.FCS_10_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_11_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_13_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_99;
import gnnt.bank.adapter.bankBusiness.message.CEB_RSP;
import gnnt.bank.adapter.bankBusiness.message.CEB_param;
import gnnt.bank.platform.CapitalProcessor;
import gnnt.bank.platform.dao.BankDAO;
import gnnt.bank.platform.dao.BankDAOFactory;
import gnnt.bank.platform.util.Tool;
import gnnt.bank.platform.vo.RequestMsg;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
import gnnt.trade.bank.vo.AbcInfoValue;
import gnnt.trade.bank.vo.BankCompareInfoValue;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.ChargeAgainstValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmID2SysFirmID;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmTradeStatus;
import gnnt.trade.bank.vo.InOutMoney;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.QSChangeResult;
import gnnt.trade.bank.vo.QSRresult;
import gnnt.trade.bank.vo.ReturnBalance;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.RgstCapitalValue;
import gnnt.trade.bank.vo.SystemMessage;
import gnnt.trade.bank.vo.SystemQSValue;
import gnnt.trade.bank.vo.TradeDetailAccount;
import gnnt.trade.bank.vo.TradeResultValue;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
import gnnt.trade.bank.vo.bankdz.boc.ClientState;
import gnnt.trade.bank.vo.bankdz.gs.sent.BankFirmRightValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRights;
import gnnt.trade.bank.vo.bankdz.gs.sent.OpenOrDelFirmValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.ProperBalanceValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.TradingDetailsValue;
import gnnt.trade.bank.vo.bankdz.hx.sent.HXSentQSMsgValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
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
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class CapitalProcessorRMIImpl
  extends UnicastRemoteObject
  implements CapitalProcessorRMI
{
  private static final long serialVersionUID = 1L;
  
  public String testRmi()
    throws RemoteException
  {
    return "test successfully!";
  }
  
  CapitalProcessor cp = new CapitalProcessor();
  
  public CapitalProcessorRMIImpl()
    throws RemoteException
  {}
  
  public ReturnValue chargeAgainst(ChargeAgainstValue cav)
    throws RemoteException
  {
    return this.cp.chargeAgainst(cav);
  }
  
  public ReturnValue chargeAgainstMaket(ChargeAgainstValue cav)
    throws RemoteException
  {
    return this.cp.chargeAgainstMaket(cav);
  }
  
  public Vector<BankValue> getBankList(String filter)
    throws RemoteException
  {
    Vector<BankValue> v = null;
    try
    {
      v = BankDAOFactory.getDAO().getBankList(filter);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
    }
    catch (InstantiationException e)
    {
      e.printStackTrace();
    }
    return v;
  }
  
  public Vector<CompareResult> checkMoney(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.checkMoney(bankID, date);
  }
  
  public Vector<CapitalCompare> sumResultInfo(String bankID, String[] firmIDs, Date date)
    throws RemoteException
  {
    return this.cp.sumResultInfo(bankID, firmIDs, date);
  }
  
  public Vector<CompareResult> checkMoney(Date date)
    throws RemoteException
  {
    return this.cp.checkMoney(date);
  }
  
  public int checkMoneyByNumber()
    throws RemoteException
  {
    return this.cp.checkMoneyByNumber();
  }
  
  public long delAccount(CorrespondValue correspondValue)
    throws RemoteException
  {
    return this.cp.delAccount(correspondValue);
  }
  
  public long delAccountMaket(CorrespondValue correspondValue)
    throws RemoteException
  {
    return this.cp.delAccountMaket(correspondValue);
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
  
  public Vector<CapitalValue> getCapitalList(String filter)
    throws RemoteException
  {
    return this.cp.getCapitalList(filter);
  }
  
  public String getConfig(String key)
    throws RemoteException
  {
    return this.cp.getConfig(key);
  }
  
  public Vector<CorrespondValue> getCorrespondValue(String filter)
    throws RemoteException
  {
    return this.cp.getCorrespondValue(filter);
  }
  
  public Map<String, CorrespondValue> getCorrespondValue(Vector<String> firmIDs, String bankID)
    throws RemoteException
  {
    return this.cp.getCorrespondValue(firmIDs, bankID);
  }
  
  public Hashtable<String, Hashtable<String, String>> getFirmTradeBal(Date b_date)
    throws RemoteException
  {
    return this.cp.getFirmTradeBal(b_date);
  }
  
  public long getMktActionID()
    throws RemoteException
  {
    return this.cp.getMktActionID();
  }
  
  public long inMoney(String bankID, String firmID, String account, Timestamp bankTime, double money, String funID, long actionID, int funFlag, String remark)
    throws RemoteException
  {
    return this.cp.inMoney(bankID, firmID, account, bankTime, money, funID, actionID, funFlag, remark);
  }
  
  public int insertBankCompareInfo(Vector<MoneyInfoValue> list)
    throws RemoteException
  {
    return this.cp.insertBankCompareInfo(list);
  }
  
  public ReturnValue outMoney(String bankID, double money, String firmID, String bankAccount, String funid, String operator, int express, int type)
    throws RemoteException
  {
    return this.cp.outMoney(bankID, money, firmID, bankAccount, funid, operator, express, type);
  }
  
  public long rgstAccount(CorrespondValue correspondValue)
    throws RemoteException
  {
    return this.cp.rgstAccount(correspondValue);
  }
  
  public int setMoneyInfo(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.setMoneyInfo(bankID, date);
  }
  
  public long setMoneyInfoAutoOrNo(String bankID, Date date, int moduleid)
    throws RemoteException
  {
    return this.cp.setMoneyInfoAutoOrNo(bankID, date, moduleid);
  }
  
  public long transferMoney(String bankID, int type, double money, String operator, String firmID)
    throws RemoteException
  {
    return this.cp.transferMoney(bankID, type, money, operator, firmID);
  }
  
  public long sendTotalMoneyToBank(String bankid, Hashtable<String, Double> ht)
    throws RemoteException
  {
    return this.cp.sendTotalMoneyToBank(bankid, ht);
  }
  
  public long outMoneyForAccess(int rvResult, String bankid, String firmid, String account, String actionid, String funcid)
    throws RemoteException
  {
    return this.cp.outMoneyForAccess(rvResult, bankid, firmid, account, actionid, funcid);
  }
  
  public ReturnValue destroyAccount(CorrespondValue cv)
    throws RemoteException
  {
    return this.cp.destroyAccount(cv);
  }
  
  public Hashtable<String, String> getBankIdAndAdapterClassname()
    throws RemoteException
  {
    return this.cp.getBankIdAndAdapterClassname();
  }
  
  public long insertBankMoneyInfo(Vector<MoneyInfoValue> mv, int ready)
    throws RemoteException
  {
    return this.cp.insertBankMoneyInfo(mv, ready);
  }
  
  public ReturnValue modAccount(CorrespondValue cv1, CorrespondValue cv2)
    throws RemoteException
  {
    return this.cp.modAccount(cv1, cv2);
  }
  
  public ReturnValue openAccount(CorrespondValue cv)
    throws RemoteException
  {
    return this.cp.openAccount(cv);
  }
  
  public FirmBalanceValue getFirmBalance(String bankid, String firmid, String pwd)
    throws RemoteException
  {
    return this.cp.getFirmBalance(bankid, firmid, pwd);
  }
  
  public long setMoneyInfoByHashtable(String bankID, Hashtable<String, TradeResultValue> ht)
    throws RemoteException
  {
    return this.cp.setMoneyInfoByHashtable(bankID, ht);
  }
  
  public FirmBalanceValue getMarketBalance(String firmid)
    throws RemoteException
  {
    return this.cp.getMarketBalance(firmid);
  }
  
  public FirmBalanceValue getMarketBalance(String firmid, String bankID)
    throws RemoteException
  {
    return this.cp.getMarketBalance(firmid, bankID);
  }
  
  public Vector<BankCompareInfoValue> getBankCapList(String filter)
    throws RemoteException
  {
    return this.cp.getBankCapList(filter);
  }
  
  public FirmMessageVo getFirmMSG(String firmid)
    throws RemoteException
  {
    return this.cp.getFirmMSG(firmid);
  }
  
  public boolean getTraderStatus()
    throws RemoteException
  {
    return this.cp.getTraderStatus();
  }
  
  public long tradeDate(String bankID)
    throws RemoteException
  {
    return this.cp.tradeDate(bankID);
  }
  
  public ReturnValue isPassword(String firmID, String password)
    throws RemoteException
  {
    return this.cp.isPassword(firmID, password);
  }
  
  public ReturnValue modPwd(String firmID, String password, String chpassword)
    throws RemoteException
  {
    return this.cp.modPwd(firmID, password, chpassword);
  }
  
  public ReturnValue openAccountMarket(CorrespondValue cv)
    throws RemoteException
  {
    return this.cp.openAccountMarket(cv);
  }
  
  public ReturnValue synchroAccountMarket(CorrespondValue cv)
    throws RemoteException
  {
    return this.cp.synchroAccountMarket(cv);
  }
  
  public BankValue getBank(String bankID)
    throws RemoteException
  {
    return this.cp.getBank(bankID);
  }
  
  public FirmBalanceValue getBalanceNoAdapter(String firmid)
    throws RemoteException
  {
    return this.cp.getBalanceNoAdapter(firmid);
  }
  
  public long moneyAuditNoAdapter(long actionID, boolean funFlag)
    throws RemoteException
  {
    return this.cp.moneyAuditNoAdapter(actionID, funFlag);
  }
  
  public ReturnValue modAccountNoAdapter(CorrespondValue correspondValue)
    throws RemoteException
  {
    return this.cp.modAccountNoAdapter(correspondValue);
  }
  
  public long delAccountNoAdapter(CorrespondValue correspondValue)
    throws RemoteException
  {
    return this.cp.delAccountNoAdapter(correspondValue);
  }
  
  public boolean getTradeFlag()
    throws RemoteException
  {
    return this.cp.getTraderStatus();
  }
  
  public ReturnValue isTradeDate(Date tradeDate)
    throws RemoteException
  {
    return this.cp.isTradeDate(tradeDate);
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
    return this.cp.saveFirmKXH(date, bankID);
  }
  
  public ReturnValue getBatCustDz(Date date, String bankID)
    throws RemoteException
  {
    return this.cp.getBatCustDz(date, bankID);
  }
  
  public ReturnValue getBatCustDz(Vector<BatCustDzFailChild> bcd, Date date, String bankID)
    throws RemoteException
  {
    return this.cp.getBatCustDz(bcd, date, bankID);
  }
  
  public ReturnValue getfirmBalanceFile(Date date, String bankID)
    throws RemoteException
  {
    return this.cp.getfirmBalanceFile(date, bankID);
  }
  
  public ReturnValue getfirmBalanceFile(Vector<BatCustDzBChild> fbf, Date date, String bankID)
    throws RemoteException
  {
    return this.cp.getfirmBalanceFile(fbf, date, bankID);
  }
  
  public ReturnValue getFirmBalanceError(Date date, String bankID)
    throws RemoteException
  {
    return this.cp.getFirmBalanceError(date, bankID);
  }
  
  public ReturnValue getFirmBalanceError(Vector<BatFailResultChild> fbe, Date date, String bankID)
    throws RemoteException
  {
    return this.cp.getFirmBalanceError(fbe, date, bankID);
  }
  
  public ReturnValue getBankFileStatus(Date tradeDate, int type, String bankID)
    throws RemoteException
  {
    return this.cp.getBankFileStatus(tradeDate, type, bankID);
  }
  
  public ReturnValue sentMaketQS(Date date, String bankID)
    throws RemoteException
  {
    return this.cp.sentMaketQS(date, bankID);
  }
  
  public ReturnValue ifQuitFirm(String firmID, String bankID)
    throws RemoteException
  {
    return this.cp.ifQuitFirm(firmID, bankID);
  }
  
  public ReturnValue modCapitalInfoStatus(long actionID, String funID)
    throws RemoteException
  {
    return this.cp.modCapitalInfoStatus(actionID, funID);
  }
  
  public ReturnValue sentFirmBalance(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.sentFirmBalance(bankID, date);
  }
  
  public Map<String, CapitalValue> getCapitalValue(Vector<String> funids, String bankID)
    throws RemoteException
  {
    return this.cp.getCapitalValue(funids, bankID);
  }
  
  public ReturnValue saveQSResult(String bankID, String tradeDate)
    throws RemoteException
  {
    return this.cp.saveQSResult(bankID, tradeDate);
  }
  
  public ReturnValue saveQSResult(Vector<QSRresult> qsResult)
    throws RemoteException
  {
    return this.cp.saveQSResult(qsResult);
  }
  
  public ReturnValue synchronousFirms(String bankID, String[] firmIDs)
    throws RemoteException
  {
    return this.cp.synchronousFirms(bankID, firmIDs);
  }
  
  public ReturnValue hxSentQS(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.hxSentQS(bankID, date);
  }
  
  public ReturnValue hxSentDZ(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.hxSentDZ(bankID, date);
  }
  
  public Vector<HXSentQSMsgValue> hxGetQS(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.hxGetQS(bankID, date);
  }
  
  public Vector<HXSentQSMsgValue> hxGetDZ(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.hxGetQS(bankID, date);
  }
  
  public ReturnValue hxGetQSError(String bankID, Date tradeDate)
    throws RemoteException
  {
    return this.cp.hxGetQSError(bankID, tradeDate);
  }
  
  public ReturnValue hxGetDZError(String bankID, Date tradeDate)
    throws RemoteException
  {
    return this.cp.hxGetDZError(bankID, tradeDate);
  }
  
  public ReturnValue hxSaveQSError(Vector<QSChangeResult> vector)
    throws RemoteException
  {
    return this.cp.hxSaveQSError(vector);
  }
  
  public ReturnValue hxSaveDZError(Vector<QSRresult> vector)
    throws RemoteException
  {
    return this.cp.hxSaveDZError(vector);
  }
  
  public ReturnValue pfdbQS(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.pfdbQS(bankID, date);
  }
  
  public ReturnValue getTradesDateDetailsList(String bankID, Date date, int sendCount, int timeOutCount, int faileCount)
    throws RemoteException
  {
    return this.cp.getTradesDateDetailsList(bankID, date, sendCount, timeOutCount, faileCount);
  }
  
  public Vector<TradeList> getTradesDateDetailsList(String bankID, Date date, String[] flag)
    throws RemoteException
  {
    return this.cp.getTradesDateDetailsList(bankID, date, flag);
  }
  
  public ReturnValue getDongjieDetailList(String bankID, Date date, int sendCount, int timeOutCount, int faileCount)
    throws RemoteException
  {
    return this.cp.getDongjieDetailList(bankID, date, sendCount, timeOutCount, faileCount);
  }
  
  public Vector<Margins> getDongjieDetailList(String bankID, Date date, String[] flag)
    throws RemoteException
  {
    return this.cp.getDongjieDetailList(bankID, date, flag);
  }
  
  public Hashtable<String, FundsMarg> getFundsMarg(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.getFundsMarg(bankID, date);
  }
  
  public ReturnValue sendGHQS(String bankId, String firmId, Date qdate)
    throws RemoteException
  {
    return this.cp.sendGHQS(bankId, firmId, qdate);
  }
  
  public List<FirmRights> getRightsList(String bankId, String firmId, Date qdate)
    throws RemoteException
  {
    return this.cp.getRightsList(bankId, firmId, qdate);
  }
  
  public List<TradingDetailsValue> getChangeBalance(String bankId, String firmId, Date qdate)
    throws RemoteException
  {
    return this.cp.getChangeBalance(bankId, firmId, qdate);
  }
  
  public List<OpenOrDelFirmValue> getOpenOrDropMaket(String bankId, Date qdate)
    throws RemoteException
  {
    return this.cp.getOpenOrDropMaket(bankId, qdate);
  }
  
  public long setBankFirmRightValue(List<BankFirmRightValue> list)
    throws RemoteException
  {
    return this.cp.getBankFirmRightValue(list);
  }
  
  public long setProperBalanceValue(ProperBalanceValue pbv)
    throws RemoteException
  {
    return this.cp.getProperBalanceValue(pbv);
  }
  
  public ReturnValue getBankFirmRightValue(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.getBankFirmRightValue(bankID, date);
  }
  
  public ReturnValue getProperBalanceValue(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.getProperBalanceValue(bankID, date);
  }
  
  public ReturnValue updownBank(String bankID, int type)
    throws RemoteException
  {
    return this.cp.updownBank(bankID, type);
  }
  
  public ReturnValue sendXYQSValue(String bankID, String[] firmIDs, Date tradeDate)
    throws RemoteException
  {
    return this.cp.sendXYQSValue(bankID, firmIDs, tradeDate);
  }
  
  public ReturnValue sendHRBQSValue(String bankID, String[] firmIDs, Date tradeDate)
    throws RemoteException
  {
    return this.cp.sendHRBQSValue(bankID, firmIDs, tradeDate);
  }
  
  public RZQSValue getXYQSValue(String bankID, String[] firmIDs, Date tradeDate)
    throws RemoteException
  {
    return this.cp.getXYQSValue(bankID, firmIDs, tradeDate);
  }
  
  public RZDZValue getXYDZValue(String bankID, String[] firmIDs, Date tradeDate)
    throws RemoteException
  {
    return this.cp.getXYDZValue(bankID, firmIDs, tradeDate);
  }
  
  public ReturnValue getZFPH(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.getZFPH(bankID, date);
  }
  
  public ReturnValue getFFHD(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.getFFHD(bankID, date);
  }
  
  public ReturnValue saveZFPH(ZFPHValue zfph)
    throws RemoteException
  {
    return this.cp.saveZFPH(zfph);
  }
  
  public ReturnValue saveFFHD(FFHDValue ffhd)
    throws RemoteException
  {
    return this.cp.saveFFHD(ffhd);
  }
  
  public ReturnValue addMarketMoney(XYMarketMoney xymm)
    throws RemoteException
  {
    return this.cp.addMarketMoney(xymm);
  }
  
  public ReturnValue modMarketMoney(XYMarketMoney xymm)
    throws RemoteException
  {
    return this.cp.modMarketMoney(xymm);
  }
  
  public ReturnValue BankTransferResultNotice(long actionId, int optRst)
    throws RemoteException
  {
    return this.cp.BankTransferResultNotice(actionId, optRst);
  }
  
  public long bankTransfer(long id, int optFlag)
    throws RemoteException
  {
    return this.cp.bankTransfer(id, optFlag);
  }
  
  public ReturnValue marketTransfer(BankTransferValue bankTransferValue)
    throws RemoteException
  {
    return this.cp.marketTransfer(bankTransferValue);
  }
  
  public ReturnValue preOpenAccountMarket(CorrespondValue cv)
    throws RemoteException
  {
    return this.cp.preOpenAccount(cv);
  }
  
  public long insertFirmTradeStatus(Vector<FirmTradeStatus> veFirmStatus, int ready)
    throws RemoteException
  {
    return this.cp.insertFirmTradeStatus(veFirmStatus, ready);
  }
  
  public long insertTradeDetailAccount(Vector<TradeDetailAccount> veDetail, int ready)
    throws RemoteException
  {
    return this.cp.insertTradeDetailAccount(veDetail, ready);
  }
  
  public ReturnValue marketOpenAccount(CorrespondValue val)
    throws RemoteException
  {
    return this.cp.marketOpenAccount(val);
  }
  
  public ReturnValue sendCMBCQSValue(String bankID, Date date)
    throws RemoteException
  {
    ReturnValue result = new ReturnValue();
    
    result = this.cp.sendCMBCQSValue(bankID, date);
    
    return result;
  }
  
  public long isLogPassword(String firmID, String password)
    throws RemoteException
  {
    return this.cp.isLogPassword(firmID, password);
  }
  
  public Vector<RgstCapitalValue> getRgstCapitalValue(String file)
    throws RemoteException
  {
    return this.cp.getRgstCapitalValue(file);
  }
  
  public ReturnValue modRgstCapitalValue(RgstCapitalValue rc)
    throws RemoteException
  {
    return this.cp.modRgstCapitalValue(rc);
  }
  
  public ReturnValue addRgstCapitalValue(CorrespondValue rc, int type)
    throws RemoteException
  {
    return this.cp.addRgstCapitalValue(rc, type);
  }
  
  public long modMoneyCapital(long actionID, String funID, boolean funFlag)
    throws RemoteException
  {
    return this.cp.modMoneyCapital(actionID, funID, funFlag);
  }
  
  public long inMoneyMarketGS(String bankID, String firmID, String account, String accountPwd, double money, String remark)
    throws RemoteException
  {
    return this.cp.inMoneyMarketGS(bankID, firmID, account, accountPwd, money, remark);
  }
  
  public ReturnValue outMoneyGS(String bankID, double money, String firmID, String bankAccount, String funid, String operator, int express, int type)
    throws RemoteException
  {
    return this.cp.outMoneyGS(bankID, money, firmID, bankAccount, funid, operator, express, type);
  }
  
  public FirmBalanceValue getBankBalance(String bankid, String firmid, String pwd)
    throws RemoteException
  {
    return this.cp.getBankBalance(bankid, firmid, pwd);
  }
  
  public ReturnValue SpecifiedStorageTubeBankSure()
  {
    return this.cp.SpecifiedStorageTubeBankSure();
  }
  
  public ReturnValue CommunicationsTest(String bankID)
    throws RemoteException
  {
    return this.cp.CommunicationsTest(bankID);
  }
  
  public ReturnValue BankYuSigning(String bankid, String cardtype, String card, String account)
    throws RemoteException
  {
    return this.cp.BankYuSigning(bankid, cardtype, card, account);
  }
  
  public Hashtable<String, List> getZHQSValue(String bankID, Date tradeDate)
    throws RemoteException
  {
    return this.cp.getZHQSValue(bankID, tradeDate);
  }
  
  public long insertClientStates(Vector<ClientState> states, int ready)
    throws RemoteException
  {
    return this.cp.insertClientStates(states, ready);
  }
  
  public ReturnValue sendZHQS(String bankID, String[] firmIDs, Date tradeDate)
    throws RemoteException
  {
    return this.cp.sendZHQS(bankID, firmIDs, tradeDate);
  }
  
  public int interfaceLog(InterfaceLog log)
    throws RemoteException
  {
    int result = this.cp.interfaceLog(log);
    return result;
  }
  
  public CEB_RSP CEB(CEB_param param, String id)
    throws RemoteException
  {
    return this.cp.CEB(param, id);
  }
  
  public String insertFcs(Vector<FCS_10_Result> fcs10_v, Vector<FCS_11_Result> fcs11_v, Vector<FCS_13_Result> fcs13_v, Vector<FCS_99> fcs99_v)
    throws RemoteException
  {
    return this.cp.insertFcs(fcs10_v, fcs11_v, fcs13_v, fcs99_v);
  }
  
  public ReturnValue modAccount(CorrespondValue cv1, CorrespondValue cv2, String bankID)
    throws RemoteException
  {
    return this.cp.modAccount(cv1, cv2, bankID);
  }
  
  public ReturnValue modBankQS(BankQSVO bq)
    throws RemoteException
  {
    return this.cp.modBankQS(bq);
  }
  
  public ReturnValue inMoneyQuery(long actionID)
    throws RemoteException
  {
    return this.cp.inMoneyQuery(actionID);
  }
  
  public ReturnValue outMoneyQuery(long actionID)
    throws RemoteException
  {
    return this.cp.outMoneyQuery(actionID);
  }
  
  public ReturnValue rgstAccountforWeb(String firmid, String pwd, String type)
    throws RemoteException
  {
    return this.cp.rgstAccountforWeb(firmid, pwd, type);
  }
  
  public AbcInfoValue getAbcInfo(String firmID, long orderNo, int type)
    throws RemoteException
  {
    return this.cp.getAbcInfo(firmID, orderNo, type);
  }
  
  public ReturnValue insertAbcInfo(AbcInfoValue value)
    throws RemoteException
  {
    return this.cp.insertAbcInfo(value);
  }
  
  public long inMoneyMarketAbc(long actionID, String bankID, String firmID, String account, String accountPwd, double money, String remark)
    throws RemoteException
  {
    return -1L;
  }
  
  public int getBankCompareInfoAbc(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.getBankCompareInfoAbc(bankID, date);
  }
  
  public ReturnValue sendFile_CEB(String bankID, Date date)
    throws RemoteException
  {
    return this.cp.sendFile_CEB(bankID, date);
  }
  
  public double getCanOutBalance(String firmID)
    throws RemoteException
  {
    return this.cp.getCanOutBalance(firmID);
  }
  
  public ReturnValue synchFirmAccount(CorrespondValue corr)
    throws RemoteException
  {
    return this.cp.synchFirmAccount(corr);
  }
  
  public ReturnValue isOpenQuery(CorrespondValue corr)
    throws RemoteException
  {
    return this.cp.isOpenQuery(corr);
  }
  
  public Vector<SystemMessage> getSystemMessages(String filter)
    throws RemoteException
  {
    return this.cp.getSystemMessages(filter);
  }
  
  public long modSystemMessage(SystemMessage sysMsg)
    throws RemoteException
  {
    return this.cp.modSystemMessage(sysMsg);
  }
  
  public ReturnValue delCorrBank(CorrespondValue corr)
    throws RemoteException
  {
    return this.cp.delCorrBank(corr);
  }
  
  public Vector<SystemMessage> getSystemMsg(String filter)
    throws RemoteException
  {
    return this.cp.getSystemMessages(filter);
  }
  
  public ReturnValue loginOrQuit(String systemID, int flag, String key)
    throws RemoteException
  {
    return this.cp.loginOrQuit(systemID, flag, key);
  }
  
  public Vector<FirmID2SysFirmID> getFirmID2SysFirmID(String filter)
    throws RemoteException
  {
    return this.cp.getFirmID2SysFirmID(filter);
  }
  
  public ReturnBalance getPTandBankBalance(CorrespondValue corr)
    throws RemoteException
  {
    return this.cp.getPTandBankBalance(corr);
  }
  
  public ReturnValue inMoneyResultQuery(InOutMoney inMoney)
    throws RemoteException
  {
    return this.cp.inOutMoneyResultQuery(inMoney);
  }
  
  public ReturnValue outMoneyResultQuery(InOutMoney outMoney)
    throws RemoteException
  {
    return this.cp.inOutMoneyResultQuery(outMoney);
  }
  
  public Vector<CapitalValue> getCRJMX(String systemID)
    throws RemoteException
  {
    return this.cp.getCRJMX(systemID);
  }
  
  public ReturnValue saveQSData(Vector<SystemQSValue> vec)
    throws RemoteException
  {
    return this.cp.saveQSData(vec);
  }
  
  public ReturnValue getSystemDetailsFile(String systemID)
    throws RemoteException
  {
    return this.cp.getSystemDetailsFile(systemID);
  }
  
  public ReturnValue fundsTransfer(InOutMoney outMoney, String systemID)
    throws RemoteException
  {
    return this.cp.fundsTransfer(outMoney, systemID);
  }
  
  public Vector<SystemMessage> getSystem(String systemID, String sysFirmID, String firmID)
    throws RemoteException
  {
    return this.cp.getSystem(systemID, sysFirmID, firmID);
  }
  
  public ReturnValue doWork(RequestMsg req)
    throws RemoteException
  {
    ReturnValue result = new ReturnValue();
    if (req == null)
    {
      Tool.log("传入的请求参数为null");
      result.result = -1L;
      result.remark = "请求参数非法";
      return result;
    }
    String methodName = req.getMethodName();
    if ((methodName == null) || ("".equals(methodName.trim())))
    {
      Tool.log("请求参数中，方法名为空");
      result.result = -1L;
      result.remark = "请求参数不正确";
      return result;
    }
    Class proc = this.cp.getClass();
    Method method = null;
    Object[] params = req.getParams();
    if ((params == null) || (params.length <= 0))
    {
      try
      {
        method = proc.getMethod(methodName, new Class[0]);
      }
      catch (SecurityException e)
      {
        Tool.log("得到被调用方法SecurityException：" + Tool.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
      catch (NoSuchMethodException e)
      {
        Tool.log("没有找到对应的方法：" + Tool.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
      try
      {
        result = (ReturnValue)method.invoke(this.cp, new Object[0]);
      }
      catch (Exception e)
      {
        Tool.log("调用方法异常：" + Tool.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
    }
    else
    {
      Class[] paramsType = new Class[params.length];
      for (int i = 0; i < params.length; i++) {
        paramsType[i] = params[i].getClass();
      }
      try
      {
        method = proc.getMethod(methodName, paramsType);
      }
      catch (SecurityException e)
      {
        Tool.log("得到被调用方法SecurityException：" + Tool.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
      catch (NoSuchMethodException e)
      {
        Tool.log("没有找到对应的方法：" + Tool.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
      try
      {
        result = (ReturnValue)method.invoke(this.cp, params);
      }
      catch (Exception e)
      {
        Tool.log("调用方法异常：" + Tool.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
    }
    return result;
  }
  
  public String getFirmID(String contact, String bankID)
    throws RemoteException
  {
    return this.cp.getFirmID(contact, bankID);
  }
  
  public ReturnValue updateCCBQS(Map<String, String> map)
    throws RemoteException
  {
    return this.cp.updateCCBQS(map);
  }
  
  public Vector<FirmBalance> getCCBQS(String filter)
    throws RemoteException
  {
    return this.cp.getCCBQS(filter);
  }
  
  public ReturnValue addCCBQS(Vector<FirmBalance> vec)
    throws RemoteException
  {
    return this.cp.addCCBQS(vec);
  }
}
