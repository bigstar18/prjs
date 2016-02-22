package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SpecialMemberFundsReport
  extends Clone
{
  private Date clearDate;
  private String s_memberNo;
  private String s_memberName;
  private Double begincapital;
  private Double fundio;
  private Double delayfee;
  private Double closepl;
  private Double holdpl;
  private Double plsum;
  private Double endcapital;
  private Double risk;
  private String status;
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public Double getFundio()
  {
    return this.fundio;
  }
  
  public void setFundio(Double fundio)
  {
    this.fundio = fundio;
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
  
  public Double getBegincapital()
  {
    return this.begincapital;
  }
  
  public void setBegincapital(Double begincapital)
  {
    this.begincapital = begincapital;
  }
  
  public Double getDelayfee()
  {
    return this.delayfee;
  }
  
  public void setDelayfee(Double delayfee)
  {
    this.delayfee = delayfee;
  }
  
  public Double getClosepl()
  {
    return this.closepl;
  }
  
  public void setClosepl(Double closepl)
  {
    this.closepl = closepl;
  }
  
  public Double getHoldpl()
  {
    return this.holdpl;
  }
  
  public void setHoldpl(Double holdpl)
  {
    this.holdpl = holdpl;
  }
  
  public Double getPlsum()
  {
    return this.plsum;
  }
  
  public void setPlsum(Double plsum)
  {
    this.plsum = plsum;
  }
  
  public Double getEndcapital()
  {
    return this.endcapital;
  }
  
  public void setEndcapital(Double endcapital)
  {
    this.endcapital = endcapital;
  }
  
  public Double getRisk()
  {
    return this.risk;
  }
  
  public void setRisk(Double risk)
  {
    this.risk = risk;
  }
  
  public Double getRisk_v()
  {
    BigDecimal b = BigDecimal.valueOf(this.risk.doubleValue());
    return Double.valueOf(formatDecimals(b.multiply(new BigDecimal(100)), 2).doubleValue());
  }
  
  public String getRisk_log()
  {
    BigDecimal b = BigDecimal.valueOf(this.risk.doubleValue());
    String rate = "";
    if ("F".equals(this.status)) {
      rate = "--";
    } else if (this.risk.doubleValue() >= 2.0D) {
      rate = "安全";
    } else {
      rate = formatDecimals(b.multiply(new BigDecimal(100)), 2) + "%";
    }
    return rate;
  }
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
