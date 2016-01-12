package gnnt.MEBS.timebargain.mgr.statictools;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.timebargain.server.model.ApplyBill;
import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.DelayOrder;
import gnnt.MEBS.timebargain.server.model.DelayStatus;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Settle;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AgencyRMIBean
{
  private static final long serialVersionUID = -7828694661022281435L;
  private Log log = LogFactory.getLog(AgencyRMIBean.class);

  private static String strRMI = null;
  public static final String LOGON_TYPE_WEB = "web";
  private ServerRMI serverRMI;
  private TradeRMI tradeRMI;
  private ExtendRMI extendRMI;
  private DelayRMI delayRMI;
  private TradeMonitorRMI tradeMonitorRMI;

  public AgencyRMIBean(HttpServletRequest request)
    throws Exception
  {
    strRMI = getStrRMI(request);
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

  public void ctlTrade(int status)
    throws RemoteException
  {
    this.serverRMI.ctlTrade(status);
  }

  public void timingContinueTrade(String recoverTime)
    throws RemoteException
  {
    this.serverRMI.timingContinueTrade(recoverTime);
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

  public void refreshFirmQueue(String firmID)
    throws RemoteException
  {
    this.serverRMI.refreshFirmQueue(firmID);
  }

  public int gage(ApplyBill applyBill)
    throws RemoteException
  {
    return this.extendRMI.gage(applyBill);
  }

  public int gageCancel(ApplyBill applyBill)
    throws RemoteException
  {
    return this.extendRMI.gageCancel(applyBill);
  }

  public int aheadSettle(ApplyBill applyBill)
    throws RemoteException
  {
    return this.extendRMI.aheadSettle(applyBill);
  }

  public int conferClose(Settle settle)
    throws RemoteException
  {
    return this.extendRMI.conferClose(settle);
  }

  public int waitSettle(ApplyBill applyBill)
    throws RemoteException
  {
    return this.extendRMI.waitSettle(applyBill);
  }

  public int waitSettleCancel(ApplyBill applyBill)
    throws RemoteException
  {
    return this.extendRMI.waitSettleCancel(applyBill);
  }

  public long consignerLogon(Consigner consigner)
    throws RemoteException
  {
    return this.tradeRMI.consignerLogon(consigner);
  }

  public int consignerOrder(long sessionID, Order order)
    throws RemoteException
  {
    return this.tradeRMI.consignerOrder(sessionID, order);
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

  public void consignerLogoff(long sessionID)
    throws RemoteException
  {
    this.tradeRMI.consignerLogoff(sessionID);
  }

  public String getConsignerID(long sessionID)
    throws RemoteException
  {
    return this.tradeRMI.getConsignerID(sessionID);
  }

  public List getConsigners()
    throws RemoteException
  {
    return this.tradeRMI.getConsigners();
  }

  public int settleProcess(String commodityID)
    throws RemoteException
  {
    return this.tradeRMI.settleProcess(commodityID);
  }

  public int deductCloseOrder(Order order)
    throws RemoteException
  {
    return this.tradeRMI.deductCloseOrder(order);
  }

  public void ctlTradeDelay(int status)
    throws RemoteException
  {
    this.delayRMI.ctlTrade(status);
  }

  public void timingContinueTradeDelay(String recoverTime)
    throws RemoteException
  {
    this.delayRMI.timingContinueTrade(recoverTime);
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

  public int order(long sessionID, DelayOrder order)
    throws Exception
  {
    return this.delayRMI.order(sessionID, order, "web");
  }

  public int consignerOrder(long sessionID, DelayOrder order)
    throws RemoteException
  {
    return this.delayRMI.consignerOrder(sessionID, order);
  }

  public int delaySettleBill(ApplyBill applyBill)
    throws RemoteException
  {
    return this.delayRMI.delaySettleBill(applyBill);
  }

  public int delaySettleBillCancel(ApplyBill applyBill)
    throws RemoteException
  {
    return this.delayRMI.delaySettleBillCancel(applyBill);
  }

  public Map<String, Object> tradeMonitorRMIMap(String filter, String type, Map<String, Integer> map)
    throws RemoteException
  {
    Map saleOrdersMap = null;
    try {
      if (("sellOrders".equals(type)) || ("buyOrders".equals(type))) saleOrdersMap = this.tradeMonitorRMI.querySaleOrders(filter, map);
      else if ("waitOrders".equals(type)) saleOrdersMap = this.tradeMonitorRMI.queryWaitOrders(filter, map);
    }
    catch (Exception e)
    {
      this.log.debug("method throws Exception in tradeMonitorRMIMap--" + e.toString());
    }
    return saleOrdersMap;
  }

  private String getStrRMI(HttpServletRequest request)
  {
    StandardService standardService = (StandardService)ApplicationContextInit.getBean("com_standardService");
    if (strRMI == null)
    {
      List rmiList = standardService.getListBySql("select * from f_systemmodule where moduleId =15");
      Map rmiMap = null;
      if ((rmiList != null) && (rmiList.size() > 0)) {
        rmiMap = (Map)rmiList.get(0);
        StringBuffer sb = new StringBuffer();
        sb.append("rmi://").append((String)rmiMap.get("HOSTIP")).append(":").append(Integer.parseInt(rmiMap.get("PORT").toString()));
        strRMI = sb.toString();
      }
    }
    return strRMI;
  }
}