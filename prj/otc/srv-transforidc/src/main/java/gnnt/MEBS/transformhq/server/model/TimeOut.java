package gnnt.MEBS.transformhq.server.model;

public class TimeOut
{
  private String beginTime;
  private String endTime;
  private long timeOut;
  
  public TimeOut() {}
  
  public TimeOut(String beginTime, String endTime, long timeOut)
  {
    this.beginTime = beginTime;
    this.endTime = endTime;
    this.timeOut = timeOut;
  }
  
  public String getBeginTime()
  {
    return this.beginTime;
  }
  
  public void setBeginTime(String beginTime)
  {
    this.beginTime = beginTime;
  }
  
  public String getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
  }
  
  public long getTimeOut()
  {
    return this.timeOut;
  }
  
  public void setTimeOut(long timeOut)
  {
    this.timeOut = timeOut;
  }
}
