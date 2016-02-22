package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class SpecialMemberOrdersReport
  extends Clone
{
  private Date clearDate;
  private String s_memberNo;
  private String s_memberName;
  private String commodityId;
  private String commodityName;
  private Double fundsum;
  private Integer qtysum;
  private Double endcapital;
  private Double delayfee;
  private Double closepl;
  private Double holdpl;
  private Double totalpl;
  
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
  
  public Double getFundsum()
  {
    return this.fundsum;
  }
  
  public void setFundsum(Double fundsum)
  {
    this.fundsum = fundsum;
  }
  
  public Integer getQtysum()
  {
    return this.qtysum;
  }
  
  public void setQtysum(Integer qtysum)
  {
    this.qtysum = qtysum;
  }
  
  public Double getEndcapital()
  {
    return this.endcapital;
  }
  
  public void setEndcapital(Double endcapital)
  {
    this.endcapital = endcapital;
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
  
  public Double getTotalpl()
  {
    return this.totalpl;
  }
  
  public void setTotalpl(Double totalpl)
  {
    this.totalpl = totalpl;
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
