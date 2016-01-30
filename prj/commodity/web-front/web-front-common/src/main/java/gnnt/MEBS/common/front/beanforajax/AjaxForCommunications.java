package gnnt.MEBS.common.front.beanforajax;

import gnnt.MEBS.common.front.common.ActiveUserManager;
import gnnt.MEBS.common.front.common.Global;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.service.StandardService;
import gnnt.MEBS.common.front.statictools.Tools;
import gnnt.MEBS.logonService.vo.CheckUserResultVO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("com_ajaxForCommunications")
@Scope("request")
public class AjaxForCommunications
  extends BaseAjax
{
  public String changeStyle()
  {
    HttpServletRequest localHttpServletRequest = getRequest();
    User localUser1 = (User)localHttpServletRequest.getSession().getAttribute("CurrentUser");
    if (localUser1 != null)
    {
      User localUser2 = (User)getService().get(localUser1);
      if ((localUser2 != null) && (localUser2.getSkin() != null)) {
        localUser1.setSkin(localUser2.getSkin());
      }
      localHttpServletRequest.getSession().setAttribute("CurrentUser", localUser1);
    }
    this.jsonValidateReturn = createJSONArray(new Object[] { Boolean.valueOf(true) });
    return result();
  }
  
  public String logout()
  {
    try
    {
      ActiveUserManager.logoff(getRequest());
    }
    catch (Exception localException) {}
    this.jsonValidateReturn = createJSONArray(new Object[] { Boolean.valueOf(true) });
    return result();
  }
  
  public String sessionGetUser()
  {
    HttpServletRequest localHttpServletRequest = getRequest();
    User localUser = (User)localHttpServletRequest.getSession().getAttribute("CurrentUser");
    if (localUser == null)
    {
      this.jsonValidateReturn = createJSONArray(new Object[] { Boolean.valueOf(false), "用户信息为空，并且未重新加载" });
      this.logger.error("用户信息为空，并且未重新加载");
    }
    else
    {
      int i = Tools.strToInt(localHttpServletRequest.getParameter("FromModuleID"), -1);
      if (i > 0)
      {
        String str1 = localHttpServletRequest.getParameter("FromLogonType");
        String str2 = localHttpServletRequest.getParameter("LogonType");
        if ((str2 == null) || (str2.trim().length() <= 0)) {
          str2 = Global.getSelfLogonType();
        }
        CheckUserResultVO localCheckUserResultVO = ActiveUserManager.checkUser(localUser.getTraderID(), localUser.getSessionId().longValue(), i, str2, str1, localUser.getModuleID().intValue());
        if (localCheckUserResultVO.getUserManageVO() != null)
        {
          this.jsonValidateReturn = createJSONArray(new Object[] { Boolean.valueOf(true) });
          return result();
        }
      }
      this.jsonValidateReturn = createJSONArray(new Object[] { Boolean.valueOf(false), "来源au编号或者登录类型为空" });
      this.logger.error("来源au编号或者登录类型为空");
    }
    return result();
  }
}
