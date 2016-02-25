package gnnt.MEBS.vendue.server.rmi;

import gnnt.MEBS.vendue.server.vo.TradeStatusValue;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.rmi.server.RemoteRef;
import java.rmi.server.RemoteStub;
import java.sql.Connection;

public final class KernelEngineRMIImpl_Stub
  extends RemoteStub
  implements KernelEngineRMI, Remote
{
  private static final long serialVersionUID = 2L;
  private static Method $method_addCommodityCharge_0;
  private static Method $method_closeTrade_1;
  private static Method $method_continueTrade_2;
  private static Method $method_delCommodityCharge_3;
  private static Method $method_forceStartTrade_4;
  private static Method $method_getTradeStatus_5;
  private static Method $method_loadCommodity_6;
  private static Method $method_manualStartTrade_7;
  private static Method $method_optTrade_8;
  private static Method $method_pauseTrade_9;
  private static Method $method_startTrade_10;
  private static Method $method_updateSysProperty_11;
  
  public KernelEngineRMIImpl_Stub(RemoteRef paramRemoteRef)
  {
    super(paramRemoteRef);
  }
  
  public int addCommodityCharge(String paramString, long paramLong, Connection paramConnection)
    throws RemoteException
  {
    try
    {
      Object localObject = this.ref.invoke(this, $method_addCommodityCharge_0, new Object[] { paramString, new Long(paramLong), paramConnection }, -4130444181427669251L);
      return ((Integer)localObject).intValue();
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (RemoteException localRemoteException)
    {
      throw localRemoteException;
    }
    catch (Exception localException)
    {
      throw new UnexpectedException("undeclared checked exception", localException);
    }
  }
  
  public int closeTrade()
    throws RemoteException
  {
    try
    {
      Object localObject = this.ref.invoke(this, $method_closeTrade_1, null, 2387459340832316847L);
      return ((Integer)localObject).intValue();
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (RemoteException localRemoteException)
    {
      throw localRemoteException;
    }
    catch (Exception localException)
    {
      throw new UnexpectedException("undeclared checked exception", localException);
    }
  }
  
  public int continueTrade()
    throws RemoteException
  {
    try
    {
      Object localObject = this.ref.invoke(this, $method_continueTrade_2, null, -2758841026181549027L);
      return ((Integer)localObject).intValue();
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (RemoteException localRemoteException)
    {
      throw localRemoteException;
    }
    catch (Exception localException)
    {
      throw new UnexpectedException("undeclared checked exception", localException);
    }
  }
  
  public int delCommodityCharge(long paramLong, Connection paramConnection)
    throws RemoteException
  {
    try
    {
      Object localObject = this.ref.invoke(this, $method_delCommodityCharge_3, new Object[] { new Long(paramLong), paramConnection }, -4195280316172388772L);
      return ((Integer)localObject).intValue();
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (RemoteException localRemoteException)
    {
      throw localRemoteException;
    }
    catch (Exception localException)
    {
      throw new UnexpectedException("undeclared checked exception", localException);
    }
  }
  
  public int forceStartTrade()
    throws RemoteException
  {
    try
    {
      Object localObject = this.ref.invoke(this, $method_forceStartTrade_4, null, -7844832758321047816L);
      return ((Integer)localObject).intValue();
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (RemoteException localRemoteException)
    {
      throw localRemoteException;
    }
    catch (Exception localException)
    {
      throw new UnexpectedException("undeclared checked exception", localException);
    }
  }
  
  public TradeStatusValue getTradeStatus()
    throws RemoteException
  {
    try
    {
      Object localObject = this.ref.invoke(this, $method_getTradeStatus_5, null, 5105907243111921749L);
      return (TradeStatusValue)localObject;
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (RemoteException localRemoteException)
    {
      throw localRemoteException;
    }
    catch (Exception localException)
    {
      throw new UnexpectedException("undeclared checked exception", localException);
    }
  }
  
  public void loadCommodity()
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_loadCommodity_6, null, -2677029413240709668L);
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (RemoteException localRemoteException)
    {
      throw localRemoteException;
    }
    catch (Exception localException)
    {
      throw new UnexpectedException("undeclared checked exception", localException);
    }
  }
  
  public int manualStartTrade()
    throws RemoteException
  {
    try
    {
      Object localObject = this.ref.invoke(this, $method_manualStartTrade_7, null, -1744667189653558760L);
      return ((Integer)localObject).intValue();
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (RemoteException localRemoteException)
    {
      throw localRemoteException;
    }
    catch (Exception localException)
    {
      throw new UnexpectedException("undeclared checked exception", localException);
    }
  }
  
  public int optTrade()
    throws RemoteException
  {
    try
    {
      Object localObject = this.ref.invoke(this, $method_optTrade_8, null, 8374516838958711385L);
      return ((Integer)localObject).intValue();
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (RemoteException localRemoteException)
    {
      throw localRemoteException;
    }
    catch (Exception localException)
    {
      throw new UnexpectedException("undeclared checked exception", localException);
    }
  }
  
  public int pauseTrade()
    throws RemoteException
  {
    try
    {
      Object localObject = this.ref.invoke(this, $method_pauseTrade_9, null, 8787615680408428126L);
      return ((Integer)localObject).intValue();
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (RemoteException localRemoteException)
    {
      throw localRemoteException;
    }
    catch (Exception localException)
    {
      throw new UnexpectedException("undeclared checked exception", localException);
    }
  }
  
  public int startTrade()
    throws RemoteException
  {
    try
    {
      Object localObject = this.ref.invoke(this, $method_startTrade_10, null, -8916894420605937832L);
      return ((Integer)localObject).intValue();
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (RemoteException localRemoteException)
    {
      throw localRemoteException;
    }
    catch (Exception localException)
    {
      throw new UnexpectedException("undeclared checked exception", localException);
    }
  }
  
  public void updateSysProperty(long paramLong1, long paramLong2, int paramInt1, int paramInt2)
    throws RemoteException
  {
    try
    {
      this.ref.invoke(this, $method_updateSysProperty_11, new Object[] { new Long(paramLong1), new Long(paramLong2), new Integer(paramInt1), new Integer(paramInt2) }, 113930732317844483L);
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (RemoteException localRemoteException)
    {
      throw localRemoteException;
    }
    catch (Exception localException)
    {
      throw new UnexpectedException("undeclared checked exception", localException);
    }
  }
  
  static
  {
    try
    {
      $method_addCommodityCharge_0 = KernelEngineRMI.class.getMethod("addCommodityCharge", new Class[] { String.class, Long.TYPE, Connection.class });
      $method_closeTrade_1 = KernelEngineRMI.class.getMethod("closeTrade", new Class[0]);
      $method_continueTrade_2 = KernelEngineRMI.class.getMethod("continueTrade", new Class[0]);
      $method_delCommodityCharge_3 = KernelEngineRMI.class.getMethod("delCommodityCharge", new Class[] { Long.TYPE, Connection.class });
      $method_forceStartTrade_4 = KernelEngineRMI.class.getMethod("forceStartTrade", new Class[0]);
      $method_getTradeStatus_5 = KernelEngineRMI.class.getMethod("getTradeStatus", new Class[0]);
      $method_loadCommodity_6 = KernelEngineRMI.class.getMethod("loadCommodity", new Class[0]);
      $method_manualStartTrade_7 = KernelEngineRMI.class.getMethod("manualStartTrade", new Class[0]);
      $method_optTrade_8 = KernelEngineRMI.class.getMethod("optTrade", new Class[0]);
      $method_pauseTrade_9 = KernelEngineRMI.class.getMethod("pauseTrade", new Class[0]);
      $method_startTrade_10 = KernelEngineRMI.class.getMethod("startTrade", new Class[0]);
      $method_updateSysProperty_11 = KernelEngineRMI.class.getMethod("updateSysProperty", new Class[] { Long.TYPE, Long.TYPE, Integer.TYPE, Integer.TYPE });
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      throw new NoSuchMethodError("stub class initialization failed");
    }
  }
}
