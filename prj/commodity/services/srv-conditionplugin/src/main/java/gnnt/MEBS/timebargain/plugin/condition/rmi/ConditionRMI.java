package gnnt.MEBS.timebargain.plugin.condition.rmi;

import gnnt.MEBS.timebargain.plugin.condition.model.ConditionOrder;
import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface ConditionRMI
  extends Remote
{
  public abstract int receiveOrder(ConditionOrder paramConditionOrder)
    throws RemoteException;
  
  public abstract int cancelOrder(ConditionOrder paramConditionOrder)
    throws RemoteException;
}
