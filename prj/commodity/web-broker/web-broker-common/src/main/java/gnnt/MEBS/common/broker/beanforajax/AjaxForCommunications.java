package gnnt.MEBS.common.broker.beanforajax;

import gnnt.MEBS.common.broker.common.ActiveUserManager;
import gnnt.MEBS.common.broker.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("com_ajaxForCommunications")
@Scope("request")
public class AjaxForCommunications extends BaseAjax
{
  public String changeStyle()
  {
    return result();
  }

  public String logout()
  {
    HttpServletRequest localHttpServletRequest = getRequest();
    User localUser = (User)localHttpServletRequest.getSession().getAttribute("CurrentUser");
    if (localUser != null)
      try
      {
        ActiveUserManager.logoff(getRequest());
      }
      catch (Exception localException)
      {
      }
    localHttpServletRequest.getSession().invalidate();
    this.jsonValidateReturn = createJSONArray(new Object[] { Boolean.valueOf(true) });
    return result();
  }

  public String sessionGetUser()
  {
    HttpServletRequest localHttpServletRequest = getRequest();
    User localUser = (User)localHttpServletRequest.getSession().getAttribute("CurrentUser");
    if (localUser != null)
      this.jsonValidateReturn = createJSONArray(new Object[] { Boolean.valueOf(true) });
    else
      this.jsonValidateReturn = createJSONArray(new Object[] { Boolean.valueOf(false) });
    return result();
  }
}