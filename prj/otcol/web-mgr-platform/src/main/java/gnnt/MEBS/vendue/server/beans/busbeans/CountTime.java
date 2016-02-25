package gnnt.MEBS.vendue.server.beans.busbeans;

public class CountTime
{
  private long countDownStart;
  private long countDownEnd;
  private long dbSystime;
  private long locSystime;
  
  public long getRestSecond()
  {
    long l1 = System.currentTimeMillis() - this.locSystime;
    long l2 = this.dbSystime + l1;
    return (this.countDownEnd - l2) / 1000L;
  }
  
  public long getCountDownEnd()
  {
    return this.countDownEnd;
  }
  
  public void setCountDownEnd(long paramLong)
  {
    this.countDownEnd = paramLong;
  }
  
  public long getCountDownStart()
  {
    return this.countDownStart;
  }
  
  public void setCountDownStart(long paramLong)
  {
    this.countDownStart = paramLong;
  }
  
  public long getDbSystime()
  {
    return this.dbSystime;
  }
  
  public void setDbSystime(long paramLong)
  {
    this.dbSystime = paramLong;
  }
  
  public long getLocSystime()
  {
    return this.locSystime;
  }
  
  public void setLocSystime(long paramLong)
  {
    this.locSystime = paramLong;
  }
}
