package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import gnnt.MEBS.member.ActiveUser.MD5;
import java.util.Date;

public class Customer
  extends Clone
{
  private String customerNo;
  private String name;
  private String memberNo;
  private String memberName;
  private Integer papersType;
  private String papersName;
  private String address;
  private String phone;
  private String fax;
  private String postCode;
  private String email;
  private String extendData;
  private String phonePWD;
  private String memberNoChange;
  private Date createTime;
  private String password;
  
  @ClassDiscription(name="密码")
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  @ClassDiscription(name="客户转移会员名称")
  public String getMemberNoChange()
  {
    return this.memberNoChange;
  }
  
  public void setMemberNoChange(String memberNoChange)
  {
    this.memberNoChange = memberNoChange;
  }
  
  public Customer() {}
  
  public Customer(String customerNo, String name, String memberNo, String memberName, Integer papersType, String address, String phone, String fax, String postCode, String email, String papersName, String extendData)
  {
    this.customerNo = customerNo;
    this.name = name;
    this.memberNo = memberNo;
    this.memberName = memberName;
    this.papersType = papersType;
    this.address = address;
    this.phone = phone;
    this.fax = fax;
    this.postCode = postCode;
    this.email = email;
    this.papersName = papersName;
    this.extendData = extendData;
  }
  
  @ClassDiscription(name="交易账户", key=true, keyWord=true)
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String id)
  {
    this.customerNo = id;
  }
  
  @ClassDiscription(name="客户名称")
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  @ClassDiscription(name="证件类型", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="身份证"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="护照")})
  public Integer getPapersType()
  {
    return this.papersType;
  }
  
  public void setPapersType(Integer papersType)
  {
    this.papersType = papersType;
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
  
  @ClassDiscription(name="电话")
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  
  @ClassDiscription(name="传真")
  public String getFax()
  {
    return this.fax;
  }
  
  public void setFax(String fax)
  {
    this.fax = fax;
  }
  
  @ClassDiscription(name="邮编")
  public String getPostCode()
  {
    return this.postCode;
  }
  
  public void setPostCode(String postCode)
  {
    this.postCode = postCode;
  }
  
  @ClassDiscription(name="电子邮箱")
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public String getExtendData()
  {
    return this.extendData;
  }
  
  public void setExtendData(String extendData)
  {
    this.extendData = extendData;
  }
  
  @ClassDiscription(name="会员编号", keyWord=true)
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  @ClassDiscription(name="会员名称", keyWord=false)
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  @ClassDiscription(name="证件号码")
  public String getPapersName()
  {
    return this.papersName;
  }
  
  public void setPapersName(String papersName)
  {
    this.papersName = papersName;
  }
  
  public String getPhonePWD()
  {
    return this.phonePWD;
  }
  
  public void setPhonePWD(String phonePWD)
  {
    this.phonePWD = phonePWD;
  }
  
  @ClassDiscription(name="电话密码")
  public String getPhonePWD_v()
  {
    return MD5.getMD5("****", this.phonePWD);
  }
  
  public String getId()
  {
    return this.customerNo;
  }
  
  public void setPrimary(String primary)
  {
    this.customerNo = primary;
  }
}
