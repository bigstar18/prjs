package gnnt.MEBS.common.security.strategy;

import gnnt.MEBS.common.model.User;
import java.util.Map;

public class VerifyImpl
  implements Verify
{
  public int checkUserRight(User paramUser, long paramLong)
  {
    int i = -99;
    if (paramUser != null)
    {
      i = -1;
      Map localMap = paramUser.getRightMap();
      if ((localMap != null) && (localMap.containsKey(Long.valueOf(paramLong)))) {
        i = 0;
      }
    }
    return i;
  }
}
