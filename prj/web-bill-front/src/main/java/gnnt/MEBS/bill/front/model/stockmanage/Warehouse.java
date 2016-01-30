package gnnt.MEBS.bill.front.model.stockmanage;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class Warehouse
  extends StandardModel
{
  private static final long serialVersionUID = 4901122171733186296L;
  @ClassDiscription(name="编号", description="")
  private Long id;
  @ClassDiscription(name="仓库编号", description="")
  private String warehouseId;
  @ClassDiscription(name="仓库名称", description="")
  private String warehouseName;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public String getWarehouseId()
  {
    return this.warehouseId;
  }
  
  public void setWarehouseId(String paramString)
  {
    this.warehouseId = paramString;
  }
  
  public String getWarehouseName()
  {
    return this.warehouseName;
  }
  
  public void setWarehouseName(String paramString)
  {
    this.warehouseName = paramString;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("id", this.id);
  }
}
