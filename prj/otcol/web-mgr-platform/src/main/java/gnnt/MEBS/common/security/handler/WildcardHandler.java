package gnnt.MEBS.common.security.handler;

import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.memoryModel.RightMemory;
import gnnt.MEBS.common.security.util.LikeHashMap;

public class WildcardHandler
  extends AbstractSecurityHandler
{
  private SecurityHandler fifthSecurityHandler;
  
  public void setFifthSecurityHandler(SecurityHandler paramSecurityHandler)
  {
    this.fifthSecurityHandler = paramSecurityHandler;
  }
  
  public int handleRequest(String paramString, User paramUser)
  {
    int i = -1;
    LikeHashMap localLikeHashMap = (LikeHashMap)RightMemory.getWILDCARDRIGHTMAP();
    long l = localLikeHashMap.checkRight(paramString, true);
    if (l >= 0L) {
      i = checkUser(paramUser, l);
    } else {
      i = toNextHandle(paramUser, paramString, this.fifthSecurityHandler);
    }
    return i;
  }
}
