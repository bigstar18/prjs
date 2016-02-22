package gnnt.MEBS.common.security.handler;

import gnnt.MEBS.common.model.User;

public class SessionHandle
  extends AbstractSecurityHandler
{
  private SecurityHandler thirdSecurityHandler;
  
  public void setThirdSecurityHandler(SecurityHandler thirdSecurityHandler)
  {
    this.thirdSecurityHandler = thirdSecurityHandler;
  }
  
  public int handleRequest(String key, User user)
  {
    int result = -99;
    if (user != null) {
      result = toNextHandle(user, key, this.thirdSecurityHandler);
    }
    return result;
  }
}
