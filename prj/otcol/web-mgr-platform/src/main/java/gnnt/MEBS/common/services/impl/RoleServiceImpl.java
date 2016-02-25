package gnnt.MEBS.common.services.impl;

import gnnt.MEBS.common.dao.RightDao;
import gnnt.MEBS.common.dao.RoleDao;
import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.util.query.PageInfo;
import gnnt.MEBS.common.util.query.QueryConditions;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleServiceImpl
{
  private RoleDao roleDao;
  private RightDao rightDao;
  
  public void setRightDao(RightDao paramRightDao)
  {
    this.rightDao = paramRightDao;
  }
  
  public void setRoleDao(RoleDao paramRoleDao)
  {
    this.roleDao = paramRoleDao;
  }
  
  public boolean addRole(Role paramRole)
  {
    if (getRoleById(paramRole.getId()) == null)
    {
      this.roleDao.addRole(paramRole);
      return true;
    }
    return false;
  }
  
  public Role getRoleById(long paramLong)
  {
    return this.roleDao.getRoleById(paramLong);
  }
  
  public void updateRole(Role paramRole)
  {
    this.roleDao.updateRole(paramRole);
  }
  
  public Role loadRoleById(long paramLong, boolean paramBoolean1, boolean paramBoolean2)
  {
    Role localRole = this.roleDao.loadRoleById(paramLong);
    if (paramBoolean1) {
      localRole.toRightSetIterator();
    }
    if (paramBoolean2) {
      localRole.toUserSetIterator();
    }
    return localRole;
  }
  
  public List<Role> findRoleByProperty(String paramString, Object paramObject)
  {
    return this.roleDao.findRoleByProperty(paramString, paramObject);
  }
  
  public List<Role> getRoleList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.roleDao.getRoleList(paramQueryConditions, paramPageInfo);
  }
  
  public void deleteRoles(String[] paramArrayOfString)
  {
    if (paramArrayOfString.length > 0) {
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        Role localRole = this.roleDao.getRoleById(Long.parseLong(paramArrayOfString[i]));
        this.roleDao.deleteRoles(localRole);
      }
    }
  }
  
  public void saveUserRights(String paramString, String[] paramArrayOfString)
  {
    Role localRole = this.roleDao.getRoleById(Long.parseLong(paramString));
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
    {
      HashSet localHashSet = new HashSet();
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        Right localRight = this.rightDao.getRightById(Long.parseLong(paramArrayOfString[i]));
        localHashSet.add(localRight);
      }
      localRole.setRightSet(localHashSet);
      this.roleDao.updateRole(localRole);
    }
  }
  
  public void removeRoleAndUserRelated(long paramLong, String[] paramArrayOfString)
  {
    Role localRole = loadRoleById(paramLong, true, true);
    for (String str : paramArrayOfString)
    {
      User localUser = new User();
      localUser.setUserId(str);
      if (localRole.getUserSet() != null)
      {
        Set localSet = localRole.getUserSet();
        if (localSet.contains(localUser)) {
          localSet.remove(localUser);
        }
      }
    }
    this.roleDao.updateRole(localRole);
  }
  
  public void saveAssociation(long paramLong, String[] paramArrayOfString)
  {
    Role localRole = loadRoleById(paramLong, true, true);
    Object localObject = localRole.getUserSet();
    if (localObject == null) {
      localObject = new HashSet();
    }
    if (paramArrayOfString.length > 0)
    {
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        User localUser = new User();
        localUser.setUserId(paramArrayOfString[i]);
        ((Set)localObject).add(localUser);
      }
      localRole.setUserSet((Set)localObject);
      this.roleDao.updateRole(localRole);
    }
  }
}
