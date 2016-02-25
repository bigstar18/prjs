package gnnt.MEBS.common.security;

import gnnt.MEBS.common.model.Menu;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.memoryModel.RightMemory;
import gnnt.MEBS.common.services.MenuService;
import gnnt.MEBS.common.util.SysData;
import gnnt.MEBS.member.ActiveUser.ActiveUserManager;
import java.io.PrintStream;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AclCtrl
  extends HttpServlet
{
  public static Menu ROOTMENU;
  
  public void destroy()
  {
    super.destroy();
  }
  
  public static String getLogonID(ServletRequest paramServletRequest)
  {
    String str1 = (String)((HttpServletRequest)paramServletRequest).getSession().getAttribute("LOGINIDS");
    ActiveUserManager localActiveUserManager = new ActiveUserManager();
    long l = -1L;
    try
    {
      l = Long.parseLong(str1);
    }
    catch (Exception localException) {}
    String str2 = localActiveUserManager.getUserID(l);
    return str2;
  }
  
  public static User getUser(ServletRequest paramServletRequest)
  {
    User localUser = (User)((HttpServletRequest)paramServletRequest).getSession().getAttribute("CURRENUSER");
    return localUser;
  }
  
  public void init()
    throws ServletException
  {
    super.init();
    try
    {
      RightMemory.init();
      MenuService localMenuService = (MenuService)SysData.getBean("menuService");
      ROOTMENU = localMenuService.getMenuById(0L);
      ActiveUserManager localActiveUserManager = new ActiveUserManager(30, 120, 2);
      System.out.println("=============Succeed to load acl===============");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      System.out.println("=============Fail to load acl=============");
    }
  }
}
