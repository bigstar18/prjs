package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;

public class MemberUser
  extends Clone
{
  private String userId;
  private String name;
  private String password;
  private String description;
  private String skin = "default";
  private String keyCode;
  private Long sessionId;
  private String memberId;
  
  public String getMemberId()
  {
    return this.memberId;
  }
  
  public void setMemberId(String memberId)
  {
    this.memberId = memberId;
  }
  
  public Long getSessionId()
  {
    return this.sessionId;
  }
  
  public void setSessionId(long sessionId)
  {
    this.sessionId = Long.valueOf(sessionId);
  }
  
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
  
  @ClassDiscription(keyWord=true, name="会员管理员名称")
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  @ClassDiscription(name="会员管理员描述")
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  public String getSkin()
  {
    return this.skin;
  }
  
  public void setSkin(String skin)
  {
    this.skin = skin;
  }
  
  public String getKeyCode()
  {
    return this.keyCode;
  }
  
  public void setKeyCode(String keyCode)
  {
    this.keyCode = keyCode;
  }
  
  public boolean equals(Object o)
  {
    boolean sign = true;
    MemberUser u = this;
    if ((o instanceof MemberUser))
    {
      MemberUser u1 = (MemberUser)o;
      if (!u1.getUserId().equals(u.getUserId())) {
        sign = false;
      }
    }
    else
    {
      sign = false;
    }
    return sign;
  }
  
  public int hashCode()
  {
    return this.userId != null ? this.userId.hashCode() : 0;
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
