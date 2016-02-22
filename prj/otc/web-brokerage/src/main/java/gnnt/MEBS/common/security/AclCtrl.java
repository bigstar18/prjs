package gnnt.MEBS.common.security;

import gnnt.MEBS.member.ActiveUser.ActiveUserManager;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AclCtrl
  extends HttpServlet
{
  public static String getCurrenuserId(ServletRequest req)
  {
    String currenuserId = (String)((HttpServletRequest)req).getSession().getAttribute("CURRENUSERID");
    return currenuserId;
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
}
