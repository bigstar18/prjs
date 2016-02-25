package gnnt.MEBS.vendue.server.rmi;

import gnnt.MEBS.vendue.server.vo.TradeStatusValue;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;

public abstract interface KernelEngineRMI
  extends Remote
{
  public abstract TradeStatusValue getTradeStatus()
    throws RemoteException;
  
  public abstract void updateSysProperty(long paramLong1, long paramLong2, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract void loadCommodity()
    throws RemoteException;
  
  public abstract int startTrade()
    throws RemoteException;
  
  public abstract int forceStartTrade()
    throws RemoteException;
  
  public abstract int manualStartTrade()
    throws RemoteException;
  
  public abstract int pauseTrade()
    throws RemoteException;
  
  public abstract int closeTrade()
    throws RemoteException;
  
  public abstract int continueTrade()
    throws RemoteException;
  
  public abstract int optTrade()
    throws RemoteException;
  
  public abstract int addCommodityCharge(String paramString, long paramLong, Connection paramConnection)
    throws RemoteException;
  
  public abstract int delCommodityCharge(long paramLong, Connection paramConnection)
    throws RemoteException;
}
