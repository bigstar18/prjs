package gnnt.MEBS.integrated.mgr.model.stockmanage;

import gnnt.MEBS.bill.core.po.StockPO;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import gnnt.MEBS.integrated.mgr.model.commoditymanage.Breed;
import java.util.Date;
import java.util.Set;

public class Stock
  extends StandardModel
{
  private static final long serialVersionUID = -5013524002449767064L;
  @ClassDiscription(name="仓单编号", description="")
  private String stockId;
  @ClassDiscription(name="关联 品名", description="对应品名ID")
  private Breed breed;
  @ClassDiscription(name="仓库仓单号", description="")
  private String realStockCode;
  @ClassDiscription(name="仓库编号", description="")
  private String warehouseId;
  @ClassDiscription(name="商品数量", description="")
  private Double quantity;
  @ClassDiscription(name="商品单位", description="")
  private String unit;
  @ClassDiscription(name="仓单所属交易商", description="对应交易商代码")
  private String ownerFirm;
  @ClassDiscription(name="仓单变更时间", description="")
  private Date lastTime;
  @ClassDiscription(name="创建时间", description="")
  private Date createTime;
  @ClassDiscription(name="仓单状态", description="仓单状态： 0:未注册仓单  1：注册仓单  2：已出库仓单  3：已拆单 4：拆仓单处理中")
  private Integer stockStatus;
  @ClassDiscription(name="关联仓单属性集合 ", description="")
  private Set<StockGoodsProperty> goodsProperties;
  @ClassDiscription(name="关联仓单业务集合", description="")
  private Set<StockOperation> operations;
  
  public String getRealStockCode()
  {
    return this.realStockCode;
  }
  
  public void setRealStockCode(String paramString)
  {
    this.realStockCode = paramString;
  }
  
  public String getStockId()
  {
    return this.stockId;
  }
  
  public void setStockId(String paramString)
  {
    this.stockId = paramString;
  }
  
  public Breed getBreed()
  {
    return this.breed;
  }
  
  public void setBreed(Breed paramBreed)
  {
    this.breed = paramBreed;
  }
  
  public String getWarehouseId()
  {
    return this.warehouseId;
  }
  
  public void setWarehouseId(String paramString)
  {
    this.warehouseId = paramString;
  }
  
  public Double getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(Double paramDouble)
  {
    this.quantity = paramDouble;
  }
  
  public String getOwnerFirm()
  {
    return this.ownerFirm;
  }
  
  public void setOwnerFirm(String paramString)
  {
    this.ownerFirm = paramString;
  }
  
  public Date getLastTime()
  {
    return this.lastTime;
  }
  
  public void setLastTime(Date paramDate)
  {
    this.lastTime = paramDate;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public Integer getStockStatus()
  {
    return this.stockStatus;
  }
  
  public void setStockStatus(Integer paramInteger)
  {
    this.stockStatus = paramInteger;
  }
  
  public String getUnit()
  {
    return this.unit;
  }
  
  public void setUnit(String paramString)
  {
    this.unit = paramString;
  }
  
  public Set<StockGoodsProperty> getGoodsProperties()
  {
    return this.goodsProperties;
  }
  
  public void setGoodsProperties(Set<StockGoodsProperty> paramSet)
  {
    this.goodsProperties = paramSet;
  }
  
  public Set<StockOperation> getOperations()
  {
    return this.operations;
  }
  
  public void setOperations(Set<StockOperation> paramSet)
  {
    this.operations = paramSet;
  }
  
  public StockPO getStockPO()
  {
    StockPO localStockPO = new StockPO();
    localStockPO.setStockID(getStockId());
    localStockPO.setRealStockCode(getRealStockCode());
    localStockPO.setBreedID(getBreed().getBreedId().longValue());
    localStockPO.setWarehouseID(getWarehouseId());
    localStockPO.setQuantity(getQuantity().doubleValue());
    localStockPO.setUnit(getUnit());
    localStockPO.setOwnerFirm(getOwnerFirm());
    localStockPO.setLastTime(getLastTime());
    localStockPO.setCreateTime(getCreateTime());
    localStockPO.setStockStatus(getStockStatus().intValue());
    return localStockPO;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("stockId", this.stockId);
  }
}
