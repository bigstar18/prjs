package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.ReturnValue;
import java.rmi.RemoteException;

public abstract interface BCMCapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract ReturnValue checkSigning(CorrespondValue paramCorrespondValue)
    throws RemoteException;
}
