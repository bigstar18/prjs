package gnnt.MEBS.delivery.model.workflow;

import java.util.Date;

public class OutWare
  extends WorkFlowClone
{
  private String id;
  private int ability;
  private String firmId;
  private String warehouseId;
  private String commodityId;
  private Date createDate;
  private double weight;
  private long quantity;
  private Date outDate;
  private Date planOutDate;
  private String responsibleman;
  private String agency;
  private String dealerAgency;
  private String remark;
  private String tel;
  private String enterWareId;
  private String rejectedReasons;
  private String xml;
  
  public String getNote()
  {
    return this.rejectedReasons;
  }
  
  public int getCurrentStatus()
  {
    return this.ability;
  }
  
  public String getBillid()
  {
    return this.id;
  }
  
  public String getXml()
  {
    return this.xml;
  }
  
  public void setXml(String paramString)
  {
    this.xml = paramString;
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
  
  public String getEnterWareId()
  {
    return this.enterWareId;
  }
  
  public void setEnterWareId(String paramString)
  {
    this.enterWareId = paramString;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public Date getOutDate()
  {
    return this.outDate;
  }
  
  public void setOutDate(Date paramDate)
  {
    this.outDate = paramDate;
  }
  
  public Date getPlanOutDate()
  {
    return this.planOutDate;
  }
  
  public void setPlanOutDate(Date paramDate)
  {
    this.planOutDate = paramDate;
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
  
  public String getTel()
  {
    return this.tel;
  }
  
  public void setTel(String paramString)
  {
    this.tel = paramString;
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
}
