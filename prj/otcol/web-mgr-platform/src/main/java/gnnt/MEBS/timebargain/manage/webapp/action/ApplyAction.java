package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.timebargain.manage.model.Apply;
import gnnt.MEBS.timebargain.manage.service.StatQueryManager;
import gnnt.MEBS.timebargain.manage.webapp.form.ApplyForm;
import gnnt.MEBS.timebargain.server.model.ApplyBill;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ApplyAction
  extends BaseAction
{
  public ActionForward applyWaitList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyWaitList' method");
    }
    String str = (String)paramHttpServletRequest.getAttribute("save");
    try
    {
      Object localObject1;
      Object localObject2;
      if (!"save".equals(str))
      {
        localObject1 = paramHttpServletRequest.getParameter("firmID_S");
        localObject2 = paramHttpServletRequest.getParameter("commodityID");
        Apply localApply = new Apply();
        localApply.setFirmID_S((String)localObject1);
        localApply.setCommodityID((String)localObject2);
        StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
        List localList = localStatQueryManager.getApplyWaitCD(localApply);
        paramHttpServletRequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
        paramHttpServletRequest.setAttribute("APPLYTYPE", CommonDictionary.APPLYTYPE);
        paramHttpServletRequest.setAttribute("applyWaitList", localList);
      }
      else
      {
        localObject1 = (StatQueryManager)getBean("statQueryManager");
        localObject2 = ((StatQueryManager)localObject1).getApplyWaitCD(null);
        paramHttpServletRequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
        paramHttpServletRequest.setAttribute("APPLYTYPE", CommonDictionary.APPLYTYPE);
        paramHttpServletRequest.setAttribute("applyWaitList", localObject2);
      }
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("applyWaitList");
  }
  
  public ActionForward applyAlreadyList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyAlreadyList' method");
    }
    try
    {
      String str1 = paramHttpServletRequest.getParameter("firmID_S");
      String str2 = paramHttpServletRequest.getParameter("commodityID");
      Apply localApply = new Apply();
      localApply.setFirmID_S(str1);
      localApply.setCommodityID(str2);
      StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
      List localList = localStatQueryManager.getApplyAlreadyCD(localApply);
      paramHttpServletRequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
      paramHttpServletRequest.setAttribute("APPLYTYPE", CommonDictionary.APPLYTYPE);
      paramHttpServletRequest.setAttribute("applyAlreadyList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("applyAlreadyList");
  }
  
  public ActionForward applyLiveInfoList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyLiveInfoList' method");
    }
    try
    {
      String str1 = paramHttpServletRequest.getParameter("firmID_S");
      String str2 = paramHttpServletRequest.getParameter("commodityID");
      String str3 = paramHttpServletRequest.getParameter("billType");
      Apply localApply = new Apply();
      localApply.setFirmID_S(str1);
      localApply.setCommodityID(str2);
      if ((str3 != null) && (!"".equals(str3))) {
        localApply.setBillType(Short.valueOf(Short.parseShort(str3)));
      }
      StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
      List localList = localStatQueryManager.getApplyLiveInfo(localApply);
      paramHttpServletRequest.setAttribute("ALIVEPRESENTSTATUS", CommonDictionary.ALIVEPRESENTSTATUS);
      paramHttpServletRequest.setAttribute("BILLTYPE", CommonDictionary.BILLTYPE);
      paramHttpServletRequest.setAttribute("applyLiveInfoList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("applyLiveInfoList");
  }
  
  public ActionForward applyNewForm(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyNewForm' method");
    }
    try
    {
      String str = paramHttpServletRequest.getParameter("applyType");
      ApplyForm localApplyForm = new ApplyForm();
      localApplyForm.setCrud(str);
      if ((str != null) && (!"".equals(str)))
      {
        paramHttpServletRequest.setAttribute("applyType", str);
        localApplyForm.setCrud(str);
      }
      if ((str != null) && (!"".equals(str)) && (("4".equals(str)) || ("5".equals(str)) || ("6".equals(str))))
      {
        updateFormBean(paramActionMapping, paramHttpServletRequest, localApplyForm);
        return paramActionMapping.findForward("applyNewForm3");
      }
      if ((str != null) && (!"".equals(str)) && ("1".equals(str)))
      {
        updateFormBean(paramActionMapping, paramHttpServletRequest, localApplyForm);
        return paramActionMapping.findForward("applyNewForm2");
      }
      if ((str != null) && (!"".equals(str)) && ("3".equals(str)))
      {
        updateFormBean(paramActionMapping, paramHttpServletRequest, localApplyForm);
        return paramActionMapping.findForward("applyNewForm");
      }
      if ((str != null) && (!"".equals(str)) && ("7".equals(str)))
      {
        updateFormBean(paramActionMapping, paramHttpServletRequest, localApplyForm);
        return paramActionMapping.findForward("applyNewForm7");
      }
      if ((str != null) && (!"".equals(str)) && ("8".equals(str)))
      {
        updateFormBean(paramActionMapping, paramHttpServletRequest, localApplyForm);
        return paramActionMapping.findForward("applyNewFormDelay");
      }
      if ((str != null) && (!"".equals(str)) && ("9".equals(str)))
      {
        updateFormBean(paramActionMapping, paramHttpServletRequest, localApplyForm);
        return paramActionMapping.findForward("applyNewFormGiveUpDelay");
      }
      updateFormBean(paramActionMapping, paramHttpServletRequest, localApplyForm);
    }
    catch (Exception localException)
    {
      this.log.debug("异常原因：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("applyNewForm3");
  }
  
  public ActionForward applyNewForm1(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyNewForm1' method");
    }
    try
    {
      ApplyForm localApplyForm = (ApplyForm)paramActionForm;
      String str1 = paramHttpServletRequest.getParameter("crud");
      String str2 = paramHttpServletRequest.getParameter("billID");
      StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
      Apply localApply = localStatQueryManager.getAlreadySettle(str2);
      if (localApply.getApplyID() != null) {
        localApplyForm.setApplyID(localApply.getApplyID().toString());
      }
      localApplyForm.setCommodityID(localApply.getCommodityID());
      localApplyForm.setCustomerID_S(localApply.getCustomerID_S());
      localApplyForm.setBillID(localApply.getBillID());
      if (localApply.getQuantity() != null) {
        localApplyForm.setQuantity(localApply.getQuantity().toString());
      }
      if (localApply.getBillType() != null) {
        localApplyForm.setBillType(localApply.getBillType().toString());
      }
      if ((str1 != null) && (!"".equals(str1))) {
        localApplyForm.setCrud(str1);
      }
      paramHttpServletRequest.setAttribute("applyNewRelForm1", str1);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localApplyForm);
    }
    catch (Exception localException)
    {
      this.log.debug("异常原因：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("applyNewRelForm1");
  }
  
  public ActionForward applyNewForm2(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyNewForm2' method");
    }
    try
    {
      ApplyForm localApplyForm = (ApplyForm)paramActionForm;
      String str1 = paramHttpServletRequest.getParameter("crud");
      String str2 = paramHttpServletRequest.getParameter("billID");
      StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
      Apply localApply = localStatQueryManager.getGiveUpDD(str2);
      localApplyForm.setCommodityID(localApply.getCommodityID());
      localApplyForm.setCustomerID_S(localApply.getCustomerID_S());
      localApplyForm.setBillID(localApply.getBillID());
      if (localApply.getQuantity() != null) {
        localApplyForm.setQuantity(localApply.getQuantity().toString());
      }
      if ((str1 != null) && (!"".equals(str1))) {
        localApplyForm.setCrud(str1);
      }
      paramHttpServletRequest.setAttribute("applyNewRelForm2", str1);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localApplyForm);
    }
    catch (Exception localException)
    {
      this.log.debug("异常原因：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("applyNewRelForm2");
  }
  
  public ActionForward applyGiveUpDelayQuery(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyGiveUpDelayQuery' method");
    }
    try
    {
      ApplyForm localApplyForm = (ApplyForm)paramActionForm;
      String str1 = paramHttpServletRequest.getParameter("crud");
      String str2 = paramHttpServletRequest.getParameter("billID");
      StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
      Apply localApply = localStatQueryManager.getGiveUpDelaySettle(str2);
      localApplyForm.setCommodityID(localApply.getCommodityID());
      localApplyForm.setFirmID_S(localApply.getFirmID_S());
      localApplyForm.setBillID(localApply.getBillID());
      if (localApply.getQuantity() != null) {
        localApplyForm.setQuantity(localApply.getQuantity().toString());
      }
      if ((str1 != null) && (!"".equals(str1))) {
        localApplyForm.setCrud(str1);
      }
      paramHttpServletRequest.setAttribute("applyGiveUpDelayQuery", str1);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localApplyForm);
    }
    catch (Exception localException)
    {
      this.log.debug("异常原因：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("applyGiveUpDelayQuery");
  }
  
  public ActionForward applyNewForm7(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyNewForm7' method");
    }
    try
    {
      String str1 = paramHttpServletRequest.getParameter("crud");
      String str2 = paramHttpServletRequest.getParameter("billID");
      StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
      Apply localApply = localStatQueryManager.getGiveUpWaitSettle(str2);
      ApplyForm localApplyForm = (ApplyForm)paramActionForm;
      localApplyForm.setCommodityID(localApply.getCommodityID());
      localApplyForm.setCustomerID_S(localApply.getCustomerID_S());
      localApplyForm.setFirmID_S(localApply.getFirmID_S());
      localApplyForm.setBillID(localApply.getBillID());
      if (localApply.getQuantity() != null) {
        localApplyForm.setQuantity(localApply.getQuantity().toString());
      }
      if ((str1 != null) && (!"".equals(str1))) {
        localApplyForm.setCrud(str1);
      }
      paramHttpServletRequest.setAttribute("applyNewRelForm7", str1);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localApplyForm);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.debug("异常原因：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("applyNewRelForm7");
  }
  
  public ActionForward applySave(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applySave' method");
    }
    try
    {
      String str1 = AclCtrl.getLogonID(paramHttpServletRequest);
      ApplyForm localApplyForm = (ApplyForm)paramActionForm;
      StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
      String str2 = paramHttpServletRequest.getParameter("crud");
      String str3;
      Object localObject1;
      Object localObject2;
      if ("1".equals(str2))
      {
        str3 = paramHttpServletRequest.getParameter("applyType");
        if ((str3 != null) && (!"".equals(str3))) {
          localApplyForm.setApplyType(str3);
        }
        localObject1 = localApplyForm.getCommodityID();
        localApplyForm.setCommodityID((String)localObject1);
        localApplyForm.setCreator(str1);
        localApplyForm.setStatus("1");
        localObject2 = (Apply)convert(localApplyForm);
        localStatQueryManager.insertApplyGiveUpDD((Apply)localObject2);
        addSysLog(paramHttpServletRequest, "增加撤消抵顶申请[" + str3 + "]");
      }
      Object localObject3;
      Object localObject4;
      if ("3".equals(str2))
      {
        str3 = localApplyForm.getCommodityID();
        localApplyForm.setCommodityID(str3);
        localApplyForm.setApplyType("3");
        localApplyForm.setStatus("1");
        localApplyForm.setCreator(str1);
        localObject1 = (Apply)convert(localApplyForm);
        localObject2 = localStatQueryManager.getValidIDByApplyID2(localApplyForm.getBillID());
        if (localObject2 == null) {
          throw new RuntimeException("查询记录不存在！");
        }
        ((Apply)localObject1).setFirmID_S(((Apply)localObject2).getFirmID_S());
        localObject3 = "";
        if (((Apply)localObject2).getBillType() != null) {
          localObject3 = ((Apply)localObject2).getBillType().toString();
        }
        Object localObject5;
        Object localObject6;
        if ("1".equals(localObject3))
        {
          localObject4 = localStatQueryManager.getGageQty((Apply)localObject1);
          if ((localObject4 != null) && (((List)localObject4).size() > 0))
          {
            localObject5 = (Map)((List)localObject4).get(0);
            if ((((Map)localObject5).get("GageQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Map)localObject5).get("GageQty").toString())))
            {
              paramHttpServletRequest.setAttribute("prompt", "卖抵顶数量不足，不能操作！");
              throw new RuntimeException("卖抵顶数量不足，不能操作！");
            }
          }
          else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
          {
            paramHttpServletRequest.setAttribute("prompt", "卖抵顶数量不足，不能操作！");
            throw new RuntimeException("卖抵顶数量不足，不能操作！");
          }
          localObject5 = localStatQueryManager.getHoldQtyAheadSettleB((Apply)localObject1);
          if ((localObject5 != null) && (((List)localObject5).size() > 0))
          {
            localObject6 = (Map)((List)localObject5).get(0);
            if ((((Map)localObject6).get("HoldQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Map)localObject6).get("HoldQty").toString())))
            {
              paramHttpServletRequest.setAttribute("prompt", "买方订货数量不足，不能操作！");
              throw new RuntimeException("买方订货数量不足，不能操作！");
            }
          }
          else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
          {
            paramHttpServletRequest.setAttribute("prompt", "买方订货数量不足，不能操作！");
            throw new RuntimeException("买方订货数量不足，不能操作！");
          }
        }
        else if ("3".equals(localObject3))
        {
          localObject4 = localStatQueryManager.getHoldQtyAheadSettleB((Apply)localObject1);
          if ((localObject4 != null) && (((List)localObject4).size() > 0))
          {
            localObject5 = (Map)((List)localObject4).get(0);
            if ((((Map)localObject5).get("HoldQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Map)localObject5).get("HoldQty").toString())))
            {
              paramHttpServletRequest.setAttribute("prompt", "买方订货数量不足，不能操作！");
              throw new RuntimeException("买方订货数量不足，不能操作！");
            }
          }
          else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
          {
            paramHttpServletRequest.setAttribute("prompt", "买方订货数量不足，不能操作！");
            throw new RuntimeException("买方订货数量不足，不能操作！");
          }
          localObject5 = localStatQueryManager.getHoldQtyAheadSettleS((Apply)localObject1);
          if ((localObject5 != null) && (((List)localObject5).size() > 0))
          {
            localObject6 = (Map)((List)localObject5).get(0);
            if ((((Map)localObject6).get("HoldQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Apply)localObject1).getQuantity().toString())))
            {
              paramHttpServletRequest.setAttribute("prompt", "卖方订货数量不足，不能操作！");
              throw new RuntimeException("卖方订货数量不足，不能操作！");
            }
          }
          else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
          {
            paramHttpServletRequest.setAttribute("prompt", "卖方订货数量不足，不能操作！");
            throw new RuntimeException("卖方订货数量不足，不能操作！");
          }
          localObject6 = localStatQueryManager.validatorCustomer_S((Apply)localObject1);
          if (!((Apply)localObject2).getFirmID_S().equals(localObject6))
          {
            paramHttpServletRequest.setAttribute("prompt", "卖方客户代码对应的交易商与原纪录不符，不能操作！");
            throw new RuntimeException("卖方客户代码对应的交易商与原纪录不符，不能操作！");
          }
        }
        localStatQueryManager.insertApplyAlreadySettle((Apply)localObject1);
        addSysLog(paramHttpServletRequest, "增加抵顶转提前交收申请[3]");
      }
      if ("4".equals(str2))
      {
        str3 = localApplyForm.getCommodityID();
        localApplyForm.setCommodityID(str3);
        localApplyForm.setApplyType("4");
        localApplyForm.setStatus("1");
        localApplyForm.setCreator(str1);
        localObject1 = (Apply)convert(localApplyForm);
        localObject2 = localStatQueryManager.getHoldQtyWaitSettle((Apply)localObject1);
        if ((localObject2 != null) && (((List)localObject2).size() > 0))
        {
          localObject3 = (Map)((List)localObject2).get(0);
          if ((((Map)localObject3).get("HoldQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Map)localObject3).get("HoldQty").toString())))
          {
            paramHttpServletRequest.setAttribute("prompt", "卖方订货数量不足，不能操作！");
            throw new RuntimeException("卖方订货数量不足，不能操作！");
          }
        }
        else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
        {
          paramHttpServletRequest.setAttribute("prompt", "卖方订货数量不足，不能操作！");
          throw new RuntimeException("卖方订货数量不足，不能操作！");
        }
        localStatQueryManager.insertWaitSettle((Apply)localObject1);
        addSysLog(paramHttpServletRequest, "增加等待交收申请[4]");
      }
      if ("5".equals(str2))
      {
        str3 = localApplyForm.getCommodityID();
        localApplyForm.setCommodityID(str3);
        localApplyForm.setApplyType("5");
        localApplyForm.setStatus("1");
        localApplyForm.setCreator(str1);
        localObject1 = (Apply)convert(localApplyForm);
        localObject2 = localStatQueryManager.getHoldQtyDD((Apply)localObject1);
        if ((localObject2 != null) && (((List)localObject2).size() > 0))
        {
          localObject3 = (Map)((List)localObject2).get(0);
          if ((((Map)localObject3).get("HoldQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Map)localObject3).get("HoldQty").toString())))
          {
            paramHttpServletRequest.setAttribute("prompt", "卖方订货数量不足，不够抵顶！");
            throw new RuntimeException("卖方订货数量不足，不够抵顶！");
          }
        }
        else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
        {
          paramHttpServletRequest.setAttribute("prompt", "卖方订货数量不足，不够抵顶！");
          throw new RuntimeException("卖方订货数量不足，不够抵顶！");
        }
        localStatQueryManager.insertDD((Apply)localObject1);
        addSysLog(paramHttpServletRequest, "增加抵顶申请[5]");
      }
      if ("6".equals(str2))
      {
        str3 = localApplyForm.getCommodityID();
        localApplyForm.setCommodityID(str3);
        localApplyForm.setApplyType("6");
        localApplyForm.setStatus("1");
        localApplyForm.setCreator(str1);
        localObject1 = (Apply)convert(localApplyForm);
        localObject2 = localStatQueryManager.getHoldQtyAheadSettleB((Apply)localObject1);
        if ((localObject2 != null) && (((List)localObject2).size() > 0))
        {
          localObject3 = (Map)((List)localObject2).get(0);
          if ((((Map)localObject3).get("HoldQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Map)localObject3).get("HoldQty").toString())))
          {
            paramHttpServletRequest.setAttribute("prompt", "买方订货数量不足，不能操作！");
            throw new RuntimeException("买方订货数量不足，不能操作！");
          }
        }
        else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
        {
          paramHttpServletRequest.setAttribute("prompt", "买方订货数量不足，不能操作！");
          throw new RuntimeException("买方订货数量不足，不能操作！");
        }
        localObject3 = localStatQueryManager.getHoldQtyAheadSettleS((Apply)localObject1);
        if ((localObject3 != null) && (((List)localObject3).size() > 0))
        {
          localObject4 = (Map)((List)localObject3).get(0);
          if ((((Map)localObject4).get("HoldQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Apply)localObject1).getQuantity().toString())))
          {
            paramHttpServletRequest.setAttribute("prompt", "卖方订货数量不足，不能操作！");
            throw new RuntimeException("卖方订货数量不足，不能操作！");
          }
        }
        else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
        {
          paramHttpServletRequest.setAttribute("prompt", "卖方订货数量不足，不能操作！");
          throw new RuntimeException("卖方订货数量不足，不能操作！");
        }
        localStatQueryManager.insertBeforeSettle((Apply)localObject1);
        addSysLog(paramHttpServletRequest, "增加提前交收申请[6]");
      }
      if ("7".equals(str2))
      {
        str3 = localApplyForm.getCommodityID();
        localApplyForm.setCommodityID(str3);
        localApplyForm.setApplyType("7");
        localApplyForm.setStatus("1");
        localApplyForm.setCreator(str1);
        localObject1 = (Apply)convert(localApplyForm);
        localStatQueryManager.inserApplyGiveUpWaitSettle((Apply)localObject1);
        addSysLog(paramHttpServletRequest, "增加撤消等待交收申请[7]");
      }
      if ("8".equals(str2))
      {
        str3 = localApplyForm.getCommodityID();
        localApplyForm.setCommodityID(str3);
        localApplyForm.setApplyType("8");
        localApplyForm.setStatus("1");
        localApplyForm.setCreator(str1);
        localObject1 = (Apply)convert(localApplyForm);
        localStatQueryManager.insertDelaySettle((Apply)localObject1);
        addSysLog(paramHttpServletRequest, "增加延期交收申请[8]");
      }
      if ("9".equals(str2))
      {
        str3 = paramHttpServletRequest.getParameter("applyType");
        if ((str3 != null) && (!"".equals(str3))) {
          localApplyForm.setApplyType(str3);
        }
        localObject1 = localApplyForm.getCommodityID();
        localApplyForm.setCommodityID((String)localObject1);
        localApplyForm.setCreator(str1);
        localApplyForm.setStatus("1");
        localApplyForm.setApplyType("9");
        localObject2 = (Apply)convert(localApplyForm);
        localStatQueryManager.insertApplyGiveUpDelaySettle((Apply)localObject2);
        addSysLog(paramHttpServletRequest, "增加撤销延期交收申请[" + str3 + "]");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.debug("异常原因：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("applySave");
  }
  
  public ActionForward applySaveGiveUp(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applySaveGiveUp' method");
    }
    try
    {
      String str1 = AclCtrl.getLogonID(paramHttpServletRequest);
      ApplyForm localApplyForm = (ApplyForm)paramActionForm;
      StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
      String str2 = paramHttpServletRequest.getParameter("crud");
      String str3;
      Object localObject1;
      Apply localApply;
      if ("1".equals(str2))
      {
        str3 = paramHttpServletRequest.getParameter("applyType");
        if ((str3 != null) && (!"".equals(str3))) {
          localApplyForm.setApplyType(str3);
        }
        localObject1 = localApplyForm.getCommodityID();
        localApplyForm.setCommodityID((String)localObject1);
        localApplyForm.setCreator(str1);
        localApplyForm.setStatus("1");
        localApply = (Apply)convert(localApplyForm);
        localStatQueryManager.insertApplyGiveUpDD(localApply);
        addSysLog(paramHttpServletRequest, "增加撤消抵顶申请[" + str3 + "]");
      }
      if ("3".equals(str2))
      {
        str3 = localApplyForm.getCommodityID();
        localApplyForm.setCommodityID(str3);
        localApplyForm.setApplyType("3");
        localApplyForm.setStatus("1");
        localApplyForm.setCreator(str1);
        localObject1 = (Apply)convert(localApplyForm);
        localApply = localStatQueryManager.getValidIDByApplyID2(localApplyForm.getBillID());
        if (localApply == null) {
          throw new RuntimeException("查询记录不存在！");
        }
        ((Apply)localObject1).setFirmID_S(localApply.getFirmID_S());
        String str4 = "";
        if (localApply.getBillType() != null) {
          str4 = localApply.getBillType().toString();
        }
        List localList;
        Object localObject2;
        Object localObject3;
        if ("1".equals(str4))
        {
          localList = localStatQueryManager.getGageQty((Apply)localObject1);
          if ((localList != null) && (localList.size() > 0))
          {
            localObject2 = (Map)localList.get(0);
            if ((((Map)localObject2).get("GageQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Map)localObject2).get("GageQty").toString())))
            {
              paramHttpServletRequest.setAttribute("prompt", "卖抵顶数量不足，不能操作！");
              throw new RuntimeException("卖抵顶数量不足，不能操作！");
            }
          }
          else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
          {
            paramHttpServletRequest.setAttribute("prompt", "卖抵顶数量不足，不能操作！");
            throw new RuntimeException("卖抵顶数量不足，不能操作！");
          }
          localObject2 = localStatQueryManager.getHoldQtyAheadSettleB((Apply)localObject1);
          if ((localObject2 != null) && (((List)localObject2).size() > 0))
          {
            localObject3 = (Map)((List)localObject2).get(0);
            if ((((Map)localObject3).get("HoldQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Map)localObject3).get("HoldQty").toString())))
            {
              paramHttpServletRequest.setAttribute("prompt", "买方订货数量不足，不能操作！");
              throw new RuntimeException("买方订货数量不足，不能操作！");
            }
          }
          else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
          {
            paramHttpServletRequest.setAttribute("prompt", "买方订货数量不足，不能操作！");
            throw new RuntimeException("买方订货数量不足，不能操作！");
          }
        }
        else if ("3".equals(str4))
        {
          localList = localStatQueryManager.getHoldQtyAheadSettleB((Apply)localObject1);
          if ((localList != null) && (localList.size() > 0))
          {
            localObject2 = (Map)localList.get(0);
            if ((((Map)localObject2).get("HoldQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Map)localObject2).get("HoldQty").toString())))
            {
              paramHttpServletRequest.setAttribute("prompt", "买方订货数量不足，不能操作！");
              throw new RuntimeException("买方订货数量不足，不能操作！");
            }
          }
          else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
          {
            paramHttpServletRequest.setAttribute("prompt", "买方订货数量不足，不能操作！");
            throw new RuntimeException("买方订货数量不足，不能操作！");
          }
          localObject2 = localStatQueryManager.getHoldQtyAheadSettleS((Apply)localObject1);
          if ((localObject2 != null) && (((List)localObject2).size() > 0))
          {
            localObject3 = (Map)((List)localObject2).get(0);
            if ((((Map)localObject3).get("HoldQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Apply)localObject1).getQuantity().toString())))
            {
              paramHttpServletRequest.setAttribute("prompt", "卖方订货数量不足，不能操作！");
              throw new RuntimeException("卖方订货数量不足，不能操作！");
            }
          }
          else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
          {
            paramHttpServletRequest.setAttribute("prompt", "卖方订货数量不足，不能操作！");
            throw new RuntimeException("卖方订货数量不足，不能操作！");
          }
          localObject3 = localStatQueryManager.validatorCustomer_S((Apply)localObject1);
          if (!localApply.getFirmID_S().equals(localObject3))
          {
            paramHttpServletRequest.setAttribute("prompt", "卖方客户代码对应的交易商与原纪录不符，不能操作！");
            throw new RuntimeException("卖方客户代码对应的交易商与原纪录不符，不能操作！");
          }
        }
        localStatQueryManager.insertApplyAlreadySettle((Apply)localObject1);
        addSysLog(paramHttpServletRequest, "增加抵顶转提前交收申请[3]");
      }
      if ("9".equals(str2))
      {
        str3 = paramHttpServletRequest.getParameter("applyType");
        if ((str3 != null) && (!"".equals(str3))) {
          localApplyForm.setApplyType(str3);
        }
        localObject1 = localApplyForm.getCommodityID();
        localApplyForm.setCommodityID((String)localObject1);
        localApplyForm.setCreator(str1);
        localApplyForm.setStatus("1");
        localApplyForm.setApplyType("9");
        localApply = (Apply)convert(localApplyForm);
        localStatQueryManager.insertApplyGiveUpDelaySettle(localApply);
        addSysLog(paramHttpServletRequest, "增加撤销延期交收申请[" + str3 + "]");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.debug("异常原因：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("applySaveGiveUp");
  }
  
  public ActionForward applySaveLiveInfo(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applySaveLiveInfo' method");
    }
    try
    {
      String str1 = AclCtrl.getLogonID(paramHttpServletRequest);
      ApplyForm localApplyForm = (ApplyForm)paramActionForm;
      StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
      String str2 = paramHttpServletRequest.getParameter("crud");
      String str3;
      Object localObject1;
      Apply localApply;
      if ("1".equals(str2))
      {
        str3 = paramHttpServletRequest.getParameter("applyType");
        if ((str3 != null) && (!"".equals(str3))) {
          localApplyForm.setApplyType(str3);
        }
        localObject1 = localApplyForm.getCommodityID();
        localApplyForm.setCommodityID((String)localObject1);
        localApplyForm.setCreator(str1);
        localApplyForm.setStatus("1");
        localApply = (Apply)convert(localApplyForm);
        localStatQueryManager.insertApplyGiveUpDD(localApply);
        addSysLog(paramHttpServletRequest, "增加撤消抵顶申请[" + str3 + "]");
      }
      if ("3".equals(str2))
      {
        str3 = localApplyForm.getCommodityID();
        localApplyForm.setCommodityID(str3);
        localApplyForm.setApplyType("3");
        localApplyForm.setStatus("1");
        localApplyForm.setCreator(str1);
        localObject1 = (Apply)convert(localApplyForm);
        localApply = localStatQueryManager.getValidIDByApplyID2(localApplyForm.getBillID());
        if (localApply == null) {
          throw new RuntimeException("查询记录不存在！");
        }
        ((Apply)localObject1).setFirmID_S(localApply.getFirmID_S());
        String str4 = "";
        if (localApply.getBillType() != null) {
          str4 = localApply.getBillType().toString();
        }
        List localList;
        Object localObject2;
        Object localObject3;
        if ("1".equals(str4))
        {
          localList = localStatQueryManager.getGageQty((Apply)localObject1);
          if ((localList != null) && (localList.size() > 0))
          {
            localObject2 = (Map)localList.get(0);
            if ((((Map)localObject2).get("GageQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Map)localObject2).get("GageQty").toString())))
            {
              paramHttpServletRequest.setAttribute("prompt", "卖抵顶数量不足，不能操作！");
              throw new RuntimeException("卖抵顶数量不足，不能操作！");
            }
          }
          else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
          {
            paramHttpServletRequest.setAttribute("prompt", "卖抵顶数量不足，不能操作！");
            throw new RuntimeException("卖抵顶数量不足，不能操作！");
          }
          localObject2 = localStatQueryManager.getHoldQtyAheadSettleB((Apply)localObject1);
          if ((localObject2 != null) && (((List)localObject2).size() > 0))
          {
            localObject3 = (Map)((List)localObject2).get(0);
            if ((((Map)localObject3).get("HoldQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Map)localObject3).get("HoldQty").toString())))
            {
              paramHttpServletRequest.setAttribute("prompt", "买方订货数量不足，不能操作！");
              throw new RuntimeException("买方订货数量不足，不能操作！");
            }
          }
          else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
          {
            paramHttpServletRequest.setAttribute("prompt", "买方订货数量不足，不能操作！");
            throw new RuntimeException("买方订货数量不足，不能操作！");
          }
        }
        else if ("3".equals(str4))
        {
          localList = localStatQueryManager.getHoldQtyAheadSettleB((Apply)localObject1);
          if ((localList != null) && (localList.size() > 0))
          {
            localObject2 = (Map)localList.get(0);
            if ((((Map)localObject2).get("HoldQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Map)localObject2).get("HoldQty").toString())))
            {
              paramHttpServletRequest.setAttribute("prompt", "买方订货数量不足，不能操作！");
              throw new RuntimeException("买方订货数量不足，不能操作！");
            }
          }
          else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
          {
            paramHttpServletRequest.setAttribute("prompt", "买方订货数量不足，不能操作！");
            throw new RuntimeException("买方订货数量不足，不能操作！");
          }
          localObject2 = localStatQueryManager.getHoldQtyAheadSettleS((Apply)localObject1);
          if ((localObject2 != null) && (((List)localObject2).size() > 0))
          {
            localObject3 = (Map)((List)localObject2).get(0);
            if ((((Map)localObject3).get("HoldQty") != null) && (((Apply)localObject1).getQuantity().longValue() > Long.parseLong(((Apply)localObject1).getQuantity().toString())))
            {
              paramHttpServletRequest.setAttribute("prompt", "卖方订货数量不足，不能操作！");
              throw new RuntimeException("卖方订货数量不足，不能操作！");
            }
          }
          else if (((Apply)localObject1).getQuantity().longValue() > Long.parseLong("0"))
          {
            paramHttpServletRequest.setAttribute("prompt", "卖方订货数量不足，不能操作！");
            throw new RuntimeException("卖方订货数量不足，不能操作！");
          }
          localObject3 = localStatQueryManager.validatorCustomer_S((Apply)localObject1);
          if (!localApply.getFirmID_S().equals(localObject3))
          {
            paramHttpServletRequest.setAttribute("prompt", "卖方客户代码对应的交易商与原纪录不符，不能操作！");
            throw new RuntimeException("卖方客户代码对应的交易商与原纪录不符，不能操作！");
          }
        }
        localStatQueryManager.insertApplyAlreadySettle((Apply)localObject1);
        addSysLog(paramHttpServletRequest, "增加抵顶转提前交收申请[3]");
      }
      if ("9".equals(str2))
      {
        str3 = paramHttpServletRequest.getParameter("applyType");
        if ((str3 != null) && (!"".equals(str3))) {
          localApplyForm.setApplyType(str3);
        }
        localObject1 = localApplyForm.getCommodityID();
        localApplyForm.setCommodityID((String)localObject1);
        localApplyForm.setCreator(str1);
        localApplyForm.setStatus("1");
        localApplyForm.setApplyType("9");
        localApply = (Apply)convert(localApplyForm);
        localStatQueryManager.insertApplyGiveUpDelaySettle(localApply);
        addSysLog(paramHttpServletRequest, "增加撤销延期交收申请[" + str3 + "]");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.debug("异常原因：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("applySaveLiveInfo");
  }
  
  public ActionForward applyWaitListCheck(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyWaitListCheck' method");
    }
    try
    {
      String str = (String)paramHttpServletRequest.getAttribute("save");
      Object localObject1;
      Object localObject2;
      Object localObject3;
      if (!"save".equals(str))
      {
        localObject1 = paramHttpServletRequest.getParameter("firmID_S");
        localObject2 = paramHttpServletRequest.getParameter("commodityID");
        localObject3 = new Apply();
        ((Apply)localObject3).setFirmID_S((String)localObject1);
        ((Apply)localObject3).setCommodityID((String)localObject2);
        StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
        List localList = localStatQueryManager.getApplyWaitCD((Apply)localObject3);
        paramHttpServletRequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
        paramHttpServletRequest.setAttribute("APPLYTYPE", CommonDictionary.APPLYTYPE);
        paramHttpServletRequest.setAttribute("applyWaitList", localList);
      }
      else
      {
        localObject1 = new Apply();
        localObject2 = (StatQueryManager)getBean("statQueryManager");
        localObject3 = ((StatQueryManager)localObject2).getApplyWaitCD((Apply)localObject1);
        paramHttpServletRequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
        paramHttpServletRequest.setAttribute("APPLYTYPE", CommonDictionary.APPLYTYPE);
        paramHttpServletRequest.setAttribute("applyWaitList", localObject3);
      }
    }
    catch (Exception localException)
    {
      this.log.debug("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("applyWaitListCheck");
  }
  
  public ActionForward applyAlreadyListCheck(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyAlreadyListCheck' method");
    }
    try
    {
      String str1 = paramHttpServletRequest.getParameter("firmID_S");
      String str2 = paramHttpServletRequest.getParameter("commodityID");
      Apply localApply = new Apply();
      localApply.setFirmID_S(str1);
      localApply.setCommodityID(str2);
      StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
      List localList = localStatQueryManager.getApplyAlreadyCD(localApply);
      paramHttpServletRequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
      paramHttpServletRequest.setAttribute("APPLYTYPE", CommonDictionary.APPLYTYPE);
      paramHttpServletRequest.setAttribute("applyAlreadyList", localList);
    }
    catch (Exception localException)
    {
      this.log.debug("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("applyAlreadyListCheck");
  }
  
  public ActionForward applyWaitListSuccessCheck(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyWaitListSuccessCheck' method");
    }
    try
    {
      String str1 = AclCtrl.getLogonID(paramHttpServletRequest);
      AgencyRMIBean localAgencyRMIBean = new AgencyRMIBean(paramHttpServletRequest);
      ApplyForm localApplyForm = (ApplyForm)paramActionForm;
      String str2 = paramHttpServletRequest.getParameter("type");
      StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
      Object localObject1;
      Object localObject2;
      if ("1".equals(str2))
      {
        localObject1 = new ApplyBill();
        localObject2 = localApplyForm.getApplyType();
        Apply localApply1;
        Object localObject4;
        if (("1".equals(localObject2)) || ("2".equals(localObject2)))
        {
          localApply1 = localStatQueryManager.getValidIDByApplyIDGiveup(localApplyForm.getBillID());
          if ((localApply1 != null) && (localApply1.getValidID() != null)) {
            ((ApplyBill)localObject1).setValidID(Long.valueOf(Long.parseLong(localApply1.getValidID().toString())));
          }
          if ((localObject2 != null) && (!"".equals(localObject2))) {
            ((ApplyBill)localObject1).setApplyType(Short.valueOf(Short.parseShort((String)localObject2)));
          }
          ((ApplyBill)localObject1).setModifier(str1);
          int k = localAgencyRMIBean.gageCancel((ApplyBill)localObject1);
          if (k == 1)
          {
            localObject4 = new Apply();
            if ((localApplyForm.getApplyID() != null) && (!"".equals(localApplyForm.getApplyID()))) {
              ((Apply)localObject4).setApplyID(Long.valueOf(Long.parseLong(localApplyForm.getApplyID())));
            }
            if ((localApplyForm.getBillID() != null) && (!"".equals(localApplyForm.getBillID()))) {
              ((Apply)localObject4).setBillID(localApplyForm.getBillID());
            }
            if ((localApplyForm.getApplyType() != null) && (!"".equals(localApplyForm.getApplyType()))) {
              ((Apply)localObject4).setApplyType(Short.valueOf(Short.parseShort(localApplyForm.getApplyType())));
            }
            localStatQueryManager.updateApplyCheckSuccess((Apply)localObject4);
            addSysLog(paramHttpServletRequest, "取消抵顶成功！");
            localStatQueryManager.deleteApplyGiveupDD((Apply)localObject4);
            addSysLog(paramHttpServletRequest, "删除撤消抵顶和对应抵顶成功！");
          }
          if (k == 2) {
            paramHttpServletRequest.setAttribute("prompt", "已处理过！");
          }
          if (k == -11) {
            paramHttpServletRequest.setAttribute("prompt", "取消抵顶时超出抵顶数量！");
          }
          if (k == -12) {
            paramHttpServletRequest.setAttribute("prompt", "取消抵顶数量大于抵顶数量！");
          }
          if (k == -13) {
            paramHttpServletRequest.setAttribute("prompt", "资金余额不足！");
          }
          if (k == -100) {
            paramHttpServletRequest.setAttribute("prompt", "其它错误！");
          }
          if (k == -202) {
            paramHttpServletRequest.setAttribute("prompt", "不是闭市或结算完成状态，不能取消抵顶！");
          }
          if (k == -203) {
            paramHttpServletRequest.setAttribute("prompt", "正在闭市结算，不能取消抵顶！");
          }
          if (k == -204) {
            paramHttpServletRequest.setAttribute("prompt", "下单服务器已关闭！");
          }
        }
        Object localObject3;
        if ("3".equals(localObject2))
        {
          localApply1 = localStatQueryManager.getValidIDByApplyID2(localApplyForm.getBillID());
          if (localApply1 == null) {
            throw new RuntimeException("查询记录不存在！");
          }
          localObject3 = "";
          localObject4 = "";
          if (localApply1.getValidID() != null) {
            localObject3 = localApply1.getValidID().toString();
          }
          if (localApply1.getBillType() != null) {
            localObject4 = localApply1.getBillType().toString();
          }
          String str3 = "";
          List localList;
          Object localObject5;
          if ("1".equals(localObject4))
          {
            localList = localStatQueryManager.getQuantity(localApplyForm.getApplyID(), "");
            if ((localList != null) && (localList.size() > 0))
            {
              localObject5 = (Map)localList.get(0);
              if (((Map)localObject5).get("Quantity") != null) {
                str3 = ((Map)localObject5).get("Quantity").toString();
              } else {
                str3 = "0";
              }
            }
            if ((str3 != null) && (!"".equals(str3)))
            {
              ((ApplyBill)localObject1).setQuantity(Long.valueOf(Long.parseLong(str3)));
              ((ApplyBill)localObject1).setSGageQty(Long.valueOf(Long.parseLong(str3)));
              ((ApplyBill)localObject1).setBGageQty(Long.valueOf(Long.parseLong("0")));
            }
          }
          else if ("3".equals(localObject4))
          {
            localList = localStatQueryManager.getQuantity(localApplyForm.getApplyID(), "4");
            if ((localList != null) && (localList.size() > 0))
            {
              localObject5 = (Map)localList.get(0);
              if (((Map)localObject5).get("Quantity") != null) {
                str3 = ((Map)localObject5).get("Quantity").toString();
              } else {
                str3 = "0";
              }
            }
            if ((str3 != null) && (!"".equals(str3)))
            {
              ((ApplyBill)localObject1).setQuantity(Long.valueOf(Long.parseLong(str3)));
              ((ApplyBill)localObject1).setSGageQty(Long.valueOf(Long.parseLong("0")));
              ((ApplyBill)localObject1).setBGageQty(Long.valueOf(Long.parseLong("0")));
            }
          }
          if ((localApplyForm.getApplyID() != null) && (!"".equals(localApplyForm.getApplyID()))) {
            ((ApplyBill)localObject1).setApplyID(Long.valueOf(Long.parseLong(localApplyForm.getApplyID())));
          }
          ((ApplyBill)localObject1).setCommodityID(localApplyForm.getCommodityID());
          ((ApplyBill)localObject1).setBillID(localApplyForm.getBillID());
          if ((localApplyForm.getPrice() != null) && (!"".equals(localApplyForm.getPrice()))) {
            ((ApplyBill)localObject1).setPrice(Double.valueOf(Double.parseDouble(localApplyForm.getPrice())));
          }
          ((ApplyBill)localObject1).setCustomerID_S(localApplyForm.getCustomerID_S());
          ((ApplyBill)localObject1).setCustomerID_B(localApplyForm.getCustomerID_B());
          ((ApplyBill)localObject1).setModifier(str1);
          if ((localObject2 != null) && (!"".equals(localObject2))) {
            ((ApplyBill)localObject1).setApplyType(Short.valueOf(Short.parseShort((String)localObject2)));
          }
          if ((localObject3 != null) && (!"".equals(localObject3))) {
            ((ApplyBill)localObject1).setValidID(Long.valueOf(Long.parseLong((String)localObject3)));
          }
          int i1 = localAgencyRMIBean.aheadSettle((ApplyBill)localObject1);
          if (i1 == 1)
          {
            localObject5 = new Apply();
            if ((localApplyForm.getApplyID() != null) && (!"".equals(localApplyForm.getApplyID()))) {
              ((Apply)localObject5).setApplyID(Long.valueOf(Long.parseLong(localApplyForm.getApplyID())));
            }
            localStatQueryManager.updateApplyCheckSuccess((Apply)localObject5);
            addSysLog(paramHttpServletRequest, "抵顶转提前交收成功！");
          }
          if (i1 == 2) {
            paramHttpServletRequest.setAttribute("prompt", "已处理过！");
          }
          if (i1 == -1) {
            paramHttpServletRequest.setAttribute("prompt", "可交收买订货数量不足！");
          }
          if (i1 == -2) {
            paramHttpServletRequest.setAttribute("prompt", "可交收买抵顶数量不足！");
          }
          if (i1 == -3) {
            paramHttpServletRequest.setAttribute("prompt", "交收买订货数量大于可交收买订货数量！");
          }
          if (i1 == -4) {
            paramHttpServletRequest.setAttribute("prompt", "交收买抵顶数量大于可买抵顶数量！");
          }
          if (i1 == -11) {
            paramHttpServletRequest.setAttribute("prompt", "可交收卖订货数量不足！");
          }
          if (i1 == -12) {
            paramHttpServletRequest.setAttribute("prompt", "可交收卖抵顶数量不足！");
          }
          if (i1 == -13) {
            paramHttpServletRequest.setAttribute("prompt", "交收卖订货数量大于可交收卖订货数量！");
          }
          if (i1 == -14) {
            paramHttpServletRequest.setAttribute("prompt", "交收卖抵顶数量大于可卖抵顶数！");
          }
          if (i1 == -100) {
            paramHttpServletRequest.setAttribute("prompt", "其它错误！");
          }
          if (i1 == -21) {
            paramHttpServletRequest.setAttribute("prompt", "买交易客户ID不存在！");
          }
          if (i1 == -31) {
            paramHttpServletRequest.setAttribute("prompt", "卖交易客户ID不存在！");
          }
          if (i1 == -41) {
            paramHttpServletRequest.setAttribute("prompt", "买卖交收数量不相等！");
          }
          if (i1 == -202) {
            paramHttpServletRequest.setAttribute("prompt", "不是闭市或结算完成状态，不能提前交收！");
          }
          if (i1 == -204) {
            paramHttpServletRequest.setAttribute("prompt", "下单服务器已关闭！");
          }
        }
        int i;
        if ("4".equals(localObject2))
        {
          if ((localApplyForm.getApplyID() != null) && (!"".equals(localApplyForm.getApplyID()))) {
            ((ApplyBill)localObject1).setApplyID(Long.valueOf(Long.parseLong(localApplyForm.getApplyID())));
          }
          ((ApplyBill)localObject1).setCommodityID(localApplyForm.getCommodityID());
          ((ApplyBill)localObject1).setFirmID_S(localApplyForm.getFirmID_S());
          ((ApplyBill)localObject1).setBillID(localApplyForm.getBillID());
          if ((localApplyForm.getQuantity() != null) && (!"".equals(localApplyForm.getQuantity()))) {
            ((ApplyBill)localObject1).setQuantity(Long.valueOf(Long.parseLong(localApplyForm.getQuantity())));
          }
          ((ApplyBill)localObject1).setModifier(str1);
          i = localAgencyRMIBean.waitSettle((ApplyBill)localObject1);
          if (i == 1)
          {
            localObject3 = new Apply();
            if ((localApplyForm.getApplyID() != null) && (!"".equals(localApplyForm.getApplyID()))) {
              ((Apply)localObject3).setApplyID(Long.valueOf(Long.parseLong(localApplyForm.getApplyID())));
            }
            localStatQueryManager.updateApplyCheckSuccess((Apply)localObject3);
            addSysLog(paramHttpServletRequest, "等待交收成功！");
          }
          if (i == 2) {
            paramHttpServletRequest.setAttribute("prompt", "已处理过！");
          }
          if (i == -100) {
            paramHttpServletRequest.setAttribute("prompt", "等待交收失败！");
          }
        }
        if ("5".equals(localObject2))
        {
          if ((localApplyForm.getApplyID() != null) && (!"".equals(localApplyForm.getApplyID()))) {
            ((ApplyBill)localObject1).setApplyID(Long.valueOf(Long.parseLong(localApplyForm.getApplyID())));
          }
          ((ApplyBill)localObject1).setCommodityID(localApplyForm.getCommodityID());
          ((ApplyBill)localObject1).setCustomerID_S(localApplyForm.getCustomerID_S());
          ((ApplyBill)localObject1).setBillID(localApplyForm.getBillID());
          if ((localApplyForm.getQuantity() != null) && (!"".equals(localApplyForm.getQuantity()))) {
            ((ApplyBill)localObject1).setQuantity(Long.valueOf(Long.parseLong(localApplyForm.getQuantity())));
          }
          ((ApplyBill)localObject1).setModifier(str1);
          ((ApplyBill)localObject1).setFirmID_S(localApplyForm.getFirmID_S());
          i = localAgencyRMIBean.gage((ApplyBill)localObject1);
          if (i == 1)
          {
            localObject3 = new Apply();
            if ((localApplyForm.getApplyID() != null) && (!"".equals(localApplyForm.getApplyID()))) {
              ((Apply)localObject3).setApplyID(Long.valueOf(Long.parseLong(localApplyForm.getApplyID())));
            }
            localStatQueryManager.updateApplyCheckSuccess((Apply)localObject3);
            addSysLog(paramHttpServletRequest, "抵顶成功！");
          }
          if (i == 2) {
            paramHttpServletRequest.setAttribute("prompt", "已处理过！");
          }
          if (i == -1) {
            paramHttpServletRequest.setAttribute("prompt", "交易商订货量不足！");
          }
          if (i == -2) {
            paramHttpServletRequest.setAttribute("prompt", "交易商订货量不足！");
          }
          if (i == -100) {
            paramHttpServletRequest.setAttribute("prompt", "其它错误！");
          }
          if (i == -202) {
            paramHttpServletRequest.setAttribute("prompt", "不是闭市或结算完成状态，不能抵顶！");
          }
          if (i == -203) {
            paramHttpServletRequest.setAttribute("prompt", "正在闭市结算，不能抵顶！");
          }
          if (i == -204) {
            paramHttpServletRequest.setAttribute("prompt", "下单服务器已关闭！");
          }
        }
        if ("6".equals(localObject2))
        {
          if ((localApplyForm.getApplyID() != null) && (!"".equals(localApplyForm.getApplyID()))) {
            ((ApplyBill)localObject1).setApplyID(Long.valueOf(Long.parseLong(localApplyForm.getApplyID())));
          }
          ((ApplyBill)localObject1).setCommodityID(localApplyForm.getCommodityID());
          ((ApplyBill)localObject1).setBillID(localApplyForm.getBillID());
          if ((localApplyForm.getQuantity() != null) && (!"".equals(localApplyForm.getQuantity()))) {
            ((ApplyBill)localObject1).setQuantity(Long.valueOf(Long.parseLong(localApplyForm.getQuantity())));
          }
          if ((localApplyForm.getPrice() != null) && (!"".equals(localApplyForm.getPrice()))) {
            ((ApplyBill)localObject1).setPrice(Double.valueOf(Double.parseDouble(localApplyForm.getPrice())));
          }
          ((ApplyBill)localObject1).setCustomerID_S(localApplyForm.getCustomerID_S());
          ((ApplyBill)localObject1).setSGageQty(Long.valueOf(Long.parseLong("0")));
          ((ApplyBill)localObject1).setCustomerID_B(localApplyForm.getCustomerID_B());
          ((ApplyBill)localObject1).setBGageQty(Long.valueOf(Long.parseLong("0")));
          ((ApplyBill)localObject1).setModifier(str1);
          ((ApplyBill)localObject1).setApplyType(Short.valueOf(Short.parseShort((String)localObject2)));
          i = localAgencyRMIBean.aheadSettle((ApplyBill)localObject1);
          if (i == 1)
          {
            localObject3 = new Apply();
            if ((localApplyForm.getApplyID() != null) && (!"".equals(localApplyForm.getApplyID()))) {
              ((Apply)localObject3).setApplyID(Long.valueOf(Long.parseLong(localApplyForm.getApplyID())));
            }
            localStatQueryManager.updateApplyCheckSuccess((Apply)localObject3);
            addSysLog(paramHttpServletRequest, "提前交收成功！");
          }
          if (i == 2) {
            paramHttpServletRequest.setAttribute("prompt", "已处理过！");
          }
          if (i == -1) {
            paramHttpServletRequest.setAttribute("prompt", "可交收买订货数量不足！");
          }
          if (i == -2) {
            paramHttpServletRequest.setAttribute("prompt", "可交收买抵顶数量不足！");
          }
          if (i == -3) {
            paramHttpServletRequest.setAttribute("prompt", "可交收买订货数量不足！");
          }
          if (i == -4) {
            paramHttpServletRequest.setAttribute("prompt", "买抵顶数量不足！");
          }
          if (i == -11) {
            paramHttpServletRequest.setAttribute("prompt", "可交收卖订货数量不足！");
          }
          if (i == -12) {
            paramHttpServletRequest.setAttribute("prompt", "可交收卖抵顶数量不足！");
          }
          if (i == -13) {
            paramHttpServletRequest.setAttribute("prompt", "可交收卖订货数量不足！");
          }
          if (i == -14) {
            paramHttpServletRequest.setAttribute("prompt", "卖抵顶数量不足！");
          }
          if (i == -100) {
            paramHttpServletRequest.setAttribute("prompt", "其它错误！");
          }
          if (i == -21) {
            paramHttpServletRequest.setAttribute("prompt", "买交易客户ID不存在！");
          }
          if (i == -31) {
            paramHttpServletRequest.setAttribute("prompt", "卖交易客户ID不存在！");
          }
          if (i == -41) {
            paramHttpServletRequest.setAttribute("prompt", "买卖交收数量不相等！");
          }
          if (i == -202) {
            paramHttpServletRequest.setAttribute("prompt", "不是闭市或结算完成状态，不能提前交收！");
          }
          if (i == -203) {
            paramHttpServletRequest.setAttribute("prompt", "正在闭市结算，不能提前交收！");
          }
          if (i == -204) {
            paramHttpServletRequest.setAttribute("prompt", "下单服务器已关闭！");
          }
        }
        if ("7".equals(localObject2))
        {
          Apply localApply2 = localStatQueryManager.getValidIDByApplyID(localApplyForm.getBillID());
          if (localApply2 == null) {
            throw new RuntimeException("查询记录不存在！");
          }
          localObject3 = "";
          localObject4 = "";
          if (localApply2.getValidID() != null) {
            localObject3 = localApply2.getValidID().toString();
          }
          if (localApply2.getBillType() != null) {
            localObject4 = localApply2.getBillType().toString();
          }
          if ((localObject3 != null) && (!"".equals(localObject3))) {
            ((ApplyBill)localObject1).setValidID(Long.valueOf(Long.parseLong((String)localObject3)));
          }
          ((ApplyBill)localObject1).setModifier(str1);
          int n = localAgencyRMIBean.waitSettleCancel((ApplyBill)localObject1);
          if (n == 1)
          {
            Apply localApply4 = new Apply();
            if ((localApplyForm.getApplyID() != null) && (!"".equals(localApplyForm.getApplyID()))) {
              localApply4.setApplyID(Long.valueOf(Long.parseLong(localApplyForm.getApplyID())));
            }
            localApply4.setBillID(localApplyForm.getBillID());
            localStatQueryManager.updateApplyCheckSuccess(localApply4);
            addSysLog(paramHttpServletRequest, "撤消等待交收成功！");
            localStatQueryManager.deleteApplyWaitSettle(localApply4);
            addSysLog(paramHttpServletRequest, "删除撤消等待交收记录和对应等待交收记录成功！");
          }
          if (n == 2) {
            paramHttpServletRequest.setAttribute("prompt", "已处理过！");
          }
          if (n == -100) {
            paramHttpServletRequest.setAttribute("prompt", "撤消等待交收失败！");
          }
          if (n == -204) {
            paramHttpServletRequest.setAttribute("prompt", "下单服务器已关闭！");
          }
        }
        if ("8".equals(localObject2))
        {
          if ((localApplyForm.getApplyID() != null) && (!"".equals(localApplyForm.getApplyID()))) {
            ((ApplyBill)localObject1).setApplyID(Long.valueOf(Long.parseLong(localApplyForm.getApplyID())));
          }
          ((ApplyBill)localObject1).setCommodityID(localApplyForm.getCommodityID());
          ((ApplyBill)localObject1).setFirmID_S(localApplyForm.getFirmID_S());
          ((ApplyBill)localObject1).setBillID(localApplyForm.getBillID());
          if ((localApplyForm.getQuantity() != null) && (!"".equals(localApplyForm.getQuantity()))) {
            ((ApplyBill)localObject1).setQuantity(Long.valueOf(Long.parseLong(localApplyForm.getQuantity())));
          }
          ((ApplyBill)localObject1).setModifier(str1);
          int j = localAgencyRMIBean.delaySettleBill((ApplyBill)localObject1);
          if (j == 1)
          {
            localObject3 = new Apply();
            if ((localApplyForm.getApplyID() != null) && (!"".equals(localApplyForm.getApplyID()))) {
              ((Apply)localObject3).setApplyID(Long.valueOf(Long.parseLong(localApplyForm.getApplyID())));
            }
            localStatQueryManager.updateApplyCheckSuccess((Apply)localObject3);
            addSysLog(paramHttpServletRequest, "延期交收成功！");
          }
          else if (j == 2)
          {
            paramHttpServletRequest.setAttribute("prompt", "已处理过！");
          }
          else if (j == -100)
          {
            paramHttpServletRequest.setAttribute("prompt", "延期交收失败！");
          }
          else if (j == -204)
          {
            paramHttpServletRequest.setAttribute("prompt", "交易服务器已关闭！");
          }
        }
        if ("9".equals(localObject2))
        {
          Apply localApply3 = localStatQueryManager.getValidIDByApplyIDGiveupDelaySettle(localApplyForm.getBillID());
          if ((localApply3 != null) && (localApply3.getValidID() != null)) {
            ((ApplyBill)localObject1).setValidID(Long.valueOf(Long.parseLong(localApply3.getValidID().toString())));
          }
          if ((localObject2 != null) && (!"".equals(localObject2))) {
            ((ApplyBill)localObject1).setApplyType(Short.valueOf(Short.parseShort((String)localObject2)));
          }
          ((ApplyBill)localObject1).setModifier(str1);
          int m = localAgencyRMIBean.delaySettleBillCancel((ApplyBill)localObject1);
          if (m == 1)
          {
            localObject4 = new Apply();
            if ((localApplyForm.getApplyID() != null) && (!"".equals(localApplyForm.getApplyID()))) {
              ((Apply)localObject4).setApplyID(Long.valueOf(Long.parseLong(localApplyForm.getApplyID())));
            }
            if ((localApplyForm.getBillID() != null) && (!"".equals(localApplyForm.getBillID()))) {
              ((Apply)localObject4).setBillID(localApplyForm.getBillID());
            }
            if ((localApplyForm.getApplyType() != null) && (!"".equals(localApplyForm.getApplyType()))) {
              ((Apply)localObject4).setApplyType(Short.valueOf(Short.parseShort(localApplyForm.getApplyType())));
            }
            localStatQueryManager.updateApplyCheckSuccess((Apply)localObject4);
            addSysLog(paramHttpServletRequest, "撤销延期交收成功！");
            localStatQueryManager.deleteApplyGiveupDelaySettle((Apply)localObject4);
            addSysLog(paramHttpServletRequest, "删除撤销延期交收与对应的延期交收成功！");
          }
          else if (m == 2)
          {
            paramHttpServletRequest.setAttribute("prompt", "已处理过！");
          }
          else if (m == 3)
          {
            paramHttpServletRequest.setAttribute("prompt", "存在冻结数量，不能撤销！");
          }
          else if (m == -100)
          {
            paramHttpServletRequest.setAttribute("prompt", "撤销延期交收失败！");
          }
          else if (m == -204)
          {
            paramHttpServletRequest.setAttribute("prompt", "交易服务器已关闭！");
          }
        }
      }
      else if ("2".equals(str2))
      {
        localObject1 = paramHttpServletRequest.getParameter("applyID");
        localObject2 = new Apply();
        if ((localObject1 != null) && (!"".equals(localObject1)))
        {
          ((Apply)localObject2).setApplyID(Long.valueOf(Long.parseLong((String)localObject1)));
          localStatQueryManager.updateApplyCheckFail((Apply)localObject2);
          addSysLog(paramHttpServletRequest, "审核不通过！");
        }
        else
        {
          paramHttpServletRequest.setAttribute("prompt", "申请单号为空！");
        }
      }
      paramHttpServletRequest.setAttribute("save", "save");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("applyWaitListSuccessCheck");
  }
  
  public ActionForward applyWaitListCheckFail(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyWaitListCheckFail' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    String str = paramHttpServletRequest.getParameter("applyID");
    Apply localApply = new Apply();
    try
    {
      if ((str != null) && (!"".equals(str)))
      {
        localApply.setApplyID(Long.valueOf(Long.parseLong(str)));
        localStatQueryManager.updateApplyCheckFail(localApply);
        paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        addSysLog(paramHttpServletRequest, "审核不通过！");
      }
      else
      {
        paramHttpServletRequest.setAttribute("prompt", "申请单号为空！");
      }
    }
    catch (Exception localException)
    {
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("applyWaitListCheckFail");
  }
  
  public ActionForward giveUp(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'giveUp' method");
    }
    try
    {
      ApplyForm localApplyForm = (ApplyForm)paramActionForm;
      StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
      String str1 = paramHttpServletRequest.getParameter("billID");
      String str2 = paramHttpServletRequest.getParameter("billType");
      Apply localApply1 = localStatQueryManager.getValidIDByApplyID2(str1);
      Apply localApply2;
      if ("1".equals(str2))
      {
        localApply2 = localStatQueryManager.getGiveUpDD(localApply1.getBillID());
        localApplyForm.setCommodityID(localApply2.getCommodityID());
        localApplyForm.setCustomerID_S(localApply2.getCustomerID_S());
        localApplyForm.setBillID(localApply2.getBillID());
        if (localApply2.getQuantity() != null) {
          localApplyForm.setQuantity(localApply2.getQuantity().toString());
        }
        localApplyForm.setCrud("1");
        updateFormBean(paramActionMapping, paramHttpServletRequest, localApplyForm);
        return paramActionMapping.findForward("liveInfoDD");
      }
      if ("5".equals(str2))
      {
        localApply2 = localStatQueryManager.getGiveUpDelaySettle(localApply1.getBillID());
        localApplyForm.setCommodityID(localApply2.getCommodityID());
        localApplyForm.setFirmID_S(localApply2.getFirmID_S());
        localApplyForm.setBillID(localApply2.getBillID());
        if (localApply2.getQuantity() != null) {
          localApplyForm.setQuantity(localApply2.getQuantity().toString());
        }
        localApplyForm.setCrud("9");
        localApplyForm.setApplyType("9");
        updateFormBean(paramActionMapping, paramHttpServletRequest, localApplyForm);
        return paramActionMapping.findForward("liveInfoDelaySettle");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("liveInfoDD");
  }
  
  public ActionForward before(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'before' method");
    }
    try
    {
      ApplyForm localApplyForm = (ApplyForm)paramActionForm;
      StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
      String str1 = paramHttpServletRequest.getParameter("billID");
      String str2 = paramHttpServletRequest.getParameter("billType");
      Apply localApply = localStatQueryManager.getValidIDByApplyID2LiveinfoBefore(str1);
      if (localApply.getApplyID() != null) {
        localApplyForm.setApplyID(localApply.getApplyID().toString());
      }
      localApplyForm.setCommodityID(localApply.getCommodityID());
      localApplyForm.setFirmID_S(localApply.getFirmID_S());
      localApplyForm.setCustomerID_S(localApply.getCustomerID_S());
      localApplyForm.setBillID(localApply.getBillID());
      if (localApply.getQuantity() != null) {
        localApplyForm.setQuantity(localApply.getQuantity().toString());
      }
      localApplyForm.setApplyType("3");
      if (localApply.getStatus() != null) {
        localApplyForm.setStatus(localApply.getStatus().toString());
      }
      localApplyForm.setBillType(str2);
      paramHttpServletRequest.setAttribute("applyType", "3");
      localApplyForm.setCrud("3");
      updateFormBean(paramActionMapping, paramHttpServletRequest, localApplyForm);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("liveInfoBefore");
  }
}
