package gnnt.MEBS.vendue.server.rmi;

import gnnt.MEBS.vendue.kernel.ActiveUserManager;
import java.rmi.RemoteException;
import javax.rmi.PortableRemoteObject;

public class ActiveUserRMIImpl
  extends PortableRemoteObject
  implements ActiveUserRMI
{
  public ActiveUserRMIImpl()
    throws RemoteException
  {}
  
  public String getAllUsers()
    throws RemoteException
  {
    String[] arrayOfString = null;
    String str = "";
    try
    {
      ActiveUserManager localActiveUserManager = new ActiveUserManager();
      arrayOfString = localActiveUserManager.getAllUsers();
      if (arrayOfString != null) {
        for (int i = 0; i < arrayOfString.length; i++) {
          str = str + arrayOfString[i] + ";";
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return str;
  }
}
