package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class CustomerOrdersReport
  extends Clone
{
  private Date clearDate;
  private String memberNo;
  private String memberName;
  private String customerNo;
  private String customerName;
  private String organizationNo;
  private String organizationName;
  private String brokerNo;
  private String brokerName;
  private String commodityId;
  private String commodityName;
  private Integer customerqtysum;
  private Double customerfundsum;
  private Double customercloseplsum;
  private Double customerholdpl;
  private Double customerplsum;
  private Double customerfee;
  private Double mktfee;
  private Double memberfee;
  
  public Double getCustomerfee()
  {
    return this.customerfee;
  }
  
  public void setCustomerfee(Double customerfee)
  {
    this.customerfee = customerfee;
  }
  
  public Double getMktfee()
  {
    return this.mktfee;
  }
  
  public void setMktfee(Double mktfee)
  {
    this.mktfee = mktfee;
  }
  
  public Double getMemberfee()
  {
    return this.memberfee;
  }
  
  public void setMemberfee(Double memberfee)
  {
    this.memberfee = memberfee;
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
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  public Integer getCustomerqtysum()
  {
    return this.customerqtysum;
  }
  
  public void setCustomerqtysum(Integer customerqtysum)
  {
    this.customerqtysum = customerqtysum;
  }
  
  public Double getCustomerfundsum()
  {
    return this.customerfundsum;
  }
  
  public void setCustomerfundsum(Double customerfundsum)
  {
    this.customerfundsum = customerfundsum;
  }
  
  public Double getCustomercloseplsum()
  {
    return this.customercloseplsum;
  }
  
  public void setCustomercloseplsum(Double customercloseplsum)
  {
    this.customercloseplsum = customercloseplsum;
  }
  
  public Double getCustomerholdpl()
  {
    return this.customerholdpl;
  }
  
  public void setCustomerholdpl(Double customerholdpl)
  {
    this.customerholdpl = customerholdpl;
  }
  
  public Double getCustomerplsum()
  {
    return this.customerplsum;
  }
  
  public void setCustomerplsum(Double customerplsum)
  {
    this.customerplsum = customerplsum;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
