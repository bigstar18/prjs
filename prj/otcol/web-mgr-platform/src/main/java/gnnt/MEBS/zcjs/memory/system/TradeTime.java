package gnnt.MEBS.zcjs.memory.system;

import java.util.Date;

public class TradeTime
{
  private int serialNumber;
  private Date startTime;
  private Date endTime;
  
  public int getSerialNumber()
  {
    return this.serialNumber;
  }
  
  public void setSerialNumber(int serialNumber)
  {
    this.serialNumber = serialNumber;
  }
  
  public Date getStartTime()
  {
    return this.startTime;
  }
  
  public void setStartTime(Date startTime)
  {
    this.startTime = startTime;
  }
  
  public Date getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(Date endTime)
  {
    this.endTime = endTime;
  }
}
