package gnnt.MEBS.member.ActiveUser;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface ActiveUserRMI
  extends Remote
{
  public abstract String getAllUsers()
    throws RemoteException;
  
  public abstract String getUserId(Long paramLong)
    throws RemoteException;
  
  public abstract String[] getAllUsersArray()
    throws RemoteException;
}
