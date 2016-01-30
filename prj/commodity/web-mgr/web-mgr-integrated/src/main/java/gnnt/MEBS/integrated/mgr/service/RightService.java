package gnnt.MEBS.integrated.mgr.service;

import gnnt.MEBS.common.mgr.model.Right;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("rightService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class RightService
  extends StandardService
{
  public void saveUserRights(User paramUser, String[] paramArrayOfString)
  {
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
    {
      HashSet localHashSet = new HashSet();
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        Right localRight = new Right();
        localRight.setId(Long.valueOf(Long.parseLong(paramArrayOfString[i])));
        localRight = (Right)get(localRight);
        if (localRight != null) {
          localHashSet.add(localRight);
        }
      }
      paramUser.setRightSet(localHashSet);
    }
    else
    {
      paramUser.setRightSet(null);
    }
    update(paramUser);
  }
}
