package gnnt.MEBS.delivery.model.workflow;

import java.util.Date;

public class RegStock
  extends WorkFlowClone
{
  private String regStockId;
  private String oldRegStockId;
  private long breedId;
  private String firmId;
  private String warehouseId;
  private String stockId;
  private double initWeight;
  private double weight;
  private double frozenWeight;
  private double unitWeight;
  private Date createTime;
  private Date modifyTime;
  private int type;
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public long getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(long paramLong)
  {
    this.breedId = paramLong;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public double getFrozenWeight()
  {
    return this.frozenWeight;
  }
  
  public void setFrozenWeight(double paramDouble)
  {
    this.frozenWeight = paramDouble;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Date paramDate)
  {
    this.modifyTime = paramDate;
  }
  
  public String getOldRegStockId()
  {
    return this.oldRegStockId;
  }
  
  public void setOldRegStockId(String paramString)
  {
    this.oldRegStockId = paramString;
  }
  
  public double getWeight()
  {
    return this.weight;
  }
  
  public void setWeight(double paramDouble)
  {
    this.weight = paramDouble;
  }
  
  public String getRegStockId()
  {
    return this.regStockId;
  }
  
  public void setRegStockId(String paramString)
  {
    this.regStockId = paramString;
  }
  
  public String getStockId()
  {
    return this.stockId;
  }
  
  public void setStockId(String paramString)
  {
    this.stockId = paramString;
  }
  
  public double getUnitWeight()
  {
    return this.unitWeight;
  }
  
  public void setUnitWeight(double paramDouble)
  {
    this.unitWeight = paramDouble;
  }
  
  public String getWarehouseId()
  {
    return this.warehouseId;
  }
  
  public void setWarehouseId(String paramString)
  {
    this.warehouseId = paramString;
  }
  
  public double getInitWeight()
  {
    return this.initWeight;
  }
  
  public void setInitWeight(double paramDouble)
  {
    this.initWeight = paramDouble;
  }
}
