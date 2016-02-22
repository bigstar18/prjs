package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class QuotationHisReport
  extends Clone
{
  private Date clearDate;
  private String commodityId;
  private String commodityName;
  private Double lowPrice;
  private Double highPrice;
  private Double yesterBalancePrice;
  private Double price;
  
  public QuotationHisReport() {}
  
  public QuotationHisReport(Date clearDate, String commodityId, String commodityName, Double lowPrice, Double highPrice, Double yesterBalancePrice, Double price)
  {
    this.clearDate = clearDate;
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.lowPrice = lowPrice;
    this.highPrice = highPrice;
    this.yesterBalancePrice = yesterBalancePrice;
    this.price = price;
  }
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  public Double getLowPrice()
  {
    return this.lowPrice;
  }
  
  public void setLowPrice(Double lowPrice)
  {
    this.lowPrice = lowPrice;
  }
  
  public Double getHighPrice()
  {
    return this.highPrice;
  }
  
  public void setHighPrice(Double highPrice)
  {
    this.highPrice = highPrice;
  }
  
  public Double getYesterBalancePrice()
  {
    return this.yesterBalancePrice;
  }
  
  public void setYesterBalancePrice(Double yesterBalancePrice)
  {
    this.yesterBalancePrice = yesterBalancePrice;
  }
  
  public Double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(Double price)
  {
    this.price = price;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
