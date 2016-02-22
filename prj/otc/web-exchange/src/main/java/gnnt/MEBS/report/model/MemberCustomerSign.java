package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class MemberCustomerSign
  extends Clone
{
  private Date clearDate;
  private String memberNo;
  private String memberName;
  private String contact;
  private String bankCode;
  private String bankName;
  private Integer signcount;
  private Integer totalsigncount;
  private Integer designcount;
  private Integer totaldesigncount;
  
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
  
  public String getContact()
  {
    return this.contact;
  }
  
  public void setContact(String contact)
  {
    this.contact = contact;
  }
  
  public String getBankCode()
  {
    return this.bankCode;
  }
  
  public void setBankCode(String bankCode)
  {
    this.bankCode = bankCode;
  }
  
  public String getBankName()
  {
    return this.bankName;
  }
  
  public void setBankName(String bankName)
  {
    this.bankName = bankName;
  }
  
  public Integer getSigncount()
  {
    return this.signcount;
  }
  
  public void setSigncount(Integer signcount)
  {
    this.signcount = signcount;
  }
  
  public Integer getTotalsigncount()
  {
    return this.totalsigncount;
  }
  
  public void setTotalsigncount(Integer totalsigncount)
  {
    this.totalsigncount = totalsigncount;
  }
  
  public Integer getDesigncount()
  {
    return this.designcount;
  }
  
  public void setDesigncount(Integer designcount)
  {
    this.designcount = designcount;
  }
  
  public Integer getTotaldesigncount()
  {
    return this.totaldesigncount;
  }
  
  public void setTotaldesigncount(Integer totaldesigncount)
  {
    this.totaldesigncount = totaldesigncount;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
