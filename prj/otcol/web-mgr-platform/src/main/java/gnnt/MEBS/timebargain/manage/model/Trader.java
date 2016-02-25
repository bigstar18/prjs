package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Trader
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049821L;
  private String TraderID;
  private String Name;
  private String Password;
  private Short Status;
  private String FirmID;
  private String OperateCode;
  private String RegWord;
  private Date CreateTime;
  private Date ModifyTime;
  private String Code;
  private Long GroupID;
  private String FirmName;
  private String keyCode;
  private Short keyStatus;
  private String permission;
  private Short id;
  private String typeID;
  private Short privilegeCode_B;
  private Short privilegeCode_S;
  private Short kind;
  private String kindID;
  private Short type;
  
  public Short getType()
  {
    return this.type;
  }
  
  public void setType(Short paramShort)
  {
    this.type = paramShort;
  }
  
  public String getKeyCode()
  {
    return this.keyCode;
  }
  
  public void setKeyCode(String paramString)
  {
    this.keyCode = paramString;
  }
  
  public Short getKeyStatus()
  {
    return this.keyStatus;
  }
  
  public void setKeyStatus(Short paramShort)
  {
    this.keyStatus = paramShort;
  }
  
  public String getFirmName()
  {
    return this.FirmName;
  }
  
  public void setFirmName(String paramString)
  {
    this.FirmName = paramString;
  }
  
  public Long getGroupID()
  {
    return this.GroupID;
  }
  
  public void setGroupID(Long paramLong)
  {
    this.GroupID = paramLong;
  }
  
  public String getCode()
  {
    return this.Code;
  }
  
  public void setCode(String paramString)
  {
    this.Code = paramString;
  }
  
  public Date getCreateTime()
  {
    return this.CreateTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.CreateTime = paramDate;
  }
  
  public String getFirmID()
  {
    return this.FirmID;
  }
  
  public void setFirmID(String paramString)
  {
    this.FirmID = paramString;
  }
  
  public Date getModifyTime()
  {
    return this.ModifyTime;
  }
  
  public void setModifyTime(Date paramDate)
  {
    this.ModifyTime = paramDate;
  }
  
  public String getOperateCode()
  {
    return this.OperateCode;
  }
  
  public void setOperateCode(String paramString)
  {
    this.OperateCode = paramString;
  }
  
  public String getRegWord()
  {
    return this.RegWord;
  }
  
  public void setRegWord(String paramString)
  {
    this.RegWord = paramString;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Trader)) {
      return false;
    }
    Trader localTrader = (Trader)paramObject;
    return this.TraderID != null ? this.TraderID.equals(localTrader.TraderID) : localTrader.TraderID == null;
  }
  
  public int hashCode()
  {
    return this.TraderID != null ? this.TraderID.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getName()
  {
    return this.Name;
  }
  
  public void setName(String paramString)
  {
    this.Name = paramString;
  }
  
  public String getPassword()
  {
    return this.Password;
  }
  
  public void setPassword(String paramString)
  {
    this.Password = paramString;
  }
  
  public Short getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(Short paramShort)
  {
    this.Status = paramShort;
  }
  
  public String getTraderID()
  {
    return this.TraderID;
  }
  
  public void setTraderID(String paramString)
  {
    this.TraderID = paramString;
  }
  
  public String getPermission()
  {
    return this.permission;
  }
  
  public void setPermission(String paramString)
  {
    this.permission = paramString;
  }
  
  public Short getPrivilegeCode_B()
  {
    return this.privilegeCode_B;
  }
  
  public void setPrivilegeCode_B(Short paramShort)
  {
    this.privilegeCode_B = paramShort;
  }
  
  public Short getId()
  {
    return this.id;
  }
  
  public void setId(Short paramShort)
  {
    this.id = paramShort;
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
}
