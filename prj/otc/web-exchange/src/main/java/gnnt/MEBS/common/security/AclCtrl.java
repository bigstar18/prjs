package gnnt.MEBS.common.security;

import gnnt.MEBS.base.util.SpringContextHelper;
import gnnt.MEBS.common.model.Menu;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.memoryModel.RightMemory;
import gnnt.MEBS.common.service.MenuService;
import gnnt.MEBS.member.ActiveUser.ActiveUserManager;
import java.util.Map;
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
  
  public static String getLogonID(ServletRequest req)
  {
    String sessionId = (String)((HttpServletRequest)req).getSession().getAttribute("LOGINIDS");
    ActiveUserManager au = new ActiveUserManager();
    long s = -1L;
    try
    {
      s = Long.parseLong(sessionId);
    }
    catch (Exception localException) {}
    String userID = au.getUserID(s);
    return userID;
  }
  
  public static User getUser(ServletRequest req)
  {
    User user = (User)((HttpServletRequest)req).getSession().getAttribute("CURRENUSER");
    return user;
  }
  
  public void init()
    throws ServletException
  {
    super.init();
    try
    {
      RightMemory.init();
      MenuService menuService = (MenuService)SpringContextHelper.getBean("menuService");
      ROOTMENU = menuService.getMenuById(0L);
      Map<String, Integer> sessionMap = (Map)SpringContextHelper.getBean("sessionMap");
      Integer auTime = (Integer)sessionMap.get("au");
      au = new ActiveUserManager(30, auTime.intValue(), 1);
    }
    catch (Exception e)
    {
      ActiveUserManager au;
      e.printStackTrace();
    }
  }
}
