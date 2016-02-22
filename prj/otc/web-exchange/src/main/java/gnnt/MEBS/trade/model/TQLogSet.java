package gnnt.MEBS.trade.model;

import gnnt.MEBS.base.model.Clone;

public class TQLogSet
  extends Clone
{
  private static final long serialVersionUID = 6661154038613464943L;
  private String commodityId;
  private Double minquoprice;
  private Double maxquoprice;
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  public Double getMinquoprice()
  {
    return this.minquoprice;
  }
  
  public void setMinquoprice(Double minquoprice)
  {
    this.minquoprice = minquoprice;
  }
  
  public Double getMaxquoprice()
  {
    return this.maxquoprice;
  }
  
  public void setMaxquoprice(Double maxquoprice)
  {
    this.maxquoprice = maxquoprice;
  }
  
  public String getId()
  {
    return this.commodityId;
  }
  
  public void setPrimary(String primary) {}
}
