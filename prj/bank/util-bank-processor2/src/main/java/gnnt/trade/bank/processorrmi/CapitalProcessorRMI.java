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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

public abstract interface CapitalProcessorRMI
  extends Remote
{
  public abstract String testRmi()
    throws RemoteException;
  
  public abstract long getMktActionID()
    throws RemoteException;
  
  public abstract Vector<BankValue> getBankList(String paramString)
    throws RemoteException;
  
  public abstract BankValue getBank(String paramString)
    throws RemoteException;
  
  public abstract ReturnValue modBank(BankValue paramBankValue)
    throws RemoteException;
  
  public abstract ReturnValue changeBankTradeType(Vector<String> paramVector, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract Hashtable<String, String> getBankInfo(String paramString)
    throws RemoteException;
  
  public abstract Map<String, CorrespondValue> getCorrespondValue(Vector<String> paramVector, String paramString)
    throws RemoteException;
  
  public abstract Vector<CorrespondValue> getCorrespondValue(String paramString)
    throws RemoteException;
  
  public abstract Vector<FirmValue> getFirmUser(String paramString)
    throws RemoteException;
  
  public abstract long isPassword(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract long modPwd(String paramString1, String paramString2, String paramString3)
    throws RemoteException;
  
  public abstract ReturnValue initializeFrimPwd(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract ReturnValue initializeFrimPwd(String paramString)
    throws RemoteException;
  
  public abstract ReturnValue modCorrespondStatus(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract long rgstAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue openAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue openAccountMarket(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract long delAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue delAccountMaket(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue modAccount(CorrespondValue paramCorrespondValue1, CorrespondValue paramCorrespondValue2)
    throws RemoteException;
  
  public abstract ReturnValue modAccountMarket(CorrespondValue paramCorrespondValue1, CorrespondValue paramCorrespondValue2)
    throws RemoteException;
  
  public abstract long tradeDate(String paramString)
    throws RemoteException;
  
  public abstract ReturnValue isTradeDate(Date paramDate)
    throws RemoteException;
  
  public abstract boolean getTraderStatus()
    throws RemoteException;
  
  public abstract FirmMessageVo getFirmMSG(String paramString)
    throws RemoteException;
  
  public abstract ReturnValue ifbankTrade(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract long inMoney(String paramString1, String paramString2, String paramString3, Timestamp paramTimestamp, double paramDouble, String paramString4, long paramLong, int paramInt, String paramString5)
    throws RemoteException;
  
  public abstract ReturnValue inMoneyMarket(InMoneyMarket paramInMoneyMarket)
    throws RemoteException;
  
  public abstract ReturnValue outMoney(String paramString1, double paramDouble, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract ReturnValue outMoneyMarket(OutMoneyMarket paramOutMoneyMarket)
    throws RemoteException;
  
  public abstract ReturnValue moneyInAudit(long paramLong, String paramString, boolean paramBoolean)
    throws RemoteException;
  
  public abstract ReturnValue chargeAgainst(ChargeAgainstValue paramChargeAgainstValue)
    throws RemoteException;
  
  public abstract Vector<FirmBankMsg> getfirmBankMsg(String paramString)
    throws RemoteException;
  
  public abstract FirmBalanceValue getMarketBalance(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract FirmBalanceValue getMarketBalance(String paramString1, String paramString2, String paramString3)
    throws RemoteException;
  
  public abstract FirmBalanceValue getFirmBalance(String paramString1, String paramString2, String paramString3)
    throws RemoteException;
  
  public abstract Vector<CapitalValue> getCapitalList(String paramString)
    throws RemoteException;
  
  public abstract int getBankCompareInfo(String paramString, Date paramDate)
    throws RemoteException;
  
  public abstract int insertBankCompareInfo(Vector<MoneyInfoValue> paramVector)
    throws RemoteException;
  
  public abstract long insertBankMoneyInfo(Vector<MoneyInfoValue> paramVector, int paramInt)
    throws RemoteException;
  
  public abstract ReturnValue roughInfo(String paramString, Date paramDate)
    throws RemoteException;
  
  public abstract Vector<CompareResult> getBankNoInfo(String paramString, Date paramDate)
    throws RemoteException;
  
  public abstract Vector<CompareResult> getMarketNoInfo(String paramString, Date paramDate)
    throws RemoteException;
  
  public abstract Vector<CapitalCompare> sumResultInfo(String paramString, String[] paramArrayOfString, Date paramDate)
    throws RemoteException;
  
  public abstract ReturnValue refuseCapitalInfo(Vector<Long> paramVector)
    throws RemoteException;
  
  public abstract ReturnValue refuseCapitalInfo(long paramLong)
    throws RemoteException;
  
  public abstract ReturnValue supplyCapitalInfo(Vector<CapitalValue> paramVector)
    throws RemoteException;
  
  public abstract ReturnValue supplyCapitalInfo(CapitalValue paramCapitalValue)
    throws RemoteException;
  
  public abstract Vector<InterfaceLog> interfaceLogList(String paramString, int[] paramArrayOfInt)
    throws RemoteException;
  
  public abstract int interfaceLog(InterfaceLog paramInterfaceLog)
    throws RemoteException;
  
  public abstract ReturnValue relevanceAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue synchroAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract Vector<FirmValue> getFirmUserList(String paramString1, int[] paramArrayOfInt, String paramString2, String paramString3, String paramString4)
    throws RemoteException;
  
  public abstract int[] getPageinfo()
    throws RemoteException;
  
  public abstract ReturnValue outMoneyAudit(long paramLong, boolean paramBoolean)
    throws RemoteException;
  
  public abstract String getfirmid(String paramString)
    throws RemoteException;
  
  public abstract ReturnValue ifQuitFirm(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract ReturnValue sendOldQS(Date paramDate)
    throws RemoteException;
  
  public abstract ReturnValue modAccount(CorrespondValue paramCorrespondValue1, CorrespondValue paramCorrespondValue2, String paramString)
    throws RemoteException;
}
