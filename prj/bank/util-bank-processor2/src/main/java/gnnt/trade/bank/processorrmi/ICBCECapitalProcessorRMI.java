package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.vo.ReturnValue;
import java.rmi.RemoteException;

public abstract interface ICBCECapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract ReturnValue modCapitailInOutMoney(long paramLong, String paramString1, boolean paramBoolean, String paramString2)
    throws RemoteException;
}
