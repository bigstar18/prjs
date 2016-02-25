package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.zcjs.model.innerObject.CommodityPropertyObject;
import gnnt.MEBS.zcjs.model.innerObject.QualityObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WarehouseRegStock
  extends Clone
{
  private String regStockId;
  private long breedId;
  private String name;
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
  private List<CommodityPropertyObject> propertyObjectList = new ArrayList();
  private List<QualityObject> qualityObjectList = new ArrayList();
  
  public String getRegStockId()
  {
    return this.regStockId;
  }
  
  public void setRegStockId(String paramString)
  {
    this.regStockId = paramString;
  }
  
  public long getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(long paramLong)
  {
    this.breedId = paramLong;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public String getWarehouseId()
  {
    return this.warehouseId;
  }
  
  public void setWarehouseId(String paramString)
  {
    this.warehouseId = paramString;
  }
  
  public String getStockId()
  {
    return this.stockId;
  }
  
  public void setStockId(String paramString)
  {
    this.stockId = paramString;
  }
  
  public double getInitWeight()
  {
    return this.initWeight;
  }
  
  public void setInitWeight(double paramDouble)
  {
    this.initWeight = paramDouble;
  }
  
  public double getWeight()
  {
    return this.weight;
  }
  
  public void setWeight(double paramDouble)
  {
    this.weight = paramDouble;
  }
  
  public double getFrozenWeight()
  {
    return this.frozenWeight;
  }
  
  public void setFrozenWeight(double paramDouble)
  {
    this.frozenWeight = paramDouble;
  }
  
  public double getUnitWeight()
  {
    return this.unitWeight;
  }
  
  public void setUnitWeight(double paramDouble)
  {
    this.unitWeight = paramDouble;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Date paramDate)
  {
    this.modifyTime = paramDate;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public List<CommodityPropertyObject> getPropertyObjectList()
  {
    return this.propertyObjectList;
  }
  
  public void setPropertyObjectList(List<CommodityPropertyObject> paramList)
  {
    this.propertyObjectList = paramList;
  }
  
  public List<QualityObject> getQualityObjectList()
  {
    return this.qualityObjectList;
  }
  
  public void setQualityObjectList(List<QualityObject> paramList)
  {
    this.qualityObjectList = paramList;
  }
}
