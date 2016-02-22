package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.sql.Timestamp;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Quotation
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049813L;
  private String commodityID;
  private Double curPrice = Double.valueOf(0.0D);
  private Double openPrice = Double.valueOf(0.0D);
  private Double highPrice = Double.valueOf(0.0D);
  private Double lowPrice = Double.valueOf(0.0D);
  private Double closePrice = Double.valueOf(0.0D);
  private Timestamp updateTime;
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public Double getCurPrice()
  {
    return this.curPrice;
  }
  
  public void setCurPrice(Double curPrice)
  {
    this.curPrice = curPrice;
  }
  
  public Double getOpenPrice()
  {
    return this.openPrice;
  }
  
  public void setOpenPrice(Double openPrice)
  {
    this.openPrice = openPrice;
  }
  
  public Double getHighPrice()
  {
    return this.highPrice;
  }
  
  public void setHighPrice(Double highPrice)
  {
    this.highPrice = highPrice;
  }
  
  public Double getLowPrice()
  {
    return this.lowPrice;
  }
  
  public void setLowPrice(Double lowPrice)
  {
    this.lowPrice = lowPrice;
  }
  
  public Double getClosePrice()
  {
    return this.closePrice;
  }
  
  public void setClosePrice(Double closePrice)
  {
    this.closePrice = closePrice;
  }
  
  public Timestamp getUpdateTime()
  {
    return this.updateTime;
  }
  
  public void setUpdateTime(Timestamp updateTime)
  {
    this.updateTime = updateTime;
  }
}
