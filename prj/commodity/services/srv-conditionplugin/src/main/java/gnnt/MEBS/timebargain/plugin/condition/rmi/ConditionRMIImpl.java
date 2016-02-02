package gnnt.MEBS.timebargain.plugin.condition.rmi;

import gnnt.MEBS.timebargain.plugin.condition.CalculateCenter;
import gnnt.MEBS.timebargain.plugin.condition.model.ConditionOrder;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConditionRMIImpl
  extends UnicastRemoteObject
  implements ConditionRMI
{
  private CalculateCenter center;
  private static final long serialVersionUID = -4357705192130521298L;
  
  public ConditionRMIImpl()
    throws RemoteException
  {}
  
  public ConditionRMIImpl(CalculateCenter paramCalculateCenter)
    throws RemoteException
  {
    this.center = paramCalculateCenter;
  }
  
  public int cancelOrder(ConditionOrder paramConditionOrder)
    throws RemoteException
  {
    int i = 0;
    try
    {
      this.center.removeOrder(paramConditionOrder);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      i = -1;
    }
    return i;
  }
  
  public int receiveOrder(ConditionOrder paramConditionOrder)
    throws RemoteException
  {
    int i = 0;
    try
    {
      this.center.putOrder(paramConditionOrder);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      i = -1;
    }
    return i;
  }
}
