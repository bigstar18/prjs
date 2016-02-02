package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.ReturnValue;
import java.rmi.RemoteException;

public abstract interface HXBCapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract ReturnValue moneyInAudit(long paramLong, boolean paramBoolean)
    throws RemoteException;
  
  public abstract ReturnValue synchroAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue relevanceAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue inMoneyMarketHx(InMoneyMarket paramInMoneyMarket)
    throws RemoteException;
}
