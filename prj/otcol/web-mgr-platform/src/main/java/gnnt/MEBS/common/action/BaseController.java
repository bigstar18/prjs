package gnnt.MEBS.common.action;

import gnnt.MEBS.common.dao.LogManagerDao;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.common.util.SysData;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class BaseController
  extends MultiActionController
{
  public String delNull(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    if ((paramString != null) && (paramString.equals("null"))) {
      paramString = "";
    }
    return paramString;
  }
  
  public ModelAndView getModelAndView(String paramString)
  {
    return new ModelAndView("common/users/" + paramString);
  }
  
  protected void addSysLog(HttpServletRequest paramHttpServletRequest, String paramString1, String paramString2)
  {
    LogManagerDao localLogManagerDao = (LogManagerDao)SysData.getBean("logManagerDAO");
    localLogManagerDao.addSysLog(AclCtrl.getLogonID(paramHttpServletRequest), paramString1, paramString2);
  }
}
