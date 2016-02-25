package gnnt.MEBS.member.broker.model;

import java.util.Date;

public class BrokerReward
  extends Cloneable
{
  public String moduleId;
  public String brokerId;
  public Date occurDate;
  public double amount;
  public double paidAmount;
  public Date payDate;
  
  public String getBrokerId()
  {
    return this.brokerId;
  }
  
  public void setBrokerId(String paramString)
  {
    this.brokerId = paramString;
  }
  
  public Date getOccurDate()
  {
    return this.occurDate;
  }
  
  public void setOccurDate(Date paramDate)
  {
    this.occurDate = paramDate;
  }
  
  public double getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(double paramDouble)
  {
    this.amount = paramDouble;
  }
  
  public double getPaidAmount()
  {
    return this.paidAmount;
  }
  
  public void setPaidAmount(double paramDouble)
  {
    this.paidAmount = paramDouble;
  }
  
  public Date getPayDate()
  {
    return this.payDate;
  }
  
  public void setPayDate(Date paramDate)
  {
    this.payDate = paramDate;
  }
  
  public String getModuleId()
  {
    return this.moduleId;
  }
  
  public void setModuleId(String paramString)
  {
    this.moduleId = paramString;
  }
}
