package gnnt.MEBS.vendue.server.bus;

public class RefreshMemoryBroadcastTitleThread
  extends Thread
{
  private long timeInterval = 2000L;
  private boolean runStatus = true;
  BroadcastCacheService service = new BroadcastCacheService();
  
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
