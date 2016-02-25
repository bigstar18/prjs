package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.timebargain.manage.service.StatuQueryAndUpdate;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class QuerySettingAction
  extends BaseAction
{
  public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.log.debug("Entering 'unspecified' method");
    StatuQueryAndUpdate localStatuQueryAndUpdate = (StatuQueryAndUpdate)getBean("statuQueryAndUpdate");
    boolean bool = localStatuQueryAndUpdate.getStatus();
    paramHttpServletRequest.setAttribute("statusResult", Boolean.valueOf(bool));
    paramHttpServletRequest.setAttribute("now", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    return paramActionMapping.findForward("showStatus");
  }
  
  public ActionForward setStatus(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.log.debug("Entering 'setStatus' method");
    String str1 = paramHttpServletRequest.getParameter("statRadio");
    this.log.debug("statRadio:" + str1);
    StatuQueryAndUpdate localStatuQueryAndUpdate = (StatuQueryAndUpdate)getBean("statuQueryAndUpdate");
    String str2 = "";
    try
    {
      localStatuQueryAndUpdate.updateStatus(str1);
      str2 = "修改成功！";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str2 = "修改失败！异常信息" + localException.getMessage();
    }
    this.log.debug("handleResult:" + str2);
    paramHttpServletRequest.setAttribute("handleResult", str2);
    return unspecified(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
}
