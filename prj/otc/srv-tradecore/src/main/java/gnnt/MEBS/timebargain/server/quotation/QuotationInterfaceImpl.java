package gnnt.MEBS.timebargain.server.quotation;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.dao.RiskcontrolDAO;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.model.CommdityPriceProtect;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.HQServerInfo;
import gnnt.MEBS.timebargain.server.model.Member;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.Quotation_RT;
import gnnt.MEBS.timebargain.server.model.QuotePoint_RT;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.trade.MarketOrderImpl;
import gnnt.MEBS.timebargain.server.trade.TradeEngine;
import gnnt.MEBS.timebargain.server.util.Arith;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QuotationInterfaceImpl
  implements QuotationInterface
{
  private Log log = LogFactory.getLog(getClass());
  private PriceInterface priceInterface;
  private ReceiveHQTimeOutThread receiveHQTimeOutThread = null;
  
  public void init()
  {
    if (this.receiveHQTimeOutThread == null)
    {
      this.receiveHQTimeOutThread = new ReceiveHQTimeOutThread();
      this.receiveHQTimeOutThread
        .init(ServerInit.getCommdityPriceProtectMap());
      this.receiveHQTimeOutThread.setDaemon(true);
      this.receiveHQTimeOutThread.start();
    }
  }
  
  public void setPriceInterface(PriceInterface priceInterface)
  {
    this.priceInterface = priceInterface;
  }
  
  public void setCommodityStatus(String commodityID, char status)
  {
    if ((status != 'N') && 
      (status != 'P')) {
      throw new IllegalArgumentException("商品状态即不属于正常交易 也不属于暂停交易");
    }
    Commodity commodity = (Commodity)ServerInit.getCommodityMap().get(commodityID);
    if (commodity != null)
    {
      commodity.setTradeStatus(status);
      commodity.setPauseType('S');
    }
    Map<Integer, Map<String, Commodity>> commodityQueue = 
      ServerInit.getCommodityQueue();
    for (Map<String, Commodity> map : commodityQueue.values())
    {
      Commodity sectionCommodity = (Commodity)map.get(commodityID);
      if (sectionCommodity != null)
      {
        sectionCommodity.setTradeStatus(status);
        sectionCommodity.setPauseType('S');
      }
    }
    Server.getServerDAO().updateCommodityStatusByS(commodityID, status);
  }
  
  public Commodity getCommodity(String commodityID)
  {
    return (Commodity)ServerInit.getCommodityMap().get(commodityID);
  }
  
  public void setQuotation(Quotation quotation)
  {
    ArrayList<String> list = (ArrayList)QuotationEngine.getThirdCmdtyMappingMap().get(
      quotation.getCommodityID());
    if ((list == null) || (list.size() == 0))
    {
      this.log.debug("商品表中不存在此行情的商品【" + quotation.getCommodityID() + 
        "】，拒绝接收此行情！");
      return;
    }
    if (TradeEngine.getInstance().getTraderOrderStatus() == 1)
    {
      for (String commodityId : list) {
        this.receiveHQTimeOutThread.setTime(commodityId);
      }
      this.log.debug("系统处于不接受委托状态，拒绝接收此行情！");
      return;
    }
    for (String commodityId : list)
    {
      Quotation quo = copyQuotation(quotation, commodityId);
      setQuotation(quo, commodityId);
    }
  }
  
  public void setQuotation(Quotation quotation, String commodityID)
  {
    Commodity commodity = getCommodity(commodityID);
    if (commodity == null)
    {
      this.log.debug("内存中不存在此行情的商品【" + quotation.getCommodityID() + 
        "】，拒绝接收此行情！");
      return;
    }
    if ((commodity.getTradeStatus() == 'P') && 
      (commodity.getPauseType() == 'S'))
    {
      this.log.info("商品 " + commodityID + " 处于没有收到行情的暂停状态，现在收到行情数据恢复交易；");
      setCommodityStatus(commodityID, 'N');
      this.receiveHQTimeOutThread.setTime(commodityID);
    }
    if (commodity.getTradeStatus() == 'P')
    {
      this.receiveHQTimeOutThread.setTime(commodityID);
      this.log.debug("商品处于暂停状态，拒绝接收此行情！");
      return;
    }
    if (((Map)ServerInit.getCommodityQueue().get(
      Server.getSystemStatus().getSectionID())).get(
      commodityID) != null)
    {
      if (!((Commodity)((Map)ServerInit.getCommodityQueue().get(Server.getSystemStatus().getSectionID())).get(commodityID)).getAcceptHQFlag()) {
        ((Commodity)((Map)ServerInit.getCommodityQueue().get(Server.getSystemStatus().getSectionID())).get(commodityID)).setAcceptHQFlag(true);
      }
    }
    else
    {
      this.log.info("商品：" + commodityID + "不属于该交易节!");
      return;
    }
    quotation.setCommodityID(commodityID);
    
    quotation.setCurPrice(this.priceInterface.convertPrice(quotation
      .getCommodityID(), quotation.getCurPrice()));
    
    Map<String, Member> memberQueue = ServerInit.getMemberQueue();
    for (Member member : memberQueue.values())
    {
      if (member.getQuotationMap() == null) {
        throw new IllegalArgumentException("会员 " + member.getM_FirmID() + 
          " 的商品" + quotation.getCommodityID() + 
          " 商品行情运行时map为空,为了避免交易出现错误抛出异常！");
      }
      if (member.getQuotePointMap() == null) {
        throw new IllegalArgumentException("会员 " + member.getM_FirmID() + 
          " 的商品" + quotation.getCommodityID() + 
          " 商品报价点差map为空,为了避免交易出现错误抛出异常！");
      }
      Quotation_RT quotation_RT = (Quotation_RT)member.getQuotationMap().get(
        quotation.getCommodityID());
      QuotePoint_RT quotePoint_RT = (QuotePoint_RT)member.getQuotePointMap().get(
        quotation.getCommodityID());
      if (quotation_RT == null) {
        throw new IllegalArgumentException("会员 " + member.getM_FirmID() + 
          " 的商品" + quotation.getCommodityID() + 
          " 的行情运行时信息为空,为了避免交易出现错误抛出异常！");
      }
      if (quotePoint_RT == null) {
        throw new IllegalArgumentException("会员 " + member.getM_FirmID() + 
          " 的商品" + quotation.getCommodityID() + 
          " 点差信息为空,为了避免交易出现错误抛出异常！");
      }
      quotation_RT.setCurPrice_B(Arith.add(quotation.getCurPrice().doubleValue(), 
        quotePoint_RT.getQuotePoint_B()));
      quotation_RT.setCurPrice_S(Arith.sub(quotation.getCurPrice().doubleValue(), 
        quotePoint_RT.getQuotePoint_S()));
      quotation_RT.setUpdateTime(quotation.getUpdateTime());
    }
    this.receiveHQTimeOutThread.setTime(commodityID);
    

    Server.getRiskcontrolDAO().hqUpdate(quotation.getCommodityID(), 
      quotation.getCurPrice().doubleValue());
    

    QuotationObservable quotationObservable = 
      (QuotationObservable)QuotationEngine.getQuotationObservableMap().get(quotation.getCommodityID());
    quotationObservable.changeCurPrice(quotation.getCurPrice());
    

    MarketOrderImpl.getInstance().addPrice(quotation.getCurPrice(), quotation.getCommodityID());
  }
  
  public Quotation copyQuotation(Quotation quotation, String commodityId)
  {
    Quotation quo = new Quotation();
    quo.setCommodityID(commodityId);
    quo.setClosePrice(quotation.getClosePrice());
    quo.setCurPrice(quotation.getCurPrice());
    quo.setHighPrice(quotation.getHighPrice());
    quo.setLowPrice(quotation.getLowPrice());
    quo.setOpenPrice(quotation.getOpenPrice());
    quo.setUpdateTime(quotation.getUpdateTime());
    return quo;
  }
  
  class ReceiveHQTimeOutThread
    extends Thread
  {
    boolean isRun = true;
    private Map<String, CommdityPriceProtect> commdityPriceProtectMap;
    private Map<String, Date> commditySetDateMap;
    
    ReceiveHQTimeOutThread() {}
    
    public void init(Map<String, CommdityPriceProtect> commdityPriceProtectMap)
    {
      this.commdityPriceProtectMap = commdityPriceProtectMap;
      this.commditySetDateMap = new HashMap();
      for (String commodityID : commdityPriceProtectMap.keySet()) {
        this.commditySetDateMap.put(commodityID, new Date());
      }
    }
    
    void setTime(String commodityID)
    {
      this.commditySetDateMap.put(commodityID, new Date());
    }
    
    public void run()
    {
      while (this.isRun) {
        try
        {
          if (TradeEngine.getInstance().getTraderOrderStatus() == 0) {
            for (String commodityID : this.commditySetDateMap.keySet())
            {
              CommdityPriceProtect commdityPriceProtect = 
                (CommdityPriceProtect)this.commdityPriceProtectMap.get(commodityID);
              

              Date preDate = (Date)this.commditySetDateMap.get(commodityID);
              if ((commdityPriceProtect != null) && (preDate != null)) {
                if (System.currentTimeMillis() - preDate.getTime() > commdityPriceProtect
                  .getTimeoutInterval() * 1000L)
                {
                  Commodity commodity = QuotationInterfaceImpl.this.getCommodity(commodityID);
                  if (commodity.getTradeStatus() == 'N')
                  {
                    QuotationInterfaceImpl.this.log.warn("商品 " + commodityID + 
                      " 接收行情超时,暂停交易 ");
                    QuotationInterfaceImpl.this.setCommodityStatus(commodityID, 
                      'P');
                  }
                }
              }
            }
          }
        }
        catch (Exception e)
        {
          QuotationInterfaceImpl.this.log.error("ReceiveHQTimeOutThread checked timeout occur Error:" + 
            e.getMessage());
          try
          {
            sleep(1000L);
          }
          catch (InterruptedException e)
          {
            QuotationInterfaceImpl.this.log.warn("ReceiveHQTimeOutThread InterruptedException occur Error:" + 
              e.getMessage());
          }
        }
        finally
        {
          try
          {
            sleep(1000L);
          }
          catch (InterruptedException e)
          {
            QuotationInterfaceImpl.this.log.warn("ReceiveHQTimeOutThread InterruptedException occur Error:" + 
              e.getMessage());
          }
        }
      }
    }
    
    public void shutdown()
    {
      this.isRun = false;
      try
      {
        interrupt();
      }
      catch (Exception localException) {}
    }
  }
  
  public List<HQServerInfo> getHQServerInfoList()
  {
    return Server.getServerDAO().getHQServerInfoList();
  }
  
  public void dispose()
  {
    if (this.receiveHQTimeOutThread != null)
    {
      this.receiveHQTimeOutThread.shutdown();
      
      this.receiveHQTimeOutThread = null;
    }
  }
  
  public boolean getTraderOrderStatus()
  {
    if (TradeEngine.getInstance().getTraderOrderStatus() == 0) {
      return true;
    }
    return false;
  }
  
  public void setCurServerInfo(HQServerInfo curServerInfo)
  {
    Server.getServerDAO().updateHQServer(curServerInfo.getServerId());
  }
}
