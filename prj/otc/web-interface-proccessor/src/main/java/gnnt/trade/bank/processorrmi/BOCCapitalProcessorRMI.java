package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.ReturnValue;
import java.rmi.RemoteException;
import java.util.Date;

public abstract interface BOCCapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract ReturnValue CommunicationsTest(String paramString)
    throws RemoteException;
  
  public abstract CorrespondValue getFirmIDbyCardAndAccount(String paramString1, String paramString2, String paramString3)
    throws RemoteException;
  
  public abstract ReturnValue checkSigning(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract void send(Date paramDate)
    throws RemoteException;
}
