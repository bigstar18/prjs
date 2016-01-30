package gnnt.MEBS.activeUser.vo;

import java.util.Date;

public class AUCheckUserVO
  extends AUBaseVO
{
  private static final long serialVersionUID = 6049725213980514919L;
  private long sessionID;
  private String userID;
  private int toModuleID;
  private String logonType;
  private String logonIp;
  private Date logonTime = null;
  private String lastLogonTime;
  private String lastLogonIp;
  
  public AUCheckUserVO()
  {
    this.logonTime = new Date();
  }
  
  public long getSessionID()
  {
    return this.sessionID;
  }
  
  public void setSessionID(long sessionID)
  {
    this.sessionID = sessionID;
  }
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String userID)
  {
    this.userID = userID;
  }
  
  public int getToModuleID()
  {
    return this.toModuleID;
  }
  
  public void setToModuleID(int toModuleID)
  {
    this.toModuleID = toModuleID;
  }
  
  public String getLogonType()
  {
    return this.logonType;
  }
  
  public void setLogonType(String logonType)
  {
    this.logonType = logonType;
  }
  
  public void setLogonIp(String logonIp)
  {
    this.logonIp = logonIp;
  }
  
  public String getLogonIp()
  {
    return this.logonIp;
  }
  
  public Date getLogonTime()
  {
    return this.logonTime;
  }
  
  public void setLogonTime(Date logonTime)
  {
    this.logonTime = logonTime;
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
