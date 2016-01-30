package gnnt.MEBS.bill.mgr.model.stockmanage;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class GlobalLogAllHis
  extends WarehouseLogBase
{
  private static final long serialVersionUID = -8197999991636037824L;
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
