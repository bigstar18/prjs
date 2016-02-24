package gnnt.trade.bank.processorrmi;

import gnnt.trade.bank.vo.AbcInfoValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.OutMoneyMarket;
import gnnt.trade.bank.vo.ReturnValue;
import java.rmi.RemoteException;
import java.util.Date;

public abstract interface ABCCapitalProcessorRMI
  extends CapitalProcessorRMI
{
  public abstract long getMktActionID()
    throws RemoteException;
  
  public abstract ReturnValue ifbankTrade(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract ReturnValue moneyInAudit(long paramLong, String paramString, boolean paramBoolean)
    throws RemoteException;
  
  public abstract ReturnValue outMoneyMarket(OutMoneyMarket paramOutMoneyMarket, long paramLong)
    throws RemoteException;
  
  public abstract ReturnValue inMoneyMarket(InMoneyMarket paramInMoneyMarket, long paramLong)
    throws RemoteException;
  
  public abstract int getBankCompareInfoAbc(String paramString, Date paramDate)
    throws RemoteException;
  
  public abstract AbcInfoValue getAbcInfo(String paramString, long paramLong, int paramInt)
    throws RemoteException;
  
  public abstract ReturnValue insertAbcInfo(AbcInfoValue paramAbcInfoValue)
    throws RemoteException;
  
  public abstract ReturnValue modAccount(CorrespondValue paramCorrespondValue1, CorrespondValue paramCorrespondValue2, String paramString)
    throws RemoteException;
}
