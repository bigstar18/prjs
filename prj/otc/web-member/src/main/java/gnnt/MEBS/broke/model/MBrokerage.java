package gnnt.MEBS.broke.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.member.ActiveUser.MD5;
import java.util.Iterator;
import java.util.Set;

public class MBrokerage
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
  private String brokerageNoChange;
  private Set<Organization> organizationSet;
  
  public Set<Organization> getOrganizationSet()
  {
    return this.organizationSet;
  }
  
  public void setOrganizationSet(Set<Organization> organizationSet)
  {
    this.organizationSet = organizationSet;
  }
  
  @ClassDiscription(name="居间转移Id")
  public String getBrokerageNoChange()
  {
    return this.brokerageNoChange;
  }
  
  public void setBrokerageNoChange(String brokerageNoChange)
  {
    this.brokerageNoChange = brokerageNoChange;
  }
  
  @ClassDiscription(name="居间代码", key=true, keyWord=true)
  public String getBrokerageNo()
  {
    return this.brokerageNo;
  }
  
  public void setBrokerageNo(String brokerageNo)
  {
    this.brokerageNo = brokerageNo;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  @ClassDiscription(name="密码")
  public String getPassword_log()
  {
    if ((this.password == null) || ("".equals(this.password))) {
      return null;
    }
    return MD5.getMD5("****", this.password);
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  @ClassDiscription(name="名称", key=true, keyWord=true)
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  @ClassDiscription(name="电话")
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
  
  @ClassDiscription(name="会员编号")
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getId()
  {
    return this.brokerageNo;
  }
  
  public void setPrimary(String arg0)
  {
    this.brokerageNo = arg0;
  }
  
  @ClassDiscription(name="上级机构Id")
  public String getOrganizationNO()
  {
    String organizationNO = "";
    if (this.organizationSet != null)
    {
      Iterator it = this.organizationSet.iterator();
      if (it.hasNext())
      {
        Organization organization = (Organization)it.next();
        organizationNO = organization.getOrganizationNO();
      }
    }
    return organizationNO;
  }
  
  public String getOrganizationName()
  {
    String organizationName = "";
    if (this.organizationSet != null)
    {
      Iterator it = this.organizationSet.iterator();
      if (it.hasNext())
      {
        Organization organization = (Organization)it.next();
        organizationName = organization.getName();
      }
    }
    return organizationName;
  }
}
