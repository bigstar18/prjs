package gnnt.MEBS.common.security.handler;

import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.memoryModel.RightMemory;
import java.util.Map;

public class SpecialHandle
  extends AbstractSecurityHandler
{
  private SecurityHandler secondSecurityHandler;
  
  public void setSecondSecurityHandler(SecurityHandler paramSecurityHandler)
  {
    this.secondSecurityHandler = paramSecurityHandler;
  }
  
  public int handleRequest(String paramString, User paramUser)
  {
    int i = -1;
    Map localMap = RightMemory.getSPECIALRIGHTMAP();
    Right localRight = (Right)localMap.get(paramString);
    if (localRight != null) {
      i = 1;
    } else {
      i = toNextHandle(paramUser, paramString, this.secondSecurityHandler);
    }
    return i;
  }
}
