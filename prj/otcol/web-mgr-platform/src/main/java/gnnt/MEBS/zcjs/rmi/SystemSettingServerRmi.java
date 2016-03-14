package gnnt.MEBS.zcjs.rmi;

import gnnt.MEBS.zcjs.memory.system.SystemObject;
import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface SystemSettingServerRmi
  extends Remote
{
  public abstract void setManualOrder(int paramInt)
    throws RemoteException;
  
  public abstract void reloadTradeTimes()
    throws RemoteException;
  
  public abstract void reloadFlowMode()
    throws RemoteException;
  
  public abstract void reloadRestMsg()
    throws RemoteException;
  
  public abstract SystemObject getSystemObject()
    throws RemoteException;
  
  public abstract String systemCancleSubRmi(Long paramLong)
    throws RemoteException;
}
