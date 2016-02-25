package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.CCBQSResult;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public abstract interface CCBCapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract ReturnValue modCapitalInfoStatus(long paramLong, String paramString, int paramInt, Timestamp paramTimestamp)
    throws RemoteException;
  
  public abstract ReturnValue ifQuitFirm(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract Map<String, CapitalValue> getCapitalValue(Vector<String> paramVector, String paramString)
    throws RemoteException;
  
  public abstract String getfirmid(String paramString)
    throws RemoteException;
  
  public abstract int modfirmuser(FirmValue paramFirmValue, String paramString)
    throws RemoteException;
  
  public abstract Vector<FirmValue> getFirmUserList(String paramString1, int[] paramArrayOfInt, String paramString2, String paramString3, String paramString4)
    throws RemoteException;
  
  public abstract FirmValue getFirmValue(FirmValue paramFirmValue)
    throws RemoteException;
  
  public abstract Vector<CorrespondValue> getCorrespondList(String paramString)
    throws RemoteException;
  
  public abstract int[] getPageinfo()
    throws RemoteException;
  
  public abstract ReturnValue checkSigning(FirmValue paramFirmValue)
    throws RemoteException;
  
  public abstract int updateFirmInfo(String paramString1, String paramString2, String paramString3, String paramString4)
    throws RemoteException;
  
  public abstract void send(Date paramDate)
    throws RemoteException;
  
  public abstract ReturnValue saveCCBQSResult(Vector<CCBQSResult> paramVector)
    throws RemoteException;
  
  public abstract long modBankQS(ReturnValue paramReturnValue, Date paramDate)
    throws RemoteException;
  
  public abstract ReturnValue updateCCBQS(Map<String, String> paramMap)
    throws RemoteException;
  
  public abstract Vector<FirmBalance> getCCBQS(String paramString)
    throws RemoteException;
  
  public abstract void sendTZData(Date paramDate, List<String> paramList)
    throws RemoteException;
  
  public abstract Map<String, Double> getFirmBalanceMap(String paramString1, String paramString2)
    throws RemoteException;
}
