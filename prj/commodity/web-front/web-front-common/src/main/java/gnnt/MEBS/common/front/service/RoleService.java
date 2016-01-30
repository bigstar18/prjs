package gnnt.MEBS.common.front.service;

import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.dao.StandardDao;
import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.front.Right;
import gnnt.MEBS.common.front.model.front.Role;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("com_roleService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class RoleService
  extends StandardService
{
  public Role getRoleById(long paramLong, boolean paramBoolean)
  {
    Role localRole = new Role();
    localRole.setId(Long.valueOf(paramLong));
    localRole = (Role)getDao().get(localRole);
    if (paramBoolean) {
      localRole.getRightSet().size();
    }
    return localRole;
  }
  
  public void saveRoleRights(Role paramRole, String[] paramArrayOfString)
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
      paramRole.setRightSet(localHashSet);
    }
    else
    {
      paramRole.setRightSet(null);
    }
    update(paramRole);
  }
  
  public Set<Role> getAllRole()
  {
    HashSet localHashSet = new HashSet();
    PageRequest localPageRequest = new PageRequest(" ");
    localPageRequest.setPageSize(100000);
    Page localPage = getPage(localPageRequest, new Role());
    List localList = localPage.getResult();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      StandardModel localStandardModel = (StandardModel)localIterator.next();
      localHashSet.add((Role)localStandardModel);
    }
    return localHashSet;
  }
}
