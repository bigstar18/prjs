package gnnt.MEBS.common.security.handler;

import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.memoryModel.RightMemory;
import gnnt.MEBS.common.security.util.LikeHashMap;

public class WildcardHandler
  extends AbstractSecurityHandler
{
  private SecurityHandler fifthSecurityHandler;
  
  public void setFifthSecurityHandler(SecurityHandler fifthSecurityHandler)
  {
    this.fifthSecurityHandler = fifthSecurityHandler;
  }
  
  public int handleRequest(String key, User user)
  {
    int result = -1;
    LikeHashMap likeHashMap = (LikeHashMap)RightMemory.getWILDCARDRIGHTMAP();
    long rightId = likeHashMap.checkRight(key, true);
    if (rightId >= 0L) {
      result = checkUser(user, rightId);
    } else {
      result = toNextHandle(user, key, this.fifthSecurityHandler);
    }
    return result;
  }
}
