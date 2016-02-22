package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.math.BigDecimal;

public class SpecialMemberFundSearch
  extends Clone
{
  private String s_memberNo;
  private String s_memberName;
  private Double beginningCaptical;
  private Double runtimeFundio;
  private Double runtimeClosepl;
  private Double floatingLoss;
  private Double presentCaptical;
  private Double riskRate;
  private String status;
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getS_memberNo()
  {
    return this.s_memberNo;
  }
  
  public void setS_memberNo(String s_memberNo)
  {
    this.s_memberNo = s_memberNo;
  }
  
  public String getS_memberName()
  {
    return this.s_memberName;
  }
  
  public void setS_memberName(String s_memberName)
  {
    this.s_memberName = s_memberName;
  }
  
  public Double getBeginningCaptical()
  {
    return this.beginningCaptical;
  }
  
  public void setBeginningCaptical(Double beginningCaptical)
  {
    this.beginningCaptical = beginningCaptical;
  }
  
  public Double getRuntimeFundio()
  {
    return this.runtimeFundio;
  }
  
  public void setRuntimeFundio(Double runtimeFundio)
  {
    this.runtimeFundio = runtimeFundio;
  }
  
  public Double getRuntimeClosepl()
  {
    return this.runtimeClosepl;
  }
  
  public void setRuntimeClosepl(Double runtimeClosepl)
  {
    this.runtimeClosepl = runtimeClosepl;
  }
  
  public Double getFloatingLoss()
  {
    return this.floatingLoss;
  }
  
  public void setFloatingLoss(Double floatingLoss)
  {
    this.floatingLoss = floatingLoss;
  }
  
  public Double getPresentCaptical()
  {
    return this.presentCaptical;
  }
  
  public void setPresentCaptical(Double presentCaptical)
  {
    this.presentCaptical = presentCaptical;
  }
  
  public Double getRiskRate()
  {
    return this.riskRate;
  }
  
  public void setRiskRate(Double riskRate)
  {
    this.riskRate = riskRate;
  }
  
  public Double getRiskRate_v()
  {
    BigDecimal b = BigDecimal.valueOf(this.riskRate.doubleValue());
    return Double.valueOf(formatDecimals(b.multiply(new BigDecimal(100)), 2).doubleValue());
  }
  
  public String getRiskRate_log()
  {
    BigDecimal b = BigDecimal.valueOf(this.riskRate.doubleValue());
    String rate = "";
    if ("F".equals(this.status)) {
      rate = "--";
    } else if (this.riskRate.doubleValue() >= 2.0D) {
      rate = "安全";
    } else {
      rate = formatDecimals(b.multiply(new BigDecimal(100)), 2) + "%";
    }
    return rate;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
