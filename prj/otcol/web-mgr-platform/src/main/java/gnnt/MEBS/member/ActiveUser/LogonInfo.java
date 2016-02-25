package gnnt.MEBS.member.ActiveUser;

import java.io.Serializable;

public class LogonInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String userId;
  public long auSessionId;
  public String password;
  public String pwd;
  public String ckey;
  public String skey;
  public String enableKey;
  public String logonIP;
  public int errSum;
  public String msg;
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public String getPwd()
  {
    return this.pwd;
  }
  
  public void setPwd(String paramString)
  {
    this.pwd = paramString;
  }
  
  public String getCkey()
  {
    return this.ckey;
  }
  
  public void setCkey(String paramString)
  {
    this.ckey = paramString;
  }
  
  public String getSkey()
  {
    return this.skey;
  }
  
  public void setSkey(String paramString)
  {
    this.skey = paramString;
  }
  
  public String getEnableKey()
  {
    return this.enableKey;
  }
  
  public void setEnableKey(String paramString)
  {
    this.enableKey = paramString;
  }
  
  public String getLogonIP()
  {
    return this.logonIP;
  }
  
  public void setLogonIP(String paramString)
  {
    this.logonIP = paramString;
  }
  
  public long getAuSessionId()
  {
    return this.auSessionId;
  }
  
  public void setAuSessionId(long paramLong)
  {
    this.auSessionId = paramLong;
  }
  
  public int getErrSum()
  {
    return this.errSum;
  }
  
  public void setErrSum(int paramInt)
  {
    this.errSum = paramInt;
  }
  
  public String getMsg()
  {
    return this.msg;
  }
  
  public void setMsg(String paramString)
  {
    this.msg = paramString;
  }
}
