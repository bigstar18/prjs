package cn.com.agree.eteller.usermanager.dao;

import cn.com.agree.eteller.generic.vo.DataTree;
import cn.com.agree.eteller.generic.vo.LoginUser;
import cn.com.agree.eteller.usermanager.persistence.Userlist;

public abstract interface IUserDao
{
  public abstract void updateLoginUser(Object paramObject);
  
  public abstract void addUser(Object paramObject);
  
  public abstract String generateUserId(String paramString);
  
  public abstract Userlist[] getAllUser();
  
  public abstract Userlist[] getAllUserFormDB();
  
  public abstract void login(LoginUser paramLoginUser)
    throws Exception;
  
  public abstract DataTree getFunctionTree(String paramString)
    throws Exception;
  
  public abstract DataTree getFunctionTreeByLevel(String paramString);
  
  public abstract boolean resetPassword(Userlist paramUserlist, String paramString);
  
  public abstract Userlist getUser(String paramString);
  
  public abstract Userlist[] getUserBydptid(String paramString);
  
  public abstract String getMaxUserId();
  
  public abstract Userlist getUserlistBysql(String paramString);
  
  public abstract Userlist getUserByName(String paramString)
    throws Exception;
  
  public abstract Userlist[] getUserlistBysql2(String paramString);
}
