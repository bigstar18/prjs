package gnnt.MEBS.common.security.handler;

import gnnt.MEBS.common.model.User;
import gnnt.MEBS.member.ActiveUser.ActiveUserManager;

public class AuHandle
  extends AbstractSecurityHandler
{
  private SecurityHandler seventhSecurityHandler;
  
  public void setSeventhSecurityHandler(SecurityHandler paramSecurityHandler)
  {
    this.seventhSecurityHandler = paramSecurityHandler;
  }
  
  public int handleRequest(String paramString, User paramUser)
  {
    int i = -99;
    ActiveUserManager localActiveUserManager = new ActiveUserManager();
    if (localActiveUserManager.getUserID(paramUser.getSessionId()) != null) {
      i = toNextHandle(paramUser, paramString, this.seventhSecurityHandler);
    }
    return i;
  }
}
