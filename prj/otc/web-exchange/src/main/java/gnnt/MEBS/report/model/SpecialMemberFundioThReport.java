package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class SpecialMemberFundioThReport
  extends Clone
{
  private Date clearDate;
  private String s_memberNo;
  private String s_memberName;
  private Double endcapital;
  private Double minriskfund;
  
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
