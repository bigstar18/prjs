package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MemberFundsReport
  extends Clone
{
  private Date clearDate;
  private String memberNo;
  private String memberName;
  private Double begincapital;
  private Double fundio;
  private Double mktfee;
  private Double customerfee;
  private Double memberfee;
  private Double customercloseplsum;
  private Double memberclosepl;
  private Double closeplsum;
  private Double customerholdpl;
  private Double memberholdpl;
  private Double holdplsum;
  private Double customerdelayfee;
  private Double smemberdelayfee;
  private Double mktdelayfee;
  private Double delayfeesum;
  private Double endcapital;
  private Double risk;
  private Double total_begincapital;
  private Double total_fundio;
  private Double total_endcapital;
  private String status;
  private Double total_margin;
  private Double total_livemargin;
  
  public Double getMktdelayfee()
  {
    return this.mktdelayfee;
  }
  
  public void setMktdelayfee(Double mktdelayfee)
  {
    this.mktdelayfee = mktdelayfee;
  }
  
  public Double getTotal_margin()
  {
    return this.total_margin;
  }
  
  public void setTotal_margin(Double total_margin)
  {
    this.total_margin = total_margin;
  }
  
  public Double getTotal_livemargin()
  {
    return this.total_livemargin;
  }
  
  public void setTotal_livemargin(Double total_livemargin)
  {
    this.total_livemargin = total_livemargin;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public Double getTotal_endcapital()
  {
    return this.total_endcapital;
  }
  
  public void setTotal_endcapital(Double total_endcapital)
  {
    this.total_endcapital = total_endcapital;
  }
  
  public Double getTotal_begincapital()
  {
    return this.total_begincapital;
  }
  
  public void setTotal_begincapital(Double total_begincapital)
  {
    this.total_begincapital = total_begincapital;
  }
  
  public Double getTotal_fundio()
  {
    return this.total_fundio;
  }
  
  public void setTotal_fundio(Double total_fundio)
  {
    this.total_fundio = total_fundio;
  }
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  public Double getBegincapital()
  {
    return this.begincapital;
  }
  
  public void setBegincapital(Double begincapital)
  {
    this.begincapital = begincapital;
  }
  
  public Double getFundio()
  {
    return this.fundio;
  }
  
  public void setFundio(Double fundio)
  {
    this.fundio = fundio;
  }
  
  public Double getMktfee()
  {
    return this.mktfee;
  }
  
  public void setMktfee(Double mktfee)
  {
    this.mktfee = mktfee;
  }
  
  public Double getCustomerfee()
  {
    return this.customerfee;
  }
  
  public void setCustomerfee(Double customerfee)
  {
    this.customerfee = customerfee;
  }
  
  public Double getMemberfee()
  {
    return this.memberfee;
  }
  
  public void setMemberfee(Double memberfee)
  {
    this.memberfee = memberfee;
  }
  
  public Double getCustomercloseplsum()
  {
    return this.customercloseplsum;
  }
  
  public void setCustomercloseplsum(Double customercloseplsum)
  {
    this.customercloseplsum = customercloseplsum;
  }
  
  public Double getMemberclosepl()
  {
    return this.memberclosepl;
  }
  
  public void setMemberclosepl(Double memberclosepl)
  {
    this.memberclosepl = memberclosepl;
  }
  
  public Double getCloseplsum()
  {
    return this.closeplsum;
  }
  
  public void setCloseplsum(Double closeplsum)
  {
    this.closeplsum = closeplsum;
  }
  
  public Double getCustomerholdpl()
  {
    return this.customerholdpl;
  }
  
  public void setCustomerholdpl(Double customerholdpl)
  {
    this.customerholdpl = customerholdpl;
  }
  
  public Double getMemberholdpl()
  {
    return this.memberholdpl;
  }
  
  public void setMemberholdpl(Double memberholdpl)
  {
    this.memberholdpl = memberholdpl;
  }
  
  public Double getHoldplsum()
  {
    return this.holdplsum;
  }
  
  public void setHoldplsum(Double holdplsum)
  {
    this.holdplsum = holdplsum;
  }
  
  public Double getCustomerdelayfee()
  {
    return this.customerdelayfee;
  }
  
  public void setCustomerdelayfee(Double customerdelayfee)
  {
    this.customerdelayfee = customerdelayfee;
  }
  
  public Double getSmemberdelayfee()
  {
    return this.smemberdelayfee;
  }
  
  public void setSmemberdelayfee(Double smemberdelayfee)
  {
    this.smemberdelayfee = smemberdelayfee;
  }
  
  public Double getDelayfeesum()
  {
    return this.delayfeesum;
  }
  
  public void setDelayfeesum(Double delayfeesum)
  {
    this.delayfeesum = delayfeesum;
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
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
