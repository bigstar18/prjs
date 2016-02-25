package gnnt.MEBS.vendue.server.rmi;

import gnnt.MEBS.vendue.server.GlobalContainer;
import gnnt.MEBS.vendue.server.KernelEngine;
import gnnt.MEBS.vendue.server.vo.TradeStatusValue;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;

public class KernelEngineRMIImpl
  extends UnicastRemoteObject
  implements KernelEngineRMI
{
  private int PARTITIONID;
  
  public KernelEngineRMIImpl(int paramInt)
    throws RemoteException
  {
    this.PARTITIONID = paramInt;
  }
  
  public TradeStatusValue getTradeStatus()
    throws RemoteException
  {
    TradeStatusValue localTradeStatusValue = null;
    try
    {
      KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
      localTradeStatusValue = localKernelEngine.getTradeStatus();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localTradeStatusValue;
  }
  
  public void updateSysProperty(long paramLong1, long paramLong2, int paramInt1, int paramInt2)
    throws RemoteException
  {
    TradeStatusValue localTradeStatusValue = null;
    try
    {
      KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
      localTradeStatusValue = localKernelEngine.getTradeStatus();
      localTradeStatusValue.setDurativeTime(paramLong1);
      localTradeStatusValue.setSpaceTime(paramLong2);
      localTradeStatusValue.setCountdownStart(paramInt1);
      localTradeStatusValue.setCountdownTime(paramInt2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void loadCommodity()
    throws RemoteException
  {
    try
    {
      KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
      localKernelEngine.loadCommodity();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public int startTrade()
    throws RemoteException
  {
    int i = -1;
    try
    {
      KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
      i = localKernelEngine.startTrade();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return i;
  }
  
  public int forceStartTrade()
    throws RemoteException
  {
    int i = -1;
    try
    {
      KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
      i = localKernelEngine.forceStartTrade();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return i;
  }
  
  public int manualStartTrade()
    throws RemoteException
  {
    int i = -1;
    try
    {
      KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
      i = localKernelEngine.manualStartTrade();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return i;
  }
  
  public int pauseTrade()
    throws RemoteException
  {
    int i = -1;
    try
    {
      KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
      i = localKernelEngine.pauseTrade();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return i;
  }
  
  public int closeTrade()
    throws RemoteException
  {
    int i = -1;
    try
    {
      KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
      i = localKernelEngine.closeTrade();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return i;
  }
  
  public int continueTrade()
    throws RemoteException
  {
    int i = -1;
    try
    {
      KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
      i = localKernelEngine.continueTrade();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return i;
  }
  
  public int optTrade()
    throws RemoteException
  {
    int i = -1;
    try
    {
      KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
      i = localKernelEngine.optTrade();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return i;
  }
  
  public int addCommodityCharge(String paramString, long paramLong, Connection paramConnection)
    throws RemoteException
  {
    int i = -1;
    try
    {
      KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
      i = localKernelEngine.addCommodityCharge(paramString, paramLong, paramConnection);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return i;
  }
  
  public int delCommodityCharge(long paramLong, Connection paramConnection)
    throws RemoteException
  {
    int i = -1;
    try
    {
      KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
      i = localKernelEngine.delCommodityCharge(paramLong, paramConnection);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return i;
  }
}
