package gnnt.MEBS.timebargain.server.trade;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.model.Member;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Quotation_RT;
import gnnt.MEBS.timebargain.server.model.SysLog;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeEngine
{
  private Log log = LogFactory.getLog(getClass());
  private AbstractOrder marketOrder;
  private AbstractOrder limitOrder;
  private Map<String, MemberOrder> memberOrderMap = new HashMap();
  private TradePoolManager tradePoolManager;
  private int traderOrderStatus = 1;
  public static final int TRADER_ORDER_ACCEPT = 0;
  public static final int TRADER_ORDER_NOTACCEPT = 1;
  public static final int CONSIGNER_ORDER_ACCEPT = 0;
  public static final int CONSIGNER_ORDER_NOTACCEPT = 1;
  private long timespace;
  private WithDrawSPOrderThread withDrawSPOrderThread;
  private static TradeEngine instance;
  
  private TradeEngine()
  {
    this.tradePoolManager = TradePoolManager.newInstance();
    this.marketOrder = MarketOrderImpl.getInstance();
    this.limitOrder = LimitOrderImpl.getInstance();
  }
  
  public static TradeEngine getInstance()
  {
    if (instance == null) {
      instance = new TradeEngine();
    }
    return instance;
  }
  
  public void init()
  {
    this.marketOrder.init(this);
    this.limitOrder.init(this);
    loadMemberOrder();
    loadNotTradeOrders();
    
    this.timespace = Long.parseLong(
      DAOBeanFactory.getConfig("WithDrawSPOrderSpace"));
    if (this.withDrawSPOrderThread != null) {
      this.withDrawSPOrderThread.close();
    }
    this.withDrawSPOrderThread = new WithDrawSPOrderThread();
    this.withDrawSPOrderThread.init(this, this.timespace);
    this.withDrawSPOrderThread.start();
    this.log.info("启动撤销无效止损止盈委托线程成功");
  }
  
  public void refreshMemory()
  {
    loadMemberOrder();
  }
  
  private void loadMemberOrder()
  {
    this.log.info("Load memberOrders...");
    
    this.memberOrderMap.clear();
    for (Member member : ServerInit.getMemberQueue().values())
    {
      this.log.debug("member:" + member);
      MemberOrder memberOrder = new MemberOrder(member.getM_FirmID());
      this.memberOrderMap.put(memberOrder.getM_FirmID(), memberOrder);
    }
  }
  
  private void loadNotTradeOrders()
  {
    List<Order> orders = Server.getTradeDAO().getNotTradeOrders();
    for (Order order : orders) {
      if (order.getOrderType().shortValue() == 1) {
        addTradeOrder(order);
      } else if ((order.getTradePrice() == null) || (order.getTradePrice().doubleValue() == 0.0D)) {
        addOrderQueue(order, getCurPriceByOrder(order));
      } else {
        addTradeOrder(order);
      }
    }
  }
  
  public void addOneMemberOrder(Member member)
  {
    this.log.debug("addOneMemberOrder member:" + member);
    MemberOrder memberOrder = new MemberOrder(member.getM_FirmID());
    this.memberOrderMap.put(memberOrder.getM_FirmID(), memberOrder);
  }
  
  public void addTradeOrder(Order order)
  {
    this.tradePoolManager.execute(order);
  }
  
  public double getCurPriceByOrder(Order order)
  {
    double curPrice = 0.0D;
    String m_FirmID = order.getOtherFirmID();
    Member member = (Member)ServerInit.getMemberQueue().get(m_FirmID);
    Quotation_RT quotation_RT = (Quotation_RT)member.getQuotationMap().get(
      order.getCommodityID());
    if (order.getBuyOrSell().shortValue() == 1) {
      curPrice = quotation_RT.getCurPrice_B();
    } else if (order.getBuyOrSell().shortValue() == 2) {
      curPrice = quotation_RT.getCurPrice_S();
    }
    return curPrice;
  }
  
  public void addOrderQueue(Order order, double curPrice)
  {
    MemberOrder memberOrder = (MemberOrder)this.memberOrderMap.get(order.getOtherFirmID());
    CommodityOrder cmdtyOrder = (CommodityOrder)memberOrder.getCommodityOrderMap().get(
      order.getCommodityID());
    cmdtyOrder.enqueueOrder(order, curPrice);
  }
  
  public long cancelOrder(Long orderNo)
  {
    long ret = -1L;
    Order canceled = null;
    Iterator localIterator2;
    for (Iterator localIterator1 = this.memberOrderMap.values().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      MemberOrder memberOrder = (MemberOrder)localIterator1.next();
      
      localIterator2 = memberOrder.getCommodityOrderMap().values().iterator(); continue;CommodityOrder cmdtyOrder = (CommodityOrder)localIterator2.next();
      canceled = cmdtyOrder.cancelOrder(orderNo);
      if (canceled != null)
      {
        ret = canceled.getQuantity().longValue();
        this.log.debug("Cancel Order No:" + orderNo + 
          " canceled quantity:" + ret);
        return ret;
      }
    }
    return ret;
  }
  
  private static final ReentrantLock lock = new ReentrantLock();
  
  public int withdrawOrderProcess(Long orderNO, String withdrawerID)
  {
    this.log.info("撤销止损止盈单,委托单号:" + orderNO);
    lock.lock();
    int ret = 0;
    try
    {
      long withdrawQty = cancelOrder(orderNO);
      this.log.debug("withdrawOrder,withdrawQty:" + withdrawQty);
      if (withdrawQty < 0L)
      {
        this.log.error("撤单失败，此委托已成交或已撤单:" + orderNO);
        return -1;
      }
      Order withdrawOrder = new Order();
      withdrawOrder.setWithdrawerID(withdrawerID);
      withdrawOrder.setWithdrawID(orderNO);
      withdrawOrder
        .setWithdrawType(new Short((short)4));
      ret = Server.getTradeDAO().withdraw(withdrawOrder);
      if (ret < 0) {
        this.log.info("撤单失败,委托号为" + orderNO + "的单时执行撤单存储失败！");
      } else {
        ret = 0;
      }
    }
    finally
    {
      lock.unlock();
    }
    lock.unlock();
    

    return ret;
  }
  
  public void autoWithdraw()
  {
    this.log.debug("正在自动撤单...");
    
    List<Order> orders = Server.getTradeDAO().getNotTradeOrders();
    int ret = 0;
    boolean isAllSuccess = true;
    for (Order order : orders)
    {
      Order withdrawOrder = new Order();
      withdrawOrder.setWithdrawerID(null);
      withdrawOrder.setWithdrawID(order.getOrderNo());
      withdrawOrder.setWithdrawType(new Short(
        (short)3));
      ret = Server.getTradeDAO().withdraw(withdrawOrder);
      if (ret == -100)
      {
        this.log.info("闭市时自动撤委托号为" + order.getOrderNo() + "的单时执行撤单存储失败！");
        isAllSuccess = false;
      }
    }
    if (!isAllSuccess)
    {
      this.log.debug("自动撤单失败");
      Server.getServerDAO().insertSysLog(new SysLog("自动撤单失败"));
    }
    else
    {
      this.log.debug("自动撤单成功");
      
      clearOrderQueue();
    }
  }
  
  public void clearOrderQueue()
  {
    Iterator localIterator2;
    for (Iterator localIterator1 = this.memberOrderMap.values().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      MemberOrder memberOrder = (MemberOrder)localIterator1.next();
      
      localIterator2 = memberOrder.getCommodityOrderMap().values().iterator(); continue;CommodityOrder commodityOrder = (CommodityOrder)localIterator2.next();
      commodityOrder.clearOrderQueue();
    }
  }
  
  public void printThreadPool()
  {
    this.log.info("打印线程池主动执行任务的近似线程数:" + 
      this.tradePoolManager.threadpool.getActiveCount());
    this.log.info("打印线程池已完成执行的近似任务总数:" + 
      this.tradePoolManager.threadpool.getCompletedTaskCount());
    this.log.info("打印线程池曾经同时位于池中的最大线程数:" + 
      this.tradePoolManager.threadpool.getLargestPoolSize());
    this.log.info("打印线程池中的当前线程数:" + this.tradePoolManager.threadpool.getPoolSize());
    this.log.info("打印线程池计划执行的近似任务总数:" + 
      this.tradePoolManager.threadpool.getTaskCount());
    this.log.info("打印线程池执行程序使用的任务队列:" + 
      this.tradePoolManager.threadpool.getQueue().toString());
    this.log.info("打印超过最大线程数后拒绝的委托队列:" + this.tradePoolManager.orderQueue.toString());
  }
  
  public void stop()
  {
    this.marketOrder = null;
    this.limitOrder = null;
    this.memberOrderMap = null;
    this.tradePoolManager = null;
    instance = null;
  }
  
  public AbstractOrder getMarketOrder()
  {
    return this.marketOrder;
  }
  
  public AbstractOrder getLimitOrder()
  {
    return this.limitOrder;
  }
  
  public int getTraderOrderStatus()
  {
    return this.traderOrderStatus;
  }
  
  public void setTraderOrderStatus(int traderOrderStatus)
  {
    this.traderOrderStatus = traderOrderStatus;
  }
}
