package gnnt.MEBS.delivery.model.workflow;

import gnnt.MEBS.delivery.model.KeyValue;
import java.util.Date;
import java.util.List;

public class EnterWare
  extends WorkFlowClone
{
  private String id;
  private int ability;
  private String enterInformId;
  private String firmId;
  private String warehouseId;
  private String commodityId;
  private Date createDate;
  private Date enterDate;
  private double weight;
  private long quantity;
  private double unitWeight;
  private String lot;
  private String cargoNo;
  private int qualityInspection;
  private String origin;
  private String grade;
  private String sort;
  private String qualityStandard;
  private List<KeyValue> qualityStandardList;
  private String productionDate;
  private String packaging;
  private String rejectedReasons;
  private String xml;
  private double existAmount;
  private double frozenAmount;
  private String changeFirmId;
  private String responsibleman;
  private String agency;
  private String dealerAgency;
  private String remark;
  private String oldEnterWareId;
  
  public String getNote()
  {
    return getRejectedReasons();
  }
  
  public int getCurrentStatus()
  {
    return this.ability;
  }
  
  public String getBillid()
  {
    return this.id;
  }
  
  public int getAbility()
  {
    return this.ability;
  }
  
  public void setAbility(int paramInt)
  {
    this.ability = paramInt;
  }
  
  public String getAgency()
  {
    return this.agency;
  }
  
  public void setAgency(String paramString)
  {
    this.agency = paramString;
  }
  
  public String getCargoNo()
  {
    return this.cargoNo;
  }
  
  public void setCargoNo(String paramString)
  {
    this.cargoNo = paramString;
  }
  
  public String getChangeFirmId()
  {
    return this.changeFirmId;
  }
  
  public void setChangeFirmId(String paramString)
  {
    this.changeFirmId = paramString;
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
  
  public String getDealerAgency()
  {
    return this.dealerAgency;
  }
  
  public void setDealerAgency(String paramString)
  {
    this.dealerAgency = paramString;
  }
  
  public String getEnterInformId()
  {
    return this.enterInformId;
  }
  
  public void setEnterInformId(String paramString)
  {
    this.enterInformId = paramString;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public double getFrozenAmount()
  {
    return this.frozenAmount;
  }
  
  public void setFrozenAmount(double paramDouble)
  {
    this.frozenAmount = paramDouble;
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
  
  public String getQualityStandard()
  {
    if (this.qualityStandardList != null) {
      this.qualityStandard = getToXml(this.qualityStandardList);
    }
    return this.qualityStandard;
  }
  
  public void setQualityStandard(String paramString)
  {
    this.qualityStandard = paramString;
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
  
  public void setRejectedReasons(String paramString)
  {
    this.rejectedReasons = paramString;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String paramString)
  {
    this.remark = paramString;
  }
  
  public String getResponsibleman()
  {
    return this.responsibleman;
  }
  
  public void setResponsibleman(String paramString)
  {
    this.responsibleman = paramString;
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
  
  public List<KeyValue> getQualityStandardList()
  {
    this.qualityStandardList = addToXml(this.qualityStandard, this.qualityStandardList);
    return this.qualityStandardList;
  }
  
  public void addQualityStandardList(List<KeyValue> paramList)
  {
    this.qualityStandardList = paramList;
  }
  
  public double getExistAmount()
  {
    return this.existAmount;
  }
  
  public void setExistAmount(double paramDouble)
  {
    this.existAmount = paramDouble;
  }
  
  public Date getEnterDate()
  {
    return this.enterDate;
  }
  
  public void setEnterDate(Date paramDate)
  {
    this.enterDate = paramDate;
  }
  
  public String getOldEnterWareId()
  {
    return this.oldEnterWareId;
  }
  
  public void setOldEnterWareId(String paramString)
  {
    this.oldEnterWareId = paramString;
  }
}
