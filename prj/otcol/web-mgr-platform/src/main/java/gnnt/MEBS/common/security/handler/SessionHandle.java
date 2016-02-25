package gnnt.MEBS.common.security.handler;

import gnnt.MEBS.common.model.User;

public class SessionHandle
  extends AbstractSecurityHandler
{
  private SecurityHandler thirdSecurityHandler;
  
  public void setThirdSecurityHandler(SecurityHandler paramSecurityHandler)
  {
    this.thirdSecurityHandler = paramSecurityHandler;
  }
  
  public int handleRequest(String paramString, User paramUser)
  {
    int i = -99;
    if (paramUser != null) {
      i = toNextHandle(paramUser, paramString, this.thirdSecurityHandler);
    }
    return i;
  }
}
