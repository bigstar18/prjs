package gnnt.MEBS.common.service;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.common.dao.RightDao;
import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.util.IntegrateToMap;
import gnnt.MEBS.common.security.util.LikeHashMap;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("rightService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class RightService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(RightService.class);
  @Autowired
  @Qualifier("rightDao")
  private RightDao rightDao;
  
  public BaseDao getDao()
  {
    return this.rightDao;
  }
  
  public Right getRightByFilter(long id)
  {
    Right right = null;
    right = this.rightDao.getRightByFilter(id, -1, 0);
    right.toRightSetIterator();
    return right;
  }
  
  public Map<String, Right> loadSpecialRight()
  {
    QueryConditions condition = new QueryConditions();
    condition.addCondition("type", "=", Integer.valueOf(-2));
    List<Right> list = this.rightDao.getList(condition, null);
    Map<String, Right> map = null;
    if (list != null)
    {
      map = new HashMap();
      for (Right right : list) {
        if ((right.getUrl() != null) && (!"".equals(right.getUrl().trim())))
        {
          this.logger.debug("url:" + right.getUrl() + "       id: " + right.getId());
          map.put(right.getUrl(), right);
        }
      }
    }
    return map;
  }
  
  public LikeHashMap loadWildcardRight(boolean wildcardFlag)
  {
    LikeHashMap map = null;
    QueryConditions condition = new QueryConditions();
    if (wildcardFlag)
    {
      condition.addCondition("url", "allLike", "*");
    }
    else
    {
      condition.addCondition("url", "notAllLike", "*");
      condition.addCondition("type", "!=", new Integer(-2), "int");
    }
    List<Right> list = this.rightDao.getList(condition, null);
    if (list != null)
    {
      map = new LikeHashMap();
      for (Right right : list) {
        map.put(right.getUrl(), right);
      }
    }
    return map;
  }
  
  public Map loadAllRight()
  {
    Map map = null;
    List<Right> list = this.rightDao.getList(null, null);
    if (list != null)
    {
      map = new HashMap();
      for (Right right : list) {
        map.put(right.getId(), right);
      }
    }
    return map;
  }
  
  public Map<Right, Integer> compareRightsWithUser(Right sysRight, User user)
  {
    Map<Right, Integer> newMap = new HashMap();
    if (user != null)
    {
      Set<Right> userRightSet = user.getRightSet();
      Set<Role> roleSet = user.getRoleSet();
      Set<Right> roleRightSet = new HashSet();
      Iterator<Right> it;
      for (Iterator<Role> roleIt = roleSet.iterator(); roleIt.hasNext(); it
            .hasNext())
      {
        Role role = (Role)roleIt.next();
        Set<Right> belongedRoleRight = role.getRightSet();
        
        it = belongedRoleRight.iterator(); continue;
        
        Right roleRight = (Right)it.next();
        roleRightSet.add(roleRight);
      }
      newMap = IntegrateToMap.sortRight(sysRight, userRightSet, 
        roleRightSet);
    }
    return newMap;
  }
  
  public Map<Right, Integer> compareRightsWithRole(Right sysRight, Role role)
  {
    Map<Right, Integer> newMap = new HashMap();
    if (role != null)
    {
      Set<Right> roleRightSet = role.getRightSet();
      newMap = IntegrateToMap.sortRight(sysRight, null, roleRightSet);
    }
    return newMap;
  }
  
  public Right getTreeRight()
  {
    Right right = null;
    right = this.rightDao.loadTreeRight(0L, -2, 0);
    return right;
  }
}
