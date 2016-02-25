package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class TradeTime
  extends Clone
{
  private int serialNumber;
  private String startTime;
  private String endTime;
  
  public int getSerialNumber()
  {
    return this.serialNumber;
  }
  
  public void setSerialNumber(int paramInt)
  {
    this.serialNumber = paramInt;
  }
  
  public String getStartTime()
  {
    return this.startTime;
  }
  
  public void setStartTime(String paramString)
  {
    this.startTime = paramString;
  }
  
  public String getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(String paramString)
  {
    this.endTime = paramString;
  }
}
