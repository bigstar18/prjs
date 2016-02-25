package gnnt.MEBS.delivery.model.workflow;

import gnnt.MEBS.delivery.model.Commodity;
import gnnt.MEBS.delivery.model.Warehouse;

public class RCWView
{
  private RegStockApply regStockApply;
  private Commodity commodity;
  private Warehouse warehouse;
  
  public Commodity getCommodity()
  {
    return this.commodity;
  }
  
  public void setCommodity(Commodity paramCommodity)
  {
    this.commodity = paramCommodity;
  }
  
  public RegStockApply getRegStockApply()
  {
    return this.regStockApply;
  }
  
  public void setRegStockApply(RegStockApply paramRegStockApply)
  {
    this.regStockApply = paramRegStockApply;
  }
  
  public Warehouse getWarehouse()
  {
    return this.warehouse;
  }
  
  public void setWarehouse(Warehouse paramWarehouse)
  {
    this.warehouse = paramWarehouse;
  }
}
