package gnnt.MEBS.member.broker.model;

public class BrokerRewardProps
  extends Cloneable
{
  private String brokerId;
  private String breedId = "-1";
  private String moduleId;
  private double rewardRate;
  private double firstPayRate;
  private double secondPayRate;
  private String autoPay;
  private int payPeriod;
  private int payPeriodDate;
  private String oprCode_T;
  
  public String getOprCode_T()
  {
    return this.oprCode_T;
  }
  
  public void setOprCode_T(String paramString)
  {
    this.oprCode_T = paramString;
  }
  
  public int getPayPeriod()
  {
    return this.payPeriod;
  }
  
  public void setPayPeriod(int paramInt)
  {
    this.payPeriod = paramInt;
  }
  
  public int getPayPeriodDate()
  {
    return this.payPeriodDate;
  }
  
  public void setPayPeriodDate(int paramInt)
  {
    this.payPeriodDate = paramInt;
  }
  
  public String getBrokerId()
  {
    return this.brokerId;
  }
  
  public void setBrokerId(String paramString)
  {
    this.brokerId = paramString;
  }
  
  public double getRewardRate()
  {
    return this.rewardRate;
  }
  
  public void setRewardRate(double paramDouble)
  {
    this.rewardRate = paramDouble;
  }
  
  public double getFirstPayRate()
  {
    return this.firstPayRate;
  }
  
  public void setFirstPayRate(double paramDouble)
  {
    this.firstPayRate = paramDouble;
  }
  
  public double getSecondPayRate()
  {
    return this.secondPayRate;
  }
  
  public void setSecondPayRate(double paramDouble)
  {
    this.secondPayRate = paramDouble;
  }
  
  public String getAutoPay()
  {
    return this.autoPay;
  }
  
  public void setAutoPay(String paramString)
  {
    this.autoPay = paramString;
  }
  
  public String getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(String paramString)
  {
    this.breedId = paramString;
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
