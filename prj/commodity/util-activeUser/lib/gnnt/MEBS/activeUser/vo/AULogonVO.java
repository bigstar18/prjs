package gnnt.MEBS.activeUser.vo;

import java.util.Date;

public class AULogonVO
  extends AUBaseVO
{
  private static final long serialVersionUID = 7741736096020219428L;
  private String userID;
  private int moduleID;
  private String logonType;
  private Date logonTime = null;
  private String logonIp = null;
  private String lastLogonTime;
  private String lastLogonIp;
  
  public AULogonVO()
  {
    this.logonTime = new Date();
  }
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String userID)
  {
    this.userID = userID;
  }
  
  public int getModuleID()
  {
    return this.moduleID;
  }
  
  public void setModuleID(int moduleID)
  {
    this.moduleID = moduleID;
  }
  
  public String getLogonType()
  {
    return this.logonType;
  }
  
  public void setLogonType(String logonType)
  {
    this.logonType = logonType;
  }
  
  public Date getLogonTime()
  {
    return this.logonTime;
  }
  
  public void setLogonTime(Date logonTime)
  {
    this.logonTime = logonTime;
  }
  
  public String getLogonIp()
  {
    return this.logonIp;
  }
  
  public void setLogonIp(String logonIp)
  {
    this.logonIp = logonIp;
  }
  
  public String getLastLogonTime()
  {
    return this.lastLogonTime;
  }
  
  public void setLastLogonTime(String lastLogonTime)
  {
    this.lastLogonTime = lastLogonTime;
  }
  
  public String getLastLogonIp()
  {
    return this.lastLogonIp;
  }
  
  public void setLastLogonIp(String lastLogonIp)
  {
    this.lastLogonIp = lastLogonIp;
  }
}
