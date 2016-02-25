package gnnt.MEBS.common.security.handler;

import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.strategy.Verify;

public abstract class AbstractSecurityHandler
  implements SecurityHandler
{
  protected Verify verify;
  
  public void setVerify(Verify paramVerify)
  {
    this.verify = paramVerify;
  }
  
  protected int checkUser(User paramUser, long paramLong)
  {
    int i = -1;
    i = this.verify.checkUserRight(paramUser, paramLong);
    return i;
  }
  
  protected int toNextHandle(User paramUser, String paramString, SecurityHandler paramSecurityHandler)
  {
    int i = -1;
    if (paramSecurityHandler != null) {
      i = paramSecurityHandler.handleRequest(paramString, paramUser);
    } else {
      i = 100;
    }
    return i;
  }
}
