package gnnt.MEBS.delivery.model.inner;

import gnnt.MEBS.base.model.Clone;

public class WarehouseRelateCommodity
  extends Clone
{
  private String warehouseId;
  private String commodityId;
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String paramString)
  {
    this.commodityId = paramString;
  }
  
  public String getWarehouseId()
  {
    return this.warehouseId;
  }
  
  public void setWarehouseId(String paramString)
  {
    this.warehouseId = paramString;
  }
}
