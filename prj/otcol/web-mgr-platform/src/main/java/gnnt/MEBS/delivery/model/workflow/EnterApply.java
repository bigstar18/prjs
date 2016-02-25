package gnnt.MEBS.delivery.model.workflow;

import java.util.Date;

public class EnterApply
  extends WorkFlowClone
{
  private String id;
  private int ability;
  private String firmId;
  private String warehouseId;
  private String commodityId;
  private Date createDate;
  private Date enterDate;
  private double weight;
  private long quantity;
  private double unitWeight;
  private String lot;
  private int qualityInspection;
  private String origin;
  private String grade;
  private String sort;
  private String productionDate;
  private String packaging;
  private String remark;
  private String rejectedReasons;
  private int informAbility;
  private String xml;
  
  public String getNote()
  {
    return getRejectedReasons();
  }
  
  public int getCurrentStatus()
  {
    return getAbility();
  }
  
  public String getBillid()
  {
    return this.id;
  }
  
  public int getAbility()
  {
    return this.ability;
  }
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String paramString)
  {
    this.commodityId = paramString;
  }
  
  public Date getCreateDate()
  {
    return this.createDate;
  }
  
  public void setCreateDate(Date paramDate)
  {
    this.createDate = paramDate;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public String getGrade()
  {
    return this.grade;
  }
  
  public void setGrade(String paramString)
  {
    this.grade = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getLot()
  {
    return this.lot;
  }
  
  public void setLot(String paramString)
  {
    this.lot = paramString;
  }
  
  public String getOrigin()
  {
    return this.origin;
  }
  
  public void setOrigin(String paramString)
  {
    this.origin = paramString;
  }
  
  public Date getEnterDate()
  {
    return this.enterDate;
  }
  
  public void setEnterDate(Date paramDate)
  {
    this.enterDate = paramDate;
  }
  
  public String getPackaging()
  {
    return this.packaging;
  }
  
  public void setPackaging(String paramString)
  {
    this.packaging = paramString;
  }
  
  public String getProductionDate()
  {
    return this.productionDate;
  }
  
  public void setProductionDate(String paramString)
  {
    this.productionDate = paramString;
  }
  
  public int getQualityInspection()
  {
    return this.qualityInspection;
  }
  
  public void setQualityInspection(int paramInt)
  {
    this.qualityInspection = paramInt;
  }
  
  public long getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(long paramLong)
  {
    this.quantity = paramLong;
  }
  
  public String getRejectedReasons()
  {
    return this.rejectedReasons;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String paramString)
  {
    this.remark = paramString;
  }
  
  public String getSort()
  {
    return this.sort;
  }
  
  public void setSort(String paramString)
  {
    this.sort = paramString;
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
  
  public double getWeight()
  {
    return this.weight;
  }
  
  public void setWeight(double paramDouble)
  {
    this.weight = paramDouble;
  }
  
  public String getXml()
  {
    return this.xml;
  }
  
  public void setXml(String paramString)
  {
    this.xml = paramString;
  }
  
  public int getInformAbility()
  {
    return this.informAbility;
  }
  
  public void setInformAbility(int paramInt)
  {
    this.informAbility = paramInt;
  }
  
  public void setAbility(int paramInt)
  {
    this.ability = paramInt;
  }
  
  public void setRejectedReasons(String paramString)
  {
    this.rejectedReasons = paramString;
  }
}
