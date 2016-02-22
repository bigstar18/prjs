package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class CustomerHoldReport
  extends Clone
{
  private Date clearDate;
  private String memberNo;
  private String memberName;
  private String customerNo;
  private String customerName;
  private String commodityId;
  private String commodityName;
  private String organizationNo;
  private String organizationName;
  private String brokerNo;
  private String brokerName;
  private Integer buyqty;
  private Integer sellqty;
  private Integer qtysum;
  private Double delayfee;
  private Double mktdelayfee;
  private Double memberdelayfee;
  private Double margin;
  private Double endcapital;
  private Double floatingloss;
  
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
  
  public Integer getBuyqty()
  {
    return this.buyqty;
  }
  
  public void setBuyqty(Integer buyqty)
  {
    this.buyqty = buyqty;
  }
  
  public Integer getSellqty()
  {
    return this.sellqty;
  }
  
  public void setSellqty(Integer sellqty)
  {
    this.sellqty = sellqty;
  }
  
  public Integer getQtysum()
  {
    return this.qtysum;
  }
  
  public void setQtysum(Integer qtysum)
  {
    this.qtysum = qtysum;
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
  
  public Double getFloatingloss()
  {
    return this.floatingloss;
  }
  
  public void setFloatingloss(Double floatingloss)
  {
    this.floatingloss = floatingloss;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
