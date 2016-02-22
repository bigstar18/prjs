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
  
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Customer)) {
      return false;
    }
    Customer m = (Customer)o;
    
    return this.CustomerID != null ? this.CustomerID.equals(m.CustomerID) : m.CustomerID == null;
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
  
  public void setAddress(String address)
  {
    this.Address = address;
  }
  
  public Date getCreateTime()
  {
    return this.CreateTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.CreateTime = createTime;
  }
  
  public String getCustomerID()
  {
    return this.CustomerID;
  }
  
  public void setCustomerID(String customerID)
  {
    this.CustomerID = customerID;
  }
  
  public String getCustomerName()
  {
    return this.CustomerName;
  }
  
  public void setCustomerName(String customerName)
  {
    this.CustomerName = customerName;
  }
  
  public Long getGroupID()
  {
    return this.GroupID;
  }
  
  public void setGroupID(Long groupID)
  {
    this.GroupID = groupID;
  }
  
  public Date getModifyTime()
  {
    return this.ModifyTime;
  }
  
  public void setModifyTime(Date modifyTime)
  {
    this.ModifyTime = modifyTime;
  }
  
  public String getNote()
  {
    return this.Note;
  }
  
  public void setNote(String note)
  {
    this.Note = note;
  }
  
  public String getPassword()
  {
    return this.Password;
  }
  
  public void setPassword(String password)
  {
    this.Password = password;
  }
  
  public String getPhone()
  {
    return this.Phone;
  }
  
  public void setPhone(String phone)
  {
    this.Phone = phone;
  }
  
  public Short getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(Short status)
  {
    this.Status = status;
  }
}
