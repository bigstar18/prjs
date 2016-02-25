package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class Firm
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmId;
  public String name;
  public String fullName;
  public int type;
  public String bank;
  public String status;
  public String bankAccount;
  public String address;
  public String contactMan;
  public String phone;
  public String fax;
  public String postCode;
  public String email;
  public Date expiryDate;
  public String date;
  public String zoneCode;
  public String industryCode;
  public String extendTata;
  public Date createTime;
  public Date modifyTime;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("firmId:" + this.firmId + sep);
    sb.append("name:" + this.name + sep);
    sb.append("fullName:" + this.fullName + sep);
    sb.append("type:" + this.type + sep);
    sb.append("bank:" + this.bank + sep);
    sb.append("status:" + this.status + sep);
    sb.append("bankAccount:" + this.bankAccount + sep);
    sb.append("address:" + this.address + sep);
    sb.append("contactMan:" + this.contactMan + sep);
    sb.append("phone:" + this.phone + sep);
    sb.append("fax:" + this.fax + sep);
    sb.append("postCode:" + this.postCode + sep);
    sb.append("email:" + this.email + sep);
    sb.append("expiryDate:" + this.expiryDate + sep);
    sb.append("date:" + this.date + sep);
    sb.append("zoneCode:" + this.zoneCode + sep);
    sb.append("industryCode:" + this.industryCode + sep);
    sb.append("extendTata:" + this.extendTata + sep);
    sb.append("createTime:" + this.createTime + sep);
    sb.append("modifyTime:" + this.modifyTime + sep);
    sb.append(sep);
    return sb.toString();
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getFullName()
  {
    return this.fullName;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public String getBank()
  {
    return this.bank;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public String getBankAccount()
  {
    return this.bankAccount;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public String getContactMan()
  {
    return this.contactMan;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public String getFax()
  {
    return this.fax;
  }
  
  public String getPostCode()
  {
    return this.postCode;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public Date getExpiryDate()
  {
    return this.expiryDate;
  }
  
  public String getDate()
  {
    return this.date;
  }
  
  public String getZoneCode()
  {
    return this.zoneCode;
  }
  
  public String getIndustryCode()
  {
    return this.industryCode;
  }
  
  public String getExtendTata()
  {
    return this.extendTata;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
}
