package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class QutationDetailSearch
  extends Clone
{
  private String detailId;
  private Date clearDate;
  private String commodityId;
  private Double price;
  private String commodityName;
  private Date occurTime;
  
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
  
  public Double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(Double price)
  {
    this.price = price;
  }
  
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  public Date getOccurTime()
  {
    return this.occurTime;
  }
  
  public void setOccurTime(Date occurTime)
  {
    this.occurTime = occurTime;
  }
  
  public String getDetailId()
  {
    return this.detailId;
  }
  
  public void setDetailId(String detailId)
  {
    this.detailId = detailId;
  }
  
  public QutationDetailSearch(Date clearDate, String commodityId, Double price, String commodityName, Date occurTime, String detailId)
  {
    this.clearDate = clearDate;
    this.commodityId = commodityId;
    this.price = price;
    this.commodityName = commodityName;
    this.occurTime = occurTime;
    this.detailId = detailId;
  }
  
  public QutationDetailSearch() {}
  
  public void setPrimary(String primary) {}
  
  public String getId()
  {
    return null;
  }
}
