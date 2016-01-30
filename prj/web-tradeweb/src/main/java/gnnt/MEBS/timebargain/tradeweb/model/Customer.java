package gnnt.MEBS.timebargain.tradeweb.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Customer
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049811L;
  private String CustomerID;
  private Long GroupID;
  private String Password;
  private String CustomerName;
  private String Phone;
  private String Address;
  private Short Status;
  private String Note;
  private Date CreateTime;
  private Date ModifyTime;
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Customer)) {
      return false;
    }
    Customer localCustomer = (Customer)paramObject;
    return this.CustomerID != null ? this.CustomerID.equals(localCustomer.CustomerID) : localCustomer.CustomerID == null;
  }
  
  public int hashCode()
  {
    return this.CustomerID != null ? this.CustomerID.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getAddress()
  {
    return this.Address;
  }
  
  public void setAddress(String paramString)
  {
    this.Address = paramString;
  }
  
  public Date getCreateTime()
  {
    return this.CreateTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.CreateTime = paramDate;
  }
  
  public String getCustomerID()
  {
    return this.CustomerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.CustomerID = paramString;
  }
  
  public String getCustomerName()
  {
    return this.CustomerName;
  }
  
  public void setCustomerName(String paramString)
  {
    this.CustomerName = paramString;
  }
  
  public Long getGroupID()
  {
    return this.GroupID;
  }
  
  public void setGroupID(Long paramLong)
  {
    this.GroupID = paramLong;
  }
  
  public Date getModifyTime()
  {
    return this.ModifyTime;
  }
  
  public void setModifyTime(Date paramDate)
  {
    this.ModifyTime = paramDate;
  }
  
  public String getNote()
  {
    return this.Note;
  }
  
  public void setNote(String paramString)
  {
    this.Note = paramString;
  }
  
  public String getPassword()
  {
    return this.Password;
  }
  
  public void setPassword(String paramString)
  {
    this.Password = paramString;
  }
  
  public String getPhone()
  {
    return this.Phone;
  }
  
  public void setPhone(String paramString)
  {
    this.Phone = paramString;
  }
  
  public Short getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(Short paramShort)
  {
    this.Status = paramShort;
  }
}
