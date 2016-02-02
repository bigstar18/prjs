package gnnt.MEBS.member.ActiveUser;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.rmi.server.RemoteRef;
import java.rmi.server.RemoteStub;

public final class ActiveUserRMIImpl_Stub
  extends RemoteStub
  implements ActiveUserRMI
{
  private static final long serialVersionUID = 2L;
  private static Method $method_getAllUsers_0;
  private static Method $method_getAllUsersArray_1;
  private static Method $method_getUserId_2;
  
  static
  {
    try
    {
      $method_getAllUsers_0 = ActiveUserRMI.class.getMethod("getAllUsers", new Class[0]);
      $method_getAllUsersArray_1 = ActiveUserRMI.class.getMethod("getAllUsersArray", new Class[0]);
      $method_getUserId_2 = ActiveUserRMI.class.getMethod("getUserId", new Class[] { Long.class });
    }
    catch (NoSuchMethodException e)
    {
      throw new NoSuchMethodError(
        "stub class initialization failed");
    }
  }
  
  public ActiveUserRMIImpl_Stub(RemoteRef ref)
  {
    super(ref);
  }
  
  public String getAllUsers()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getAllUsers_0, null, -4190356650193925170L);
      return (String)$result;
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (RemoteException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }
  
  public String[] getAllUsersArray()
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getAllUsersArray_1, null, 5395517622942186329L);
      return (String[])$result;
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (RemoteException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }
  
  public String getUserId(Long $param_Long_1)
    throws RemoteException
  {
    try
    {
      Object $result = this.ref.invoke(this, $method_getUserId_2, new Object[] { $param_Long_1 }, -4823374771251851994L);
      return (String)$result;
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (RemoteException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new UnexpectedException("undeclared checked exception", e);
    }
  }
}
