package gnnt.MEBS.timebargain.server.trade;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.SysLog;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.Trade;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeThread
  implements Runnable
{
  private Log log = LogFactory.getLog(getClass());
  private Order order;
  
  public Order getOrder()
  {
    return this.order;
  }
  
  public void setOrder(Order order)
  {
    this.order = order;
  }
  
  public TradeThread() {}
  
  public TradeThread(Order order)
  {
    this.order = order;
  }
  
  public void run()
  {
    this.log.info("处理成交委托:" + this.order);
    Trade trade = new Trade();
    Commodity commodity = (Commodity)ServerInit.getCommodityMap().get(
      this.order.getCommodityID());
    if (this.order.getOC_Flag() == 'O')
    {
      trade.setOrderNo(this.order.getOrderNo());
      trade.setContractFactor(Double.valueOf(commodity.getContractFactor()));
      trade.setTradeDate(Server.getSystemStatus().getTradeDate());
      this.log.debug("处理开仓成交:" + trade);
      openTradeProcess(trade);
      if (this.order.getOrderType().shortValue() == 2)
      {
        if (this.order.getStopProfitPrice().doubleValue() > 0.0D)
        {
          Order newOrder = new Order();
          newOrder
            .setBuyOrSell(Short.valueOf(
            (short)(this.order.getBuyOrSell().shortValue() == 1 ? 2 : 1)));
          newOrder.setCloseMode(Short.valueOf((short)2));
          newOrder.setCommodityID(this.order.getCommodityID());
          newOrder.setConsignerID(this.order.getConsignerID());
          newOrder.setFirmID(this.order.getFirmID());
          newOrder.setHoldNo(trade.getHoldingNO());
          newOrder.setOC_Flag('C');
          newOrder.setOrderPoint(Double.valueOf(0.0D));
          newOrder.setOrderType(Short.valueOf((short)2));
          newOrder.setOtherFirmID(this.order.getOtherFirmID());
          
          newOrder.setPrice(this.order.getStopProfitPrice());
          newOrder.setQuantity(this.order.getQuantity());
          newOrder.setTraderID(this.order.getTraderID());
          
          newOrder.setStopPLFlag((short)2);
          newOrder.setRelatedPrice(this.order.getTradePrice().doubleValue());
          
          int ret = Server.getInstance().getTradeEngine()
            .getLimitOrder().closeOrder(newOrder);
          if (ret != 0) {
            this.log.error("添加止盈委托失败，" + newOrder.toString() + "ret=" + 
              ret);
          }
        }
        if (this.order.getStopLossPrice().doubleValue() > 0.0D)
        {
          Order newOrder = new Order();
          newOrder
            .setBuyOrSell(Short.valueOf(
            (short)(this.order.getBuyOrSell().shortValue() == 1 ? 2 : 1)));
          newOrder.setCloseMode(Short.valueOf((short)2));
          newOrder.setCommodityID(this.order.getCommodityID());
          newOrder.setConsignerID(this.order.getConsignerID());
          newOrder.setFirmID(this.order.getFirmID());
          newOrder.setHoldNo(trade.getHoldingNO());
          newOrder.setOC_Flag('C');
          newOrder.setOrderPoint(Double.valueOf(0.0D));
          newOrder.setOrderType(Short.valueOf((short)2));
          newOrder.setOtherFirmID(this.order.getOtherFirmID());
          
          newOrder.setPrice(this.order.getStopLossPrice());
          newOrder.setQuantity(this.order.getQuantity());
          newOrder.setTraderID(this.order.getTraderID());
          
          newOrder.setStopPLFlag((short)1);
          
          newOrder.setRelatedPrice(this.order.getTradePrice().doubleValue());
          
          int ret = Server.getInstance().getTradeEngine()
            .getLimitOrder().closeOrder(newOrder);
          if (ret != 0) {
            this.log.error("添加止损委托失败，" + newOrder.toString() + "ret=" + 
              ret);
          }
        }
      }
    }
    else if (this.order.getOC_Flag() == 'C')
    {
      trade.setOrderNo(this.order.getOrderNo());
      trade.setContractFactor(Double.valueOf(commodity.getContractFactor()));
      this.log.debug("处理平仓成交:" + trade);
      closeTradeProcess(trade);
      

      this.order.getOrderType().shortValue();
    }
  }
  
  public void openTradeProcess(Trade trade)
  {
    this.log.debug("trade:" + trade.toString());
    if (!openTrade(trade)) {
      if ((!openTrade(trade)) && 
        (!openTrade(trade)) && 
        (!openTrade(trade)))
      {
        this.log.error("*******************警告：更新开仓成交到db中失败，timebargain整个应用停止服务，请手工重启此timebargain服务！******************");
        
        Server.getServerDAO().insertSysLog(
          new SysLog("更新开仓成交到db中失败;" + trade));
        return;
      }
    }
  }
  
  private boolean openTrade(Trade trade)
  {
    boolean bRet = true;
    try
    {
      Long ret = Server.getTradeDAO().openTrade(trade);
      trade.setHoldingNO(ret);
      this.log.debug("TradeThread.openTrade ret=" + ret);
      if (ret.longValue() < 0L)
      {
        this.log.error("开仓成交失败：" + trade.toString());
        this.log.error("TradeProcess.openTrade ret=" + ret);
        Server.getServerDAO().insertSysLog(
          new SysLog("开仓成交失败：" + trade.toString()));
        bRet = false;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.log.error("开仓成交异常失败：" + trade.toString());
      bRet = false;
      try
      {
        Thread.sleep(10L);
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
        this.log.error("Thread sleep error:" + e1);
      }
    }
    return bRet;
  }
  
  public void closeTradeProcess(Trade trade)
  {
    this.log.debug("trade:" + trade.toString());
    if (!closeTrade(trade)) {
      if ((!closeTrade(trade)) && 
        (!closeTrade(trade)) && 
        (!closeTrade(trade)))
      {
        this.log.error("*******************警告：更新平仓成交到db中失败，timebargain整个应用停止服务，请手工重启此timebargain服务！******************");
        
        Server.getServerDAO().insertSysLog(
          new SysLog("更新平仓成交到db中失败;" + trade));
        return;
      }
    }
  }
  
  private boolean closeTrade(Trade trade)
  {
    boolean bRet = true;
    try
    {
      int ret = Server.getTradeDAO().closeTrade(trade);
      this.log.debug("TradeThread.closeTrade ret=" + ret);
      if (ret < 0)
      {
        this.log.error("平仓成交失败：" + trade.toString());
        this.log.error("TradeProcess.closeTrade ret=" + ret);
        Server.getServerDAO().insertSysLog(
          new SysLog("平仓成交失败：" + trade.toString()));
        bRet = false;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.log.error("平仓成交异常失败：" + trade.toString());
      bRet = false;
      try
      {
        Thread.sleep(10L);
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
        this.log.error("Thread sleep error:" + e1);
      }
    }
    return bRet;
  }
}
