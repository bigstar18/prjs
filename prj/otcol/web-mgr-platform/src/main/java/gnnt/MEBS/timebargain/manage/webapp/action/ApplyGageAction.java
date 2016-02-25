package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.common.model.User;
import gnnt.MEBS.timebargain.manage.model.ApplyGage;
import gnnt.MEBS.timebargain.manage.service.ApplyGageManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ApplyGageAction
  extends BaseAction
{
  private Log logger = LogFactory.getLog(ApplyGageAction.class);
  
  private boolean whetherIsNullOrEmptySpace(String paramString)
  {
    return (paramString == null) || ("".equals(paramString));
  }
  
  public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---enter  默认方法 unspecified()-----do nothing---------return null----------");
    return listApplyGage(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward listApplyGage(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---enter  listApplyGage()--------------");
    ApplyGageManager localApplyGageManager = (ApplyGageManager)getBean("applyGageManager");
    QueryConditions localQueryConditions = new QueryConditions();
    String str1 = paramHttpServletRequest.getParameter("commodityID");
    String str2 = paramHttpServletRequest.getParameter("firmID");
    String str3 = paramHttpServletRequest.getParameter("status");
    this.logger.debug("commodityID:" + str1);
    this.logger.debug("firmID:" + str2);
    this.logger.debug("status:" + str3);
    if (!whetherIsNullOrEmptySpace(str1)) {
      localQueryConditions.addCondition("COMMODITYID", "like", "%" + str1 + "%");
    }
    if (!whetherIsNullOrEmptySpace(str2)) {
      localQueryConditions.addCondition("FIRMID", "like", "%" + str2 + "%");
    }
    localQueryConditions.addCondition("STATUS", "1".equals(str3) ? "=" : "<>", Integer.valueOf(1));
    List localList = localApplyGageManager.listApplyGage(localQueryConditions);
    paramHttpServletRequest.setAttribute("applyGageList", localList);
    this.logger.debug("applyGageList.size():" + localList.size());
    paramHttpServletRequest.setAttribute("commodityID", str1);
    paramHttpServletRequest.setAttribute("firmID", str2);
    return paramActionMapping.findForward("applyGageList");
  }
  
  public ActionForward listAuditGage(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---enter  listApplyGage()--------------");
    ApplyGageManager localApplyGageManager = (ApplyGageManager)getBean("applyGageManager");
    QueryConditions localQueryConditions = new QueryConditions();
    String str1 = paramHttpServletRequest.getParameter("commodityID");
    String str2 = paramHttpServletRequest.getParameter("firmID");
    String str3 = paramHttpServletRequest.getParameter("status");
    this.logger.debug("commodityID:" + str1);
    this.logger.debug("firmID:" + str2);
    this.logger.debug("status:" + str3);
    if (!whetherIsNullOrEmptySpace(str1)) {
      localQueryConditions.addCondition("COMMODITYID", "like", "%" + str1 + "%");
    }
    if (!whetherIsNullOrEmptySpace(str2)) {
      localQueryConditions.addCondition("FIRMID", "like", "%" + str2 + "%");
    }
    localQueryConditions.addCondition("STATUS", "1".equals(str3) ? "=" : "<>", Integer.valueOf(1));
    List localList = localApplyGageManager.listApplyGage(localQueryConditions);
    paramHttpServletRequest.setAttribute("auditGageList", localList);
    this.logger.debug("applyGageList.size():" + localList.size());
    paramHttpServletRequest.setAttribute("commodityID", str1);
    paramHttpServletRequest.setAttribute("firmID", str2);
    if ("1".equals(str3)) {
      return paramActionMapping.findForward("auditWaitGageList");
    }
    return paramActionMapping.findForward("auditAlreadyGageList");
  }
  
  public ActionForward getApplyGage(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---enter  getApplyGage()--------------");
    String str = paramHttpServletRequest.getParameter("applyId");
    ApplyGageManager localApplyGageManager = (ApplyGageManager)getBean("applyGageManager");
    ApplyGage localApplyGage = localApplyGageManager.getApplyGage(Long.parseLong(str));
    paramHttpServletRequest.setAttribute("applyGage", localApplyGage);
    return paramActionMapping.findForward("applyGageMessage");
  }
  
  public ActionForward applyGageSave(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---enter  applyGageSave()--------------");
    String str1 = paramHttpServletRequest.getParameter("customerID");
    String str2 = paramHttpServletRequest.getParameter("commodityID");
    String str3 = paramHttpServletRequest.getParameter("quantity");
    String str4 = paramHttpServletRequest.getParameter("repealType");
    String str5 = paramHttpServletRequest.getParameter("applyType");
    String str6 = paramHttpServletRequest.getParameter("remark1");
    User localUser = (User)paramHttpServletRequest.getSession().getAttribute("CURRENUSER");
    ApplyGage localApplyGage = new ApplyGage();
    localApplyGage.setCustomerID(str1);
    localApplyGage.setCommodityID(str2);
    localApplyGage.setQuantity(Long.parseLong(str3));
    localApplyGage.setApplyType(Integer.parseInt("1".equals(str5) ? str5 : str4));
    localApplyGage.setCreator(localUser.getUserId());
    localApplyGage.setRemark1(str6);
    ApplyGageManager localApplyGageManager = (ApplyGageManager)getBean("applyGageManager");
    int i = localApplyGageManager.saveApplyGage(localApplyGage);
    String str7 = "";
    switch (i)
    {
    case 0: 
      str7 = "申请成功";
      break;
    case 1: 
      str7 = "不存在的客户代码";
      break;
    case 2: 
      str7 = "不存在的商品代码";
      break;
    case 3: 
      str7 = "数据库操作异常";
      break;
    case 4: 
      str7 = "可撤销的抵顶数量不足";
      break;
    case 5: 
      str7 = "资金不足";
      break;
    case -100: 
      str7 = "其他异常";
    }
    paramHttpServletRequest.setAttribute("returnMsg", str7);
    return paramActionMapping.findForward("addApplyGage");
  }
  
  public ActionForward addApplyGage(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---enter  addApplyGage()--------------");
    paramHttpServletRequest.setAttribute("applyType", paramHttpServletRequest.getParameter("applyType"));
    return paramActionMapping.findForward("addApplyGage");
  }
  
  public ActionForward gageAudit(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---enter  gageAudit()--------------");
    String str1 = paramHttpServletRequest.getParameter("applyId");
    String str2 = paramHttpServletRequest.getParameter("status");
    this.logger.debug("---applyId:" + str1);
    this.logger.debug("---status:" + str2);
    ApplyGageManager localApplyGageManager = (ApplyGageManager)getBean("applyGageManager");
    ApplyGage localApplyGage = localApplyGageManager.getApplyGage(Long.parseLong(str1));
    localApplyGage.setModifier(((User)paramHttpServletRequest.getSession().getAttribute("CURRENUSER")).getUserId());
    int i = localApplyGageManager.gageProcessorAudit(localApplyGage, str2);
    String str3 = "";
    switch (i)
    {
    case 1: 
      str3 = "处理成功";
      break;
    case 2: 
      str3 = "已处理过";
      break;
    case 3: 
      str3 = "不存在的申请";
      break;
    case -1: 
      str3 = "抵顶时超出可抵顶数";
      break;
    case -2: 
      str3 = "抵顶数量大于持仓数量";
      break;
    case -3: 
      str3 = "抵顶数量大于可用数量";
      break;
    case -4: 
      str3 = "没有对应的生效仓单抵顶记录";
      break;
    case -5: 
      str3 = "无效的操作";
      break;
    case -6: 
      str3 = "数据库操作异常";
      break;
    case -11: 
      str3 = "撤销抵顶时超出可抵顶数量";
      break;
    case -12: 
      str3 = "撤销抵顶数量大于持仓数量";
      break;
    case -13: 
      str3 = "未查询到对应的持仓数量";
      break;
    case -14: 
      str3 = "资金余额不足";
      break;
    case -100: 
      str3 = "其他异常";
    }
    paramHttpServletRequest.setAttribute("returnMsg", str3);
    return paramActionMapping.findForward("afterAudit");
  }
}
