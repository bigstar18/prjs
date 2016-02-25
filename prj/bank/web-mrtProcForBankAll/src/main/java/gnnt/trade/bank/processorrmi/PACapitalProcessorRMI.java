package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.data.sfz.vo.BatCustDzBChild;
import gnnt.trade.bank.data.sfz.vo.BatCustDzFailChild;
import gnnt.trade.bank.data.sfz.vo.BatFailResultChild;
import gnnt.trade.bank.data.sfz.vo.KXHfailChild;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.ReturnValue;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Vector;

public abstract interface PACapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract ReturnValue relevanceAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue saveFirmKXH(Vector<KXHfailChild> paramVector, String paramString)
    throws RemoteException;
  
  public abstract ReturnValue saveFirmKXH(Date paramDate, String paramString)
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
  
  public abstract FirmBalanceValue getMarketBalance(String paramString)
    throws RemoteException;
  
  public abstract ReturnValue destroyAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;
}
