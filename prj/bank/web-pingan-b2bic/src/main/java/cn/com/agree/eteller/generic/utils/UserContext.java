package cn.com.agree.eteller.generic.utils;

import cn.com.agree.eteller.generic.vo.LoginUser;
import java.util.LinkedList;
import java.util.List;

public class UserContext
{
  private static final int SINGLE_LOGIN;
  private static final int LOGOUT_TIME;
  
  static
  {
    ConfigUtil conf = new ConfigUtil("config/init.properties");
    SINGLE_LOGIN = conf.getInt("singleLogin");
    LOGOUT_TIME = conf.getInt("loginOut");
  }
  
  private static List<LoginUser> ctx = new LinkedList();
  
  public static boolean hasUser(LoginUser user)
  {
    return ctx.contains(user);
  }
  
  public static void addUser(LoginUser user)
  {
    ctx.add(user);
  }
  
  public static void removeUser(LoginUser user)
  {
    ctx.remove(user);
  }
  
  public static boolean isSingleLogin()
  {
    return SINGLE_LOGIN == 1;
  }
  
  public static boolean isTime2Logout(LoginUser user)
  {
    if (user == null) {
      return false;
    }
    return getTimeDefference(user) / 60 >= LOGOUT_TIME;
  }
  
  public static int getTimeDefference(LoginUser user)
  {
    return (int)((System.currentTimeMillis() - user.getLoginTime()) / 1000L);
  }
}
