package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class MemberFundioThReport
  extends Clone
{
  private Date clearDate;
  private String memberNo;
  private String memberName;
  private String membertype;
  private Double customerendcapital;
  private Double minriskfund;
  private Double endcapital;
  private Double netfund;
  
  public Double getCustomerendcapital()
  {
    return this.customerendcapital;
  }
  
  public void setCustomerendcapital(Double customerendcapital)
  {
    this.customerendcapital = customerendcapital;
  }
  
  public Double getNetfund()
  {
    return this.netfund;
  }
  
  public void setNetfund(Double netfund)
  {
    this.netfund = netfund;
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
  
  public String getMembertype()
  {
    return this.membertype;
  }
  
  public void setMembertype(String membertype)
  {
    this.membertype = membertype;
  }
  
  public Double getEndcapital()
  {
    return this.endcapital;
  }
  
  public void setEndcapital(Double endcapital)
  {
    this.endcapital = endcapital;
  }
  
  public Double getMinriskfund()
  {
    return this.minriskfund;
  }
  
  public void setMinriskfund(Double minriskfund)
  {
    this.minriskfund = minriskfund;
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
