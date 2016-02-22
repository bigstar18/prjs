package gnnt.MEBS.common.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.member.ActiveUser.MD5;

public class UserForPassword
  extends Clone
{
  private String userId;
  private String name;
  private String password;
  private String memberNo;
  private String old;
  
  public void setOld(String old)
  {
    this.old = old;
  }
  
  public String getOld()
  {
    return this.old;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  @ClassDiscription(name="管理员Id", key=true, keyWord=true)
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
  
  @ClassDiscription(name="密码")
  public String getPassword_log()
  {
    return MD5.getMD5("****", this.password);
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  @ClassDiscription(name="管理员名称", keyWord=true)
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
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
