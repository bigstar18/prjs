package gnnt.MEBS.broke.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.member.ActiveUser.MD5;

public class Manager
  extends Clone
{
  private String managerNo;
  private String parentOrganizationNO;
  private String memberNo;
  private String password;
  private String name;
  private String telephone;
  private String mobile;
  private String email;
  private String address;
  private String note;
  private String managerNoChange;
  
  @ClassDiscription(name="会员编号")
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
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
  
  @ClassDiscription(name="名称")
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
  
  @ClassDiscription(name="手机")
  public String getMobile()
  {
    return this.mobile;
  }
  
  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }
  
  @ClassDiscription(name="电子邮件")
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
  
  @ClassDiscription(key=true, keyWord=true, name="客户代表代码")
  public String getManagerNo()
  {
    return this.managerNo;
  }
  
  public void setManagerNo(String managerNo)
  {
    this.managerNo = managerNo;
  }
  
  @ClassDiscription(name="父机构编号")
  public String getParentOrganizationNO()
  {
    return this.parentOrganizationNO;
  }
  
  public void setParentOrganizationNO(String parentOrganizationNO)
  {
    this.parentOrganizationNO = parentOrganizationNO;
  }
  
  public String getId()
  {
    return this.managerNo;
  }
  
  public void setPrimary(String arg0)
  {
    this.managerNo = arg0;
  }
  
  @ClassDiscription(name="经纪转移ID")
  public String getManagerNoChange()
  {
    return this.managerNoChange;
  }
  
  public void setManagerNoChange(String managerNoChange)
  {
    this.managerNoChange = managerNoChange;
  }
}
