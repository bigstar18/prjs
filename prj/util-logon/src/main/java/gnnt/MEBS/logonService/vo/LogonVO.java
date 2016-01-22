package gnnt.MEBS.logonService.vo;

import java.util.Date;

public class LogonVO extends BaseVO
{
  private static final long serialVersionUID = 7741736096020219428L;
  private String userID;
  private int moduleID;
  private String logonType;
  private Date logonTime = null;
  private String logonIp;
  private String lastLogonTime;
  private String lastLogonIp;

  public String getUserID()
  {
    return this.userID;
  }

  public void setUserID(String paramString)
  {
    this.userID = paramString;
  }

  public int getModuleID()
  {
    return this.moduleID;
  }

  public void setModuleID(int paramInt)
  {
    this.moduleID = paramInt;
  }

  public String getLogonType()
  {
    return this.logonType;
  }

  public void setLogonType(String paramString)
  {
    this.logonType = paramString;
  }

  public Date getLogonTime()
  {
    return this.logonTime;
  }

  public void setLogonTime(Date paramDate)
  {
    this.logonTime = paramDate;
  }

  public String getLogonIp()
  {
    return this.logonIp;
  }

  public void setLogonIp(String paramString)
  {
    this.logonIp = paramString;
  }

  public String getLastLogonTime()
  {
    return this.lastLogonTime;
  }

  public void setLastLogonTime(String paramString)
  {
    this.lastLogonTime = paramString;
  }

  public String getLastLogonIp()
  {
    return this.lastLogonIp;
  }

  public void setLastLogonIp(String paramString)
  {
    this.lastLogonIp = paramString;
  }
}