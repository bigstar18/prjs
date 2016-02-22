package gnnt.MEBS.settlement.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class AvgPrice
  extends Clone
{
  private Long detailId;
  private String commodityId;
  private double price;
  private Date occurTime;
  
  public Long getDetailId()
  {
    return this.detailId;
  }
  
  public void setDetailId(Long detailId)
  {
    this.detailId = detailId;
  }
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  public double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(double price)
  {
    this.price = price;
  }
  
  public Date getOccurTime()
  {
    return this.occurTime;
  }
  
  public void setOccurTime(Date occurTime)
  {
    this.occurTime = occurTime;
  }
  
  public AvgPrice() {}
  
  public AvgPrice(Long detailId, String commodityId, double price, Date occurTime)
  {
    this.detailId = detailId;
    this.commodityId = commodityId;
    this.price = price;
    this.occurTime = occurTime;
  }
  
  public Long getId()
  {
    return this.detailId;
  }
  
  public void setPrimary(String primary)
  {
    this.detailId = Long.valueOf(Long.parseLong(primary));
  }
}
