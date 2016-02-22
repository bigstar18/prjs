package gnnt.MEBS.common.service;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.common.dao.RightDao;
import gnnt.MEBS.common.dao.RoleDao;
import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("roleService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class RoleService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(RoleService.class);
  @Autowired
  @Qualifier("roleDao")
  private RoleDao roleDao;
  @Autowired
  @Qualifier("rightDao")
  private RightDao rightDao;
  
  public BaseDao getDao()
  {
    return this.roleDao;
  }
  
  public boolean addRole(Role role)
  {
    if (this.roleDao.getById(role.getId()) == null)
    {
      this.roleDao.add(role);
      return true;
    }
    return false;
  }
  
  public Role loadRoleById(long id, boolean rightSign, boolean userSign)
  {
    Role role = (Role)this.roleDao.getById(Long.valueOf(id));
    if (rightSign) {
      role.toRightSetIterator();
    }
    if (userSign) {
      role.toUserSetIterator();
    }
    return role;
  }
  
  public List<Role> findRoleByProperty(String propertyName, Object value)
  {
    return this.roleDao.findRoleByProperty(propertyName, value);
  }
  
  public int saveUserRights(String roleId, String[] rightIds)
  {
    int result = 1;
    Role role = (Role)this.roleDao.getById(Long.valueOf(Long.parseLong(roleId)));
    if ((rightIds != null) && (rightIds.length > 0))
    {
      Set<Right> rightSet = new HashSet();
      for (int i = 0; i < rightIds.length; i++)
      {
        Right right = (Right)this.rightDao.getById(Long.valueOf(Long.parseLong(rightIds[i])));
        rightSet.add(right);
      }
      role.setRightSet(rightSet);
    }
    else
    {
      role.setRightSet(null);
    }
    this.roleDao.update(role);
    return result;
  }
  
  public int removeRoleAndUserRelated(long roleId, String[] userIds)
  {
    int result = 1;
    Role role = loadRoleById(roleId, true, true);
    for (String userId : userIds)
    {
      User u = new User();
      u.setUserId(userId);
      if (role.getUserSet() != null)
      {
        Set<User> userSet = role.getUserSet();
        if (userSet.contains(u)) {
          userSet.remove(u);
        }
      }
    }
    this.roleDao.update(role);
    return result;
  }
  
  public int saveAssociation(long roleId, String[] userIds)
  {
    int result = 1;
    Role role = loadRoleById(roleId, true, true);
    Set<User> userSet = role.getUserSet();
    if (userSet == null) {
      userSet = new HashSet();
    }
    if (userIds.length > 0)
    {
      for (int i = 0; i < userIds.length; i++)
      {
        User u = new User();
        u.setUserId(userIds[i]);
        userSet.add(u);
      }
      role.setUserSet(userSet);
      this.roleDao.update(role);
    }
    return result;
  }
  
  public int copy(Clone obj, Object attribute)
  {
    int num = 0;
    Role role = (Role)this.roleDao.getById(Long.valueOf(Long.parseLong((String)attribute)));
    Set<Right> set = role.getRightSet();
    Set<Right> copySet = new HashSet();
    copySet.addAll(set);
    ((Role)obj).setRightSet(copySet);
    Clone clone = (Clone)obj.clone();
    this.roleDao.add(clone);
    obj.setPrimary(clone.getId().toString());
    num = 3;
    return num;
  }
}
