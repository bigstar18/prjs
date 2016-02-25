package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class ConsignerForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String ConsignerID;
  private String Name;
  private String Password;
  private String Status;
  private String OperateFirm;
  private String CreateTime;
  private String ModifyTime;
  private String permission;
  private String FirmID;
  private String crud = "";
  private String OldPassword;
  private String type;
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
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
  
  public String getPermission()
  {
    return this.permission;
  }
  
  public void setPermission(String paramString)
  {
    this.permission = paramString;
  }
  
  public String getConsignerID()
  {
    return this.ConsignerID;
  }
  
  public void setConsignerID(String paramString)
  {
    this.ConsignerID = paramString;
  }
  
  public String getCreateTime()
  {
    return this.CreateTime;
  }
  
  public void setCreateTime(String paramString)
  {
    this.CreateTime = paramString;
  }
  
  public String getModifyTime()
  {
    return this.ModifyTime;
  }
  
  public void setModifyTime(String paramString)
  {
    this.ModifyTime = paramString;
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
  
  public String getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(String paramString)
  {
    this.Status = paramString;
  }
}
