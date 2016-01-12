package gnnt.MEBS.timebargain.mgr.model.settle;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class Stock extends StandardModel
{
  private static final long serialVersionUID = 234874695698504944L;

  @ClassDiscription(name="仓单号", description="")
  private String stockID;

  @ClassDiscription(name="仓库仓单号", description="")
  private String realStockCode;

  @ClassDiscription(name="品名ID", description="")
  private Integer breedID;

  @ClassDiscription(name="仓库编号", description="")
  private String warehouseID;

  @ClassDiscription(name="商品数量", description="")
  private Double quantity;

  @ClassDiscription(name="商品单位", description="")
  private String unit;

  @ClassDiscription(name="仓单所属交易商", description="")
  private String ownerFirm;

  @ClassDiscription(name="仓单状态", description="")
  private Long stockStatus;

  public String getStockID()
  {
    return this.stockID;
  }

  public void setStockID(String stockID)
  {
    this.stockID = stockID;
  }

  public String getRealStockCode()
  {
    return this.realStockCode;
  }

  public void setRealStockCode(String realStockCode)
  {
    this.realStockCode = realStockCode;
  }

  public Integer getBreedID()
  {
    return this.breedID;
  }

  public void setBreedID(Integer breedID)
  {
    this.breedID = breedID;
  }

  public String getWarehouseID()
  {
    return this.warehouseID;
  }

  public void setWarehouseID(String warehouseID)
  {
    this.warehouseID = warehouseID;
  }

  public Double getQuantity()
  {
    return this.quantity;
  }

  public void setQuantity(Double quantity)
  {
    this.quantity = quantity;
  }

  public String getUnit()
  {
    return this.unit;
  }

  public void setUnit(String unit)
  {
    this.unit = unit;
  }

  public String getOwnerFirm()
  {
    return this.ownerFirm;
  }

  public void setOwnerFirm(String ownerFirm)
  {
    this.ownerFirm = ownerFirm;
  }

  public Long getStockStatus()
  {
    return this.stockStatus;
  }

  public void setStockStatus(Long stockStatus)
  {
    this.stockStatus = stockStatus;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "stockID", this.stockID);
  }
}