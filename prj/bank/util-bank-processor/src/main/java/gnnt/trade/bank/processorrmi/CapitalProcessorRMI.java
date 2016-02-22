package gnnt.trade.bank.processorrmi;

import gnnt.bank.adapter.bankBusiness.dayData.FCS_10_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_11_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_13_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_99;
import gnnt.bank.adapter.bankBusiness.message.CEB_RSP;
import gnnt.bank.adapter.bankBusiness.message.CEB_param;
import gnnt.bank.platform.vo.RequestMsg;
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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public abstract interface CapitalProcessorRMI extends Remote
{
  public abstract long getMktActionID()
    throws RemoteException;

  public abstract Vector<BankValue> getBankList(String paramString)
    throws RemoteException;

  public abstract long inMoney(String paramString1, String paramString2, String paramString3, Timestamp paramTimestamp, double paramDouble, String paramString4, long paramLong, int paramInt, String paramString5)
    throws RemoteException;

  public abstract long inMoneyMarket(String paramString1, String paramString2, String paramString3, String paramString4, double paramDouble, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10)
    throws RemoteException;

  public abstract long inMoneyMarket(String paramString1, String paramString2, String paramString3, String paramString4, double paramDouble, String paramString5)
    throws RemoteException;

  public abstract ReturnValue outMoney(String paramString1, double paramDouble, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract long transferMoney(String paramString1, int paramInt, double paramDouble, String paramString2, String paramString3)
    throws RemoteException;

  public abstract long rgstAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract long delAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract long delAccountMaket(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue delAccountMaketForYJF(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract int setMoneyInfo(String paramString, Date paramDate)
    throws RemoteException;

  public abstract long setMoneyInfoAutoOrNo(String paramString, Date paramDate, int paramInt)
    throws RemoteException;

  public abstract int getBankCompareInfo(String paramString, Date paramDate)
    throws RemoteException;

  public abstract int checkMoneyByNumber()
    throws RemoteException;

  public abstract Vector<CompareResult> checkMoney(String paramString, Date paramDate)
    throws RemoteException;

  public abstract Vector<CapitalCompare> sumResultInfo(String paramString, String[] paramArrayOfString, Date paramDate)
    throws RemoteException;

  public abstract Vector<CompareResult> checkMoney(Date paramDate)
    throws RemoteException;

  public abstract int insertBankCompareInfo(Vector<MoneyInfoValue> paramVector)
    throws RemoteException;

  public abstract Hashtable<String, String> getBankInfo(String paramString)
    throws RemoteException;

  public abstract Vector<CapitalValue> getCapitalList(String paramString)
    throws RemoteException;

  public abstract Vector<CorrespondValue> getCorrespondValue(String paramString)
    throws RemoteException;

  public abstract Map<String, CorrespondValue> getCorrespondValue(Vector<String> paramVector, String paramString)
    throws RemoteException;

  public abstract ReturnValue chargeAgainst(ChargeAgainstValue paramChargeAgainstValue)
    throws RemoteException;

  public abstract ReturnValue chargeAgainstMaket(ChargeAgainstValue paramChargeAgainstValue)
    throws RemoteException;

  public abstract Hashtable<String, Hashtable<String, String>> getFirmTradeBal(Date paramDate)
    throws RemoteException;

  public abstract long sendTotalMoneyToBank(String paramString, Hashtable<String, Double> paramHashtable)
    throws RemoteException;

  public abstract long outMoneyForAccess(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws RemoteException;

  public abstract String testRmi()
    throws RemoteException;

  public abstract Hashtable<String, String> getBankIdAndAdapterClassname()
    throws RemoteException;

  public abstract long insertBankMoneyInfo(Vector<MoneyInfoValue> paramVector, int paramInt)
    throws RemoteException;

  public abstract ReturnValue openAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue destroyAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue modAccount(CorrespondValue paramCorrespondValue1, CorrespondValue paramCorrespondValue2)
    throws RemoteException;

  public abstract FirmBalanceValue getMarketBalance(String paramString)
    throws RemoteException;

  public abstract FirmBalanceValue getMarketBalance(String paramString1, String paramString2)
    throws RemoteException;

  public abstract FirmBalanceValue getFirmBalance(String paramString1, String paramString2, String paramString3)
    throws RemoteException;

  public abstract long setMoneyInfoByHashtable(String paramString, Hashtable<String, TradeResultValue> paramHashtable)
    throws RemoteException;

  public abstract Vector<BankCompareInfoValue> getBankCapList(String paramString)
    throws RemoteException;

  public abstract FirmMessageVo getFirmMSG(String paramString)
    throws RemoteException;

  public abstract boolean getTraderStatus()
    throws RemoteException;

  public abstract long moneyInAudit(long paramLong, boolean paramBoolean)
    throws RemoteException;

  public abstract long tradeDate(String paramString)
    throws RemoteException;

  public abstract ReturnValue isPassword(String paramString1, String paramString2)
    throws RemoteException;

  public abstract ReturnValue modPwd(String paramString1, String paramString2, String paramString3)
    throws RemoteException;

  public abstract ReturnValue openAccountMarket(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue synchroAccountMarket(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract BankValue getBank(String paramString)
    throws RemoteException;

  public abstract boolean getTradeFlag()
    throws RemoteException;

  public abstract ReturnValue isTradeDate(Date paramDate)
    throws RemoteException;

  public abstract long inMoneyNoAdapter(String paramString1, String paramString2, String paramString3, String paramString4, double paramDouble, String paramString5)
    throws RemoteException;

  public abstract long outMoneyNoAdapter(String paramString1, double paramDouble, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract FirmBalanceValue getBalanceNoAdapter(String paramString)
    throws RemoteException;

  public abstract long moneyAuditNoAdapter(long paramLong, boolean paramBoolean)
    throws RemoteException;

  public abstract ReturnValue modAccountNoAdapter(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract long delAccountNoAdapter(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue ifQuitFirm(String paramString1, String paramString2)
    throws RemoteException;

  public abstract ReturnValue relevanceAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue saveFirmKXH(Vector<KXHfailChild> paramVector, String paramString)
    throws RemoteException;

  public abstract ReturnValue saveFirmKXH(Date paramDate, String paramString)
    throws RemoteException;

  public abstract ReturnValue getBatCustDz(Date paramDate, String paramString)
    throws RemoteException;

  public abstract ReturnValue getBatCustDz(Vector<BatCustDzFailChild> paramVector, Date paramDate, String paramString)
    throws RemoteException;

  public abstract ReturnValue getfirmBalanceFile(Date paramDate, String paramString)
    throws RemoteException;

  public abstract ReturnValue getfirmBalanceFile(Vector<BatCustDzBChild> paramVector, Date paramDate, String paramString)
    throws RemoteException;

  public abstract ReturnValue getFirmBalanceError(Date paramDate, String paramString)
    throws RemoteException;

  public abstract ReturnValue getFirmBalanceError(Vector<BatFailResultChild> paramVector, Date paramDate, String paramString)
    throws RemoteException;

  public abstract ReturnValue getBankFileStatus(Date paramDate, int paramInt, String paramString)
    throws RemoteException;

  public abstract ReturnValue sentMaketQS(Date paramDate, String paramString)
    throws RemoteException;

  public abstract ReturnValue modCapitalInfoStatus(long paramLong, String paramString)
    throws RemoteException;

  public abstract ReturnValue sentFirmBalance(String paramString, Date paramDate)
    throws RemoteException;

  public abstract Map<String, CapitalValue> getCapitalValue(Vector<String> paramVector, String paramString)
    throws RemoteException;

  public abstract ReturnValue saveQSResult(String paramString1, String paramString2)
    throws RemoteException;

  public abstract ReturnValue saveQSResult(Vector<QSRresult> paramVector)
    throws RemoteException;

  public abstract ReturnValue synchronousFirms(String paramString, String[] paramArrayOfString)
    throws RemoteException;

  public abstract ReturnValue hxSentQS(String paramString, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue hxSentDZ(String paramString, Date paramDate)
    throws RemoteException;

  public abstract Vector<HXSentQSMsgValue> hxGetQS(String paramString, Date paramDate)
    throws RemoteException;

  public abstract Vector<HXSentQSMsgValue> hxGetDZ(String paramString, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue hxGetQSError(String paramString, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue hxGetDZError(String paramString, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue hxSaveQSError(Vector<QSChangeResult> paramVector)
    throws RemoteException;

  public abstract ReturnValue hxSaveDZError(Vector<QSRresult> paramVector)
    throws RemoteException;

  public abstract ReturnValue pfdbQS(String paramString, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue getTradesDateDetailsList(String paramString, Date paramDate, int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract Vector<TradeList> getTradesDateDetailsList(String paramString, Date paramDate, String[] paramArrayOfString)
    throws RemoteException;

  public abstract ReturnValue getDongjieDetailList(String paramString, Date paramDate, int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract Vector<Margins> getDongjieDetailList(String paramString, Date paramDate, String[] paramArrayOfString)
    throws RemoteException;

  public abstract Hashtable<String, FundsMarg> getFundsMarg(String paramString, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue sendGHQS(String paramString1, String paramString2, Date paramDate)
    throws RemoteException;

  public abstract List<FirmRights> getRightsList(String paramString1, String paramString2, Date paramDate)
    throws RemoteException;

  public abstract List<TradingDetailsValue> getChangeBalance(String paramString1, String paramString2, Date paramDate)
    throws RemoteException;

  public abstract List<OpenOrDelFirmValue> getOpenOrDropMaket(String paramString, Date paramDate)
    throws RemoteException;

  public abstract long setBankFirmRightValue(List<BankFirmRightValue> paramList)
    throws RemoteException;

  public abstract long setProperBalanceValue(ProperBalanceValue paramProperBalanceValue)
    throws RemoteException;

  public abstract ReturnValue getBankFirmRightValue(String paramString, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue getProperBalanceValue(String paramString, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue updownBank(String paramString, int paramInt)
    throws RemoteException;

  public abstract ReturnValue sendXYQSValue(String paramString, String[] paramArrayOfString, Date paramDate)
    throws RemoteException;

  public abstract RZQSValue getXYQSValue(String paramString, String[] paramArrayOfString, Date paramDate)
    throws RemoteException;

  public abstract RZDZValue getXYDZValue(String paramString, String[] paramArrayOfString, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue getZFPH(String paramString, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue getFFHD(String paramString, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue saveZFPH(ZFPHValue paramZFPHValue)
    throws RemoteException;

  public abstract ReturnValue saveFFHD(FFHDValue paramFFHDValue)
    throws RemoteException;

  public abstract ReturnValue addMarketMoney(XYMarketMoney paramXYMarketMoney)
    throws RemoteException;

  public abstract ReturnValue modMarketMoney(XYMarketMoney paramXYMarketMoney)
    throws RemoteException;

  public abstract ReturnValue BankTransferResultNotice(long paramLong, int paramInt)
    throws RemoteException;

  public abstract ReturnValue marketTransfer(BankTransferValue paramBankTransferValue)
    throws RemoteException;

  public abstract long bankTransfer(long paramLong, int paramInt)
    throws RemoteException;

  public abstract ReturnValue sendHRBQSValue(String paramString, String[] paramArrayOfString, Date paramDate)
    throws RemoteException;

  public abstract long insertFirmTradeStatus(Vector<FirmTradeStatus> paramVector, int paramInt)
    throws RemoteException;

  public abstract long insertTradeDetailAccount(Vector<TradeDetailAccount> paramVector, int paramInt)
    throws RemoteException;

  public abstract ReturnValue preOpenAccountMarket(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue sendCMBCQSValue(String paramString, Date paramDate)
    throws RemoteException;

  public abstract long isLogPassword(String paramString1, String paramString2)
    throws RemoteException;

  public abstract ReturnValue marketOpenAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract Vector<RgstCapitalValue> getRgstCapitalValue(String paramString)
    throws RemoteException;

  public abstract ReturnValue modRgstCapitalValue(RgstCapitalValue paramRgstCapitalValue)
    throws RemoteException;

  public abstract ReturnValue addRgstCapitalValue(CorrespondValue paramCorrespondValue, int paramInt)
    throws RemoteException;

  public abstract long modMoneyCapital(long paramLong, String paramString, boolean paramBoolean)
    throws RemoteException;

  public abstract long inMoneyMarketGS(String paramString1, String paramString2, String paramString3, String paramString4, double paramDouble, String paramString5)
    throws RemoteException;

  public abstract ReturnValue outMoneyGS(String paramString1, double paramDouble, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract FirmBalanceValue getBankBalance(String paramString1, String paramString2, String paramString3)
    throws RemoteException;

  public abstract ReturnValue SpecifiedStorageTubeBankSure()
    throws RemoteException;

  public abstract ReturnValue CommunicationsTest(String paramString)
    throws RemoteException;

  public abstract ReturnValue BankYuSigning(String paramString1, String paramString2, String paramString3, String paramString4)
    throws RemoteException;

  public abstract Hashtable<String, List> getZHQSValue(String paramString, Date paramDate)
    throws RemoteException;

  public abstract long insertClientStates(Vector<ClientState> paramVector, int paramInt)
    throws RemoteException;

  public abstract ReturnValue sendZHQS(String paramString, String[] paramArrayOfString, Date paramDate)
    throws RemoteException;

  public abstract int interfaceLog(InterfaceLog paramInterfaceLog)
    throws RemoteException;

  public abstract CEB_RSP CEB(CEB_param paramCEB_param, String paramString)
    throws RemoteException;

  public abstract String insertFcs(Vector<FCS_10_Result> paramVector, Vector<FCS_11_Result> paramVector1, Vector<FCS_13_Result> paramVector2, Vector<FCS_99> paramVector3)
    throws RemoteException;

  public abstract ReturnValue inMoneyMarketForYJF(String paramString1, String paramString2, String paramString3, String paramString4, double paramDouble, String paramString5)
    throws RemoteException;

  public abstract ReturnValue modAccount(CorrespondValue paramCorrespondValue1, CorrespondValue paramCorrespondValue2, String paramString)
    throws RemoteException;

  public abstract ReturnValue modBankQS(BankQSVO paramBankQSVO)
    throws RemoteException;

  public abstract ReturnValue inMoneyMarketByEBank(String paramString1, String paramString2, String paramString3, String paramString4, double paramDouble, String paramString5)
    throws RemoteException;

  public abstract ReturnValue inMoneyQuery(long paramLong)
    throws RemoteException;

  public abstract ReturnValue outMoneyQuery(long paramLong)
    throws RemoteException;

  public abstract ReturnValue rgstAccountforWeb(String paramString1, String paramString2, String paramString3)
    throws RemoteException;

  public abstract AbcInfoValue getAbcInfo(String paramString, long paramLong, int paramInt)
    throws RemoteException;

  public abstract ReturnValue insertAbcInfo(AbcInfoValue paramAbcInfoValue)
    throws RemoteException;

  public abstract long inMoneyMarketAbc(long paramLong, String paramString1, String paramString2, String paramString3, String paramString4, double paramDouble, String paramString5)
    throws RemoteException;

  public abstract int getBankCompareInfoAbc(String paramString, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue sendFile_CEB(String paramString, Date paramDate)
    throws RemoteException;

  public abstract double getCanOutBalance(String paramString)
    throws RemoteException;

  public abstract ReturnValue synchFirmAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue isOpenQuery(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract Vector<SystemMessage> getSystemMessages(String paramString)
    throws RemoteException;

  public abstract long modSystemMessage(SystemMessage paramSystemMessage)
    throws RemoteException;

  public abstract ReturnValue delCorrBank(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract Vector<SystemMessage> getSystemMsg(String paramString)
    throws RemoteException;

  public abstract ReturnValue loginOrQuit(String paramString1, int paramInt, String paramString2)
    throws RemoteException;

  public abstract Vector<FirmID2SysFirmID> getFirmID2SysFirmID(String paramString)
    throws RemoteException;

  public abstract ReturnBalance getPTandBankBalance(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue inMoneyResultQuery(InOutMoney paramInOutMoney)
    throws RemoteException;

  public abstract ReturnValue outMoneyResultQuery(InOutMoney paramInOutMoney)
    throws RemoteException;

  public abstract Vector<CapitalValue> getCRJMX(String paramString)
    throws RemoteException;

  public abstract ReturnValue saveQSData(Vector<SystemQSValue> paramVector)
    throws RemoteException;

  public abstract ReturnValue getSystemDetailsFile(String paramString)
    throws RemoteException;

  public abstract ReturnValue fundsTransfer(InOutMoney paramInOutMoney, String paramString)
    throws RemoteException;

  public abstract Vector<SystemMessage> getSystem(String paramString1, String paramString2, String paramString3)
    throws RemoteException;

  public abstract ReturnValue doWork(RequestMsg paramRequestMsg)
    throws RemoteException;

  public abstract String getFirmID(String paramString1, String paramString2)
    throws RemoteException;
}