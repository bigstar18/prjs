package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.member.broker.services.BrokerService;
import gnnt.MEBS.member.broker.util.SysData;
import gnnt.MEBS.timebargain.manage.model.CommoditySettleProp;
import gnnt.MEBS.timebargain.manage.model.Delay;
import gnnt.MEBS.timebargain.manage.model.DelayStatusLocal;
import gnnt.MEBS.timebargain.manage.model.Settleprivilege;
import gnnt.MEBS.timebargain.manage.service.CommoditySettlePropManager;
import gnnt.MEBS.timebargain.manage.service.DelayManager;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.service.SettleprivilegeManager;
import gnnt.MEBS.timebargain.manage.service.StatQueryManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import gnnt.MEBS.timebargain.manage.webapp.form.DelayForm;
import gnnt.MEBS.timebargain.manage.webapp.util.QueryUtil;
import gnnt.MEBS.timebargain.server.model.DelayStatus;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DelayAction
  extends BaseAction
{
  public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return editDelaySection(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward editDelaySection(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editDelaySection' method");
    }
    DelayForm localDelayForm = (DelayForm)paramActionForm;
    DelayManager localDelayManager = (DelayManager)getBean("delayManager");
    String str = "";
    try
    {
      short s = 0;
      Delay localDelay1 = localDelayManager.getDelayByType(s);
      if (localDelay1 != null)
      {
        str = "update";
      }
      else
      {
        str = "create";
        localDelay1 = new Delay();
      }
      s = 1;
      Delay localDelay2 = localDelayManager.getDelayByType(s);
      if (localDelay2 == null)
      {
        paramHttpServletRequest.setAttribute("gouxuan", "bugou");
      }
      else
      {
        paramHttpServletRequest.setAttribute("gouxuan", "gou");
        localDelay1.setStartMiddleTime(localDelay2.getStartMiddleTime());
        localDelay1.setEndMiddleTime(localDelay2.getEndMiddleTime());
      }
      BeanUtils.copyProperties(localDelayForm, localDelay1);
      localDelayForm.setCrud(str);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localDelayForm);
    }
    catch (Exception localException)
    {
      this.log.error("出错：" + localException.getMessage());
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", "操作失败，原因：" + localException.getMessage());
    }
    return paramActionMapping.findForward("editDelaySection");
  }
  
  public ActionForward saveDelaySection(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveDelaySection' method");
    }
    DelayForm localDelayForm = (DelayForm)paramActionForm;
    Delay localDelay1 = new Delay();
    BeanUtils.copyProperties(localDelay1, localDelayForm);
    localDelay1.setSectionID(new Long(1L));
    localDelay1.setName("");
    localDelay1.setType(new Short("0"));
    localDelay1.setStatus(new Short("1"));
    DelayManager localDelayManager = (DelayManager)getBean("delayManager");
    String str1 = localDelayForm.getCrud();
    String str2 = paramHttpServletRequest.getParameter("isNotDo");
    int i = 0;
    if ((str2 != null) && (str2.equals("yes"))) {
      i = 1;
    }
    try
    {
      if ("create".equals(str1))
      {
        localDelayManager.insertDelaySection(localDelay1);
        addSysLog(paramHttpServletRequest, "保存延期交易节信息[" + localDelay1.getSectionID() + "]");
        if (i != 0)
        {
          localDelay1.setSectionID(new Long(2L));
          localDelay1.setStartTime(localDelay1.getStartMiddleTime());
          localDelay1.setEndTime(localDelay1.getEndMiddleTime());
          localDelay1.setType(Short.valueOf((short)1));
          localDelayManager.insertDelaySection(localDelay1);
          addSysLog(paramHttpServletRequest, "保存延期交易节信息[" + localDelay1.getSectionID() + "]");
        }
        paramHttpServletRequest.setAttribute("prompt", "操作成功！");
      }
      else if ("update".equals(str1))
      {
        localDelayManager.updateDelaySection(localDelay1);
        addSysLog(paramHttpServletRequest, "修改延期交易节信息[" + localDelay1.getSectionID() + "]");
        if (i != 0)
        {
          localDelay1.setSectionID(new Long(2L));
          localDelay1.setStartTime(localDelay1.getStartMiddleTime());
          localDelay1.setEndTime(localDelay1.getEndMiddleTime());
          localDelay1.setType(Short.valueOf((short)1));
          Delay localDelay2 = localDelayManager.getDelayByType((short)1);
          if (localDelay2 == null)
          {
            localDelayManager.insertDelaySection(localDelay1);
            addSysLog(paramHttpServletRequest, "保存延期交易节信息[" + localDelay1.getSectionID() + "]");
          }
          else
          {
            localDelayManager.updateDelaySection(localDelay1);
            addSysLog(paramHttpServletRequest, "修改延期交易节信息[" + localDelay1.getSectionID() + "]");
          }
        }
        else
        {
          localDelayManager.deleteDelaySectionById("2");
          addSysLog(paramHttpServletRequest, "修改延期交易节信息[2]");
        }
        paramHttpServletRequest.setAttribute("prompt", "操作成功！");
      }
    }
    catch (Exception localException)
    {
      this.log.error("出错：" + localException.getMessage());
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", "操作失败，原因：" + localException.getMessage());
    }
    return editDelaySection(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward deleteDelaySection(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deleteDelaySection' method");
    }
    String str = paramHttpServletRequest.getParameter("sectionID");
    DelayManager localDelayManager = (DelayManager)getBean("delayManager");
    try
    {
      localDelayManager.deleteDelaySectionById(str);
      addSysLog(paramHttpServletRequest, "修改延期交易节信息[" + str + "]");
      paramHttpServletRequest.setAttribute("prompt", "操作成功！");
    }
    catch (Exception localException)
    {
      this.log.debug("出错：" + localException.getMessage());
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", "操作失败，原因：" + localException.getMessage());
    }
    return editDelaySection(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward editDelayStatus(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editDelayStatus' method");
    }
    DelayManager localDelayManager = (DelayManager)getBean("delayManager");
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    try
    {
      DelayStatusLocal localDelayStatusLocal = localDelayManager.getDelayStatus();
      if (localDelayStatusLocal == null) {
        localDelayStatusLocal = new DelayStatusLocal();
      }
      paramHttpServletRequest.setAttribute("tradeTime", localDelayStatusLocal.getTradeDate() == null ? "" : localDelayStatusLocal.getTradeDate());
      paramHttpServletRequest.setAttribute("sectionID", localDelayStatusLocal.getSectionID() + "");
      paramHttpServletRequest.setAttribute("status", localDelayStatusLocal.getStatus() == null ? "" : localDelayStatusLocal.getStatus());
      paramHttpServletRequest.setAttribute("note", localDelayStatusLocal.getNote() == null ? "" : localDelayStatusLocal.getNote());
      String str1 = localStatQueryManager.getSysdate();
      paramHttpServletRequest.setAttribute("sysdate", str1);
      DelayStatus localDelayStatus = new DelayStatus();
      if ((0 + "").equals(localDelayStatusLocal.getStatus())) {
        paramHttpServletRequest.setAttribute("result2", DelayStatus.DELAY_STATUS[0]);
      }
      if ((1 + "").equals(localDelayStatusLocal.getStatus())) {
        paramHttpServletRequest.setAttribute("result2", DelayStatus.DELAY_STATUS[1]);
      }
      if ((2 + "").equals(localDelayStatusLocal.getStatus())) {
        paramHttpServletRequest.setAttribute("result2", DelayStatus.DELAY_STATUS[2]);
      }
      if ((3 + "").equals(localDelayStatusLocal.getStatus())) {
        paramHttpServletRequest.setAttribute("result2", DelayStatus.DELAY_STATUS[3]);
      }
      if ((4 + "").equals(localDelayStatusLocal.getStatus())) {
        paramHttpServletRequest.setAttribute("result2", DelayStatus.DELAY_STATUS[4]);
      }
      if ((5 + "").equals(localDelayStatusLocal.getStatus())) {
        paramHttpServletRequest.setAttribute("result2", DelayStatus.DELAY_STATUS[5]);
      }
      String str2 = localDelayStatusLocal.getRecoverTime();
      paramHttpServletRequest.setAttribute("recoverTime2", str2);
    }
    catch (Exception localException)
    {
      this.log.debug("出错：" + localException.getMessage());
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", "操作失败，原因：" + localException.getMessage());
    }
    return paramActionMapping.findForward("editDelayStatus");
  }
  
  public ActionForward operate(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'operate' method");
    }
    String str = paramHttpServletRequest.getParameter("type");
    try
    {
      AgencyRMIBean localAgencyRMIBean = new AgencyRMIBean(paramHttpServletRequest);
      if ("01".equals(str))
      {
        localAgencyRMIBean.ctlTradeDelay(0);
        addSysLog(paramHttpServletRequest, "修改延期交易节状态[暂停]");
      }
      if ("02".equals(str))
      {
        localAgencyRMIBean.ctlTradeDelay(1);
        addSysLog(paramHttpServletRequest, "修改延期交易节状态[继续交易]");
      }
      if ("03".equals(str))
      {
        localAgencyRMIBean.tradeEndDelay();
        addSysLog(paramHttpServletRequest, "修改延期交易节状态[交易结束]");
      }
    }
    catch (Exception localException)
    {
      this.log.debug("出错：" + localException.getMessage());
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", "操作失败，原因：" + localException.getMessage());
    }
    return paramActionMapping.findForward("operateDelayStatus");
  }
  
  public ActionForward operateRecoverTime(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'operateRecoverTime' method");
    }
    String str1 = paramHttpServletRequest.getParameter("type");
    String str2 = paramHttpServletRequest.getParameter("recoverTime");
    try
    {
      AgencyRMIBean localAgencyRMIBean = new AgencyRMIBean(paramHttpServletRequest);
      if ("99".equals(str1))
      {
        localAgencyRMIBean.timingContinueTradeDelay(str2);
        addSysLog(paramHttpServletRequest, "修改延期交易节恢复时间[" + str2 + "]");
      }
    }
    catch (Exception localException)
    {
      this.log.debug("出错：" + localException.getMessage());
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", "操作失败，原因：" + localException.getMessage());
    }
    return paramActionMapping.findForward("operateDelayStatusRecoverTime");
  }
  
  public ActionForward listDelayOrders(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listDelayOrders' method");
    }
    DelayManager localDelayManager = (DelayManager)getBean("delayManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localDelayManager.getDelayOrdersList(localQueryConditions);
      paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
      paramHttpServletRequest.setAttribute("ORDER_STATUS", CommonDictionary.ORDER_STATUS);
      paramHttpServletRequest.setAttribute("DELAYORDERTYPE_STATUS", CommonDictionary.DELAYORDERTYPE_STATUS);
      paramHttpServletRequest.setAttribute("listDelayOrders", localList);
    }
    catch (Exception localException)
    {
      this.log.debug("出错：" + localException.getMessage());
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", "操作失败，原因：" + localException.getMessage());
    }
    return paramActionMapping.findForward("listDelayOrders");
  }
  
  public ActionForward listDelayTrade(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listDelayTrade' method");
    }
    DelayManager localDelayManager = (DelayManager)getBean("delayManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localDelayManager.getDelayTradeList(localQueryConditions);
      paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
      paramHttpServletRequest.setAttribute("DELAYORDERTYPE", CommonDictionary.DELAYORDERTYPE);
      paramHttpServletRequest.setAttribute("listDelayTrade", localList);
    }
    catch (Exception localException)
    {
      this.log.debug("出错：" + localException.getMessage());
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", "操作失败，原因：" + localException.getMessage());
    }
    return paramActionMapping.findForward("listDelayTrade");
  }
  
  public ActionForward listDelayQuotation(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listDelayQuotation' method");
    }
    DelayManager localDelayManager = (DelayManager)getBean("delayManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localDelayManager.getDelayQuotationList(localQueryConditions);
      paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
      paramHttpServletRequest.setAttribute("DELAYORDERTYPE", CommonDictionary.DELAYORDERTYPE);
      paramHttpServletRequest.setAttribute("listDelayQuotation", localList);
    }
    catch (Exception localException)
    {
      this.log.debug("出错：" + localException.getMessage());
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", "操作失败，原因：" + localException.getMessage());
    }
    return paramActionMapping.findForward("listDelayQuotation");
  }
  
  public ActionForward listSettleprivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listSettleprivilege' method");
    }
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    SettleprivilegeManager localSettleprivilegeManager = (SettleprivilegeManager)getBean("settleprivilegeManager");
    try
    {
      List localList = localSettleprivilegeManager.listSettleprivilege(localQueryConditions);
      paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
      paramHttpServletRequest.setAttribute("DELAYORDERTYPE", CommonDictionary.DELAYORDERTYPE);
      paramHttpServletRequest.setAttribute("PRIVILEGECODE_B", CommonDictionary.PRIVILEGECODE_B);
      paramHttpServletRequest.setAttribute("PRIVILEGECODE_S", CommonDictionary.PRIVILEGECODE_S);
      paramHttpServletRequest.setAttribute("listSettleprivilege", localList);
    }
    catch (Exception localException)
    {
      this.log.debug("出错：" + localException.getMessage());
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", "操作失败，原因：" + localException.getMessage());
    }
    return paramActionMapping.findForward("listSettleprivilege");
  }
  
  public ActionForward deletePrivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'delete' method");
    }
    SettleprivilegeManager localSettleprivilegeManager = (SettleprivilegeManager)getBean("settleprivilegeManager");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("itemlist");
    if (arrayOfString != null)
    {
      this.log.debug("==ids.length:" + arrayOfString.length);
      String str2 = "";
      for (int i = 0; i < arrayOfString.length; i++)
      {
        String str1 = arrayOfString[i];
        localSettleprivilegeManager.deletePrivilege(Long.parseLong(str1));
      }
    }
    paramHttpServletRequest.setAttribute("msg", "删除成功");
    return listSettleprivilege(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward privilegeAddForward(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'addforward' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    return paramActionMapping.findForward("privilegeAdd");
  }
  
  public ActionForward privilegeUpdateForward(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'update' method");
    }
    System.out.println("djie");
    getSelectAttribute(paramHttpServletRequest);
    String str = paramHttpServletRequest.getParameter("id");
    System.out.println("privilegeId=========" + str);
    SettleprivilegeManager localSettleprivilegeManager = (SettleprivilegeManager)getBean("settleprivilegeManager");
    List localList = localSettleprivilegeManager.getSettlePrivilege(Long.parseLong(str));
    paramHttpServletRequest.setAttribute("settlePrivilege", localList.get(0));
    return paramActionMapping.findForward("privilegeUpdate");
  }
  
  public ActionForward privilegeAdd(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'add' method");
    }
    SettleprivilegeManager localSettleprivilegeManager = (SettleprivilegeManager)getBean("settleprivilegeManager");
    Settleprivilege localSettleprivilege = new Settleprivilege();
    localSettleprivilege.setKindId(paramHttpServletRequest.getParameter("kindid"));
    localSettleprivilege.setPrivilegecode_b(Integer.parseInt(paramHttpServletRequest.getParameter("privilegecode_b")));
    localSettleprivilege.setPrivilegecode_s(Integer.parseInt(paramHttpServletRequest.getParameter("privilegecode_s")));
    localSettleprivilege.setTypeId(paramHttpServletRequest.getParameter("typeid"));
    localSettleprivilegeManager.insertPrivilege(localSettleprivilege);
    paramHttpServletRequest.setAttribute("msg", "添加成功");
    return paramActionMapping.findForward("alertMsg");
  }
  
  public ActionForward privilegeUpdate(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'update' method");
    }
    SettleprivilegeManager localSettleprivilegeManager = (SettleprivilegeManager)getBean("settleprivilegeManager");
    Settleprivilege localSettleprivilege = new Settleprivilege();
    localSettleprivilege.setId(Long.parseLong(paramHttpServletRequest.getParameter("id")));
    localSettleprivilege.setKindId(paramHttpServletRequest.getParameter("kindid"));
    localSettleprivilege.setPrivilegecode_b(Integer.parseInt(paramHttpServletRequest.getParameter("privilegecode_b")));
    localSettleprivilege.setPrivilegecode_s(Integer.parseInt(paramHttpServletRequest.getParameter("privilegecode_s")));
    localSettleprivilege.setTypeId(paramHttpServletRequest.getParameter("typeid"));
    localSettleprivilegeManager.updateSettlePrivilege(localSettleprivilege);
    paramHttpServletRequest.setAttribute("msg", "修改成功");
    return paramActionMapping.findForward("alertUpdateMsg");
  }
  
  public ActionForward commoditySettlePropList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listCommoditySettleProp' method");
    }
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    CommoditySettlePropManager localCommoditySettlePropManager = (CommoditySettlePropManager)getBean("commoditySettlePropManager");
    try
    {
      List localList = localCommoditySettlePropManager.commoditySettlePropList(localQueryConditions);
      paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
      paramHttpServletRequest.setAttribute("DELAYORDERTYPE", CommonDictionary.DELAYORDERTYPE);
      paramHttpServletRequest.setAttribute("listCommoditySettleProp", localList);
    }
    catch (Exception localException)
    {
      this.log.debug("出错：" + localException.getMessage());
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", "操作失败，原因：" + localException.getMessage());
    }
    return paramActionMapping.findForward("listCommoditySettleProp");
  }
  
  public ActionForward commoditySettlePropDelete(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'commoditySettlePropDelete' method");
    }
    CommoditySettlePropManager localCommoditySettlePropManager = (CommoditySettlePropManager)getBean("commoditySettlePropManager");
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
    CommoditySettleProp localCommoditySettleProp = new CommoditySettleProp();
    if (arrayOfString1 != null)
    {
      this.log.debug("==ids.length:" + arrayOfString1.length);
      String str2 = "";
      for (int i = 0; i < arrayOfString1.length; i++)
      {
        String str1 = arrayOfString1[i];
        String[] arrayOfString2 = str1.split(",");
        localCommoditySettleProp.setCommodityId(arrayOfString2[0]);
        localCommoditySettleProp.setSectionId(Integer.parseInt(arrayOfString2[1]));
        localCommoditySettlePropManager.commoditySettlePropDelete(localCommoditySettleProp);
      }
    }
    paramHttpServletRequest.setAttribute("msg", "删除成功");
    return commoditySettlePropList(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward commoditySettlePropEditForward(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'commoditySettlePropEditForward' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    CommoditySettlePropManager localCommoditySettlePropManager = (CommoditySettlePropManager)getBean("commoditySettlePropManager");
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("SECTIONID", "=", "1");
    List localList1 = localCommoditySettlePropManager.commoditySettlePropList(localQueryConditions);
    localQueryConditions.removeCondition("SECTIONID", "=");
    localQueryConditions.addCondition("SECTIONID", "=", "2");
    List localList2 = localCommoditySettlePropManager.commoditySettlePropList(localQueryConditions);
    paramHttpServletRequest.setAttribute("listSettle", localList1);
    paramHttpServletRequest.setAttribute("listMiddle", localList2);
    return paramActionMapping.findForward("commoditySettlePropEdit");
  }
  
  public ActionForward commoditySettlePropEdit(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'commoditySettlePropEdit' method");
    }
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("settle");
    String[] arrayOfString2 = paramHttpServletRequest.getParameterValues("middle");
    CommoditySettlePropManager localCommoditySettlePropManager = (CommoditySettlePropManager)getBean("commoditySettlePropManager");
    int i = localCommoditySettlePropManager.commoditySettlePropAdd(arrayOfString1, arrayOfString2);
    if (i == 1) {
      paramHttpServletRequest.setAttribute("msg", "修改成功");
    } else {
      paramHttpServletRequest.setAttribute("msg", "操作异常");
    }
    return commoditySettlePropEditForward(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  private void getSelectAttribute(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
    paramHttpServletRequest.setAttribute("commoditySelect", localLookupManager.getSelectLabelValueByTable("T_commodity", "Name", "CommodityID", " where settleWay=1 order by name "));
  }
  
  public void getFirmId(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    int i = 0;
    try
    {
      paramHttpServletRequest.setCharacterEncoding("GBK");
      paramHttpServletResponse.setContentType("text/html;charset=GBK");
      PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
      String str = paramHttpServletRequest.getParameter("firmId");
      BrokerService localBrokerService = (BrokerService)SysData.getBean("m_brokerService");
      Dealer localDealer = localBrokerService.getDealerByfirmId(str);
      if (localDealer == null) {
        i = 1;
      }
      localPrintWriter.print(i);
      localPrintWriter.flush();
      localPrintWriter.close();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public void getSettleprivilegeByFirmIdAndCommId(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    int i = 0;
    try
    {
      paramHttpServletRequest.setCharacterEncoding("GBK");
      paramHttpServletResponse.setContentType("text/html;charset=GBK");
      PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
      String str1 = paramHttpServletRequest.getParameter("firmId");
      String str2 = paramHttpServletRequest.getParameter("kindId");
      SettleprivilegeManager localSettleprivilegeManager = (SettleprivilegeManager)getBean("settleprivilegeManager");
      List localList = localSettleprivilegeManager.getSettleprivilegeByFirmIdAndCommId(str1, str2);
      if (localList.size() > 0) {
        i = 1;
      }
      localPrintWriter.print(i);
      localPrintWriter.flush();
      localPrintWriter.close();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
}
