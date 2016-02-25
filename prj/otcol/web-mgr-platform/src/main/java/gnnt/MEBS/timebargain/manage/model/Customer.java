package gnnt.MEBS.timebargain.manage.model;

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
  private String marketCode;
  private String FirmID;
  private Long customerCounts;
  private String FirmName;
  private String Code;
  private Long tcounts;
  private String TraderID;
  private String startCode;
  private String endCode;
  private Long ApplyID;
  private Double VirtualFunds;
  private String Creator;
  private String Remark1;
  private String Modifier;
  private String Remark2;
  private String type;
  private Date ValidDate;
  private Short id;
  private String typeID;
  private String uni_Cmdty_Code;
  private String commodityID;
  private Short privilegeCode_B;
  private Short privilegeCode_S;
  private Short typeprivilege;
  private Short kind;
  private String kindID;
  
  public Short getTypeprivilege()
  {
    return this.typeprivilege;
  }
  
  public void setTypeprivilege(Short paramShort)
  {
    this.typeprivilege = paramShort;
  }
  
  public Date getValidDate()
  {
    return this.ValidDate;
  }
  
  public void setValidDate(Date paramDate)
  {
    this.ValidDate = paramDate;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public Long getApplyID()
  {
    return this.ApplyID;
  }
  
  public void setApplyID(Long paramLong)
  {
    this.ApplyID = paramLong;
  }
  
  public String getCreator()
  {
    return this.Creator;
  }
  
  public void setCreator(String paramString)
  {
    this.Creator = paramString;
  }
  
  public String getModifier()
  {
    return this.Modifier;
  }
  
  public void setModifier(String paramString)
  {
    this.Modifier = paramString;
  }
  
  public String getRemark1()
  {
    return this.Remark1;
  }
  
  public void setRemark1(String paramString)
  {
    this.Remark1 = paramString;
  }
  
  public String getRemark2()
  {
    return this.Remark2;
  }
  
  public void setRemark2(String paramString)
  {
    this.Remark2 = paramString;
  }
  
  public Double getVirtualFunds()
  {
    return this.VirtualFunds;
  }
  
  public void setVirtualFunds(Double paramDouble)
  {
    this.VirtualFunds = paramDouble;
  }
  
  public Long getTcounts()
  {
    return this.tcounts;
  }
  
  public void setTcounts(Long paramLong)
  {
    this.tcounts = paramLong;
  }
  
  public String getTraderID()
  {
    return this.TraderID;
  }
  
  public void setTraderID(String paramString)
  {
    this.TraderID = paramString;
  }
  
  public String getCode()
  {
    return this.Code;
  }
  
  public void setCode(String paramString)
  {
    this.Code = paramString;
  }
  
  public String getFirmName()
  {
    return this.FirmName;
  }
  
  public void setFirmName(String paramString)
  {
    this.FirmName = paramString;
  }
  
  public Long getCustomerCounts()
  {
    return this.customerCounts;
  }
  
  public void setCustomerCounts(Long paramLong)
  {
    this.customerCounts = paramLong;
  }
  
  public String getFirmID()
  {
    return this.FirmID;
  }
  
  public void setFirmID(String paramString)
  {
    this.FirmID = paramString;
  }
  
  public String getMarketCode()
  {
    return this.marketCode;
  }
  
  public void setMarketCode(String paramString)
  {
    this.marketCode = paramString;
  }
  
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
  
  public String getEndCode()
  {
    return this.endCode;
  }
  
  public void setEndCode(String paramString)
  {
    this.endCode = paramString;
  }
  
  public String getStartCode()
  {
    return this.startCode;
  }
  
  public void setStartCode(String paramString)
  {
    this.startCode = paramString;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
  }
  
  public Short getId()
  {
    return this.id;
  }
  
  public void setId(Short paramShort)
  {
    this.id = paramShort;
  }
  
  public Short getPrivilegeCode_B()
  {
    return this.privilegeCode_B;
  }
  
  public void setPrivilegeCode_B(Short paramShort)
  {
    this.privilegeCode_B = paramShort;
  }
  
  public Short getPrivilegeCode_S()
  {
    return this.privilegeCode_S;
  }
  
  public void setPrivilegeCode_S(Short paramShort)
  {
    this.privilegeCode_S = paramShort;
  }
  
  public String getTypeID()
  {
    return this.typeID;
  }
  
  public void setTypeID(String paramString)
  {
    this.typeID = paramString;
  }
  
  public String getUni_Cmdty_Code()
  {
    return this.uni_Cmdty_Code;
  }
  
  public void setUni_Cmdty_Code(String paramString)
  {
    this.uni_Cmdty_Code = paramString;
  }
  
  public Short getKind()
  {
    return this.kind;
  }
  
  public void setKind(Short paramShort)
  {
    this.kind = paramShort;
  }
  
  public String getKindID()
  {
    return this.kindID;
  }
  
  public void setKindID(String paramString)
  {
    this.kindID = paramString;
  }
}
