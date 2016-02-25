package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.common.model.User;
import gnnt.MEBS.timebargain.manage.model.AheadSettle;
import gnnt.MEBS.timebargain.manage.service.AheadSettleManager;
import gnnt.MEBS.timebargain.manage.service.StatQueryManager;
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

public class AheadSettleAction
  extends BaseAction
{
  private static Log logger = LogFactory.getLog(AheadSettleAction.class);
  
  public ActionForward applyAheadSettle(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    logger.debug("---enter  applyAheadSettle()---------");
    String str1 = paramHttpServletRequest.getParameter("customerID_B");
    String str2 = paramHttpServletRequest.getParameter("customerID_S");
    String str3 = paramHttpServletRequest.getParameter("commodityID");
    String str4 = paramHttpServletRequest.getParameter("price");
    String str5 = paramHttpServletRequest.getParameter("quantity");
    String str6 = paramHttpServletRequest.getParameter("gageQty");
    String str7 = paramHttpServletRequest.getParameter("remark1");
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    AheadSettleManager localAheadSettleManager1 = (AheadSettleManager)getBean("aheadSettleManager");
    String str8 = localStatQueryManager.getSystemStatus();
    int i = 0;
    try
    {
      if ("1".equals(str8))
      {
        long l1 = localAheadSettleManager1.getHoldQty(str1, str3, 1);
        long l2 = localAheadSettleManager1.getHoldQty(str2, str3, 2);
        long l3 = localAheadSettleManager1.getGageQty(str2, str3, 2);
        if (Long.parseLong(str6) <= l3)
        {
          long l4 = Long.parseLong(str5) - Long.parseLong(str6);
          if ((l1 >= Long.parseLong(str5)) && (l2 >= l4) && (l3 >= Long.parseLong(str6)))
          {
            AheadSettle localAheadSettle = new AheadSettle();
            localAheadSettle.setApplyType(1);
            localAheadSettle.setCustomerID_B(str1);
            localAheadSettle.setCustomerID_S(str2);
            localAheadSettle.setCommodityID(str3);
            localAheadSettle.setPrice(Double.parseDouble(str4));
            localAheadSettle.setQuantity(Long.parseLong(str5));
            localAheadSettle.setGageQty(Long.parseLong(str6));
            localAheadSettle.setRemark1(str7);
            localAheadSettle.setCreator(((User)paramHttpServletRequest.getSession().getAttribute("CURRENUSER")).getUserId());
            AheadSettleManager localAheadSettleManager2 = (AheadSettleManager)getBean("aheadSettleManager");
            i = localAheadSettleManager2.saveAheadSettle(localAheadSettle);
          }
          else
          {
            i = -4;
          }
        }
        else
        {
          i = -6;
        }
      }
      else
      {
        i = -5;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      i = -100;
    }
    String str9 = "";
    switch (i)
    {
    case 0: 
      str9 = "保存成功！";
      break;
    case -1: 
      str9 = "不存在的买方客户代码！";
      break;
    case -2: 
      str9 = "不存在的卖方客户代码！";
      break;
    case -3: 
      str9 = "不存在的商品代码！";
      break;
    case -4: 
      str9 = "买方或者卖方持仓数量不足，不能操作！";
      break;
    case -5: 
      str9 = "系统不是闭市状态，不能操作！";
      break;
    case -6: 
      str9 = "卖方抵顶数量输入有误！";
      break;
    case -100: 
      str9 = "操作异常！";
    }
    paramHttpServletRequest.setAttribute("returnMsg", str9);
    return paramActionMapping.findForward("afterSave");
  }
  
  public ActionForward listApplyAheadSettle(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    logger.debug("---enter  listAheadSettle()---------");
    String str = paramHttpServletRequest.getParameter("status");
    paramHttpServletRequest.setAttribute("aheadSettleList", getAheadSettleDataList(paramHttpServletRequest));
    if ("1".equals(str)) {
      return paramActionMapping.findForward("appllyAheadSettleWaitList");
    }
    return paramActionMapping.findForward("aheadSettleAlreadyList");
  }
  
  public ActionForward aheadSettleAudit(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    logger.debug("---enter  aheadSettleAudit()---------");
    String str1 = paramHttpServletRequest.getParameter("applyID");
    String str2 = paramHttpServletRequest.getParameter("status");
    AheadSettleManager localAheadSettleManager = (AheadSettleManager)getBean("aheadSettleManager");
    AheadSettle localAheadSettle = localAheadSettleManager.getAheadSettle(str1);
    localAheadSettle.setStatus(Integer.parseInt(str2));
    localAheadSettle.setModifier(((User)paramHttpServletRequest.getSession().getAttribute("CURRENUSER")).getUserId());
    localAheadSettle.setRemark2(paramHttpServletRequest.getParameter("remark2"));
    int i = localAheadSettleManager.auditAheadSettle(localAheadSettle);
    String str3 = "";
    switch (i)
    {
    case 1: 
      str3 = "审核成功!";
      break;
    case 2: 
      str3 = "已处理过!";
      break;
    case 3: 
      str3 = "无效的操作!";
      break;
    case -1: 
      str3 = "可交收买持仓数量不足!";
      break;
    case -2: 
      str3 = "可交收买抵顶数量不足!";
      break;
    case -3: 
      str3 = "交收买持仓数量大于可交收买持仓数量!";
      break;
    case -4: 
      str3 = "交收买抵顶数量大于可买抵顶数量!";
      break;
    case -11: 
      str3 = "可交收卖持仓数量不足!";
      break;
    case -12: 
      str3 = "可交收卖抵顶数量不足!";
      break;
    case -13: 
      str3 = "交收卖持仓数量大于可交收卖持仓数量!";
      break;
    case -14: 
      str3 = "交收卖抵顶数量大于可卖抵顶数量!";
      break;
    case -15: 
      str3 = "未查询到对应的持仓数量!";
      break;
    case -99: 
      str3 = "操作异常!";
      break;
    case -100: 
      str3 = "其它错误!";
    }
    paramHttpServletRequest.setAttribute("returnMsg", str3);
    return paramActionMapping.findForward("afterAudit");
  }
  
  public ActionForward listAuditAheadSettle(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    logger.debug("---enter  listAuditAheadSettle()---------");
    String str = paramHttpServletRequest.getParameter("status");
    paramHttpServletRequest.setAttribute("aheadSettleList", getAheadSettleDataList(paramHttpServletRequest));
    if ("1".equals(str)) {
      return paramActionMapping.findForward("auditAheadSettleWaitList");
    }
    return paramActionMapping.findForward("aheadSettleAlreadyList");
  }
  
  private List getAheadSettleDataList(HttpServletRequest paramHttpServletRequest)
  {
    String str1 = paramHttpServletRequest.getParameter("customerID_B");
    String str2 = paramHttpServletRequest.getParameter("customerID_S");
    String str3 = paramHttpServletRequest.getParameter("commodityID");
    String str4 = paramHttpServletRequest.getParameter("status");
    QueryConditions localQueryConditions = new QueryConditions();
    if ((str1 != null) && (!"".equals(str1))) {
      localQueryConditions.addCondition("customerID_B", "like", "%" + str1 + "%");
    }
    if ((str2 != null) && (!"".equals(str2))) {
      localQueryConditions.addCondition("customerID_S", "like", "%" + str2 + "%");
    }
    if ((str3 != null) && (!"".equals(str3))) {
      localQueryConditions.addCondition("commodityID", "like", "%" + str3 + "%");
    }
    if ((str4 != null) && (!"".equals(str4))) {
      localQueryConditions.addCondition("status", "1".equals(str4) ? "=" : "<>", Integer.valueOf(1));
    }
    AheadSettleManager localAheadSettleManager = (AheadSettleManager)getBean("aheadSettleManager");
    List localList = localAheadSettleManager.listAheadSettle(localQueryConditions);
    return localList;
  }
}
