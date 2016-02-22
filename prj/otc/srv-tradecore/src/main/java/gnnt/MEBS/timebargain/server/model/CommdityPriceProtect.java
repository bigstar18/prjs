package gnnt.MEBS.timebargain.server.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CommdityPriceProtect
{
  private String commodityID;
  private double ScreeningPricePoint;
  private long TimeoutInterval;
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public double getScreeningPricePoint()
  {
    return this.ScreeningPricePoint;
  }
  
  public void setScreeningPricePoint(double screeningPricePoint)
  {
    this.ScreeningPricePoint = screeningPricePoint;
  }
  
  public long getTimeoutInterval()
  {
    return this.TimeoutInterval;
  }
  
  public void setTimeoutInterval(long timeoutInterval)
  {
    this.TimeoutInterval = timeoutInterval;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, 
      ToStringStyle.MULTI_LINE_STYLE);
  }
}
