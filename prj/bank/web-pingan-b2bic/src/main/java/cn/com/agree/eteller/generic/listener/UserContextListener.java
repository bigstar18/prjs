package cn.com.agree.eteller.generic.listener;

import cn.com.agree.eteller.generic.utils.UserContext;
import cn.com.agree.eteller.generic.vo.LoginUser;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class UserContextListener
  implements HttpSessionListener
{
  public void sessionCreated(HttpSessionEvent event) {}
  
  public void sessionDestroyed(HttpSessionEvent event)
  {
    if (UserContext.isSingleLogin())
    {
      LoginUser user = (LoginUser)event.getSession()
        .getAttribute("user");
      UserContext.removeUser(user);
    }
  }
}
