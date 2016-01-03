package gnnt.mebsv.hqservice.model;

import java.io.Serializable;

public class User
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049817L;
  private String UserID;
  private String UserName;
  private String Password;
  private String Status;
  private String Jurisdiction;

  public String getUserID()
  {
    return this.UserID;
  }

  public void setUserID(String paramString)
  {
    this.UserID = paramString;
  }

  public String getUserName()
  {
    return this.UserName;
  }

  public void setUserName(String paramString)
  {
    this.UserName = paramString;
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

  public String getJurisdiction()
  {
    return this.Jurisdiction;
  }

  public void setJurisdiction(String paramString)
  {
    this.Jurisdiction = paramString;
  }

  public static long getSerialversionuid()
  {
    return 3690197650654049817L;
  }
}