package gnnt.MEBS.common.security.handler;

import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.memoryModel.RightMemory;
import gnnt.MEBS.common.security.util.LikeHashMap;

public class PathHandle
  extends AbstractSecurityHandler
{
  private SecurityHandler sixthSecurityHandler;
  
  public int handleRequest(String paramString, User paramUser)
  {
    int i = -1;
    LikeHashMap localLikeHashMap = (LikeHashMap)RightMemory.getNOWILDCARDRIGHTMAP();
    long l = localLikeHashMap.checkRight(paramString, false);
    if (l >= 0L) {
      i = checkUser(paramUser, l);
    }
    if (i < 0)
    {
      localLikeHashMap = (LikeHashMap)RightMemory.getWILDCARDRIGHTMAP();
      if (l < 0L) {
        l = localLikeHashMap.checkRight(paramString, true);
      }
      if (l >= 0L) {
        i = checkUser(paramUser, l);
      } else {
        i = toNextHandle(paramUser, paramString, this.sixthSecurityHandler);
      }
    }
    return i;
  }
}
