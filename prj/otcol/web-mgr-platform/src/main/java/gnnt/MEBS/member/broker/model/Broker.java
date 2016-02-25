package gnnt.MEBS.member.broker.model;

import java.util.Date;

public class Broker
  extends Cloneable
{
  private String brokerid;
  private String password;
  private String acllist;
  private String name;
  private String telephone;
  private String mobile;
  private String email;
  private String address;
  private String note;
  private String firmId;
  private Date createDate;
  private String marketManager;
  private String locationProvince;
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public String getBrokerid()
  {
    return this.brokerid;
  }
  
  public void setBrokerid(String paramString)
  {
    this.brokerid = paramString;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public String getAcllist()
  {
    return this.acllist;
  }
  
  public void setAcllist(String paramString)
  {
    this.acllist = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getTelephone()
  {
    return this.telephone;
  }
  
  public void setTelephone(String paramString)
  {
    this.telephone = paramString;
  }
  
  public String getMobile()
  {
    return this.mobile;
  }
  
  public void setMobile(String paramString)
  {
    this.mobile = paramString;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String paramString)
  {
    this.email = paramString;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String paramString)
  {
    this.address = paramString;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String paramString)
  {
    this.note = paramString;
  }
  
  public Date getCreateDate()
  {
    return this.createDate;
  }
  
  public void setCreateDate(Date paramDate)
  {
    this.createDate = paramDate;
  }
  
  public String getMarketManager()
  {
    return this.marketManager;
  }
  
  public void setMarketManager(String paramString)
  {
    this.marketManager = paramString;
  }
  
  public String getLocationProvince()
  {
    return this.locationProvince;
  }
  
  public void setLocationProvince(String paramString)
  {
    this.locationProvince = paramString;
  }
  
  public String toString()
  {
    return "[BROKER]--brokerid:" + this.brokerid + "\n" + "password:" + this.password + "\n" + "acllist:" + this.acllist + "\n" + "name:" + this.name + "\n" + "telephone:" + this.telephone + "\n" + "mobile:" + this.mobile + "\n" + "email:" + this.email + "\n" + "address:" + this.address + "\n" + "note:" + this.note + "\n" + "firmId:" + this.firmId + "\n" + "marketManager:" + this.marketManager + "\n" + "locationProvince:" + this.locationProvince + "\n";
  }
}
