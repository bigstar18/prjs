package gnnt.MEBS.broke.model;

import gnnt.MEBS.base.model.Clone;

public class BrokerageVO
  extends Clone
{
  private String brokerageNo;
  private String password;
  private String name;
  private String telephone;
  private String mobile;
  private String memberNo;
  private String email;
  private String address;
  private String note;
  private String orgNo;
  private String orgName;
  
  public String getBrokerageNo()
  {
    return this.brokerageNo;
  }
  
  public BrokerageVO() {}
  
  public BrokerageVO(String brokerageNo, String name, String memberNo, String orgNo)
  {
    this.brokerageNo = brokerageNo;
    this.name = name;
    this.memberNo = memberNo;
    this.orgNo = orgNo;
  }
  
  public void setBrokerageNo(String brokerageNo)
  {
    this.brokerageNo = brokerageNo;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getTelephone()
  {
    return this.telephone;
  }
  
  public void setTelephone(String telephone)
  {
    this.telephone = telephone;
  }
  
  public String getMobile()
  {
    return this.mobile;
  }
  
  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
  
  public String getOrgNo()
  {
    return this.orgNo;
  }
  
  public void setOrgNo(String orgNo)
  {
    this.orgNo = orgNo;
  }
  
  public String getOrgName()
  {
    return this.orgName;
  }
  
  public void setOrgName(String orgName)
  {
    this.orgName = orgName;
  }
  
  public String getId()
  {
    return this.brokerageNo;
  }
  
  public void setPrimary(String arg0)
  {
    this.brokerageNo = arg0;
  }
}
