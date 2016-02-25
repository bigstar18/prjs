package gnnt.MEBS.common.security.handler;

import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.memoryModel.RightMemory;
import gnnt.MEBS.common.security.util.LikeHashMap;

public class NoWildcardHandler
  extends AbstractSecurityHandler
{
  private SecurityHandler fourthSecurityHandler;
  
  public void setFourthSecurityHandler(SecurityHandler paramSecurityHandler)
  {
    this.fourthSecurityHandler = paramSecurityHandler;
  }
  
  public int handleRequest(String paramString, User paramUser)
  {
    int i = -1;
    LikeHashMap localLikeHashMap = (LikeHashMap)RightMemory.getNOWILDCARDRIGHTMAP();
    long l = localLikeHashMap.checkRight(paramString, false);
    if (l >= 0L) {
      i = checkUser(paramUser, l);
    }
    if (i < 0) {
      i = toNextHandle(paramUser, paramString, this.fourthSecurityHandler);
    }
    return i;
  }
}
