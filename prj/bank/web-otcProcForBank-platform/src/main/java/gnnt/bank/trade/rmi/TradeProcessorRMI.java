package gnnt.bank.trade.rmi;

import gnnt.bank.platform.vo.RequestMsg;
import gnnt.trade.bank.vo.ReturnValue;
import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface TradeProcessorRMI
  extends Remote
{
  public abstract ReturnValue doWork(RequestMsg paramRequestMsg)
    throws RemoteException;
}
