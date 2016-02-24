package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfo;
import java.rmi.RemoteException;
import java.util.Date;

public abstract interface HZCapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract void send(Date paramDate)
    throws RemoteException;
  
  public abstract ReturnValue transferPositions(TransferInfo paramTransferInfo)
    throws RemoteException;
}
