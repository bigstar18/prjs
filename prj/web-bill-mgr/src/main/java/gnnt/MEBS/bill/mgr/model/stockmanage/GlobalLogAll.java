package gnnt.MEBS.bill.mgr.model.stockmanage;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class GlobalLogAll
  extends WarehouseLogBase
{
  private static final long serialVersionUID = 5215187578438868766L;
  @ClassDiscription(name="仓库编号", description="")
  private String wareHouseID;
  
  public String getWareHouseID()
  {
    return this.wareHouseID;
  }
  
  public void setWareHouseID(String paramString)
  {
    this.wareHouseID = paramString;
  }
}
