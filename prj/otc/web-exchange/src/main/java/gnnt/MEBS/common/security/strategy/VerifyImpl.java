package gnnt.MEBS.common.security.strategy;

import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.User;
import java.util.Map;

public class VerifyImpl
  implements Verify
{
  public int checkUserRight(User user, long rightId)
  {
    int sign = -99;
    if (user != null)
    {
      sign = -1;
      Map<Long, Right> rightMap = user.getRightMap();
      if ((rightMap != null) && 
        (rightMap.containsKey(Long.valueOf(rightId)))) {
        sign = 0;
      }
    }
    return sign;
  }
}
