package gnnt.MEBS.vendue.server.bus;

import gnnt.MEBS.vendue.util.Configuration;
import java.io.PrintStream;
import java.util.Properties;

public class RefreshMemoryThreadManager
{
  private static boolean isThreadRunning = false;
  private static RefreshMemoryQuotationDataThread threadRefreshQuotationMemory;
  private static RefreshMemoryBroadcastTitleThread threadRefreshBroadcastMemory;
  private static RefreshMemoryQuotationCommodityDataThread refreshMemoryQuotationCommodityDataThread;
  private static MonitorStatusAndSectorThread monitorStatusAndSectorThread;
  
  public static void runRefreshThread()
  {
    if (isThreadRunning) {
      return;
    }
    isThreadRunning = true;
    System.out.println("Congratulation! Thread Refresh Memory Running......");
    threadRefreshQuotationMemory = new RefreshMemoryQuotationDataThread();
    threadRefreshBroadcastMemory = new RefreshMemoryBroadcastTitleThread();
    refreshMemoryQuotationCommodityDataThread = new RefreshMemoryQuotationCommodityDataThread();
    monitorStatusAndSectorThread = new MonitorStatusAndSectorThread();
    threadRefreshQuotationMemory.setDaemon(true);
    threadRefreshBroadcastMemory.setDaemon(true);
    refreshMemoryQuotationCommodityDataThread.setDaemon(true);
    monitorStatusAndSectorThread.setDaemon(true);
    threadRefreshQuotationMemory.start();
    threadRefreshBroadcastMemory.start();
    refreshMemoryQuotationCommodityDataThread.start();
    monitorStatusAndSectorThread.setCurCommodityService(refreshMemoryQuotationCommodityDataThread.getService());
    monitorStatusAndSectorThread.start();
    long l1 = getQuotationTimeInterval();
    if (l1 > 0L) {
      threadRefreshQuotationMemory.setTimeInterval(l1);
    }
    long l2 = getBroadcastTimeInterval();
    if (l2 > 0L) {
      threadRefreshBroadcastMemory.setTimeInterval(l2);
    }
    long l3 = getCommodityDataThreadInterval();
    if (l3 > 0L) {
      refreshMemoryQuotationCommodityDataThread.setTimeInterval(l3);
    }
    long l4 = getCommodityDataChangeMonitorThreadInterval();
    if (l4 > 0L) {
      monitorStatusAndSectorThread.setTimeInterval(l4);
    }
    try
    {
      System.out.println("主线程沉睡3秒, 等待线程完全运行...");
      Thread.sleep(5000L);
    }
    catch (Exception localException) {}
  }
  
  private static long getQuotationTimeInterval()
  {
    try
    {
      Properties localProperties = new Configuration().getSection("MEBS.ThreadRefreshMemory");
      String str = localProperties.getProperty("QuotationTimeInterval");
      return Long.parseLong(str);
    }
    catch (Exception localException) {}
    return 1000L;
  }
  
  private static long getBroadcastTimeInterval()
  {
    try
    {
      Properties localProperties = new Configuration().getSection("MEBS.ThreadRefreshMemory");
      String str = localProperties.getProperty("BroadcastTimeInterval");
      return Long.parseLong(str);
    }
    catch (Exception localException) {}
    return 5000L;
  }
  
  private static long getCommodityDataThreadInterval()
  {
    try
    {
      Properties localProperties = new Configuration().getSection("MEBS.ThreadRefreshMemory");
      String str = localProperties.getProperty("CommodityDataThreadInterval");
      return Long.parseLong(str);
    }
    catch (Exception localException) {}
    return 1000L;
  }
  
  private static long getCommodityDataChangeMonitorThreadInterval()
  {
    try
    {
      Properties localProperties = new Configuration().getSection("MEBS.ThreadRefreshMemory");
      String str = localProperties.getProperty("CommodityDataChangeMonitorThreadInterval");
      return Long.parseLong(str);
    }
    catch (Exception localException) {}
    return 1000L;
  }
  
  public static void stopAllThreads()
  {
    if (isThreadRunning)
    {
      threadRefreshQuotationMemory.stopMe();
      threadRefreshBroadcastMemory.stopMe();
      refreshMemoryQuotationCommodityDataThread.stopMe();
      monitorStatusAndSectorThread.stopMe();
      isThreadRunning = false;
    }
  }
  
  static
  {
    try
    {
      runRefreshThread();
    }
    catch (Exception localException) {}
  }
}
