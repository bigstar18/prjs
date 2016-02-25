package gnnt.MEBS.vendue.server.bus;

public class MonitorStatusAndSectorThread
  extends Thread
{
  private long timeInterval = 200L;
  private boolean runStatus = true;
  private CurCommodityCacheService curCommodityCacheService = null;
  private PartitonAndSectionMonitorService service = new PartitonAndSectionMonitorService();
  
  public void setCurCommodityService(CurCommodityCacheService paramCurCommodityCacheService)
  {
    this.curCommodityCacheService = paramCurCommodityCacheService;
  }
  
  public void run()
  {
    this.service.setCurCommodityCacheService(this.curCommodityCacheService);
    while (this.runStatus)
    {
      if (this.service != null) {
        this.service.monitor();
      }
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
