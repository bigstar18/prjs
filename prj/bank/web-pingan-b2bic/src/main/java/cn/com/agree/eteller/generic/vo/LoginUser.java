package cn.com.agree.eteller.generic.vo;

import cn.com.agree.eteller.usermanager.persistence.Department;
import cn.com.agree.eteller.usermanager.persistence.Rolelist;
import java.io.Serializable;
import java.util.Set;

public class LoginUser
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String userId;
  private String username;
  private String appId;
  private String userPwd;
  private String userType;
  private String userLevel;
  private String userComp;
  private String userMail;
  private Set<String> funcs;
  private String userIp;
  private long loginTime;
  private Rolelist role;
  private Department dept;
  
  public LoginUser() {}
  
  public LoginUser(String userId, String userIp, long loginTime)
  {
    this.userId = userId;
    this.userIp = userIp;
    this.loginTime = loginTime;
  }
  
  public Rolelist getRole()
  {
    return this.role;
  }
  
  public void setRole(Rolelist role)
  {
    this.role = role;
  }
  
  public Department getDept()
  {
    return this.dept;
  }
  
  public void setDept(Department dept)
  {
    this.dept = dept;
  }
  
  public String getAppId()
  {
    return this.appId;
  }
  
  public void setAppId(String appId)
  {
    this.appId = appId;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public String getUserPwd()
  {
    return this.userPwd;
  }
  
  public void setUserPwd(String userPwd)
  {
    this.userPwd = userPwd;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public String getUserType()
  {
    return this.userType;
  }
  
  public void setUserType(String userType)
  {
    this.userType = userType;
  }
  
  public String getUserComp()
  {
    return this.userComp;
  }
  
  public void setUserComp(String userComp)
  {
    this.userComp = userComp;
  }
  
  public String getUserLevel()
  {
    return this.userLevel;
  }
  
  public void setUserLevel(String userLevel)
  {
    this.userLevel = userLevel;
  }
  
  public String getUserMail()
  {
    return this.userMail;
  }
  
  public void setUserMail(String userMail)
  {
    this.userMail = userMail;
  }
  
  public void setUserIp(String userIp)
  {
    this.userIp = userIp;
  }
  
  public String getUserIp()
  {
    return this.userIp;
  }
  
  public void setLoginTime(long loginTime)
  {
    this.loginTime = loginTime;
  }
  
  public long getLoginTime()
  {
    return this.loginTime;
  }
  
  public void setFuncs(Set<String> funcs)
  {
    this.funcs = funcs;
  }
  
  public Set<String> getFuncs()
  {
    return this.funcs;
  }
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (this.userId == null ? 0 : this.userId.hashCode());
    return result;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    LoginUser other = (LoginUser)obj;
    if (this.userId == null)
    {
      if (other.userId != null) {
        return false;
      }
    }
    else if (!this.userId.equals(other.userId)) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    return 
    



      "LoginUser [userId=" + this.userId + ", username=" + this.username + ", appId=" + this.appId + ", userPwd=" + this.userPwd + ", userType=" + this.userType + ", userLevel=" + this.userLevel + ", userComp=" + this.userComp + ", userMail=" + this.userMail + ", funcs=" + this.funcs + ", userIp=" + this.userIp + ", loginTime=" + this.loginTime + ", role=" + this.role + ", dept=" + this.dept + "]";
  }
}
