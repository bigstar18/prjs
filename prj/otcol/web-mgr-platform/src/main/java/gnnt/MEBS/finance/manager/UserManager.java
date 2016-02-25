package gnnt.MEBS.finance.manager;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.finance.base.util.SysData;
import gnnt.MEBS.finance.dao.UserDao;
import gnnt.MEBS.finance.unit.User;
import java.util.List;

public class UserManager
{
  public static void createUser(User paramUser)
  {
    UserDao localUserDao = (UserDao)SysData.getBean("userDao");
    localUserDao.createUser(paramUser);
  }
  
  public static void updateUser(User paramUser)
  {
    UserDao localUserDao = (UserDao)SysData.getBean("userDao");
    localUserDao.updateUser(paramUser);
  }
  
  public static void updateUserWithAuthorities(User paramUser)
  {
    UserDao localUserDao = (UserDao)SysData.getBean("userDao");
    localUserDao.updateUserWithAuthorities(paramUser);
  }
  
  public static void deleteUser(String paramString)
  {
    UserDao localUserDao = (UserDao)SysData.getBean("userDao");
    localUserDao.deleteUser(paramString);
  }
  
  public static User getUserById(String paramString)
  {
    UserDao localUserDao = (UserDao)SysData.getBean("userDao");
    return localUserDao.getUserById(paramString);
  }
  
  public static List getUsers(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    UserDao localUserDao = (UserDao)SysData.getBean("userDao");
    return localUserDao.getUsers(paramQueryConditions, paramPageInfo);
  }
}
