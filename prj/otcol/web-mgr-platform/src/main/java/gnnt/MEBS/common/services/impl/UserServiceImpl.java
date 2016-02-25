package gnnt.MEBS.common.services.impl;

import gnnt.MEBS.common.dao.OutSideDao;
import gnnt.MEBS.common.dao.RightDao;
import gnnt.MEBS.common.dao.UserDao;
import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.util.query.PageInfo;
import gnnt.MEBS.common.util.query.QueryConditions;
import gnnt.MEBS.member.ActiveUser.MD5;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class UserServiceImpl
{
  private UserDao userDao;
  private RightDao rightDao;
  private OutSideDao outSideDao;
  
  public void setOutSideDao(OutSideDao paramOutSideDao)
  {
    this.outSideDao = paramOutSideDao;
  }
  
  public void setRightDao(RightDao paramRightDao)
  {
    this.rightDao = paramRightDao;
  }
  
  public void setUserDao(UserDao paramUserDao)
  {
    this.userDao = paramUserDao;
  }
  
  public boolean addUser(User paramUser)
  {
    if (getUserById(paramUser.getUserId()) == null)
    {
      paramUser.setPassword(MD5.getMD5(paramUser.getUserId(), paramUser.getPassword()));
      this.userDao.addUser(paramUser);
      return true;
    }
    return false;
  }
  
  public User getUserById(String paramString)
  {
    return this.userDao.getUserById(paramString);
  }
  
  public void updateUser(User paramUser)
  {
    this.userDao.updateUser(paramUser);
  }
  
  public String authenticateUser(User paramUser, String paramString1, String paramString2)
  {
    String str = null;
    int i = 1;
    if ((paramString1 != null) && (!paramString1.equals(paramString2)))
    {
      str = "验证码错误,请重新登录!";
      i = 0;
    }
    if (i != 0)
    {
      User localUser = this.userDao.getUserById(paramUser.getUserId());
      if (localUser == null)
      {
        str = "用户不存在,请重新登录!";
        i = 0;
      }
      else
      {
        if (!localUser.getPassword().equals(MD5.getMD5(paramUser.getUserId(), paramUser.getPassword())))
        {
          str = "密码错误,请重新登录!";
          i = 0;
        }
        if ((!"0123456789ABCDE".equals(localUser.getKeyCode())) && (!localUser.getKeyCode().equals(paramUser.getKeyCode())))
        {
          str = "key盘身份验证失败,请重新登录!";
          i = 0;
        }
        if (i != 0) {
          str = localUser.getSkin();
        }
      }
    }
    return str;
  }
  
  public List<User> getUserList(QueryConditions paramQueryConditions, PageInfo paramPageInfo, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    List localList = this.userDao.getUserList(paramQueryConditions, paramPageInfo);
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      User localUser = (User)localIterator.next();
      if (paramBoolean1) {
        localUser.toRightSetIterator();
      }
      if (paramBoolean2) {
        localUser.toRoleSetIterator(paramBoolean3);
      }
    }
    return localList;
  }
  
  public User loadUserById(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    User localUser = this.userDao.loadUserById(paramString);
    if (paramBoolean1) {
      localUser.toRightSetIterator();
    }
    if (paramBoolean2) {
      localUser.toRoleSetIterator(paramBoolean3);
    }
    return localUser;
  }
  
  public List<User> findRoleByProperty(String paramString, Object paramObject)
  {
    return this.userDao.findRoleByProperty(paramString, paramObject);
  }
  
  public void deleteUsers(String[] paramArrayOfString)
  {
    if (paramArrayOfString.length > 0) {
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        User localUser = this.userDao.getUserById(paramArrayOfString[i]);
        this.userDao.deleteUsers(localUser);
      }
    }
  }
  
  public void saveUserRights(String paramString, String[] paramArrayOfString)
  {
    User localUser = this.userDao.getUserById(paramString);
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
    {
      HashSet localHashSet = new HashSet();
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        Right localRight = this.rightDao.getRightById(Long.parseLong(paramArrayOfString[i]));
        localHashSet.add(localRight);
      }
      localUser.setRightSet(localHashSet);
      this.userDao.updateUser(localUser);
    }
  }
  
  public List<User> getUserNoRoleById(long paramLong)
  {
    String str = "select distinct user from User user,Role role where user not in elements(role.userSet) and role.id=" + paramLong;
    List localList = this.outSideDao.getList(str);
    return localList;
  }
}
