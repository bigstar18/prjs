package gnnt.MEBS.finance.unit;

import gnnt.MEBS.base.query.Utils;

public class User
{
  private String userId;
  private String userName;
  private String password;
  private boolean enabled;
  private String[] authorities;
  
  public boolean hasAuthority(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    for (int i = 0; i < this.authorities.length; i++) {
      if (paramString.equals(this.authorities[i])) {
        return true;
      }
    }
    return false;
  }
  
  public String getAuthoritiesJion()
  {
    return Utils.join(this.authorities, ",", null);
  }
  
  public String[] getAuthorities()
  {
    return this.authorities;
  }
  
  public void setAuthorities(String[] paramArrayOfString)
  {
    this.authorities = paramArrayOfString;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String paramString)
  {
    this.userName = paramString;
  }
  
  public boolean isEnabled()
  {
    return this.enabled;
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    this.enabled = paramBoolean;
  }
  
  public String toString()
  {
    return this.userId + ":" + getAuthoritiesJion();
  }
}
