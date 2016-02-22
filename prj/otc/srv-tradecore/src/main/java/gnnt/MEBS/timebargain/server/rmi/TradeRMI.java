package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.member.ActiveUser.TraderInfo;
import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.LimitOrder;
import gnnt.MEBS.timebargain.server.model.MarketOrder;
import gnnt.MEBS.timebargain.server.model.Trader;
import gnnt.MEBS.timebargain.server.model.WithdrawOrder;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public abstract interface TradeRMI
  extends Remote
{
  public abstract TraderInfo logon(Trader paramTrader)
    throws RemoteException;
  
  public abstract TraderInfo remoteLogon(String paramString1, String paramString2, long paramLong, String paramString3)
    throws RemoteException;
  
  public abstract boolean isLogon(String paramString, long paramLong)
    throws RemoteException;
  
  public abstract TraderInfo getTraderInfo(String paramString)
    throws RemoteException;
  
  public abstract long consignerLogon(Consigner paramConsigner)
    throws RemoteException;
  
  public abstract long checkDelegateInfo(String paramString1, String paramString2, String paramString3)
    throws RemoteException;
  
  public abstract int marketOrder(long paramLong, MarketOrder paramMarketOrder)
    throws RemoteException;
  
  public abstract int limitOrder(long paramLong, LimitOrder paramLimitOrder)
    throws RemoteException;
  
  public abstract int withdrawOrder(long paramLong, WithdrawOrder paramWithdrawOrder)
    throws RemoteException;
  
  public abstract int consignerMarketOrder(long paramLong, MarketOrder paramMarketOrder)
    throws RemoteException;
  
  public abstract int consignerLimitOrder(long paramLong, LimitOrder paramLimitOrder)
    throws RemoteException;
  
  public abstract int consignerWithdrawOrder(long paramLong, WithdrawOrder paramWithdrawOrder)
    throws RemoteException;
  
  public abstract int tradingReComputeFunds()
    throws RemoteException;
  
  public abstract void logoff(long paramLong)
    throws RemoteException;
  
  public abstract void logoff(String paramString1, long paramLong, String paramString2, String paramString3)
    throws RemoteException;
  
  public abstract void consignerLogoff(long paramLong)
    throws RemoteException;
  
  public abstract int checkUser(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract String getUserID(long paramLong)
    throws RemoteException;
  
  public abstract String getConsignerID(long paramLong)
    throws RemoteException;
  
  public abstract List getTraders()
    throws RemoteException;
  
  public abstract List getConsigners()
    throws RemoteException;
  
  public abstract String getFirmID(String paramString)
    throws RemoteException;
  
  public abstract void kickOnlineTrader(String paramString)
    throws RemoteException;
  
  public abstract void kickAllTrader(String paramString)
    throws RemoteException;
  
  public abstract int changePassowrd(String paramString1, String paramString2, String paramString3)
    throws RemoteException;
  
  public abstract int changePhonePassowrd(String paramString1, String paramString2, String paramString3)
    throws RemoteException;
  
  public abstract int setLossProfit(Long paramLong, Double paramDouble1, Double paramDouble2, String paramString)
    throws RemoteException;
  
  public abstract int withdrawLossProfit(Long paramLong, Short paramShort, String paramString)
    throws RemoteException;
  
  public abstract int getLogonStatus(String paramString, long paramLong)
    throws RemoteException;
}
