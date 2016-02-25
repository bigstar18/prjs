package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.OutMoneyMarket;
import gnnt.trade.bank.vo.ReturnValue;
import java.rmi.RemoteException;

public abstract interface UPCapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract ReturnValue inMoneyMarket(InMoneyMarket paramInMoneyMarket, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
    throws RemoteException;
  
  public abstract ReturnValue outMoneyMarket(OutMoneyMarket paramOutMoneyMarket, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
    throws RemoteException;
  
  public abstract long rgstAccount(CorrespondValue paramCorrespondValue)
    throws RemoteException;
  
  public abstract ReturnValue outMoneyAudit(long paramLong, boolean paramBoolean)
    throws RemoteException;
}
