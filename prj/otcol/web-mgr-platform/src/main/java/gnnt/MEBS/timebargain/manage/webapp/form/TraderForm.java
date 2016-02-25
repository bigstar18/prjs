package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;
import java.util.Date;

public class TraderForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049821L;
  private String TraderID;
  private String Name;
  private String Password;
  private String ConfirmPassword;
  private Short Status;
  private String OldPassword;
  private String FirmID;
  private String OperateCode;
  private String RegWord;
  private Date CreateTime;
  private Date ModifyTime;
  private String Code;
  private String GroupID;
  private String FirmName;
  private String keyCode;
  private Short keyStatus;
  private String permission;
  private String id;
  private String typeID;
  private String privilegeCode_B;
  private String privilegeCode_S;
  private String kind;
  private String kindID;
  private String type;
  private String crud = "";
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String paramString)
  {
    this.crud = paramString;
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
  
  public String getOldPassword()
  {
    return this.OldPassword;
  }
  
  public void setOldPassword(String paramString)
  {
    this.OldPassword = paramString;
  }
  
  public String getConfirmPassword()
  {
    return this.ConfirmPassword;
  }
  
  public void setConfirmPassword(String paramString)
  {
    this.ConfirmPassword = paramString;
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
  
  public String getFirmName()
  {
    return this.FirmName;
  }
  
  public void setFirmName(String paramString)
  {
    this.FirmName = paramString;
  }
  
  public String getGroupID()
  {
    return this.GroupID;
  }
  
  public void setGroupID(String paramString)
  {
    this.GroupID = paramString;
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
  
  public String getPermission()
  {
    return this.permission;
  }
  
  public void setPermission(String paramString)
  {
    this.permission = paramString;
  }
  
  public String getPrivilegeCode_B()
  {
    return this.privilegeCode_B;
  }
  
  public void setPrivilegeCode_B(String paramString)
  {
    this.privilegeCode_B = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getKind()
  {
    return this.kind;
  }
  
  public void setKind(String paramString)
  {
    this.kind = paramString;
  }
  
  public String getKindID()
  {
    return this.kindID;
  }
  
  public void setKindID(String paramString)
  {
    this.kindID = paramString;
  }
  
  public String getPrivilegeCode_S()
  {
    return this.privilegeCode_S;
  }
  
  public void setPrivilegeCode_S(String paramString)
  {
    this.privilegeCode_S = paramString;
  }
  
  public String getTypeID()
  {
    return this.typeID;
  }
  
  public void setTypeID(String paramString)
  {
    this.typeID = paramString;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
}
