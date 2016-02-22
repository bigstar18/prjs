package gnnt.MEBS.common.security.handler;

import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.memoryModel.RightMemory;
import gnnt.MEBS.common.security.util.LikeHashMap;

public class NoWildcardHandler
  extends AbstractSecurityHandler
{
  private SecurityHandler fourthSecurityHandler;
  
  public void setFourthSecurityHandler(SecurityHandler fourthSecurityHandler)
  {
    this.fourthSecurityHandler = fourthSecurityHandler;
  }
  
  public int handleRequest(String key, User user)
  {
    int result = -1;
    LikeHashMap likeHashMap = (LikeHashMap)
      RightMemory.getNOWILDCARDRIGHTMAP();
    long rightId = likeHashMap.checkRight(key, false);
    if (rightId >= 0L) {
      result = checkUser(user, rightId);
    }
    if (result < 0) {
      result = toNextHandle(user, key, this.fourthSecurityHandler);
    }
    return result;
  }
}
