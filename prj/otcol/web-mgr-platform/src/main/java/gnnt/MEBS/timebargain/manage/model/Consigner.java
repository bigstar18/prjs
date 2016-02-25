package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import java.util.Date;

public class Consigner
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String ConsignerID;
  private String Name;
  private String Password;
  private Short Status;
  private String OperateFirm;
  private Date CreateTime;
  private Date ModifyTime;
  private String permission;
  private String FirmID;
  private String crud = "";
  private String OldPassword;
  private Short type;
  
  public Short getType()
  {
    return this.type;
  }
  
  public void setType(Short paramShort)
  {
    this.type = paramShort;
  }
  
  public String getOldPassword()
  {
    return this.OldPassword;
  }
  
  public void setOldPassword(String paramString)
  {
    this.OldPassword = paramString;
  }
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String paramString)
  {
    this.crud = paramString;
  }
  
  public String getFirmID()
  {
    return this.FirmID;
  }
  
  public void setFirmID(String paramString)
  {
    this.FirmID = paramString;
  }
  
  public String getConsignerID()
  {
    return this.ConsignerID;
  }
  
  public void setConsignerID(String paramString)
  {
    this.ConsignerID = paramString;
  }
  
  public Date getCreateTime()
  {
    return this.CreateTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.CreateTime = paramDate;
  }
  
  public Date getModifyTime()
  {
    return this.ModifyTime;
  }
  
  public void setModifyTime(Date paramDate)
  {
    this.ModifyTime = paramDate;
  }
  
  public String getName()
  {
    return this.Name;
  }
  
  public void setName(String paramString)
  {
    this.Name = paramString;
  }
  
  public String getOperateFirm()
  {
    return this.OperateFirm;
  }
  
  public void setOperateFirm(String paramString)
  {
    this.OperateFirm = paramString;
  }
  
  public String getPassword()
  {
    return this.Password;
  }
  
  public void setPassword(String paramString)
  {
    this.Password = paramString;
  }
  
  public String getPermission()
  {
    return this.permission;
  }
  
  public void setPermission(String paramString)
  {
    this.permission = paramString;
  }
  
  public Short getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(Short paramShort)
  {
    this.Status = paramShort;
  }
  
  public String toString()
  {
    return null;
  }
  
  public boolean equals(Object paramObject)
  {
    return false;
  }
  
  public int hashCode()
  {
    return 0;
  }
}
