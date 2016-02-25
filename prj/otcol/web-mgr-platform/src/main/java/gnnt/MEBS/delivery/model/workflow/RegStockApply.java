package gnnt.MEBS.delivery.model.workflow;

import java.util.Date;

public class RegStockApply
  extends WorkFlowClone
{
  private String applyId;
  private long breedId;
  private String firmId;
  private String warehouseId;
  private String stockId;
  private double weight;
  private double unitWeight;
  private int status;
  private String regStockId;
  private String applicant;
  private Date applyTime;
  private String auditor;
  private Date auditTime;
  private int type;
  private String rejectedReasons;
  
  public String getNote()
  {
    return this.rejectedReasons;
  }
  
  public int getCurrentStatus()
  {
    return this.status;
  }
  
  public String getBillid()
  {
    return this.applyId;
  }
  
  public String getApplicant()
  {
    return this.applicant;
  }
  
  public void setApplicant(String paramString)
  {
    this.applicant = paramString;
  }
  
  public String getApplyId()
  {
    return this.applyId;
  }
  
  public void setApplyId(String paramString)
  {
    this.applyId = paramString;
  }
  
  public Date getApplyTime()
  {
    return this.applyTime;
  }
  
  public void setApplyTime(Date paramDate)
  {
    this.applyTime = paramDate;
  }
  
  public String getAuditor()
  {
    return this.auditor;
  }
  
  public void setAuditor(String paramString)
  {
    this.auditor = paramString;
  }
  
  public Date getAuditTime()
  {
    return this.auditTime;
  }
  
  public void setAuditTime(Date paramDate)
  {
    this.auditTime = paramDate;
  }
  
  public long getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(long paramLong)
  {
    this.breedId = paramLong;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
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
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
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
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public String getRejectedReasons()
  {
    return this.rejectedReasons;
  }
  
  public void setRejectedReasons(String paramString)
  {
    this.rejectedReasons = paramString;
  }
}
