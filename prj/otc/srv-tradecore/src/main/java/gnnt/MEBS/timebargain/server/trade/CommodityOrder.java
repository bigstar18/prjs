package gnnt.MEBS.timebargain.server.trade;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.model.Member;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Quotation_RT;
import gnnt.MEBS.timebargain.server.quotation.QuotationEngine;
import gnnt.MEBS.timebargain.server.quotation.QuotationObservable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommodityOrder
  implements Observer
{
  private Log log = LogFactory.getLog(getClass());
  private String m_FirmID;
  private String commodityID;
  private SortedSet<PriceOrder> buyUpQueue;
  private SortedSet<PriceOrder> buyDownQueue;
  private SortedSet<PriceOrder> sellUpQueue;
  private SortedSet<PriceOrder> sellDownQueue;
  private TradeEngine tradeEngine;
  
  public CommodityOrder(String m_FirmID, String commodityID)
  {
    this.m_FirmID = m_FirmID;
    this.commodityID = commodityID;
    this.buyUpQueue = new TreeSet(descComparator);
    this.buyDownQueue = new TreeSet(ascComparator);
    this.sellUpQueue = new TreeSet(descComparator);
    this.sellDownQueue = new TreeSet(ascComparator);
    this.tradeEngine = TradeEngine.getInstance();
    
    QuotationObservable quotationObservable = (QuotationObservable)QuotationEngine.getQuotationObservableMap().get(commodityID);
    this.log.debug("quotationObservable:" + quotationObservable);
    quotationObservable.addObserver(this);
  }
  
  public void update(Observable quotationObservable, Object arg)
  {
    this.log.debug("CommodityOrder quotation price has been changed to " + ((QuotationObservable)quotationObservable).getCurPrice());
    Quotation_RT quotation_RT = (Quotation_RT)((Member)ServerInit.getMemberQueue().get(this.m_FirmID)).getQuotationMap().get(this.commodityID);
    synchronized (this)
    {
      try
      {
        matchBuyUpOrder(quotation_RT);
        matchBuyDownOrder(quotation_RT);
        matchSellUpOrder(quotation_RT);
        matchSellDownOrder(quotation_RT);
      }
      catch (Exception e)
      {
        this.log.error("match Order Error!", e);
      }
    }
  }
  
  private void matchBuyDownOrder(Quotation_RT quotation_RT)
  {
    List<PriceOrder> delList = new ArrayList();
    for (PriceOrder priceOrder : this.buyDownQueue)
    {
      if (priceOrder.price < quotation_RT.getCurPrice_B()) {
        break;
      }
      for (Order pOrder : priceOrder.orderQueue)
      {
        pOrder.setTradePrice(pOrder.getPrice());
        Server.getTradeDAO().updateOrderTradePrice(pOrder.getOrderNo(), pOrder.getTradePrice());
        this.tradeEngine.addTradeOrder(pOrder);
      }
      delList.add(priceOrder);
    }
    this.buyDownQueue.removeAll(delList);
  }
  
  private void matchBuyUpOrder(Quotation_RT quotation_RT)
  {
    List<PriceOrder> delList = new ArrayList();
    for (PriceOrder priceOrder : this.buyUpQueue)
    {
      if (priceOrder.price > quotation_RT.getCurPrice_B()) {
        break;
      }
      for (Order pOrder : priceOrder.orderQueue)
      {
        pOrder.setTradePrice(pOrder.getPrice());
        Server.getTradeDAO().updateOrderTradePrice(pOrder.getOrderNo(), pOrder.getTradePrice());
        this.tradeEngine.addTradeOrder(pOrder);
      }
      delList.add(priceOrder);
    }
    this.buyUpQueue.removeAll(delList);
  }
  
  private void matchSellUpOrder(Quotation_RT quotation_RT)
  {
    List<PriceOrder> delList = new ArrayList();
    for (PriceOrder priceOrder : this.sellUpQueue)
    {
      if (priceOrder.price > quotation_RT.getCurPrice_S()) {
        break;
      }
      for (Order pOrder : priceOrder.orderQueue)
      {
        pOrder.setTradePrice(pOrder.getPrice());
        Server.getTradeDAO().updateOrderTradePrice(pOrder.getOrderNo(), pOrder.getTradePrice());
        this.tradeEngine.addTradeOrder(pOrder);
      }
      delList.add(priceOrder);
    }
    this.sellUpQueue.removeAll(delList);
  }
  
  private void matchSellDownOrder(Quotation_RT quotation_RT)
  {
    List<PriceOrder> delList = new ArrayList();
    for (PriceOrder priceOrder : this.sellDownQueue)
    {
      if (priceOrder.price < quotation_RT.getCurPrice_S()) {
        break;
      }
      for (Order pOrder : priceOrder.orderQueue)
      {
        pOrder.setTradePrice(pOrder.getPrice());
        Server.getTradeDAO().updateOrderTradePrice(pOrder.getOrderNo(), pOrder.getTradePrice());
        this.tradeEngine.addTradeOrder(pOrder);
      }
      delList.add(priceOrder);
    }
    this.sellDownQueue.removeAll(delList);
  }
  
  public Order cancelOrder(Long orderNo)
  {
    Order canceled = null;
    synchronized (this)
    {
      canceled = cancelOrder(orderNo, this.buyUpQueue);
      if (canceled != null) {
        return canceled;
      }
      canceled = cancelOrder(orderNo, this.buyDownQueue);
      if (canceled != null) {
        return canceled;
      }
      canceled = cancelOrder(orderNo, this.sellUpQueue);
      if (canceled != null) {
        return canceled;
      }
      canceled = cancelOrder(orderNo, this.sellDownQueue);
      if (canceled != null) {
        return canceled;
      }
    }
    return canceled;
  }
  
  private Order cancelOrder(Long orderNo, SortedSet<PriceOrder> queue)
  {
    int pos = 0;
    Order canceled = null;
    ListIterator<Order> litr;
    for (Iterator localIterator = queue.iterator(); localIterator.hasNext(); litr.hasNext())
    {
      PriceOrder po = (PriceOrder)localIterator.next();
      pos++;
      
      litr = po.orderQueue.listIterator(); continue;
      Order order = (Order)litr.next();
      if (order.getOrderNo().equals(orderNo))
      {
        canceled = order;
        litr.remove();
        
        po.quantity -= order.getQuantity().longValue();
        if (po.quantity == 0L) {
          queue.remove(po);
        }
        return canceled;
      }
    }
    return canceled;
  }
  
  public void enqueueOrder(Order order, double curPrice)
  {
    synchronized (this)
    {
      if (order.getBuyOrSell().shortValue() == 1)
      {
        if (order.getPrice().doubleValue() > curPrice) {
          addOrder(order, this.buyUpQueue);
        } else {
          addOrder(order, this.buyDownQueue);
        }
      }
      else if (order.getPrice().doubleValue() > curPrice) {
        addOrder(order, this.sellUpQueue);
      } else {
        addOrder(order, this.sellDownQueue);
      }
    }
  }
  
  private void addOrder(Order order, SortedSet<PriceOrder> queue)
  {
    PriceOrder priceOrder = null;
    for (PriceOrder pOrder : queue) {
      if (pOrder.price == order.getPrice().doubleValue())
      {
        priceOrder = pOrder;
        break;
      }
    }
    if (priceOrder == null)
    {
      priceOrder = new PriceOrder(order.getPrice().doubleValue());
      queue.add(priceOrder);
    }
    priceOrder.addOrder(order);
  }
  
  public void clearOrderQueue()
  {
    this.buyUpQueue.clear();
    this.buyDownQueue.clear();
    this.sellUpQueue.clear();
    this.sellDownQueue.clear();
  }
  
  static Comparator<PriceOrder> ascComparator = new Comparator()
  {
    public int compare(PriceOrder o1, PriceOrder o2)
    {
      if (o1.price == o2.price) {
        return 0;
      }
      if (o1.price > o2.price) {
        return -1;
      }
      return 1;
    }
  };
  static Comparator<PriceOrder> descComparator = new Comparator()
  {
    public int compare(PriceOrder o1, PriceOrder o2)
    {
      if (o1.price == o2.price) {
        return 0;
      }
      if (o1.price < o2.price) {
        return -1;
      }
      return 1;
    }
  };
  
  public String getM_FirmID()
  {
    return this.m_FirmID;
  }
  
  public void setM_FirmID(String firmID)
  {
    this.m_FirmID = firmID;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public SortedSet<PriceOrder> getBuyUpQueue()
  {
    return this.buyUpQueue;
  }
  
  public void setBuyUpQueue(SortedSet<PriceOrder> buyUpQueue)
  {
    this.buyUpQueue = buyUpQueue;
  }
  
  public SortedSet<PriceOrder> getBuyDownQueue()
  {
    return this.buyDownQueue;
  }
  
  public void setBuyDownQueue(SortedSet<PriceOrder> buyDownQueue)
  {
    this.buyDownQueue = buyDownQueue;
  }
  
  public SortedSet<PriceOrder> getSellUpQueue()
  {
    return this.sellUpQueue;
  }
  
  public void setSellUpQueue(SortedSet<PriceOrder> sellUpQueue)
  {
    this.sellUpQueue = sellUpQueue;
  }
  
  public SortedSet<PriceOrder> getSellDownQueue()
  {
    return this.sellDownQueue;
  }
  
  public void setSellDownQueue(SortedSet<PriceOrder> sellDownQueue)
  {
    this.sellDownQueue = sellDownQueue;
  }
}
