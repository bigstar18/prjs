package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.member.ActiveUser.TraderInfo;
import gnnt.MEBS.timebargain.manage.util.SysData;
import gnnt.MEBS.timebargain.server.model.ApplyBill;
import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.DelayOrder;
import gnnt.MEBS.timebargain.server.model.DelayStatus;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Settle;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.Trader;
import gnnt.MEBS.timebargain.server.rmi.DelayRMI;
import gnnt.MEBS.timebargain.server.rmi.ExtendRMI;
import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeMonitorRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AgencyRMIBean
{
  private Log log = LogFactory.getLog(AgencyRMIBean.class);
  private static String strRMI = null;
  private ServerRMI serverRMI;
  private TradeRMI tradeRMI;
  private ExtendRMI extendRMI;
  private DelayRMI delayRMI;
  private TradeMonitorRMI tradeMonitorRMI;
  
  public AgencyRMIBean(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    strRMI = getStrRMI(paramHttpServletRequest);
    this.serverRMI = ((ServerRMI)Naming.lookup(strRMI + "/ServerRMI"));
    this.tradeRMI = ((TradeRMI)Naming.lookup(strRMI + "/TradeRMI"));
    this.extendRMI = ((ExtendRMI)Naming.lookup(strRMI + "/ExtendRMI"));
    this.delayRMI = ((DelayRMI)Naming.lookup(strRMI + "/DelayRMI"));
    this.tradeMonitorRMI = ((TradeMonitorRMI)Naming.lookup(strRMI + "/TradeMonitorRMI"));
  }
  
  public void start()
    throws RemoteException
  {
    this.serverRMI.start();
  }
  
  public void close()
    throws RemoteException
  {
    this.serverRMI.close();
  }
  
  public void ctlTrade(int paramInt)
    throws RemoteException
  {
    this.serverRMI.ctlTrade(paramInt);
  }
  
  public void timingContinueTrade(String paramString)
    throws RemoteException
  {
    this.serverRMI.timingContinueTrade(paramString);
  }
  
  public void tradeEnd()
    throws RemoteException
  {
    this.serverRMI.tradeEnd();
  }
  
  public void recoverTrade()
    throws RemoteException
  {
    this.serverRMI.recoverTrade();
  }
  
  public SystemStatus getSystemStatus()
    throws RemoteException
  {
    return this.serverRMI.getSystemStatus();
  }
  
  public void refreshTradeTime()
    throws RemoteException
  {
    this.serverRMI.refreshTradeTime();
  }
  
  public void refreshMemory()
    throws RemoteException
  {
    this.serverRMI.refreshMemory();
  }
  
  public void refreshFirmQueue(String paramString)
    throws RemoteException
  {
    this.serverRMI.refreshFirmQueue(paramString);
  }
  
  public int gage(ApplyBill paramApplyBill)
    throws RemoteException
  {
    return this.extendRMI.gage(paramApplyBill);
  }
  
  public int gageCancel(ApplyBill paramApplyBill)
    throws RemoteException
  {
    return this.extendRMI.gageCancel(paramApplyBill);
  }
  
  public int aheadSettle(ApplyBill paramApplyBill)
    throws RemoteException
  {
    return this.extendRMI.aheadSettle(paramApplyBill);
  }
  
  public int conferClose(Settle paramSettle)
    throws RemoteException
  {
    return this.extendRMI.conferClose(paramSettle);
  }
  
  public int waitSettle(ApplyBill paramApplyBill)
    throws RemoteException
  {
    return this.extendRMI.waitSettle(paramApplyBill);
  }
  
  public int waitSettleCancel(ApplyBill paramApplyBill)
    throws RemoteException
  {
    return this.extendRMI.waitSettleCancel(paramApplyBill);
  }
  
  public TraderInfo logon(Trader paramTrader)
    throws RemoteException
  {
    return this.tradeRMI.logon(paramTrader);
  }
  
  public boolean isLogon(String paramString, long paramLong)
    throws RemoteException
  {
    return this.tradeRMI.isLogon(paramString, paramLong);
  }
  
  public long consignerLogon(Consigner paramConsigner)
    throws RemoteException
  {
    return this.tradeRMI.consignerLogon(paramConsigner);
  }
  
  public int order(long paramLong, Order paramOrder)
    throws RemoteException
  {
    return this.tradeRMI.order(paramLong, paramOrder);
  }
  
  public int consignerOrder(long paramLong, Order paramOrder)
    throws RemoteException
  {
    return this.tradeRMI.consignerOrder(paramLong, paramOrder);
  }
  
  public int tradingReComputeFunds()
    throws RemoteException
  {
    return this.tradeRMI.tradingReComputeFunds();
  }
  
  public int checkFrozenQtyAtBalance()
    throws RemoteException
  {
    return this.tradeRMI.checkFrozenQtyAtBalance();
  }
  
  public int balance()
    throws RemoteException
  {
    return this.tradeRMI.balance();
  }
  
  public void logoff(long paramLong)
    throws RemoteException
  {
    this.tradeRMI.logoff(paramLong);
  }
  
  public void consignerLogoff(long paramLong)
    throws RemoteException
  {
    this.tradeRMI.consignerLogoff(paramLong);
  }
  
  public int checkUser(String paramString1, String paramString2)
    throws RemoteException
  {
    return this.tradeRMI.checkUser(paramString1, paramString2);
  }
  
  public String getUserID(long paramLong)
    throws RemoteException
  {
    return this.tradeRMI.getUserID(paramLong);
  }
  
  public String getConsignerID(long paramLong)
    throws RemoteException
  {
    return this.tradeRMI.getConsignerID(paramLong);
  }
  
  public List getTraders()
    throws RemoteException
  {
    return this.tradeRMI.getTraders();
  }
  
  public List getConsigners()
    throws RemoteException
  {
    return this.tradeRMI.getConsigners();
  }
  
  public String getFirmID(String paramString)
    throws RemoteException
  {
    return this.tradeRMI.getFirmID(paramString);
  }
  
  public int settleProcess(String paramString)
    throws RemoteException
  {
    return this.tradeRMI.settleProcess(paramString);
  }
  
  public int deductCloseOrder(Order paramOrder)
    throws RemoteException
  {
    return this.tradeRMI.deductCloseOrder(paramOrder);
  }
  
  public void kickOnlineTrader(String paramString)
    throws RemoteException
  {
    this.tradeRMI.kickOnlineTrader(paramString);
  }
  
  public void kickAllTrader(String paramString)
    throws RemoteException
  {
    this.tradeRMI.kickAllTrader(paramString);
  }
  
  public void ctlTradeDelay(int paramInt)
    throws RemoteException
  {
    this.delayRMI.ctlTrade(paramInt);
  }
  
  public void timingContinueTradeDelay(String paramString)
    throws RemoteException
  {
    this.delayRMI.timingContinueTrade(paramString);
  }
  
  public void tradeEndDelay()
    throws RemoteException
  {
    this.delayRMI.tradeEnd();
  }
  
  public void recoverTradeDelay()
    throws RemoteException
  {
    this.delayRMI.recoverTrade();
  }
  
  public DelayStatus getDelayStatus()
    throws RemoteException
  {
    return this.delayRMI.getDelayStatus();
  }
  
  public void refreshDelayTradeTime()
    throws RemoteException
  {
    this.delayRMI.refreshDelayTradeTime();
  }
  
  public int order(long paramLong, DelayOrder paramDelayOrder)
    throws RemoteException
  {
    return this.delayRMI.order(paramLong, paramDelayOrder);
  }
  
  public int consignerOrder(long paramLong, DelayOrder paramDelayOrder)
    throws RemoteException
  {
    return this.delayRMI.consignerOrder(paramLong, paramDelayOrder);
  }
  
  public int delaySettleBill(ApplyBill paramApplyBill)
    throws RemoteException
  {
    return this.delayRMI.delaySettleBill(paramApplyBill);
  }
  
  public int delaySettleBillCancel(ApplyBill paramApplyBill)
    throws RemoteException
  {
    return this.delayRMI.delaySettleBillCancel(paramApplyBill);
  }
  
  public Map<String, Object> tradeMonitorRMIMap(String paramString1, String paramString2, Map<String, Integer> paramMap)
    throws RemoteException
  {
    Map localMap = null;
    try
    {
      if (("sellOrders".equals(paramString2)) || ("buyOrders".equals(paramString2))) {
        localMap = this.tradeMonitorRMI.querySaleOrders(paramString1, paramMap);
      } else if ("waitOrders".equals(paramString2)) {
        localMap = this.tradeMonitorRMI.queryWaitOrders(paramString1, paramMap);
      }
    }
    catch (Exception localException)
    {
      this.log.debug("method throws Exception in tradeMonitorRMIMap--" + localException.toString());
    }
    return localMap;
  }
  
  private String getStrRMI(HttpServletRequest paramHttpServletRequest)
  {
    if (strRMI == null)
    {
      Map localMap = LogonManager.getRMIConfig("2", (DataSource)SysData.getBean("dataSource"));
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("rmi://").append((String)localMap.get("host")).append(":").append((Integer)localMap.get("port"));
      strRMI = localStringBuffer.toString();
    }
    return strRMI;
  }
}
