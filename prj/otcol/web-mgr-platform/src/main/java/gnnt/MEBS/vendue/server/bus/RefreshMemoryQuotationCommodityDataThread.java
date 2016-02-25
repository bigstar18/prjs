package gnnt.MEBS.vendue.server.bus;

public class RefreshMemoryQuotationCommodityDataThread
  extends Thread
{
  private long timeInterval = 200L;
  private boolean runStatus = true;
  private CurCommodityCacheService service = new CurCommodityCacheService();
  
  public CurCommodityCacheService getService()
  {
    return this.service;
  }
  
  public void run()
  {
    while (this.runStatus)
    {
      this.service.buildSharedData();
      try
      {
        sleep(this.timeInterval);
      }
      catch (InterruptedException localInterruptedException) {}
    }
  }
  
  public void stopMe()
  {
    this.runStatus = false;
  }
  
  public long getTimeInterval()
  {
    return this.timeInterval;
  }
  
  public void setTimeInterval(long paramLong)
  {
    this.timeInterval = paramLong;
  }
}
