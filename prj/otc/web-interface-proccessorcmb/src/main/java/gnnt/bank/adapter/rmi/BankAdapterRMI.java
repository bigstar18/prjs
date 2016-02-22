package gnnt.bank.adapter.rmi;

import gnnt.bank.adapter.bankBusiness.message.CEB_RSP;
import gnnt.bank.adapter.bankBusiness.message.CEB_param;
import gnnt.trade.bank.data.cgb.vo.AccountStatusReconciliation;
import gnnt.trade.bank.data.cgb.vo.StorageMoneyLedgerBalanceList;
import gnnt.trade.bank.data.cgb.vo.StorageMoneySettlementList;
import gnnt.trade.bank.data.cgb.vo.TransferAccountsTransactionDetailed;
import gnnt.trade.bank.data.cmbc.vo.Accr;
import gnnt.trade.bank.data.cmbc.vo.Sbala;
import gnnt.trade.bank.data.cmbc.vo.Sbusi;
import gnnt.trade.bank.data.cmbc.vo.Spay;
import gnnt.trade.bank.data.hxb.DZ;
import gnnt.trade.bank.data.hxb.QS;
import gnnt.trade.bank.data.icbc.vo.FirmRights;
import gnnt.trade.bank.data.icbc.vo.OpenOrDelFirmValue;
import gnnt.trade.bank.data.icbc.vo.TradingDetailsValue;
import gnnt.trade.bank.data.sfz.vo.BatQs;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalance_CEB;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.MarketCashBalance;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.QSRresult;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TradeResultValue;
import gnnt.trade.bank.vo.TransferMoneyVO;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import gnnt.trade.bank.vo.bankdz.xy.resave.FFHDValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.ZFPHValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public abstract interface BankAdapterRMI
  extends Remote
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
  
  public abstract ReturnValue delAccountQuery(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue loginBank(String paramString)
    throws RemoteException;
  
  public abstract ReturnValue quitBank(String paramString)
    throws RemoteException;
  
  public abstract Vector<MoneyInfoValue> getBankMoneyInfo(Date paramDate, Vector paramVector)
    throws RemoteException;
  
  public abstract double accountQuery(CorrespondValue paramCorrespondValue, String paramString)
    throws RemoteException;
  
  public abstract Vector<InMoneyVO> getHisDitail(Date paramDate)
    throws RemoteException;
  
  public abstract ReturnValue SendFirmInfo(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue sendTradeDate(Vector<FirmBalance> paramVector)
    throws RemoteException;
  
  public abstract ReturnValue sendTradeDate(Vector<FirmBalance> paramVector1, Vector<FirmBalance> paramVector2)
    throws RemoteException;
  
  public abstract ReturnValue sendTradeDate(Vector<FirmBalance> paramVector, MarketCashBalance paramMarketCashBalance)
    throws RemoteException;
  
  public abstract ReturnValue getBankFileStatus(Date paramDate1, Date paramDate2, int paramInt)
    throws RemoteException;
  
  public abstract Vector<QSRresult> getQSRresult(Date paramDate)
    throws RemoteException;
  
  public abstract boolean createDataFile(String paramString1, String paramString2, Date paramDate)
    throws RemoteException;
  
  public abstract int setBankMoneyInfo(Date paramDate)
    throws RemoteException;
  
  public abstract ReturnValue setBankMoneyInfo(Hashtable<String, TradeResultValue> paramHashtable)
    throws RemoteException;
  
  public abstract ReturnValue setTotalMoneyInfo(Hashtable<String, Double> paramHashtable)
    throws RemoteException;
  
  public abstract ReturnValue inMoneyQueryBankByEBank(InMoneyVO paramInMoneyVO)
    throws RemoteException;
  
  public abstract ReturnValue synchroAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue cancelAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue rgstAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue delAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue modAccount(CorrespondValue paramCorrespondValue1, CorrespondValue paramCorrespondValue2)
    throws RemoteException;
  
  public abstract ReturnValue setRZ(RZQSValue paramRZQSValue, RZDZValue paramRZDZValue, Date paramDate)
    throws RemoteException;
  
  public abstract ZFPHValue getZFPH(Date paramDate)
    throws RemoteException;
  
  public abstract FFHDValue getFFHD(Date paramDate)
    throws RemoteException;
  
  public abstract ReturnValue bankTransfer(BankTransferValue paramBankTransferValue)
    throws RemoteException;
  
  public abstract ReturnValue synchroAccountMarket(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue inMoneyResultQuery(String paramString)
    throws RemoteException;
  
  public abstract ReturnValue outMoneyResultQuery(String paramString)
    throws RemoteException;
  
  public abstract ReturnValue sendTradeData(List<FirmRights> paramList, List<TradingDetailsValue> paramList1, List<OpenOrDelFirmValue> paramList2, Date paramDate)
    throws RemoteException;
  
  public abstract ReturnValue getQSDate(Date paramDate, String paramString)
    throws RemoteException;
  
  public abstract ReturnValue hxSentQS(Vector<QS> paramVector, Date paramDate)
    throws RemoteException;
  
  public abstract ReturnValue hxSentDZ(Vector<DZ> paramVector, Date paramDate)
    throws RemoteException;
  
  public abstract CEB_RSP CEB(CEB_param paramCEB_param, String paramString)
    throws RemoteException;
  
  public abstract ReturnValue sendFile_CEB(Vector<FirmBalance_CEB> paramVector, Date paramDate)
    throws RemoteException;
  
  public abstract ReturnValue sentMaketQS(BatQs paramBatQs)
    throws RemoteException;
  
  public abstract ReturnValue sendCMBCQSValue(List<Sbusi> paramList, List<Sbala> paramList1, List<Spay> paramList2, List<Accr> paramList3, Date paramDate)
    throws RemoteException;
  
  public abstract ReturnValue sendCGBQS(List<TransferAccountsTransactionDetailed> paramList, List<AccountStatusReconciliation> paramList1, List<StorageMoneySettlementList> paramList2, List<StorageMoneyLedgerBalanceList> paramList3, Date paramDate)
    throws RemoteException;
}
