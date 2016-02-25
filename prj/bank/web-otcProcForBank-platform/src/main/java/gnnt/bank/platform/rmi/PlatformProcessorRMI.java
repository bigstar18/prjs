package gnnt.bank.platform.rmi;

import gnnt.bank.platform.vo.RequestMsg;
import gnnt.trade.bank.vo.ReturnValue;
import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface PlatformProcessorRMI
  extends Remote
{
  public abstract ReturnValue doWork(RequestMsg paramRequestMsg)
    throws RemoteException;
}
