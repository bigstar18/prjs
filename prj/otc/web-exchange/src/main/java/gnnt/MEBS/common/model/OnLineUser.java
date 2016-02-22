package gnnt.MEBS.common.model;

import gnnt.MEBS.base.model.Clone;

public class OnLineUser
  extends Clone
{
  private String userId;
  private String logonTime;
  private String logonIp;
  private long sessionId;
  
  public long getSessionId()
  {
    return this.sessionId;
  }
  
  public void setSessionId(long sessionId)
  {
    this.sessionId = sessionId;
  }
  
  public String getLogonIp()
  {
    return this.logonIp;
  }
  
  public void setLogonIp(String logonIp)
  {
    this.logonIp = logonIp;
  }
  
  public String getLogonTime()
  {
    return this.logonTime;
  }
  
  public void setLogonTime(String logonTime)
  {
    this.logonTime = logonTime;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public String getId()
  {
    return this.userId;
  }
  
  public void setPrimary(String primary)
  {
    this.userId = primary;
  }
}
