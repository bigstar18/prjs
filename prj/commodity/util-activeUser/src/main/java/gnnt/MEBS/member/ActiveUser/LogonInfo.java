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
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getPwd()
  {
    return this.pwd;
  }
  
  public void setPwd(String pwd)
  {
    this.pwd = pwd;
  }
  
  public String getCkey()
  {
    return this.ckey;
  }
  
  public void setCkey(String ckey)
  {
    this.ckey = ckey;
  }
  
  public String getSkey()
  {
    return this.skey;
  }
  
  public void setSkey(String skey)
  {
    this.skey = skey;
  }
  
  public String getEnableKey()
  {
    return this.enableKey;
  }
  
  public void setEnableKey(String enableKey)
  {
    this.enableKey = enableKey;
  }
  
  public String getLogonIP()
  {
    return this.logonIP;
  }
  
  public void setLogonIP(String logonIP)
  {
    this.logonIP = logonIP;
  }
  
  public long getAuSessionId()
  {
    return this.auSessionId;
  }
  
  public void setAuSessionId(long auSessionId)
  {
    this.auSessionId = auSessionId;
  }
  
  public int getErrSum()
  {
    return this.errSum;
  }
  
  public void setErrSum(int errSum)
  {
    this.errSum = errSum;
  }
  
  public String getMsg()
  {
    return this.msg;
  }
  
  public void setMsg(String msg)
  {
    this.msg = msg;
  }
}
