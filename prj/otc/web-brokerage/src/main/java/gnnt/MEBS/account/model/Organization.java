package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.member.ActiveUser.MD5;

public class Organization
  extends Clone
{
  private String organizationNO;
  private String parentOrganizationNO;
  private String memberNo;
  private String password;
  private String name;
  private String telephone;
  private String mobile;
  private String email;
  private String address;
  private String note;
  private String organizationNoChange;
  private String brokerageDesc;
  
  @ClassDiscription(name="机构所有居间人")
  public String getBrokerageDesc()
  {
    return this.brokerageDesc;
  }
  
  public void setBrokerageDesc(String brokerageDesc)
  {
    this.brokerageDesc = brokerageDesc;
  }
  
  @ClassDiscription(name="机构转移ID")
  public String getOrganizationNoChange()
  {
    return this.organizationNoChange;
  }
  
  public void setOrganizationNoChange(String organizationNoChange)
  {
    this.organizationNoChange = organizationNoChange;
  }
  
  @ClassDiscription(name="机构代码", key=true, keyWord=true)
  public String getOrganizationNO()
  {
    return this.organizationNO;
  }
  
  public void setOrganizationNO(String organizationNO)
  {
    this.organizationNO = organizationNO;
  }
  
  @ClassDiscription(name="父机构代码")
  public String getParentOrganizationNO()
  {
    return this.parentOrganizationNO;
  }
  
  public void setParentOrganizationNO(String parentOrganizationNO)
  {
    this.parentOrganizationNO = parentOrganizationNO;
  }
  
  @ClassDiscription(name="会员编号")
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  @ClassDiscription(name="密码")
  public String getPassword()
  {
    return this.password;
  }
  
  @ClassDiscription(name="密码")
  public String getPassword_log()
  {
    return MD5.getMD5("****", this.password);
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  @ClassDiscription(name="机构名称", keyWord=true)
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  @ClassDiscription(name="电话号码")
  public String getTelephone()
  {
    return this.telephone;
  }
  
  public void setTelephone(String telephone)
  {
    this.telephone = telephone;
  }
  
  @ClassDiscription(name="手机号码")
  public String getMobile()
  {
    return this.mobile;
  }
  
  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }
  
  @ClassDiscription(name="邮件地址")
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  @ClassDiscription(name="地址")
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
  
  public String getId()
  {
    return this.organizationNO;
  }
  
  public void setPrimary(String arg0)
  {
    this.organizationNO = arg0;
  }
}
