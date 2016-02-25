package gnnt.MEBS.finance.unit;

import java.sql.Timestamp;

public class Tradeuser
{
  private String firmId;
  private String name;
  private String password;
  private String fullname;
  private String bank;
  private String bankAccount;
  private String address;
  private String contactMan;
  private String phone;
  private String fax;
  private String postCode;
  private String email;
  private Timestamp createTime;
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String paramString)
  {
    this.address = paramString;
  }
  
  public String getBank()
  {
    return this.bank;
  }
  
  public void setBank(String paramString)
  {
    this.bank = paramString;
  }
  
  public String getBankAccount()
  {
    return this.bankAccount;
  }
  
  public void setBankAccount(String paramString)
  {
    this.bankAccount = paramString;
  }
  
  public String getContactMan()
  {
    return this.contactMan;
  }
  
  public void setContactMan(String paramString)
  {
    this.contactMan = paramString;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String paramString)
  {
    this.email = paramString;
  }
  
  public Timestamp getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Timestamp paramTimestamp)
  {
    this.createTime = paramTimestamp;
  }
  
  public String getFax()
  {
    return this.fax;
  }
  
  public void setFax(String paramString)
  {
    this.fax = paramString;
  }
  
  public String getFullname()
  {
    return this.fullname;
  }
  
  public void setFullname(String paramString)
  {
    this.fullname = paramString;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setPhone(String paramString)
  {
    this.phone = paramString;
  }
  
  public String getPostCode()
  {
    return this.postCode;
  }
  
  public void setPostCode(String paramString)
  {
    this.postCode = paramString;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
}
