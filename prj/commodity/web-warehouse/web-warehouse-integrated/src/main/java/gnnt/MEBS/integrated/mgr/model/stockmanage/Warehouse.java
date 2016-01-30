package gnnt.MEBS.integrated.mgr.model.stockmanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class Warehouse
  extends StandardModel
{
  private static final long serialVersionUID = 4901122171733186296L;
  @ClassDiscription(name="编号", description="")
  private Long id;
  @ClassDiscription(name="仓库编号", description="")
  private String warehouseID;
  @ClassDiscription(name="仓库名称", description="")
  private String warehouseName;
  @ClassDiscription(name="仓库状态", description="仓库状态：0 可用 1 不可用")
  private Integer status;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public String getWarehouseID()
  {
    return this.warehouseID;
  }
  
  public void setWarehouseID(String paramString)
  {
    this.warehouseID = paramString;
  }
  
  public String getWarehouseName()
  {
    return this.warehouseName;
  }
  
  public void setWarehouseName(String paramString)
  {
    this.warehouseName = paramString;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer paramInteger)
  {
    this.status = paramInteger;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("id", this.id);
  }
}
