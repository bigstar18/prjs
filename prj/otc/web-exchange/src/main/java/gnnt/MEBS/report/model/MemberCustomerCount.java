package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class MemberCustomerCount
  extends Clone
{
  private Date clearDate;
  private String memberNo;
  private String memberName;
  private String contact;
  private Integer createcount;
  private Integer totalcreatecount;
  private Integer demisecount;
  private Integer totaldemisecount;
  private Integer dealcount;
  private Integer totaldealcount;
  
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
  
  public Integer getCreatecount()
  {
    return this.createcount;
  }
  
  public void setCreatecount(Integer createcount)
  {
    this.createcount = createcount;
  }
  
  public Integer getTotalcreatecount()
  {
    return this.totalcreatecount;
  }
  
  public void setTotalcreatecount(Integer totalcreatecount)
  {
    this.totalcreatecount = totalcreatecount;
  }
  
  public Integer getDemisecount()
  {
    return this.demisecount;
  }
  
  public void setDemisecount(Integer demisecount)
  {
    this.demisecount = demisecount;
  }
  
  public Integer getTotaldemisecount()
  {
    return this.totaldemisecount;
  }
  
  public void setTotaldemisecount(Integer totaldemisecount)
  {
    this.totaldemisecount = totaldemisecount;
  }
  
  public Integer getDealcount()
  {
    return this.dealcount;
  }
  
  public void setDealcount(Integer dealcount)
  {
    this.dealcount = dealcount;
  }
  
  public Integer getTotaldealcount()
  {
    return this.totaldealcount;
  }
  
  public void setTotaldealcount(Integer totaldealcount)
  {
    this.totaldealcount = totaldealcount;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
