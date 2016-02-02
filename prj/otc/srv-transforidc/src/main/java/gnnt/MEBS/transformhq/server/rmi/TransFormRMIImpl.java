package gnnt.MEBS.transformhq.server.rmi;

import gnnt.MEBS.transformhq.server.quotation.HQStatus;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TransFormRMIImpl
  extends UnicastRemoteObject
  implements TransFormRMI
{
  private static final long serialVersionUID = 1L;
  private HQStatus hqStatus;
  
  public TransFormRMIImpl(HQStatus hqStatus)
    throws RemoteException
  {
    this.hqStatus = hqStatus;
  }
  
  public boolean isUsable()
    throws RemoteException
  {
    return this.hqStatus.isUsable();
  }
}
