package gnnt.MEBS.member.ActiveUser;

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
    String[] userList = (String[])null;
    String result = "";
    try
    {
      ActiveUserManager au = new ActiveUserManager();
      userList = au.getAllUsers();
      if (userList != null) {
        for (int i = 0; i < userList.length; i++) {
          result = result + userList[i] + ";";
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return result;
  }
  
  public String getUserId(Long auSessionId)
    throws RemoteException
  {
    ActiveUserManager au = new ActiveUserManager();
    return au.getUserID(auSessionId.longValue());
  }
  
  public String[] getAllUsersArray()
    throws RemoteException
  {
    ActiveUserManager au = new ActiveUserManager();
    return au.getAllUsers();
  }
}
