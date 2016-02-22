package gnnt.MEBS.trade.rmi;

import gnnt.MEBS.base.util.SpringContextHelper;
import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.timebargain.server.model.LimitOrder;
import gnnt.MEBS.timebargain.server.model.Market;
import gnnt.MEBS.timebargain.server.model.MarketOrder;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.rmi.QuotationRMI;
import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
import gnnt.MEBS.trade.service.AgencyService;
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
  private final transient Log logger = LogFactory.getLog(AgencyService.class);
  private static String strRMI = null;
  private ServerRMI serverRMI;
  private TradeRMI tradeRMI;
  private QuotationRMI quotationRMI;
  
  public AgencyRMIBean(HttpServletRequest request)
    throws Exception
  {
    strRMI = getStrRMI(request);
    this.serverRMI = ((ServerRMI)Naming.lookup(strRMI + "/ServerRMI"));
    this.tradeRMI = ((TradeRMI)Naming.lookup(strRMI + "/TradeRMI"));
    this.quotationRMI = ((QuotationRMI)Naming.lookup(strRMI + "/QuotationRMI"));
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
  
  public void refreshTradeTime()
    throws RemoteException
  {
    this.serverRMI.refreshTradeTime();
  }
  
  public void refreshExchageRate()
    throws RemoteException
  {
    this.serverRMI.refreshExchageRate();
  }
  
  public void refreshFirmQueue(String firmID)
    throws RemoteException
  {
    this.serverRMI.refreshMemory();
  }
  
  public List getTraders()
    throws RemoteException
  {
    return this.tradeRMI.getTraders();
  }
  
  public int consignerMarketOrder(long paramLong, MarketOrder paramMarketOrder)
    throws RemoteException
  {
    return this.tradeRMI.consignerMarketOrder(paramLong, paramMarketOrder);
  }
  
  public int consignerLimitOrder(long sessionID, LimitOrder limitOrder)
    throws RemoteException
  {
    return this.tradeRMI.consignerLimitOrder(sessionID, limitOrder);
  }
  
  private String getStrRMI(HttpServletRequest request)
  {
    if (strRMI == null)
    {
      Map rmiMap = LogonManager.getRMIConfig("2", (DataSource)SpringContextHelper.getBean("dataSource"));
      StringBuffer sb = new StringBuffer();
      sb.append("rmi://").append((String)rmiMap.get("host")).append(":").append((Integer)rmiMap.get("port"));
      strRMI = sb.toString();
    }
    return strRMI;
  }
  
  public void kickOnlineTrader(String traderID)
    throws RemoteException
  {
    this.tradeRMI.kickOnlineTrader(traderID);
  }
  
  public int balance()
    throws RemoteException
  {
    int result = this.serverRMI.balance();
    Map map = (Map)SpringContextHelper.getBean("returnOperationRMIMap");
    String resultInt = (String)map.get(Integer.valueOf(result));
    return Integer.parseInt(resultInt);
  }
  
  public void loadOneMember2Queue(String firmID)
    throws RemoteException
  {
    this.serverRMI.loadOneMember2Queue(firmID);
  }
  
  public void setCommodityStatus(String commodityID, char status)
    throws RemoteException
  {
    this.serverRMI.setCommodityStatus(commodityID, status);
  }
  
  public boolean isTradeDate()
    throws RemoteException
  {
    return this.serverRMI.isTradeDate();
  }
  
  public Market getMarketInfo()
    throws RemoteException
  {
    return this.serverRMI.getMarketInfo();
  }
  
  public void loadMarketBalanceInfo()
    throws RemoteException
  {
    this.serverRMI.loadMarketBalanceInfo();
  }
  
  public void loadMarketStartInfo()
    throws RemoteException
  {
    this.serverRMI.loadMarketStartInfo();
  }
  
  public int setQuotation(Quotation quotation)
    throws RemoteException
  {
    return this.quotationRMI.setQuotation(quotation);
  }
  
  public void getMaxDelayTime()
    throws RemoteException
  {
    this.serverRMI.getMaxDelayTime();
  }
  
  public void refreshHQserverinfo()
    throws RemoteException
  {
    this.quotationRMI.refreshHQserverinfo();
  }
}
