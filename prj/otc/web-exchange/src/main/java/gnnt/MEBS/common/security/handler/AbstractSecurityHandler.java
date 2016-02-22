package gnnt.MEBS.common.security.handler;

import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.strategy.Verify;

public abstract class AbstractSecurityHandler
  implements SecurityHandler
{
  protected Verify verify;
  
  public void setVerify(Verify verify)
  {
    this.verify = verify;
  }
  
  protected int checkUser(User user, long rightId)
  {
    int sign = -1;
    sign = this.verify.checkUserRight(user, rightId);
    return sign;
  }
  
  protected int toNextHandle(User user, String path, SecurityHandler securityHandler)
  {
    int result = -1;
    if (securityHandler != null) {
      result = securityHandler.handleRequest(path, user);
    } else {
      result = 100;
    }
    return result;
  }
}
