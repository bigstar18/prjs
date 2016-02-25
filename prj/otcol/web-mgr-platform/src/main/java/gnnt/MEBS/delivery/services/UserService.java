package gnnt.MEBS.delivery.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.dao.UserDao;
import gnnt.MEBS.delivery.model.User;
import gnnt.MEBS.delivery.model.Warehouse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_userService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class UserService
{
  private final transient Log logger = LogFactory.getLog(UserService.class);
  @Autowired
  @Qualifier("w_userDao")
  private UserDao userDao;
  @Autowired
  @Qualifier("w_warehouseService")
  private WarehouseService warehouseService;
  
  public int addUser(User paramUser)
  {
    int i = 0;
    this.userDao.getUserLock(paramUser);
    i = checkUser(paramUser, true);
    if (i == 0) {
      this.userDao.addUser(paramUser);
    }
    return i;
  }
  
  public Map checkUser(String paramString1, String paramString2, String paramString3)
    throws SQLException
  {
    boolean bool = this.userDao.checkUser(paramString1);
    QueryConditions localQueryConditions = new QueryConditions("id", "=", paramString3);
    User localUser = null;
    Warehouse localWarehouse = this.warehouseService.getWarehouseById(paramString3, false, false);
    int i = 0;
    if (bool)
    {
      localUser = this.userDao.getUser(paramString1, paramString3);
      if ((localWarehouse != null) && (localUser != null))
      {
        if (localWarehouse.getAbility() == 1) {
          i = -1;
        } else if (!paramString2.equals(localUser.getPassword())) {
          i = -3;
        }
      }
      else {
        i = -3;
      }
    }
    else
    {
      i = -2;
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("msg", Integer.valueOf(i));
    localHashMap.put("user", localUser);
    return localHashMap;
  }
  
  public void deleteUser(String paramString)
  {
    this.userDao.deleteUser(paramString);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public User getUser(String paramString1, String paramString2)
  {
    return this.userDao.getUser(paramString1, paramString2);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List queryUsers(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.userDao.queryUsers(paramQueryConditions, paramPageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List queryPermissions(int paramInt, boolean paramBoolean, String paramString)
  {
    List localList = null;
    if ((paramBoolean) && (paramString != null) && (paramString.length() > 1)) {
      localList = this.userDao.queryChiPermissions(paramInt, paramString.substring(0, 2));
    } else {
      localList = this.userDao.queryTopPermissions(paramInt);
    }
    return localList;
  }
  
  public void updateUser(User paramUser)
  {
    this.userDao.updateUser(paramUser);
  }
  
  public int changPwd(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    User localUser = this.userDao.getUser(paramString1, paramString2);
    String str = localUser.getPassword();
    int i = 0;
    if ((paramString3 != null) && (str.equals(paramString3)))
    {
      localUser.setPassword(paramString4);
      this.userDao.updateUser(localUser);
      i = 1;
    }
    else
    {
      i = -1;
    }
    return i;
  }
  
  public int changPwd(String paramString1, String paramString2, String paramString3)
  {
    User localUser = this.userDao.getUser(paramString1, paramString2);
    int i = 0;
    if (paramString3 != null)
    {
      localUser.setPassword(paramString3);
      this.userDao.updateUser(localUser);
      i = 1;
    }
    else
    {
      i = -1;
    }
    return i;
  }
  
  public int freshPermission(List paramList, String paramString)
  {
    int i = -1;
    if (paramList != null)
    {
      String str = "";
      int j = paramList.size();
      if (j > 0)
      {
        int k = 0;
        while (k < j) {
          str = str + paramList.get(k++) + ",";
        }
        str = str.substring(0, str.length() - 1);
      }
      this.userDao.freshPermission(str, paramString);
      i = 1;
    }
    else
    {
      i = -1;
    }
    return i;
  }
  
  public User getUser(String paramString)
  {
    QueryConditions localQueryConditions = null;
    if ((paramString != null) && (!"".equals(paramString))) {
      localQueryConditions = new QueryConditions("userId", "=", paramString);
    }
    User localUser = null;
    List localList = this.userDao.getUsers(localQueryConditions);
    if (localList.size() > 0) {
      localUser = (User)localList.get(0);
    }
    return localUser;
  }
  
  public int checkUser(User paramUser, boolean paramBoolean)
  {
    int i = 0;
    List localList = null;
    QueryConditions localQueryConditions = null;
    if ((paramBoolean) && (getUser(paramUser.getUserId()) != null))
    {
      i = -1;
      return i;
    }
    if (paramBoolean)
    {
      localQueryConditions = new QueryConditions("name", "=", paramUser.getName());
    }
    else
    {
      localQueryConditions = new QueryConditions("userId", "!=", paramUser.getUserId());
      localQueryConditions.addCondition("name", "=", paramUser.getName());
    }
    localList = this.userDao.getUsers(localQueryConditions);
    if (localList.size() != 0)
    {
      i = -2;
      return i;
    }
    return i;
  }
}
