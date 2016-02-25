package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.timebargain.manage.model.Broadcast;
import gnnt.MEBS.timebargain.manage.service.BroadcastManager;
import gnnt.MEBS.timebargain.manage.util.FormatPunctuation;
import gnnt.MEBS.timebargain.manage.webapp.form.BroadcastForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.dao.DataIntegrityViolationException;

public class BroadcastAction
  extends BaseAction
{
  public ActionForward edit(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'edit' method");
    }
    BroadcastForm localBroadcastForm = (BroadcastForm)paramActionForm;
    String str1 = localBroadcastForm.getCrud();
    BroadcastManager localBroadcastManager = (BroadcastManager)getBean("broadcastManager");
    Broadcast localBroadcast = null;
    try
    {
      if (!str1.trim().equals("create"))
      {
        localBroadcast = localBroadcastManager.getBroadcastById(Long.valueOf(localBroadcastForm.getId()));
        localBroadcast.setContent(localBroadcast.getContent());
      }
      else
      {
        localBroadcast = new Broadcast();
      }
      String str2 = "";
      if ((localBroadcast.getSendTime() != null) && (!"".equals(localBroadcast.getSendTime()))) {
        str2 = localBroadcast.getSendTime().substring(localBroadcast.getSendTime().length() - 10, localBroadcast.getSendTime().length() - 2);
      }
      localBroadcastForm = (BroadcastForm)convert(localBroadcast);
      localBroadcastForm.setCrud(str1);
      localBroadcastForm.setSendTime(str2);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localBroadcastForm);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    return paramActionMapping.findForward("edit");
  }
  
  public ActionForward editWait(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editWait' method");
    }
    saveToken(paramHttpServletRequest);
    BroadcastForm localBroadcastForm = (BroadcastForm)paramActionForm;
    String str1 = localBroadcastForm.getCrud();
    BroadcastManager localBroadcastManager = (BroadcastManager)getBean("broadcastManager");
    Broadcast localBroadcast = null;
    String str2 = "";
    try
    {
      if (!str1.trim().equals("create"))
      {
        localBroadcast = localBroadcastManager.getBroadcastWaitById(Long.valueOf(localBroadcastForm.getId()));
        localBroadcast.setContent(localBroadcast.getContent());
        if ((localBroadcast.getSendTime() != null) && (!"".equals(localBroadcast.getSendTime()))) {
          str2 = localBroadcast.getSendTime().substring(localBroadcast.getSendTime().length() - 10, localBroadcast.getSendTime().length() - 2);
        }
      }
      else
      {
        localBroadcast = new Broadcast();
      }
      String str3 = AclCtrl.getLogonID(paramHttpServletRequest);
      paramHttpServletRequest.setAttribute("userID", str3);
      localBroadcastForm = (BroadcastForm)convert(localBroadcast);
      localBroadcastForm.setCrud(str1);
      localBroadcastForm.setSendTime(str2);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localBroadcastForm);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return waitSend(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    return paramActionMapping.findForward("editWait");
  }
  
  public ActionForward editAready(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editAready' method");
    }
    BroadcastForm localBroadcastForm = (BroadcastForm)paramActionForm;
    BroadcastManager localBroadcastManager = (BroadcastManager)getBean("broadcastManager");
    Broadcast localBroadcast = null;
    try
    {
      localBroadcast = localBroadcastManager.getBroadcastById(Long.valueOf(localBroadcastForm.getId()));
      localBroadcast.setContent(localBroadcast.getContent());
      localBroadcastForm = (BroadcastForm)convert(localBroadcast);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localBroadcastForm);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return waitSend(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    return paramActionMapping.findForward("editAready");
  }
  
  public ActionForward alreadySend(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'alreadySend' method");
    }
    BroadcastManager localBroadcastManager = (BroadcastManager)getBean("broadcastManager");
    try
    {
      List localList = localBroadcastManager.getBroadcastAready();
      paramHttpServletRequest.setAttribute("alreadySendList", localList);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return waitSend(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    return paramActionMapping.findForward("alreadySend");
  }
  
  public ActionForward save(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'save' method");
    }
    try
    {
      if (isTokenValid(paramHttpServletRequest))
      {
        BroadcastForm localBroadcastForm = (BroadcastForm)paramActionForm;
        String str1 = paramHttpServletRequest.getParameter("content");
        if (str1 != null) {
          localBroadcastForm.setContent(FormatPunctuation.EN2CN(str1));
        }
        String str2 = localBroadcastForm.getCrud();
        BroadcastManager localBroadcastManager = (BroadcastManager)getBean("broadcastManager");
        Broadcast localBroadcast = (Broadcast)convert(localBroadcastForm);
        BeanUtils.copyProperties(localBroadcast, localBroadcastForm);
        if (str2.trim().equals("create"))
        {
          if ((localBroadcast.getCustomerID() != null) && (localBroadcast.getCustomerID().trim().equals(""))) {
            localBroadcast.setCustomerID("");
          }
          if (localBroadcast.getStatus() != null) {
            if ("0".equals(localBroadcast.getStatus().toString()))
            {
              localBroadcastManager.insertBroadcast(localBroadcast);
              addSysLog(paramHttpServletRequest, "保存广播消息信息");
            }
            else if ("1".equals(localBroadcast.getStatus().toString()))
            {
              localBroadcastManager.insertHisBroadcast(localBroadcast);
              addSysLog(paramHttpServletRequest, "保存广播消息信息");
            }
          }
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
        else if (str2.trim().equals("update"))
        {
          localBroadcastManager.updateBroadcast(localBroadcast);
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
      }
      resetToken(paramHttpServletRequest);
    }
    catch (Exception localException)
    {
      this.log.error("===>save err：" + localException);
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return paramActionMapping.findForward("edit");
    }
    return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward saveWait(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveWait' method");
    }
    BroadcastForm localBroadcastForm = (BroadcastForm)paramActionForm;
    String str1 = paramHttpServletRequest.getParameter("content");
    if (str1 != null) {
      localBroadcastForm.setContent(FormatPunctuation.EN2CN(str1));
    }
    String str2 = localBroadcastForm.getCrud();
    String str3 = localBroadcastForm.getType();
    BroadcastManager localBroadcastManager = (BroadcastManager)getBean("broadcastManager");
    Broadcast localBroadcast = (Broadcast)convert(localBroadcastForm);
    BeanUtils.copyProperties(localBroadcast, localBroadcastForm);
    try
    {
      if (isTokenValid(paramHttpServletRequest)) {
        for (String str4 : localBroadcast.getCustomerID().split(","))
        {
          localBroadcast.setCustomerID(str4);
          if (str2.trim().equals("create"))
          {
            if ((localBroadcast.getCustomerID() != null) && (localBroadcast.getCustomerID().trim().equals(""))) {
              localBroadcast.setCustomerID("");
            }
            if ((localBroadcast.getType() != null) && (!"".equals(localBroadcast.getType()))) {
              if ("true".equals(localBroadcast.getType()))
              {
                localBroadcastManager.insertBroadcast(localBroadcast);
                addSysLog(paramHttpServletRequest, "保存即时广播消息信息");
              }
              else if ("false".equals(localBroadcast.getType()))
              {
                localBroadcastManager.insertHisBroadcast(localBroadcast);
                addSysLog(paramHttpServletRequest, "保存定时广播消息信息");
              }
            }
            paramHttpServletRequest.setAttribute("prompt", "操作成功！");
          }
          else if (str2.trim().equals("update"))
          {
            if ("true".equals(localBroadcast.getType()))
            {
              localBroadcastManager.updateBroadcast(localBroadcast);
              addSysLog(paramHttpServletRequest, "保存即时广播消息信息，并删除未发送表的记录！");
            }
            else if ("false".equals(localBroadcast.getType()))
            {
              localBroadcastManager.updateBroadcastWait(localBroadcast);
              addSysLog(paramHttpServletRequest, "修改定时广播消息信息！");
            }
            paramHttpServletRequest.setAttribute("prompt", "操作成功！");
          }
        }
      }
      resetToken(paramHttpServletRequest);
    }
    catch (Exception localException)
    {
      this.log.error("===>save err：" + localException);
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return waitSend(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward delete(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'delete' method");
    }
    BroadcastManager localBroadcastManager = (BroadcastManager)getBean("broadcastManager");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("itemlist");
    int i = 0;
    if (arrayOfString != null)
    {
      this.log.debug("==ids.length:" + arrayOfString.length);
      String str2 = "";
      for (int j = 0; j < arrayOfString.length; j++)
      {
        String str1 = arrayOfString[j];
        try
        {
          localBroadcastManager.deleteBroadcastWaitById(Long.valueOf(str1));
          addSysLog(paramHttpServletRequest, "删除广播消息信息");
          i++;
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str2 = str2 + str1 + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + str1 + "]与其他数据关联，删除失败！");
        }
      }
      if (!str2.equals(""))
      {
        str2 = str2.substring(0, str2.length() - 1);
        str2 = str2 + "与其他数据关联，不能删除！";
      }
      str2 = str2 + "成功删除" + i + "条纪录！";
      paramHttpServletRequest.setAttribute("prompt", str2);
    }
    return waitSend(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward deleteAready(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deleteAready' method");
    }
    BroadcastManager localBroadcastManager = (BroadcastManager)getBean("broadcastManager");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("itemlist");
    int i = 0;
    if (arrayOfString != null)
    {
      this.log.debug("==ids.length:" + arrayOfString.length);
      String str2 = "";
      for (int j = 0; j < arrayOfString.length; j++)
      {
        String str1 = arrayOfString[j];
        try
        {
          localBroadcastManager.deleteBroadcastAreadyById(Long.valueOf(str1));
          addSysLog(paramHttpServletRequest, "删除广播消息信息");
          i++;
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str2 = str2 + str1 + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + str1 + "]与其他数据关联，删除失败！");
        }
      }
      if (!str2.equals(""))
      {
        str2 = str2.substring(0, str2.length() - 1);
        str2 = str2 + "与其他数据关联，不能删除！";
      }
      str2 = str2 + "成功删除" + i + "条纪录！";
      paramHttpServletRequest.setAttribute("prompt", str2);
    }
    return alreadySend(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward search(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'search' method");
    }
    BroadcastManager localBroadcastManager = (BroadcastManager)getBean("broadcastManager");
    try
    {
      List localList = localBroadcastManager.getBroadcasts(null);
      paramHttpServletRequest.setAttribute("broadcastList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询Broadcast表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("list");
  }
  
  public ActionForward waitSend(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'waitSend' method");
    }
    BroadcastManager localBroadcastManager = (BroadcastManager)getBean("broadcastManager");
    try
    {
      List localList = localBroadcastManager.getBroadcastWait();
      paramHttpServletRequest.setAttribute("waitSendList", localList);
      paramHttpServletRequest.setAttribute("BROADCAST_STATUS", CommonDictionary.BROADCAST_STATUS);
    }
    catch (Exception localException)
    {
      this.log.error("查询Broadcast表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("waitSend");
  }
  
  public ActionForward searchHis(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchHis' method");
    }
    String str1 = paramHttpServletRequest.getParameter("content");
    String str2 = paramHttpServletRequest.getParameter("createTime");
    String str3 = paramHttpServletRequest.getParameter("sendTime");
    String str4 = "where 1=1 ";
    if ((str1 != null) && (!"".equals(str1))) {
      str4 = str4 + " and hb.content like '%" + str1 + "%'";
    }
    if ((str2 != null) && (!"".equals(str2))) {
      str4 = str4 + " and to_char(hb.sendTime,'yyyy-MM-dd') >= '" + str2 + "'";
    }
    if ((str3 != null) && (!"".equals(str3))) {
      str4 = str4 + " and to_char(hb.sendTime,'yyyy-MM-dd') <= '" + str3 + "'";
    }
    BroadcastManager localBroadcastManager = (BroadcastManager)getBean("broadcastManager");
    try
    {
      List localList = localBroadcastManager.getHisBroadcast(str4);
      paramHttpServletRequest.setAttribute("hisBroadcastList", localList);
      paramHttpServletRequest.setAttribute("today", getThisDay());
    }
    catch (Exception localException)
    {
      this.log.error("查询HisBroadcast表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("searchHis");
  }
  
  public ActionForward getHisBroadcastByKye(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'getHisBroadcastByKye' method");
    }
    BroadcastForm localBroadcastForm = (BroadcastForm)paramActionForm;
    String str1 = paramHttpServletRequest.getParameter("clearDate");
    String str2 = paramHttpServletRequest.getParameter("id");
    BroadcastManager localBroadcastManager = (BroadcastManager)getBean("broadcastManager");
    Broadcast localBroadcast = new Broadcast();
    if ((str2 != null) && (!"".equals(str2))) {
      localBroadcast.setId(Long.valueOf(Long.parseLong(str2)));
    }
    localBroadcast.setSendTime(str1);
    try
    {
      localBroadcast = localBroadcastManager.getHisBroadcastByKye(localBroadcast);
      localBroadcast.setContent(localBroadcast.getContent());
      localBroadcastForm = (BroadcastForm)convert(localBroadcast);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localBroadcastForm);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("getHisBroadcastByKye");
  }
  
  public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return waitSend(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
}
