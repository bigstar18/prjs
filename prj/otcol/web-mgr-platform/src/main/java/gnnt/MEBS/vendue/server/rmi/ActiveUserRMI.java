package gnnt.MEBS.vendue.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface ActiveUserRMI
  extends Remote
{
  public abstract String getAllUsers()
    throws RemoteException;
}
