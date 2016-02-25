package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
import java.rmi.RemoteException;
import java.util.Date;

public abstract interface YjfCapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract ReturnValue modCapitalInfoStatus(long paramLong, String paramString)
    throws RemoteException;
  
  public abstract long outMoneyForAccess(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws RemoteException;
  
  public abstract ReturnValue synchroAccountMarket(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue BankTransferResultNotice(long paramLong, int paramInt)
    throws RemoteException;
  
  public abstract ReturnValue sendHRBQSValue(String paramString, String[] paramArrayOfString, Date paramDate)
    throws RemoteException;
  
  public abstract ReturnValue modBankQS(BankQSVO paramBankQSVO)
    throws RemoteException;
  
  public abstract ReturnValue modAccount(CorrespondValue paramCorrespondValue1, CorrespondValue paramCorrespondValue2, String paramString)
    throws RemoteException;
  
  public abstract ReturnValue inMoneyResultQuery(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract ReturnValue outMoneyResultQuery(String paramString1, String paramString2)
    throws RemoteException;
}
