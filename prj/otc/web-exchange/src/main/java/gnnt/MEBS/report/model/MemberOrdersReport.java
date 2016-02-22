package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class MemberOrdersReport
  extends Clone
{
  private Date clearDate;
  private String memberNo;
  private String memberName;
  private String commodityId;
  private String commodityName;
  private Integer customerqtysum;
  private Integer memberqtysum;
  private Integer qtysum;
  private Double customerfundsum;
  private Double memberfundsum;
  private Double fundsum;
  private Double mktfee;
  private Double memberfee;
  private Double customerfee;
  private Double customercloseplsum;
  private Double customerholdpl;
  private Double memberclosepl;
  private Double memberholdpl;
  private Double holdplsum;
  private Double closeplsum;
  private Double netplsum;
  
  public Double getNetplsum()
  {
    return this.netplsum;
  }
  
  public void setNetplsum(Double netplsum)
  {
    this.netplsum = netplsum;
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
  
  public Integer getMemberqtysum()
  {
    return this.memberqtysum;
  }
  
  public void setMemberqtysum(Integer memberqtysum)
  {
    this.memberqtysum = memberqtysum;
  }
  
  public Integer getQtysum()
  {
    return this.qtysum;
  }
  
  public void setQtysum(Integer qtysum)
  {
    this.qtysum = qtysum;
  }
  
  public Double getCustomerfundsum()
  {
    return this.customerfundsum;
  }
  
  public void setCustomerfundsum(Double customerfundsum)
  {
    this.customerfundsum = customerfundsum;
  }
  
  public Double getMemberfundsum()
  {
    return this.memberfundsum;
  }
  
  public void setMemberfundsum(Double memberfundsum)
  {
    this.memberfundsum = memberfundsum;
  }
  
  public Double getFundsum()
  {
    return this.fundsum;
  }
  
  public void setFundsum(Double fundsum)
  {
    this.fundsum = fundsum;
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
  
  public Double getCustomerfee()
  {
    return this.customerfee;
  }
  
  public void setCustomerfee(Double customerfee)
  {
    this.customerfee = customerfee;
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
  
  public Double getMemberclosepl()
  {
    return this.memberclosepl;
  }
  
  public void setMemberclosepl(Double memberclosepl)
  {
    this.memberclosepl = memberclosepl;
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
  
  public Double getCloseplsum()
  {
    return this.closeplsum;
  }
  
  public void setCloseplsum(Double closeplsum)
  {
    this.closeplsum = closeplsum;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
