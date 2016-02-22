package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class MemberHoldReport
  extends Clone
{
  private Date clearDate;
  private String memberNo;
  private String memberName;
  private String commodityId;
  private String commodityName;
  private Integer customerbuyqty;
  private Integer customersellqty;
  private Integer customernetqty;
  private Integer memberbuyqty;
  private Integer membersellqty;
  private Double membernetqty;
  private Double netqty;
  private Double maxnethold;
  private Double customerdelayfee;
  private Double memberdelayfee;
  private Double mktdelayfee;
  private Double delayfee;
  private Double capital;
  
  public Double getNetqty()
  {
    return this.netqty;
  }
  
  public void setNetqty(Double netqty)
  {
    this.netqty = netqty;
  }
  
  public Double getMktdelayfee()
  {
    return this.mktdelayfee;
  }
  
  public void setMktdelayfee(Double mktdelayfee)
  {
    this.mktdelayfee = mktdelayfee;
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
  
  public Integer getCustomerbuyqty()
  {
    return this.customerbuyqty;
  }
  
  public void setCustomerbuyqty(Integer customerbuyqty)
  {
    this.customerbuyqty = customerbuyqty;
  }
  
  public Integer getCustomersellqty()
  {
    return this.customersellqty;
  }
  
  public void setCustomersellqty(Integer customersellqty)
  {
    this.customersellqty = customersellqty;
  }
  
  public Integer getCustomernetqty()
  {
    return this.customernetqty;
  }
  
  public void setCustomernetqty(Integer customernetqty)
  {
    this.customernetqty = customernetqty;
  }
  
  public Integer getMemberbuyqty()
  {
    return this.memberbuyqty;
  }
  
  public void setMemberbuyqty(Integer memberbuyqty)
  {
    this.memberbuyqty = memberbuyqty;
  }
  
  public Integer getMembersellqty()
  {
    return this.membersellqty;
  }
  
  public void setMembersellqty(Integer membersellqty)
  {
    this.membersellqty = membersellqty;
  }
  
  public Double getMembernetqty()
  {
    return this.membernetqty;
  }
  
  public void setMembernetqty(Double membernetqty)
  {
    this.membernetqty = membernetqty;
  }
  
  public Double getMaxnethold()
  {
    return this.maxnethold;
  }
  
  public void setMaxnethold(Double maxnethold)
  {
    this.maxnethold = maxnethold;
  }
  
  public Double getCustomerdelayfee()
  {
    return this.customerdelayfee;
  }
  
  public void setCustomerdelayfee(Double customerdelayfee)
  {
    this.customerdelayfee = customerdelayfee;
  }
  
  public Double getMemberdelayfee()
  {
    return this.memberdelayfee;
  }
  
  public void setMemberdelayfee(Double memberdelayfee)
  {
    this.memberdelayfee = memberdelayfee;
  }
  
  public Double getDelayfee()
  {
    return this.delayfee;
  }
  
  public void setDelayfee(Double delayfee)
  {
    this.delayfee = delayfee;
  }
  
  public Double getCapital()
  {
    return this.capital;
  }
  
  public void setCapital(Double capital)
  {
    this.capital = capital;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
