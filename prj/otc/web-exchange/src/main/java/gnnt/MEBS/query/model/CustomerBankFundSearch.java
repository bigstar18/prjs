package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class CustomerBankFundSearch
  extends Clone
{
  private String customerNo;
  private String customerName;
  private String memberNo;
  private String organizationNo;
  private String organizationName;
  private String brokerageName;
  private String brokerageNo;
  private String managerName;
  private String managerNo;
  private Date b_date;
  private String firmId;
  private String firmName;
  private String bankName;
  private String bankCode;
  private Double fundio;
  private Double capital;
  private Double closepl;
  private Double holdpl;
  private Double tradefee;
  private Double delayfee;
  private Double lastcapital;
  
  public String getOrganizationName()
  {
    return this.organizationName;
  }
  
  public void setOrganizationName(String organizationName)
  {
    this.organizationName = organizationName;
  }
  
  public String getBrokerageName()
  {
    return this.brokerageName;
  }
  
  public void setBrokerageName(String brokerageName)
  {
    this.brokerageName = brokerageName;
  }
  
  public String getManagerName()
  {
    return this.managerName;
  }
  
  public void setManagerName(String managerName)
  {
    this.managerName = managerName;
  }
  
  public String getCustomerName()
  {
    return this.customerName;
  }
  
  public void setCustomerName(String customerName)
  {
    this.customerName = customerName;
  }
  
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String customerNo)
  {
    this.customerNo = customerNo;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getOrganizationNo()
  {
    return this.organizationNo;
  }
  
  public void setOrganizationNo(String organizationNo)
  {
    this.organizationNo = organizationNo;
  }
  
  public String getBrokerageNo()
  {
    return this.brokerageNo;
  }
  
  public void setBrokerageNo(String brokerageNo)
  {
    this.brokerageNo = brokerageNo;
  }
  
  public String getManagerNo()
  {
    return this.managerNo;
  }
  
  public void setManagerNo(String managerNo)
  {
    this.managerNo = managerNo;
  }
  
  public Date getB_date()
  {
    return this.b_date;
  }
  
  public void setB_date(Date b_date)
  {
    this.b_date = b_date;
  }
  
  public String getBankName()
  {
    return this.bankName;
  }
  
  public void setBankName(String bankName)
  {
    this.bankName = bankName;
  }
  
  public String getBankCode()
  {
    return this.bankCode;
  }
  
  public void setBankCode(String bankCode)
  {
    this.bankCode = bankCode;
  }
  
  public Double getFundio()
  {
    return this.fundio;
  }
  
  public void setFundio(Double fundio)
  {
    this.fundio = fundio;
  }
  
  public Double getCapital()
  {
    return this.capital;
  }
  
  public void setCapital(Double capital)
  {
    this.capital = capital;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public String getFirmName()
  {
    return this.firmName;
  }
  
  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
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
  
  public Double getTradefee()
  {
    return this.tradefee;
  }
  
  public void setTradefee(Double tradefee)
  {
    this.tradefee = tradefee;
  }
  
  public Double getDelayfee()
  {
    return this.delayfee;
  }
  
  public void setDelayfee(Double delayfee)
  {
    this.delayfee = delayfee;
  }
  
  public Double getLastcapital()
  {
    return this.lastcapital;
  }
  
  public void setLastcapital(Double lastcapital)
  {
    this.lastcapital = lastcapital;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
