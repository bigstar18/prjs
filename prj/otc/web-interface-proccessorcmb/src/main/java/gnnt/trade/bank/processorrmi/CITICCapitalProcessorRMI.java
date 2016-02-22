package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.vo.OutMoneyMarket;
import gnnt.trade.bank.vo.ReturnValue;
import java.rmi.RemoteException;

public abstract interface CITICCapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract ReturnValue modCapitailOutMoney(long paramLong, String paramString1, boolean paramBoolean, String paramString2, int paramInt)
    throws RemoteException;
  
  public abstract ReturnValue outMoney2Account(OutMoneyMarket paramOutMoneyMarket, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws RemoteException;
}
