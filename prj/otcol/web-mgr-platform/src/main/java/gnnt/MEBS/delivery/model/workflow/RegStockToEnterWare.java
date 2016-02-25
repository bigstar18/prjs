package gnnt.MEBS.delivery.model.workflow;

import java.util.Date;

public class RegStockToEnterWare
  extends WorkFlowClone
{
  private String id;
  private String regStockId;
  private double relTurnToWeight;
  private String breedId;
  private String firmId;
  private String warehouseId;
  private int status;
  private String stockId;
  private int Type;
  private Date ModifyTime;
  private Date createDate;
  private String rejectedReasons;
  
  public String getNote()
  {
    return getRejectedReasons();
  }
  
  public int getCurrentStatus()
  {
    return this.status;
  }
  
  public String getBillid()
  {
    return this.id;
  }
  
  public String getRejectedReasons()
  {
    return this.rejectedReasons;
  }
  
  public void setRejectedReasons(String paramString)
  {
    this.rejectedReasons = paramString;
  }
  
  public String getRegStockId()
  {
    return this.regStockId;
  }
  
  public void setRegStockId(String paramString)
  {
    this.regStockId = paramString;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
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
  
  public int getType()
  {
    return this.Type;
  }
  
  public void setType(int paramInt)
  {
    this.Type = paramInt;
  }
  
  public Date getModifyTime()
  {
    return this.ModifyTime;
  }
  
  public void setModifyTime(Date paramDate)
  {
    this.ModifyTime = paramDate;
  }
  
  public Date getCreateDate()
  {
    return this.createDate;
  }
  
  public void setCreateDate(Date paramDate)
  {
    this.createDate = paramDate;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(String paramString)
  {
    this.breedId = paramString;
  }
  
  public double getRelTurnToWeight()
  {
    return this.relTurnToWeight;
  }
  
  public void setRelTurnToWeight(double paramDouble)
  {
    this.relTurnToWeight = paramDouble;
  }
}
