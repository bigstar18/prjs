package gnnt.MEBS.transformhq.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface TransFormRMI
  extends Remote
{
  public abstract boolean isUsable()
    throws RemoteException;
}
