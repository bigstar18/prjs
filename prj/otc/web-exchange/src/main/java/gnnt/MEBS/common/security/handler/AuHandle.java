package gnnt.MEBS.common.security.handler;

import gnnt.MEBS.common.model.User;
import gnnt.MEBS.member.ActiveUser.ActiveUserManager;

public class AuHandle
  extends AbstractSecurityHandler
{
  private SecurityHandler seventhSecurityHandler;
  
  public void setSeventhSecurityHandler(SecurityHandler seventhSecurityHandler)
  {
    this.seventhSecurityHandler = seventhSecurityHandler;
  }
  
  public int handleRequest(String key, User user)
  {
    int result = -99;
    ActiveUserManager au = new ActiveUserManager();
    if (au.getUserID(user.getSessionId()) != null) {
      result = toNextHandle(user, key, this.seventhSecurityHandler);
    }
    return result;
  }
}
