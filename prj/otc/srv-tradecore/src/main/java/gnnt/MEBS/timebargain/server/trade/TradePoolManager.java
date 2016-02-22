package gnnt.MEBS.timebargain.server.trade;

import gnnt.MEBS.timebargain.server.model.Order;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TradePoolManager
{
  private static TradePoolManager pm = new TradePoolManager();
  private static final int CORE_POOL_SIZE = 20;
  private static final int MAX_POOL_SIZE = 100;
  private static final int KEEP_ALIVE_TIME = 0;
  private static final int WORK_QUEUE_SIZE = 2000;
  Queue<Order> orderQueue = new LinkedList();
  final Runnable access = new Runnable()
  {
    public void run()
    {
      if (TradePoolManager.this.hasMroeAcquire())
      {
        Order order = (Order)TradePoolManager.this.orderQueue.poll();
        Runnable task = new TradeThread(order);
        TradePoolManager.this.threadpool.execute(task);
      }
    }
  };
  final RejectedExecutionHandler handler = new RejectedExecutionHandler()
  {
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
    {
      TradePoolManager.this.orderQueue.offer(((TradeThread)r).getOrder());
    }
  };
  final ThreadPoolExecutor threadpool = new ThreadPoolExecutor(20, 100, 0L, 
    TimeUnit.SECONDS, new ArrayBlockingQueue(2000), 
    this.handler);
  final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  final ScheduledFuture taskHandler = this.scheduler
    .scheduleAtFixedRate(this.access, 0L, 1L, TimeUnit.SECONDS);
  
  public static TradePoolManager newInstance()
  {
    return pm;
  }
  
  public boolean hasMroeAcquire()
  {
    return !this.orderQueue.isEmpty();
  }
  
  public void execute(Order order)
  {
    Runnable task = new TradeThread(order);
    this.threadpool.execute(task);
  }
}
