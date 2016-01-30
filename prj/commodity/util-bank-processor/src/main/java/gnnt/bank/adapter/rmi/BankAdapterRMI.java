package gnnt.bank.adapter.rmi;

import gnnt.bank.adapter.bankBusiness.message.CEB_RSP;
import gnnt.bank.adapter.bankBusiness.message.CEB_param;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalance_CEB;
import gnnt.trade.bank.vo.FirmRightsValue;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.InOutMoney;
import gnnt.trade.bank.vo.MarketFirmMsg;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.QSChangeResult;
import gnnt.trade.bank.vo.QSRresult;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TradeResultValue;
import gnnt.trade.bank.vo.TransferMoneyVO;
import gnnt.trade.bank.vo.bankdz.boc.AccountStatusReconciliation;
import gnnt.trade.bank.vo.bankdz.boc.StorageMoneyLedgerBalanceList;
import gnnt.trade.bank.vo.bankdz.boc.StorageMoneySettlementList;
import gnnt.trade.bank.vo.bankdz.boc.TransferAccountsTransactionDetailed;
import gnnt.trade.bank.vo.bankdz.gs.sent.BankFirmRightValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRights;
import gnnt.trade.bank.vo.bankdz.gs.sent.OpenOrDelFirmValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.ProperBalanceValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.TradingDetailsValue;
import gnnt.trade.bank.vo.bankdz.hx.sent.HXSentQSMsgValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import gnnt.trade.bank.vo.bankdz.ms.Accr;
import gnnt.trade.bank.vo.bankdz.ms.Sbala;
import gnnt.trade.bank.vo.bankdz.ms.Sbusi;
import gnnt.trade.bank.vo.bankdz.ms.Spay;
import gnnt.trade.bank.vo.bankdz.pf.resave.TraderResult;
import gnnt.trade.bank.vo.bankdz.pf.sent.Margins;
import gnnt.trade.bank.vo.bankdz.pf.sent.TradeList;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzBChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzFailChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatFailResultChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.KXHfailChild;
import gnnt.trade.bank.vo.bankdz.sfz.sent.BatQs;
import gnnt.trade.bank.vo.bankdz.xy.resave.FFHDValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.ZFPHValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public abstract interface BankAdapterRMI extends Remote
{
  public abstract String testRmi()
    throws RemoteException;

  public abstract ReturnValue inMoneyQueryBank(InMoneyVO paramInMoneyVO)
    throws RemoteException;

  public abstract ReturnValue outMoneyMarketDone(OutMoneyVO paramOutMoneyVO)
    throws RemoteException;

  public abstract ReturnValue outMoneyBackBank(OutMoneyVO paramOutMoneyVO, boolean paramBoolean)
    throws RemoteException;

  public abstract ReturnValue transferMoney(TransferMoneyVO paramTransferMoneyVO)
    throws RemoteException;

  public abstract ReturnValue rgstAccountQuery(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue cancelAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue delAccountQuery(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue loginBank(String paramString)
    throws RemoteException;

  public abstract ReturnValue loginBank()
    throws RemoteException;

  public abstract ReturnValue quitBank(String paramString)
    throws RemoteException;

  public abstract ReturnValue quitBank()
    throws RemoteException;

  public abstract Vector<MoneyInfoValue> getBankMoneyInfo(Date paramDate, Vector paramVector)
    throws RemoteException;

  public abstract int setBankMoneyInfo(Date paramDate)
    throws RemoteException;

  public abstract int setBankMoneyInfo(List<FirmBalance> paramList)
    throws RemoteException;

  public abstract double accountQuery(CorrespondValue paramCorrespondValue, String paramString)
    throws RemoteException;

  public abstract ReturnValue setBankMoneyInfo(Hashtable<String, TradeResultValue> paramHashtable)
    throws RemoteException;

  public abstract ReturnValue setTotalMoneyInfo(Hashtable<String, Double> paramHashtable)
    throws RemoteException;

  public abstract ReturnValue SendFirmInfo(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue sendTradeDate(Vector<FirmBalance> paramVector)
    throws RemoteException;

  public abstract Vector<QSRresult> getQSRresult(Date paramDate)
    throws RemoteException;

  public abstract BigDecimal getTotalMoney(Date paramDate)
    throws RemoteException;

  public abstract Vector<FirmRightsValue> getFirmMoney(Date paramDate)
    throws RemoteException;

  public abstract Vector<InMoneyVO> getHisDitail(Date paramDate)
    throws RemoteException;

  public abstract Vector<KXHfailChild> getFirmODInfo(Date paramDate)
    throws RemoteException;

  public abstract ReturnValue sentMaketQS(BatQs paramBatQs)
    throws RemoteException;

  public abstract Vector<BatCustDzFailChild> getBatCustDz(Date paramDate)
    throws RemoteException;

  public abstract Vector<BatCustDzBChild> getfirmBalanceFile(Date paramDate)
    throws RemoteException;

  public abstract Vector<BatFailResultChild> getFirmBalanceError(Date paramDate)
    throws RemoteException;

  public abstract ReturnValue getBankFileStatus(Date paramDate1, Date paramDate2, int paramInt)
    throws RemoteException;

  public abstract TraderResult sendTradeDetails(Vector<TradeList> paramVector, int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract TraderResult sendMargins(Vector<Margins> paramVector, int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract ReturnValue setRZ(RZQSValue paramRZQSValue, RZDZValue paramRZDZValue, Date paramDate)
    throws RemoteException;

  public abstract ZFPHValue getZFPH(Date paramDate)
    throws RemoteException;

  public abstract FFHDValue getFFHD(Date paramDate)
    throws RemoteException;

  public abstract ReturnValue rgstAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue delAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue modAccount(CorrespondValue paramCorrespondValue1, CorrespondValue paramCorrespondValue2)
    throws RemoteException;

  public abstract ReturnValue synchronousFirms(Vector<MarketFirmMsg> paramVector)
    throws RemoteException;

  public abstract ReturnValue hxSentQS(Vector<HXSentQSMsgValue> paramVector, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue hxSentDZ(Vector<HXSentQSMsgValue> paramVector, Date paramDate)
    throws RemoteException;

  public abstract Vector<QSChangeResult> hxGetQSError(Date paramDate)
    throws RemoteException;

  public abstract Vector<QSRresult> hxGetDZError(Date paramDate)
    throws RemoteException;

  public abstract void sendTradeData()
    throws RemoteException;

  public abstract ReturnValue sendTradeData(List<FirmRights> paramList, List<TradingDetailsValue> paramList1, List<OpenOrDelFirmValue> paramList2, Date paramDate)
    throws RemoteException;

  public abstract void getTradeDataRst(Date paramDate)
    throws RemoteException;

  public abstract Vector<BankFirmRightValue> getBankFirmRightValue(String paramString, Date paramDate)
    throws RemoteException;

  public abstract ProperBalanceValue getProperBalanceValue(String paramString, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue sendCMBCQSValue(List<Sbusi> paramList, List<Sbala> paramList1, List<Spay> paramList2, List<Accr> paramList3, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue synchroAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue sendZHQS(List<TransferAccountsTransactionDetailed> paramList, List<AccountStatusReconciliation> paramList1, List<StorageMoneySettlementList> paramList2, List<StorageMoneyLedgerBalanceList> paramList3, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue CommunicatTest()
    throws RemoteException;

  public abstract ReturnValue setRZ(RZQSValue paramRZQSValue, RZDZValue paramRZDZValue)
    throws RemoteException;

  public abstract ReturnValue bankTransfer(BankTransferValue paramBankTransferValue)
    throws RemoteException;

  public abstract ReturnValue inMoneyQueryBankByEBank(InMoneyVO paramInMoneyVO)
    throws RemoteException;

  public abstract ReturnValue outMoneyResultQuery(long paramLong)
    throws RemoteException;

  public abstract ReturnValue outMoneyResultQuery(String paramString)
    throws RemoteException;

  public abstract ReturnValue inMoneyResultQuery(long paramLong)
    throws RemoteException;

  public abstract ReturnValue inMoneyResultQuery(String paramString)
    throws RemoteException;

  public abstract CEB_RSP CEB(CEB_param paramCEB_param, String paramString)
    throws RemoteException;

  public abstract ReturnValue sendFile_CEB(Vector<FirmBalance_CEB> paramVector)
    throws RemoteException;

  public abstract ReturnValue inMoneyResultQuery(String paramString1, String paramString2)
    throws RemoteException;

  public abstract ReturnValue outMoneyResultQuery(String paramString1, String paramString2)
    throws RemoteException;

  public abstract boolean createDataFile(String paramString1, String paramString2, Date paramDate)
    throws RemoteException;

  public abstract ReturnValue inMoneyPT2Sys(InOutMoney paramInOutMoney)
    throws RemoteException;

  public abstract ReturnValue outMoneyPT2Sys(InOutMoney paramInOutMoney)
    throws RemoteException;

  public abstract ReturnValue rsgAccountResultNotice(CorrespondValue paramCorrespondValue)
    throws RemoteException;

  public abstract ReturnValue inMoneyResultNotice(InOutMoney paramInOutMoney, int paramInt)
    throws RemoteException;

  public abstract ReturnValue outMoneyResultNotice(InOutMoney paramInOutMoney, int paramInt)
    throws RemoteException;

  public abstract ReturnValue getSystemInOutMoneyDetails(Vector<CapitalValue> paramVector, Date paramDate, String paramString)
    throws RemoteException;
}