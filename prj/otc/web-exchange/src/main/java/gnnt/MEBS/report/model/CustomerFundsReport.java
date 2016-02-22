package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CustomerFundsReport
  extends Clone
{
  private Date clearDate;
  private String memberNo;
  private String memberName;
  private String brokerNo;
  private String brokerName;
  private String organizationNo;
  private String organizationName;
  private String customerNo;
  private String customerName;
  private Double begincapital;
  private Double fundio;
  private Double closepl;
  private Double holdpl;
  private Double plsum;
  private Double mktfee;
  private Double customerfee;
  private Double memberfee;
  private Double delayfee;
  private Double mktdelayfee;
  private Double memberdelayfee;
  private Double margin;
  private Double endcapital;
  private Double risk;
  private String status;
  
  public Double getMktdelayfee()
  {
    return this.mktdelayfee;
  }
  
  public void setMktdelayfee(Double mktdelayfee)
  {
    this.mktdelayfee = mktdelayfee;
  }
  
  public Double getMemberdelayfee()
  {
    return this.memberdelayfee;
  }
  
  public void setMemberdelayfee(Double memberdelayfee)
  {
    this.memberdelayfee = memberdelayfee;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getBrokerNo()
  {
    return this.brokerNo;
  }
  
  public void setBrokerNo(String brokerNo)
  {
    this.brokerNo = brokerNo;
  }
  
  public String getBrokerName()
  {
    return this.brokerName;
  }
  
  public void setBrokerName(String brokerName)
  {
    this.brokerName = brokerName;
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
  
  public String getOrganizationNo()
  {
    return this.organizationNo;
  }
  
  public void setOrganizationNo(String organizationNo)
  {
    this.organizationNo = organizationNo;
  }
  
  public String getOrganizationName()
  {
    return this.organizationName;
  }
  
  public void setOrganizationName(String organizationName)
  {
    this.organizationName = organizationName;
  }
  
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String customerNo)
  {
    this.customerNo = customerNo;
  }
  
  public String getCustomerName()
  {
    return this.customerName;
  }
  
  public void setCustomerName(String customerName)
  {
    this.customerName = customerName;
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
  
  public Double getDelayfee()
  {
    return this.delayfee;
  }
  
  public void setDelayfee(Double delayfee)
  {
    this.delayfee = delayfee;
  }
  
  public Double getMargin()
  {
    return this.margin;
  }
  
  public void setMargin(Double margin)
  {
    this.margin = margin;
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
  
  public Double getMemberfee()
  {
    return this.memberfee;
  }
  
  public void setMemberfee(Double memberfee)
  {
    this.memberfee = memberfee;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
