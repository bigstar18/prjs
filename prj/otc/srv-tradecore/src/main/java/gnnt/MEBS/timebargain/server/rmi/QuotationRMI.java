package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.timebargain.server.model.Quotation;
import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface QuotationRMI
  extends Remote
{
  public abstract int setQuotation(Quotation paramQuotation)
    throws RemoteException;
  
  public abstract void refreshHQserverinfo()
    throws RemoteException;
}
