package gnnt.MEBS.integrated.mgr.model.usermanage;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.io.Serializable;

public class OnlineUser
  implements Serializable
{
  private static final long serialVersionUID = -4242146522970882672L;
  @ClassDiscription(name="在线用户ID", description="Y")
  private String userId;
  @ClassDiscription(name="在线用户登录时间", description="")
  private String logonTime;
  @ClassDiscription(name="在线用户登录的IP", description="")
  private String loginIp;
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
  
  public String getLogonTime()
  {
    return this.logonTime;
  }
  
  public void setLogonTime(String paramString)
  {
    this.logonTime = paramString;
  }
  
  public String getLoginIp()
  {
    return this.loginIp;
  }
  
  public void setLoginIp(String paramString)
  {
    this.loginIp = paramString;
  }
}
