package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfo;
import java.rmi.RemoteException;
import java.util.Date;

public abstract interface JSBCapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract ReturnValue synchroAccountMarket(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue transferPositions(TransferInfo paramTransferInfo)
    throws RemoteException;
  
  public abstract ReturnValue send(Date paramDate)
    throws RemoteException;
  
  public abstract ReturnValue modTransferStatus(long paramLong, String paramString, boolean paramBoolean)
    throws RemoteException;
  
  public abstract ReturnValue getQSDate(Date paramDate, String paramString)
    throws RemoteException;
}
