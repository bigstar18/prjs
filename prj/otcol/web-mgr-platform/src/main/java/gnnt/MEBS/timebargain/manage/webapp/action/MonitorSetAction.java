package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.timebargain.manage.model.MonitorSet;
import gnnt.MEBS.timebargain.manage.webapp.form.MonitorSetForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MonitorSetAction
  extends BaseAction
{
  public ActionForward edit(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'edit' method");
    }
    MonitorSetForm localMonitorSetForm = (MonitorSetForm)paramActionForm;
    localMonitorSetForm.setPageSize(MonitorSet.getInstance().getPageSize());
    localMonitorSetForm.setRefreshTime(MonitorSet.getInstance().getRefreshTime());
    updateFormBean(paramActionMapping, paramHttpServletRequest, localMonitorSetForm);
    return paramActionMapping.findForward("edit");
  }
  
  public ActionForward save(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'save' method");
    }
    MonitorSetForm localMonitorSetForm = (MonitorSetForm)paramActionForm;
    try
    {
      int i = localMonitorSetForm.getPageSize();
      int j = localMonitorSetForm.getRefreshTime();
      if ((i > 0) && (j >= 3))
      {
        MonitorSet.getInstance().setPageSize(i);
        MonitorSet.getInstance().setRefreshTime(j);
        addSysLog(paramHttpServletRequest, "修改风险监控刷新时间[" + localMonitorSetForm.getRefreshTime() + "]");
        paramHttpServletRequest.setAttribute("prompt", "操作成功！");
      }
      else
      {
        paramHttpServletRequest.setAttribute("prompt", "输入的参数不正确");
      }
    }
    catch (Exception localException)
    {
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return paramActionMapping.findForward("edit");
    }
    return edit(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return edit(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
}
