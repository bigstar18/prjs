package gnnt.MEBS.vendue.server.bus;

public class RefreshMemoryQuotationDataThread
  extends Thread
{
  private long timeInterval = 200L;
  private boolean runStatus = true;
  QuotationCacheService service = new QuotationCacheService();
  
  public void run()
  {
    while (this.runStatus)
    {
      this.service.buildSharedData();
      this.service.printAllMemoryXML();
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
