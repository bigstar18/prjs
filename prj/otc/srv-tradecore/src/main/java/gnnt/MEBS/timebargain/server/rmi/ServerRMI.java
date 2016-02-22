package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.timebargain.server.model.Market;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public abstract interface ServerRMI
  extends Remote
{
  public abstract void start()
    throws RemoteException;
  
  public abstract void close()
    throws RemoteException;
  
  public abstract void ctlTrade(int paramInt)
    throws RemoteException;
  
  public abstract void timingContinueTrade(String paramString)
    throws RemoteException;
  
  public abstract void getMaxDelayTime()
    throws RemoteException;
  
  public abstract void tradeEnd()
    throws RemoteException;
  
  public abstract void recoverTrade()
    throws RemoteException;
  
  public abstract SystemStatus getSystemStatus()
    throws RemoteException;
  
  public abstract void refreshTradeTime()
    throws RemoteException;
  
  public abstract void refreshCommodityQueue()
    throws RemoteException;
  
  public abstract void refreshExchageRate()
    throws RemoteException;
  
  public abstract void refreshMemory()
    throws RemoteException;
  
  public abstract Date getCurDbDate()
    throws RemoteException;
  
  public abstract int balance()
    throws RemoteException;
  
  public abstract void loadOneMember2Queue(String paramString)
    throws RemoteException;
  
  public abstract void setCommodityStatus(String paramString, char paramChar)
    throws RemoteException;
  
  public abstract Market getMarketInfo()
    throws RemoteException;
  
  public abstract void loadMarketStartInfo()
    throws RemoteException;
  
  public abstract void loadMarketBalanceInfo()
    throws RemoteException;
  
  public abstract boolean isTradeDate()
    throws RemoteException;
}
