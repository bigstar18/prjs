package gnnt.MEBS.common.model;

public class OnLineUser
{
  private String userId;
  private String logonTime;
  private String logonIp;
  private long sessionId;
  
  public long getSessionId()
  {
    return this.sessionId;
  }
  
  public void setSessionId(long paramLong)
  {
    this.sessionId = paramLong;
  }
  
  public String getLogonIp()
  {
    return this.logonIp;
  }
  
  public void setLogonIp(String paramString)
  {
    this.logonIp = paramString;
  }
  
  public String getLogonTime()
  {
    return this.logonTime;
  }
  
  public void setLogonTime(String paramString)
  {
    this.logonTime = paramString;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
}
