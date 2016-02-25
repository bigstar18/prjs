package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.timebargain.manage.model.LabelValue;
import gnnt.MEBS.timebargain.manage.model.TradeBreedRuleGC;
import gnnt.MEBS.timebargain.manage.model.TradeRuleGC;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.service.TradeBreedRuleManager;
import gnnt.MEBS.timebargain.manage.service.TradeRuleManager;
import gnnt.MEBS.timebargain.manage.util.Arith;
import gnnt.MEBS.timebargain.manage.webapp.form.TradeBreedRuleGCForm;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.dao.DataIntegrityViolationException;

public class TradeBreedRuleAction
  extends BaseAction
{
  public ActionForward editGroup(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editGroup' method");
    }
    TradeBreedRuleGCForm localTradeBreedRuleGCForm = (TradeBreedRuleGCForm)paramActionForm;
    String str1 = localTradeBreedRuleGCForm.getCrud();
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    TradeBreedRuleGC localTradeBreedRuleGC = null;
    try
    {
      getSelectAttribute(paramHttpServletRequest);
      String str2 = "2";
      String str3 = "2";
      String str4 = "2";
      String str5 = "2";
      String str6 = "2";
      if (!str1.trim().equals("create"))
      {
        localTradeBreedRuleGC = localTradeBreedRuleManager.getGM_TradeBreedRuleById(new Integer(localTradeBreedRuleGCForm.getGroupID()), localTradeBreedRuleGCForm.getBreedID());
        if ((localTradeBreedRuleGC.getMarginAssure_B().toString().equals(localTradeBreedRuleGC.getMarginAssure_S().toString())) && (localTradeBreedRuleGC.getMarginRate_B().toString().equals(localTradeBreedRuleGC.getMarginRate_S().toString()))) {
          str2 = "1";
        }
        if ((localTradeBreedRuleGC.getMarginItem1().toString().equals(localTradeBreedRuleGC.getMarginItem1_S().toString())) && (localTradeBreedRuleGC.getMarginItemAssure1().toString().equals(localTradeBreedRuleGC.getMarginItemAssure1_S().toString()))) {
          str3 = "1";
        }
        if ((localTradeBreedRuleGC.getMarginItem2().toString().equals(localTradeBreedRuleGC.getMarginItem2_S().toString())) && (localTradeBreedRuleGC.getMarginItemAssure2().toString().equals(localTradeBreedRuleGC.getMarginItemAssure2_S().toString()))) {
          str4 = "1";
        }
        if ((localTradeBreedRuleGC.getMarginItem3().toString().equals(localTradeBreedRuleGC.getMarginItem3_S().toString())) && (localTradeBreedRuleGC.getMarginItemAssure3().toString().equals(localTradeBreedRuleGC.getMarginItemAssure3_S().toString()))) {
          str5 = "1";
        }
        if ((localTradeBreedRuleGC.getMarginItem4().toString().equals(localTradeBreedRuleGC.getMarginItem4_S().toString())) && (localTradeBreedRuleGC.getMarginItemAssure4().toString().equals(localTradeBreedRuleGC.getMarginItemAssure4_S().toString()))) {
          str6 = "1";
        }
      }
      else
      {
        localTradeBreedRuleGC = new TradeBreedRuleGC();
        str2 = "1";
        str3 = "1";
        str4 = "1";
        str5 = "1";
        str6 = "1";
      }
      localTradeBreedRuleGCForm = (TradeBreedRuleGCForm)convert(localTradeBreedRuleGC);
      String str7 = localTradeBreedRuleGC.getGroupID();
      List localList = (List)paramHttpServletRequest.getAttribute("customerGroupSelect");
      if ((localList != null) && (localList.size() > 0)) {
        for (int i = 0; i < localList.size(); i++)
        {
          localObject = (LabelValue)localList.get(i);
          if (((LabelValue)localObject).getValue().equals(str7)) {
            paramHttpServletRequest.setAttribute("groupName", ((LabelValue)localObject).getLabel());
          }
        }
      }
      paramHttpServletRequest.setAttribute("groupID", str7);
      String str8 = localTradeBreedRuleGC.getBreedID();
      Object localObject = (List)paramHttpServletRequest.getAttribute("breedSelect");
      if ((localObject != null) && (((List)localObject).size() > 0)) {
        for (int j = 0; j < ((List)localObject).size(); j++)
        {
          LabelValue localLabelValue = (LabelValue)((List)localObject).get(j);
          if (localLabelValue.getValue().equals(str8)) {
            paramHttpServletRequest.setAttribute("name", localLabelValue.getLabel());
          }
        }
      }
      paramHttpServletRequest.setAttribute("commodityID", str8);
      localTradeBreedRuleGCForm.setCrud(str1);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localTradeBreedRuleGCForm);
      paramHttpServletRequest.setAttribute("type", str2);
      paramHttpServletRequest.setAttribute("type1", str3);
      paramHttpServletRequest.setAttribute("type2", str4);
      paramHttpServletRequest.setAttribute("type3", str5);
      paramHttpServletRequest.setAttribute("type4", str6);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return searchGroup(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    return paramActionMapping.findForward("editGroup");
  }
  
  public ActionForward saveGroup(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveGroup' method");
    }
    TradeBreedRuleGCForm localTradeBreedRuleGCForm = (TradeBreedRuleGCForm)paramActionForm;
    String str1 = localTradeBreedRuleGCForm.getCrud();
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    TradeBreedRuleGC localTradeBreedRuleGC = (TradeBreedRuleGC)convert(localTradeBreedRuleGCForm);
    String str2 = paramHttpServletRequest.getParameter("type1");
    String str3 = paramHttpServletRequest.getParameter("type2");
    String str4 = paramHttpServletRequest.getParameter("type3");
    String str5 = paramHttpServletRequest.getParameter("type4");
    String str6 = paramHttpServletRequest.getParameter("type");
    try
    {
      if (str1.trim().equals("create"))
      {
        if ("1".equals(str6))
        {
          localTradeBreedRuleGC.setMarginRate_S(new Double(localTradeBreedRuleGCForm.getMarginRate_B()));
          localTradeBreedRuleGC.setMarginAssure_S(new Double(localTradeBreedRuleGCForm.getMarginAssure_B()));
        }
        if (("1".equals(str2)) && (localTradeBreedRuleGCForm.getMarginItem1() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem1())) && (localTradeBreedRuleGCForm.getMarginItemAssure1() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure1())))
        {
          localTradeBreedRuleGC.setMarginItem1_S(new Double(localTradeBreedRuleGCForm.getMarginItem1()));
          localTradeBreedRuleGC.setMarginItemAssure1_S(new Double(localTradeBreedRuleGCForm.getMarginItemAssure1()));
        }
        if (("1".equals(str3)) && (localTradeBreedRuleGCForm.getMarginItem2() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem2())) && (localTradeBreedRuleGCForm.getMarginItemAssure2() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure2())))
        {
          localTradeBreedRuleGC.setMarginItem2_S(new Double(localTradeBreedRuleGCForm.getMarginItem2()));
          localTradeBreedRuleGC.setMarginItemAssure2_S(new Double(localTradeBreedRuleGCForm.getMarginItemAssure2()));
        }
        if (("1".equals(str4)) && (localTradeBreedRuleGCForm.getMarginItem3() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem3())) && (localTradeBreedRuleGCForm.getMarginItemAssure3() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure3())))
        {
          localTradeBreedRuleGC.setMarginItem3_S(new Double(localTradeBreedRuleGCForm.getMarginItem3()));
          localTradeBreedRuleGC.setMarginItemAssure3_S(new Double(localTradeBreedRuleGCForm.getMarginItemAssure3()));
        }
        if (("1".equals(str5)) && (localTradeBreedRuleGCForm.getMarginItem4() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem4())) && (localTradeBreedRuleGCForm.getMarginItemAssure4() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure4())))
        {
          localTradeBreedRuleGC.setMarginItem4_S(new Double(localTradeBreedRuleGCForm.getMarginItem4()));
          localTradeBreedRuleGC.setMarginItemAssure4_S(new Double(localTradeBreedRuleGCForm.getMarginItemAssure4()));
        }
        localTradeBreedRuleManager.insertGM_TradeBreedRule(localTradeBreedRuleGC);
        addSysLog(paramHttpServletRequest, "增加客户组交易规则[" + localTradeBreedRuleGC.getBreedID() + "," + localTradeBreedRuleGC.getGroupID() + "]");
        paramHttpServletRequest.setAttribute("prompt", "操作成功！");
      }
      else if (str1.trim().equals("update"))
      {
        if ("1".equals(str6))
        {
          if ((localTradeBreedRuleGCForm.getMarginRate_B() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginRate_B()))) {
            localTradeBreedRuleGC.setMarginRate_S(new Double(localTradeBreedRuleGCForm.getMarginRate_B()));
          }
          if ((localTradeBreedRuleGCForm.getMarginAssure_B() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginAssure_B()))) {
            localTradeBreedRuleGC.setMarginAssure_S(new Double(localTradeBreedRuleGCForm.getMarginAssure_B()));
          }
        }
        if (("1".equals(str2)) && (localTradeBreedRuleGCForm.getMarginItem1() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem1())) && (localTradeBreedRuleGCForm.getMarginItemAssure1() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure1())))
        {
          localTradeBreedRuleGC.setMarginItem1_S(new Double(localTradeBreedRuleGCForm.getMarginItem1()));
          localTradeBreedRuleGC.setMarginItemAssure1_S(new Double(localTradeBreedRuleGCForm.getMarginItemAssure1()));
        }
        if (("1".equals(str3)) && (localTradeBreedRuleGCForm.getMarginItem2() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem2())) && (localTradeBreedRuleGCForm.getMarginItemAssure2() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure2())))
        {
          localTradeBreedRuleGC.setMarginItem2_S(new Double(localTradeBreedRuleGCForm.getMarginItem2()));
          localTradeBreedRuleGC.setMarginItemAssure2_S(new Double(localTradeBreedRuleGCForm.getMarginItemAssure2()));
        }
        if (("1".equals(str4)) && (localTradeBreedRuleGCForm.getMarginItem3() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem3())) && (localTradeBreedRuleGCForm.getMarginItemAssure3() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure3())))
        {
          localTradeBreedRuleGC.setMarginItem3_S(new Double(localTradeBreedRuleGCForm.getMarginItem3()));
          localTradeBreedRuleGC.setMarginItemAssure3_S(new Double(localTradeBreedRuleGCForm.getMarginItemAssure3()));
        }
        if (("1".equals(str5)) && (localTradeBreedRuleGCForm.getMarginItem4() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem4())) && (localTradeBreedRuleGCForm.getMarginItemAssure4() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure4())))
        {
          localTradeBreedRuleGC.setMarginItem4_S(new Double(localTradeBreedRuleGCForm.getMarginItem4()));
          localTradeBreedRuleGC.setMarginItemAssure4_S(new Double(localTradeBreedRuleGCForm.getMarginItemAssure4()));
        }
        localTradeBreedRuleManager.updateGM_TradeBreedRule(localTradeBreedRuleGC);
        addSysLog(paramHttpServletRequest, "修改客户组交易规则[" + localTradeBreedRuleGC.getBreedID() + "," + localTradeBreedRuleGC.getGroupID() + "]");
        paramHttpServletRequest.setAttribute("prompt", "操作成功！");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      getSelectAttribute(paramHttpServletRequest);
      return paramActionMapping.findForward("editGroup");
    }
    return searchGroup(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward deleteGroup(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deleteGroup' method");
    }
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
    int i = 0;
    if (arrayOfString1 != null)
    {
      this.log.debug("==ids.length:" + arrayOfString1.length);
      String str2 = "";
      for (int j = 0; j < arrayOfString1.length; j++)
      {
        String[] arrayOfString2 = arrayOfString1[j].split(",");
        String str1 = arrayOfString2[1];
        Integer localInteger = new Integer(arrayOfString2[0]);
        try
        {
          localTradeBreedRuleManager.deleteGM_TradeBreedRuleById(str1, localInteger);
          addSysLog(paramHttpServletRequest, "删除客户组交易规则[" + str1 + "," + localInteger + "]");
          i++;
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str2 = str2 + arrayOfString1[j] + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + arrayOfString1[j] + "]与其他数据关联，删除失败！");
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
    return searchGroup(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward searchGroup(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchGroup' method");
    }
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    try
    {
      List localList = localTradeBreedRuleManager.getGM_TradeBreedRules(null);
      paramHttpServletRequest.setAttribute("tradeBreedRuleList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询GM_TradeBreedRule表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listGroup");
  }
  
  public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return searchGroup(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward searchFirmBreedMargin(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchFirmBreedMargin' method");
    }
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    try
    {
      List localList = localTradeBreedRuleManager.getFirmBreedMargin();
      paramHttpServletRequest.setAttribute("firmBreedMarginList", localList);
      paramHttpServletRequest.setAttribute("MARGINALGR", CommonDictionary.MARGINALGR);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("查询CM_TradeRule表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("searchFirmBreedMargin");
  }
  
  public ActionForward editFirmBreedMargin(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editFirmBreedMargin' method");
    }
    TradeBreedRuleGCForm localTradeBreedRuleGCForm = (TradeBreedRuleGCForm)paramActionForm;
    String str1 = localTradeBreedRuleGCForm.getCrud();
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    TradeBreedRuleGC localTradeBreedRuleGC = null;
    try
    {
      getSelectAttribute(paramHttpServletRequest);
      String str2 = "2";
      String str3 = "2";
      String str4 = "2";
      String str5 = "2";
      String str6 = "2";
      String str7 = "2";
      String str8 = "2";
      String str9 = "2";
      if (!str1.trim().equals("create"))
      {
        localTradeBreedRuleGC = localTradeBreedRuleManager.getFirmBreedMarginById(localTradeBreedRuleGCForm.getFirmID(), localTradeBreedRuleGCForm.getBreedID());
        if (localTradeBreedRuleGC != null)
        {
          if ((localTradeBreedRuleGC.getMarginItem1() != null) && (localTradeBreedRuleGC.getMarginItem1_S() != null) && (localTradeBreedRuleGC.getMarginItemAssure1() != null) && (localTradeBreedRuleGC.getMarginItemAssure1_S() != null) && (localTradeBreedRuleGC.getMarginItem1().toString().equals(localTradeBreedRuleGC.getMarginItem1_S().toString())) && (localTradeBreedRuleGC.getMarginItemAssure1().toString().equals(localTradeBreedRuleGC.getMarginItemAssure1_S().toString()))) {
            str2 = "1";
          }
          if ((localTradeBreedRuleGC.getMarginItem2() != null) && (localTradeBreedRuleGC.getMarginItem2_S() != null) && (localTradeBreedRuleGC.getMarginItemAssure2() != null) && (localTradeBreedRuleGC.getMarginItemAssure2_S() != null) && (localTradeBreedRuleGC.getMarginItem2().toString().equals(localTradeBreedRuleGC.getMarginItem2_S().toString())) && (localTradeBreedRuleGC.getMarginItemAssure2().toString().equals(localTradeBreedRuleGC.getMarginItemAssure2_S().toString()))) {
            str3 = "1";
          }
          if ((localTradeBreedRuleGC.getMarginItem3() != null) && (localTradeBreedRuleGC.getMarginItem3_S() != null) && (localTradeBreedRuleGC.getMarginItemAssure3() != null) && (localTradeBreedRuleGC.getMarginItemAssure3_S() != null) && (localTradeBreedRuleGC.getMarginItem3().toString().equals(localTradeBreedRuleGC.getMarginItem3_S().toString())) && (localTradeBreedRuleGC.getMarginItemAssure3().toString().equals(localTradeBreedRuleGC.getMarginItemAssure3_S().toString()))) {
            str4 = "1";
          }
          if ((localTradeBreedRuleGC.getMarginItem4() != null) && (localTradeBreedRuleGC.getMarginItem4_S() != null) && (localTradeBreedRuleGC.getMarginItemAssure4() != null) && (localTradeBreedRuleGC.getMarginItemAssure4_S() != null) && (localTradeBreedRuleGC.getMarginItem4().toString().equals(localTradeBreedRuleGC.getMarginItem4_S().toString())) && (localTradeBreedRuleGC.getMarginItemAssure4().toString().equals(localTradeBreedRuleGC.getMarginItemAssure4_S().toString()))) {
            str5 = "1";
          }
          if ((localTradeBreedRuleGC.getMarginItem5() != null) && (localTradeBreedRuleGC.getMarginItem5_S() != null) && (localTradeBreedRuleGC.getMarginItemAssure5() != null) && (localTradeBreedRuleGC.getMarginItemAssure5_S() != null) && (localTradeBreedRuleGC.getMarginItem5().toString().equals(localTradeBreedRuleGC.getMarginItem5_S().toString())) && (localTradeBreedRuleGC.getMarginItemAssure5().toString().equals(localTradeBreedRuleGC.getMarginItemAssure5_S().toString()))) {
            str6 = "1";
          }
          if ((localTradeBreedRuleGC.getSettleMarginAlgr_B() != null) && ("1".equals(localTradeBreedRuleGC.getSettleMarginAlgr_B().toString())))
          {
            str7 = "1";
            localTradeBreedRuleGC.setSettleMarginRate_B(Arith.mul(localTradeBreedRuleGC.getSettleMarginRate_B(), new Double(100.0D)));
          }
          if ((localTradeBreedRuleGC.getSettleMarginAlgr_S() != null) && ("1".equals(localTradeBreedRuleGC.getSettleMarginAlgr_S().toString())))
          {
            str8 = "1";
            localTradeBreedRuleGC.setSettleMarginRate_S(Arith.mul(localTradeBreedRuleGC.getSettleMarginRate_S(), new Double(100.0D)));
          }
          if ((localTradeBreedRuleGC.getPayoutAlgr() != null) && ("1".equals(localTradeBreedRuleGC.getPayoutAlgr().toString())))
          {
            str9 = "1";
            localTradeBreedRuleGC.setPayoutRate(Arith.mul(localTradeBreedRuleGC.getPayoutRate(), new Double(100.0D)));
          }
          if ((localTradeBreedRuleGC.getMarginAlgr() != null) && ("1".equals(localTradeBreedRuleGC.getMarginAlgr().toString())))
          {
            localTradeBreedRuleGC.setMarginItem1(Arith.mul(localTradeBreedRuleGC.getMarginItem1(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItem2(Arith.mul(localTradeBreedRuleGC.getMarginItem2(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItem3(Arith.mul(localTradeBreedRuleGC.getMarginItem3(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItem4(Arith.mul(localTradeBreedRuleGC.getMarginItem4(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItem5(Arith.mul(localTradeBreedRuleGC.getMarginItem5(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItem1_S(Arith.mul(localTradeBreedRuleGC.getMarginItem1_S(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItem2_S(Arith.mul(localTradeBreedRuleGC.getMarginItem2_S(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItem3_S(Arith.mul(localTradeBreedRuleGC.getMarginItem3_S(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItem4_S(Arith.mul(localTradeBreedRuleGC.getMarginItem4_S(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItem5_S(Arith.mul(localTradeBreedRuleGC.getMarginItem5_S(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItemAssure1(Arith.mul(localTradeBreedRuleGC.getMarginItemAssure1(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItemAssure2(Arith.mul(localTradeBreedRuleGC.getMarginItemAssure2(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItemAssure3(Arith.mul(localTradeBreedRuleGC.getMarginItemAssure3(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItemAssure4(Arith.mul(localTradeBreedRuleGC.getMarginItemAssure4(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItemAssure5(Arith.mul(localTradeBreedRuleGC.getMarginItemAssure5(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItemAssure1_S(Arith.mul(localTradeBreedRuleGC.getMarginItemAssure1_S(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItemAssure2_S(Arith.mul(localTradeBreedRuleGC.getMarginItemAssure2_S(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItemAssure3_S(Arith.mul(localTradeBreedRuleGC.getMarginItemAssure3_S(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItemAssure4_S(Arith.mul(localTradeBreedRuleGC.getMarginItemAssure4_S(), new Double(100.0D)));
            localTradeBreedRuleGC.setMarginItemAssure5_S(Arith.mul(localTradeBreedRuleGC.getMarginItemAssure5_S(), new Double(100.0D)));
          }
        }
      }
      else
      {
        localTradeBreedRuleGC = new TradeBreedRuleGC();
        str2 = "1";
        str3 = "1";
        str4 = "1";
        str5 = "1";
        str6 = "1";
      }
      localTradeBreedRuleGCForm = (TradeBreedRuleGCForm)convert(localTradeBreedRuleGC);
      String str10 = localTradeBreedRuleGC.getBreedID();
      List localList = (List)paramHttpServletRequest.getAttribute("breedSelect");
      if ((localList != null) && (localList.size() > 0)) {
        for (int i = 0; i < localList.size(); i++)
        {
          LabelValue localLabelValue = (LabelValue)localList.get(i);
          if (localLabelValue.getValue().equals(str10)) {
            paramHttpServletRequest.setAttribute("name", localLabelValue.getLabel());
          }
        }
      }
      paramHttpServletRequest.setAttribute("breedID", str10);
      localTradeBreedRuleGCForm.setCrud(str1);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localTradeBreedRuleGCForm);
      paramHttpServletRequest.setAttribute("type", str2);
      paramHttpServletRequest.setAttribute("type1", str3);
      paramHttpServletRequest.setAttribute("type2", str4);
      paramHttpServletRequest.setAttribute("type3", str5);
      paramHttpServletRequest.setAttribute("type4", str6);
      paramHttpServletRequest.setAttribute("typeSettleMarginAlgr_B", str7);
      paramHttpServletRequest.setAttribute("typeSettleMarginAlgr_S", str8);
      paramHttpServletRequest.setAttribute("typePayoutAlgr", str9);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return searchFirmBreedMargin(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    return paramActionMapping.findForward("editFirmBreedMargin");
  }
  
  public ActionForward saveFirmBreedMargin(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveFirmBreedMargin' method");
    }
    TradeBreedRuleGCForm localTradeBreedRuleGCForm = (TradeBreedRuleGCForm)paramActionForm;
    String str1 = localTradeBreedRuleGCForm.getCrud();
    String str2 = paramHttpServletRequest.getParameter("logos");
    System.out.println("==================logos=" + str2);
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    TradeRuleManager localTradeRuleManager = (TradeRuleManager)getBean("tradeRuleManager");
    TradeBreedRuleGC localTradeBreedRuleGC = (TradeBreedRuleGC)convert(localTradeBreedRuleGCForm);
    String str3 = paramHttpServletRequest.getParameter("type1");
    String str4 = paramHttpServletRequest.getParameter("type2");
    String str5 = paramHttpServletRequest.getParameter("type3");
    String str6 = paramHttpServletRequest.getParameter("type4");
    String str7 = paramHttpServletRequest.getParameter("type");
    try
    {
      if ((localTradeBreedRuleGC.getSettleMarginAlgr_B() != null) && ("1".equals(localTradeBreedRuleGC.getSettleMarginAlgr_B().toString()))) {
        localTradeBreedRuleGC.setSettleMarginRate_B(Double.valueOf(localTradeBreedRuleGC.getSettleMarginRate_B().doubleValue() / new Double(100.0D).doubleValue()));
      }
      if ((localTradeBreedRuleGC.getSettleMarginAlgr_S() != null) && ("1".equals(localTradeBreedRuleGC.getSettleMarginAlgr_S().toString()))) {
        localTradeBreedRuleGC.setSettleMarginRate_S(Double.valueOf(localTradeBreedRuleGC.getSettleMarginRate_S().doubleValue() / new Double(100.0D).doubleValue()));
      }
      if ((localTradeBreedRuleGC.getPayoutAlgr() != null) && ("1".equals(localTradeBreedRuleGC.getPayoutAlgr().toString()))) {
        localTradeBreedRuleGC.setPayoutRate(Double.valueOf(localTradeBreedRuleGC.getPayoutRate().doubleValue() / new Double(100.0D).doubleValue()));
      }
      if ((localTradeBreedRuleGC.getMarginAlgr() != null) && ("1".equals(localTradeBreedRuleGC.getMarginAlgr().toString())))
      {
        if (localTradeBreedRuleGC.getMarginItem1() != null) {
          localTradeBreedRuleGC.setMarginItem1(Double.valueOf(localTradeBreedRuleGC.getMarginItem1().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItem2() != null) {
          localTradeBreedRuleGC.setMarginItem2(Double.valueOf(localTradeBreedRuleGC.getMarginItem2().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItem3() != null) {
          localTradeBreedRuleGC.setMarginItem3(Double.valueOf(localTradeBreedRuleGC.getMarginItem3().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItem4() != null) {
          localTradeBreedRuleGC.setMarginItem4(Double.valueOf(localTradeBreedRuleGC.getMarginItem4().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItem5() != null) {
          localTradeBreedRuleGC.setMarginItem5(Double.valueOf(localTradeBreedRuleGC.getMarginItem5().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItem1_S() != null) {
          localTradeBreedRuleGC.setMarginItem1_S(Double.valueOf(localTradeBreedRuleGC.getMarginItem1_S().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItem2_S() != null) {
          localTradeBreedRuleGC.setMarginItem2_S(Double.valueOf(localTradeBreedRuleGC.getMarginItem2_S().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItem3_S() != null) {
          localTradeBreedRuleGC.setMarginItem3_S(Double.valueOf(localTradeBreedRuleGC.getMarginItem3_S().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItem4_S() != null) {
          localTradeBreedRuleGC.setMarginItem4_S(Double.valueOf(localTradeBreedRuleGC.getMarginItem4_S().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItem5_S() != null) {
          localTradeBreedRuleGC.setMarginItem5_S(Double.valueOf(localTradeBreedRuleGC.getMarginItem5_S().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItemAssure1() != null) {
          localTradeBreedRuleGC.setMarginItemAssure1(Double.valueOf(localTradeBreedRuleGC.getMarginItemAssure1().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItemAssure2() != null) {
          localTradeBreedRuleGC.setMarginItemAssure2(Double.valueOf(localTradeBreedRuleGC.getMarginItemAssure2().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItemAssure3() != null) {
          localTradeBreedRuleGC.setMarginItemAssure3(Double.valueOf(localTradeBreedRuleGC.getMarginItemAssure3().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItemAssure4() != null) {
          localTradeBreedRuleGC.setMarginItemAssure4(Double.valueOf(localTradeBreedRuleGC.getMarginItemAssure4().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItemAssure5() != null) {
          localTradeBreedRuleGC.setMarginItemAssure5(Double.valueOf(localTradeBreedRuleGC.getMarginItemAssure5().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItemAssure1_S() != null) {
          localTradeBreedRuleGC.setMarginItemAssure1_S(Double.valueOf(localTradeBreedRuleGC.getMarginItemAssure1_S().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItemAssure2_S() != null) {
          localTradeBreedRuleGC.setMarginItemAssure2_S(Double.valueOf(localTradeBreedRuleGC.getMarginItemAssure2_S().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItemAssure3_S() != null) {
          localTradeBreedRuleGC.setMarginItemAssure3_S(Double.valueOf(localTradeBreedRuleGC.getMarginItemAssure3_S().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItemAssure4_S() != null) {
          localTradeBreedRuleGC.setMarginItemAssure4_S(Double.valueOf(localTradeBreedRuleGC.getMarginItemAssure4_S().doubleValue() / new Double(100.0D).doubleValue()));
        }
        if (localTradeBreedRuleGC.getMarginItemAssure5_S() != null) {
          localTradeBreedRuleGC.setMarginItemAssure5_S(Double.valueOf(localTradeBreedRuleGC.getMarginItemAssure5_S().doubleValue() / new Double(100.0D).doubleValue()));
        }
      }
      TradeRuleGC localTradeRuleGC;
      List localList;
      int i;
      Map localMap;
      if (str1.trim().equals("create"))
      {
        if (("1".equals(str7)) && (localTradeBreedRuleGCForm.getMarginItem1() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem1())) && (localTradeBreedRuleGCForm.getMarginItemAssure1() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure1())))
        {
          localTradeBreedRuleGC.setMarginItem1_S(localTradeBreedRuleGC.getMarginItem1());
          localTradeBreedRuleGC.setMarginItemAssure1_S(localTradeBreedRuleGC.getMarginItemAssure1());
        }
        if (("1".equals(str3)) && (localTradeBreedRuleGCForm.getMarginItem2() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem2())) && (localTradeBreedRuleGCForm.getMarginItemAssure2() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure2())))
        {
          localTradeBreedRuleGC.setMarginItem2_S(localTradeBreedRuleGC.getMarginItem2());
          localTradeBreedRuleGC.setMarginItemAssure2_S(localTradeBreedRuleGC.getMarginItemAssure2());
        }
        if (("1".equals(str4)) && (localTradeBreedRuleGCForm.getMarginItem3() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem3())) && (localTradeBreedRuleGCForm.getMarginItemAssure3() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure3())))
        {
          localTradeBreedRuleGC.setMarginItem3_S(localTradeBreedRuleGC.getMarginItem3());
          localTradeBreedRuleGC.setMarginItemAssure3_S(localTradeBreedRuleGC.getMarginItemAssure3());
        }
        if (("1".equals(str5)) && (localTradeBreedRuleGCForm.getMarginItem4() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem4())) && (localTradeBreedRuleGCForm.getMarginItemAssure4() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure4())))
        {
          localTradeBreedRuleGC.setMarginItem4_S(localTradeBreedRuleGC.getMarginItem4());
          localTradeBreedRuleGC.setMarginItemAssure4_S(localTradeBreedRuleGC.getMarginItemAssure4());
        }
        if (("1".equals(str6)) && (localTradeBreedRuleGCForm.getMarginItem5() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem5())) && (localTradeBreedRuleGCForm.getMarginItemAssure5() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure5())))
        {
          localTradeBreedRuleGC.setMarginItem5_S(localTradeBreedRuleGC.getMarginItem5());
          localTradeBreedRuleGC.setMarginItemAssure5_S(localTradeBreedRuleGC.getMarginItemAssure5());
        }
        if ((str2 != null) && (str2.equals("true")))
        {
          localTradeRuleGC = new TradeRuleGC();
          BeanUtils.copyProperties(localTradeRuleGC, localTradeBreedRuleGC);
          localList = localTradeBreedRuleManager.getFirmCommodityIDFromMarginForAdd(localTradeBreedRuleGC.getFirmID(), localTradeBreedRuleGC.getBreedID());
          for (i = 0; i < localList.size(); i++)
          {
            localMap = (Map)localList.get(i);
            localTradeRuleGC.setCommodityID((String)localMap.get("COMMODITYID"));
            localTradeRuleManager.insertFirmMargin(localTradeRuleGC);
          }
          localTradeBreedRuleManager.insertFirmBreedMargin(localTradeBreedRuleGC);
          addSysLog(paramHttpServletRequest, "增加品种特殊保证金");
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
        else
        {
          localTradeBreedRuleManager.insertFirmBreedMargin(localTradeBreedRuleGC);
          addSysLog(paramHttpServletRequest, "增加品种特殊保证金");
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
      }
      else if (str1.trim().equals("update"))
      {
        if (("1".equals(str7)) && (localTradeBreedRuleGCForm.getMarginItem1() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem1())) && (localTradeBreedRuleGCForm.getMarginItemAssure1() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure1())))
        {
          localTradeBreedRuleGC.setMarginItem1_S(localTradeBreedRuleGC.getMarginItem1());
          localTradeBreedRuleGC.setMarginItemAssure1_S(localTradeBreedRuleGC.getMarginItemAssure1());
        }
        if (("1".equals(str3)) && (localTradeBreedRuleGCForm.getMarginItem2() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem2())) && (localTradeBreedRuleGCForm.getMarginItemAssure2() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure2())))
        {
          localTradeBreedRuleGC.setMarginItem2_S(localTradeBreedRuleGC.getMarginItem2());
          localTradeBreedRuleGC.setMarginItemAssure2_S(localTradeBreedRuleGC.getMarginItemAssure2());
        }
        if (("1".equals(str4)) && (localTradeBreedRuleGCForm.getMarginItem3() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem3())) && (localTradeBreedRuleGCForm.getMarginItemAssure3() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure3())))
        {
          localTradeBreedRuleGC.setMarginItem3_S(localTradeBreedRuleGC.getMarginItem3());
          localTradeBreedRuleGC.setMarginItemAssure3_S(localTradeBreedRuleGC.getMarginItemAssure3());
        }
        if (("1".equals(str5)) && (localTradeBreedRuleGCForm.getMarginItem4() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem4())) && (localTradeBreedRuleGCForm.getMarginItemAssure4() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure4())))
        {
          localTradeBreedRuleGC.setMarginItem4_S(localTradeBreedRuleGC.getMarginItem4());
          localTradeBreedRuleGC.setMarginItemAssure4_S(localTradeBreedRuleGC.getMarginItemAssure4());
        }
        if (("1".equals(str6)) && (localTradeBreedRuleGCForm.getMarginItem5() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItem5())) && (localTradeBreedRuleGCForm.getMarginItemAssure5() != null) && (!"".equals(localTradeBreedRuleGCForm.getMarginItemAssure5())))
        {
          localTradeBreedRuleGC.setMarginItem5_S(localTradeBreedRuleGC.getMarginItem5());
          localTradeBreedRuleGC.setMarginItemAssure5_S(localTradeBreedRuleGC.getMarginItemAssure5());
        }
        if ((str2 != null) && (str2.equals("true")))
        {
          localTradeRuleGC = new TradeRuleGC();
          BeanUtils.copyProperties(localTradeRuleGC, localTradeBreedRuleGC);
          localList = localTradeBreedRuleManager.getFirmCommodityIDFromMargin(localTradeBreedRuleGC.getFirmID(), localTradeBreedRuleGC.getBreedID());
          for (i = 0; i < localList.size(); i++)
          {
            localMap = (Map)localList.get(i);
            localTradeRuleGC.setCommodityID((String)localMap.get("COMMODITYID"));
            localTradeRuleManager.updateFirmMargin(localTradeRuleGC);
          }
          localTradeBreedRuleManager.updateFirmBreedMargin(localTradeBreedRuleGC);
          addSysLog(paramHttpServletRequest, "修改品种特殊保证金");
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
        else
        {
          localTradeBreedRuleManager.updateFirmBreedMargin(localTradeBreedRuleGC);
          addSysLog(paramHttpServletRequest, "修改品种特殊保证金");
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      getSelectAttribute(paramHttpServletRequest);
      return paramActionMapping.findForward("editFirmBreedMargin");
    }
    return searchFirmBreedMargin(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward deleteFirmBreedMargin(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deleteFirmBreedMargin' method");
    }
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    TradeRuleManager localTradeRuleManager = (TradeRuleManager)getBean("tradeRuleManager");
    String str1 = paramHttpServletRequest.getParameter("logos");
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
    int i = 0;
    if (arrayOfString1 != null)
    {
      this.log.debug("==ids.length:" + arrayOfString1.length);
      String str4 = "";
      for (int j = 0; j < arrayOfString1.length; j++)
      {
        String[] arrayOfString2 = arrayOfString1[j].split(",");
        String str2 = arrayOfString2[1];
        String str3 = arrayOfString2[0];
        try
        {
          if ((str1 != null) && (str1.equals("true")))
          {
            List localList = localTradeBreedRuleManager.getFirmCommodityIDFromMargin(str3, str2);
            for (int k = 0; k < localList.size(); k++)
            {
              Map localMap = (Map)localList.get(k);
              localTradeRuleManager.deleteFirmMarginById((String)localMap.get("FIRMID"), (String)localMap.get("COMMODITYID"));
            }
            localTradeBreedRuleManager.deleteFirmBreedMarginById(str3, str2);
            addSysLog(paramHttpServletRequest, "删除品种特殊保证金");
            i++;
          }
          else
          {
            localTradeBreedRuleManager.deleteFirmBreedMarginById(str3, str2);
            addSysLog(paramHttpServletRequest, "删除品种特殊保证金");
            i++;
          }
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str4 = str4 + arrayOfString1[j] + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + arrayOfString1[j] + "]与其他数据关联，删除失败！");
        }
      }
      if (!str4.equals(""))
      {
        str4 = str4.substring(0, str4.length() - 1);
        str4 = str4 + "与其他数据关联，不能删除！";
      }
      str4 = str4 + "成功删除" + i + "条纪录！";
      paramHttpServletRequest.setAttribute("prompt", str4);
    }
    return searchFirmBreedMargin(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward searchFirmBreedFee(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchFirmBreedFee' method");
    }
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    try
    {
      List localList = localTradeBreedRuleManager.getFirmBreedFee();
      paramHttpServletRequest.setAttribute("firmBreedFeeList", localList);
      paramHttpServletRequest.setAttribute("FEEALGR", CommonDictionary.FEEALGR);
    }
    catch (Exception localException)
    {
      this.log.error("查询T_A_FirmFee表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("searchFirmBreedFee");
  }
  
  public ActionForward editFirmBreedFee(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editFirmBreedFee' method");
    }
    TradeBreedRuleGCForm localTradeBreedRuleGCForm = (TradeBreedRuleGCForm)paramActionForm;
    String str1 = localTradeBreedRuleGCForm.getCrud();
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    TradeBreedRuleGC localTradeBreedRuleGC = null;
    String str2 = "2";
    String str3 = "2";
    try
    {
      getSelectAttribute(paramHttpServletRequest);
      if (!str1.trim().equals("create"))
      {
        localTradeBreedRuleGC = localTradeBreedRuleManager.getFirmBreedFeeById(localTradeBreedRuleGCForm.getFirmID(), localTradeBreedRuleGCForm.getBreedID());
        if (localTradeBreedRuleGC != null)
        {
          if ((localTradeBreedRuleGC.getFeeAlgr() != null) && ("1".equals(localTradeBreedRuleGC.getFeeAlgr().toString())))
          {
            str2 = "1";
            localTradeBreedRuleGC.setFeeRate_B(Arith.mul(localTradeBreedRuleGC.getFeeRate_B(), new Double(100.0D)));
            localTradeBreedRuleGC.setFeeRate_S(Arith.mul(localTradeBreedRuleGC.getFeeRate_S(), new Double(100.0D)));
            localTradeBreedRuleGC.setHistoryCloseFeeRate_B(Arith.mul(localTradeBreedRuleGC.getHistoryCloseFeeRate_B(), new Double(100.0D)));
            localTradeBreedRuleGC.setHistoryCloseFeeRate_S(Arith.mul(localTradeBreedRuleGC.getHistoryCloseFeeRate_S(), new Double(100.0D)));
            localTradeBreedRuleGC.setTodayCloseFeeRate_B(Arith.mul(localTradeBreedRuleGC.getTodayCloseFeeRate_B(), new Double(100.0D)));
            localTradeBreedRuleGC.setTodayCloseFeeRate_S(Arith.mul(localTradeBreedRuleGC.getTodayCloseFeeRate_S(), new Double(100.0D)));
            localTradeBreedRuleGC.setForceCloseFeeRate_B(Arith.mul(localTradeBreedRuleGC.getForceCloseFeeRate_B(), new Double(100.0D)));
            localTradeBreedRuleGC.setForceCloseFeeRate_S(Arith.mul(localTradeBreedRuleGC.getForceCloseFeeRate_S(), new Double(100.0D)));
          }
          if ((localTradeBreedRuleGC.getSettleFeeAlgr() != null) && ("1".equals(localTradeBreedRuleGC.getSettleFeeAlgr().toString())))
          {
            str3 = "1";
            localTradeBreedRuleGC.setSettleFeeRate_B(Arith.mul(localTradeBreedRuleGC.getSettleFeeRate_B(), new Double(100.0D)));
            localTradeBreedRuleGC.setSettleFeeRate_S(Arith.mul(localTradeBreedRuleGC.getSettleFeeRate_S(), new Double(100.0D)));
          }
        }
      }
      else
      {
        localTradeBreedRuleGC = new TradeBreedRuleGC();
      }
      localTradeBreedRuleGCForm = (TradeBreedRuleGCForm)convert(localTradeBreedRuleGC);
      String str4 = localTradeBreedRuleGC.getBreedID();
      List localList = (List)paramHttpServletRequest.getAttribute("breedSelect");
      if ((localList != null) && (localList.size() > 0)) {
        for (int i = 0; i < localList.size(); i++)
        {
          LabelValue localLabelValue = (LabelValue)localList.get(i);
          if (localLabelValue.getValue().equals(str4)) {
            paramHttpServletRequest.setAttribute("name", localLabelValue.getLabel());
          }
        }
      }
      paramHttpServletRequest.setAttribute("breedID", str4);
      paramHttpServletRequest.setAttribute("typeFeeAlgr", str2);
      paramHttpServletRequest.setAttribute("typeSettleFeeAlgr", str3);
      localTradeBreedRuleGCForm.setCrud(str1);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localTradeBreedRuleGCForm);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return searchFirmBreedFee(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    return paramActionMapping.findForward("editFirmBreedFee");
  }
  
  public ActionForward saveFirmBreedFee(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveFirmBreedFee' method");
    }
    TradeBreedRuleGCForm localTradeBreedRuleGCForm = (TradeBreedRuleGCForm)paramActionForm;
    String str1 = localTradeBreedRuleGCForm.getCrud();
    String str2 = paramHttpServletRequest.getParameter("logos");
    TradeRuleManager localTradeRuleManager = (TradeRuleManager)getBean("tradeRuleManager");
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    TradeBreedRuleGC localTradeBreedRuleGC = (TradeBreedRuleGC)convert(localTradeBreedRuleGCForm);
    try
    {
      if ((localTradeBreedRuleGC.getFeeAlgr() != null) && ("1".equals(localTradeBreedRuleGC.getFeeAlgr().toString())))
      {
        localTradeBreedRuleGC.setFeeRate_B(Double.valueOf(localTradeBreedRuleGC.getFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        localTradeBreedRuleGC.setFeeRate_S(Double.valueOf(localTradeBreedRuleGC.getFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
        localTradeBreedRuleGC.setHistoryCloseFeeRate_B(Double.valueOf(localTradeBreedRuleGC.getHistoryCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        localTradeBreedRuleGC.setHistoryCloseFeeRate_S(Double.valueOf(localTradeBreedRuleGC.getHistoryCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
        localTradeBreedRuleGC.setTodayCloseFeeRate_B(Double.valueOf(localTradeBreedRuleGC.getTodayCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        localTradeBreedRuleGC.setTodayCloseFeeRate_S(Double.valueOf(localTradeBreedRuleGC.getTodayCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
        localTradeBreedRuleGC.setForceCloseFeeRate_B(Double.valueOf(localTradeBreedRuleGC.getForceCloseFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        localTradeBreedRuleGC.setForceCloseFeeRate_S(Double.valueOf(localTradeBreedRuleGC.getForceCloseFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
      }
      if ((localTradeBreedRuleGC.getSettleFeeAlgr() != null) && ("1".equals(localTradeBreedRuleGC.getSettleFeeAlgr().toString())))
      {
        localTradeBreedRuleGC.setSettleFeeRate_B(Double.valueOf(localTradeBreedRuleGC.getSettleFeeRate_B().doubleValue() / new Double(100.0D).doubleValue()));
        localTradeBreedRuleGC.setSettleFeeRate_S(Double.valueOf(localTradeBreedRuleGC.getSettleFeeRate_S().doubleValue() / new Double(100.0D).doubleValue()));
      }
      TradeRuleGC localTradeRuleGC;
      List localList;
      int i;
      Map localMap;
      if (str1.trim().equals("create"))
      {
        if ((str2 != null) && (str2.equals("true")))
        {
          localTradeRuleGC = new TradeRuleGC();
          BeanUtils.copyProperties(localTradeRuleGC, localTradeBreedRuleGC);
          localList = localTradeBreedRuleManager.getFirmCommodityIDFromFeeForAdd(localTradeBreedRuleGC.getFirmID(), localTradeBreedRuleGC.getBreedID());
          for (i = 0; i < localList.size(); i++)
          {
            localMap = (Map)localList.get(i);
            localTradeRuleGC.setCommodityID((String)localMap.get("COMMODITYID"));
            localTradeRuleManager.insertFirmFee(localTradeRuleGC);
          }
          localTradeBreedRuleManager.insertFirmBreedFee(localTradeBreedRuleGC);
          addSysLog(paramHttpServletRequest, "增加品种特殊手续费");
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
        else
        {
          localTradeBreedRuleManager.insertFirmBreedFee(localTradeBreedRuleGC);
          addSysLog(paramHttpServletRequest, "增加品种特殊手续费");
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
      }
      else if (str1.trim().equals("update")) {
        if ((str2 != null) && (str2.equals("true")))
        {
          localTradeRuleGC = new TradeRuleGC();
          BeanUtils.copyProperties(localTradeRuleGC, localTradeBreedRuleGC);
          localList = localTradeBreedRuleManager.getFirmCommodityIDFromFee(localTradeBreedRuleGC.getFirmID(), localTradeBreedRuleGC.getBreedID());
          for (i = 0; i < localList.size(); i++)
          {
            localMap = (Map)localList.get(i);
            localTradeRuleGC.setCommodityID((String)localMap.get("COMMODITYID"));
            localTradeRuleManager.updateFirmFee(localTradeRuleGC);
          }
          localTradeBreedRuleManager.updateFirmBreedFee(localTradeBreedRuleGC);
          addSysLog(paramHttpServletRequest, "修改品种特殊手续费");
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
        else
        {
          localTradeBreedRuleManager.updateFirmBreedFee(localTradeBreedRuleGC);
          addSysLog(paramHttpServletRequest, "修改品种特殊手续费");
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      getSelectAttribute(paramHttpServletRequest);
      return paramActionMapping.findForward("editFirmBreedFee");
    }
    return searchFirmBreedFee(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward deleteFirmBreedFee(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deleteFirmBreedFee' method");
    }
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    TradeRuleManager localTradeRuleManager = (TradeRuleManager)getBean("tradeRuleManager");
    String str1 = paramHttpServletRequest.getParameter("logos");
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
    int i = 0;
    if (arrayOfString1 != null)
    {
      this.log.debug("==ids.length:" + arrayOfString1.length);
      String str4 = "";
      for (int j = 0; j < arrayOfString1.length; j++)
      {
        String[] arrayOfString2 = arrayOfString1[j].split(",");
        String str2 = arrayOfString2[1];
        String str3 = arrayOfString2[0];
        try
        {
          if ((str1 != null) && (str1.equals("true")))
          {
            List localList = localTradeBreedRuleManager.getFirmCommodityIDFromFee(str3, str2);
            for (int k = 0; k < localList.size(); k++)
            {
              Map localMap = (Map)localList.get(k);
              localTradeRuleManager.deleteFirmFeeById((String)localMap.get("FIRMID"), (String)localMap.get("COMMODITYID"));
            }
            localTradeBreedRuleManager.deleteFirmBreedFeeById(str3, str2);
            addSysLog(paramHttpServletRequest, "删除品种特殊手续费");
            i++;
          }
          else
          {
            localTradeBreedRuleManager.deleteFirmBreedFeeById(str3, str2);
            addSysLog(paramHttpServletRequest, "删除品种特殊手续费");
            i++;
          }
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str4 = str4 + arrayOfString1[j] + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + arrayOfString1[j] + "]与其他数据关联，删除失败！");
        }
      }
      if (!str4.equals(""))
      {
        str4 = str4.substring(0, str4.length() - 1);
        str4 = str4 + "与其他数据关联，不能删除！";
      }
      str4 = str4 + "成功删除" + i + "条纪录！";
      paramHttpServletRequest.setAttribute("prompt", str4);
    }
    return searchFirmBreedFee(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward searchFirmBreedMaxHoldQty(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchFirmBreedMaxHoldQty' method");
    }
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    try
    {
      List localList = localTradeBreedRuleManager.getFirmBreedMaxHoldQty();
      paramHttpServletRequest.setAttribute("firmBreedMaxHoldQtyList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询T_A_FirmFee表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("searchFirmBreedMaxHoldQty");
  }
  
  public ActionForward editFirmBreedMaxHoldQty(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editFirmBreedMaxHoldQty' method");
    }
    TradeBreedRuleGCForm localTradeBreedRuleGCForm = (TradeBreedRuleGCForm)paramActionForm;
    String str1 = localTradeBreedRuleGCForm.getCrud();
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    TradeBreedRuleGC localTradeBreedRuleGC = null;
    String str2 = "1";
    String str3 = "1";
    try
    {
      getSelectAttribute(paramHttpServletRequest);
      if (!str1.trim().equals("create"))
      {
        localTradeBreedRuleGC = localTradeBreedRuleManager.getFirmBreedMaxHoldQtyById(localTradeBreedRuleGCForm.getFirmID(), localTradeBreedRuleGCForm.getBreedID());
        if ((localTradeBreedRuleGC.getMaxHoldQty() != null) && ("-1".equals(localTradeBreedRuleGC.getMaxHoldQty().toString()))) {
          str2 = "2";
        }
        if ((localTradeBreedRuleGC.getCleanMaxHoldQty() != null) && ("-1".equals(localTradeBreedRuleGC.getCleanMaxHoldQty().toString()))) {
          str3 = "2";
        }
      }
      else
      {
        localTradeBreedRuleGC = new TradeBreedRuleGC();
      }
      paramHttpServletRequest.setAttribute("type101", str2);
      paramHttpServletRequest.setAttribute("type102", str3);
      localTradeBreedRuleGCForm = (TradeBreedRuleGCForm)convert(localTradeBreedRuleGC);
      String str4 = localTradeBreedRuleGC.getBreedID();
      List localList = (List)paramHttpServletRequest.getAttribute("breedSelect");
      if ((localList != null) && (localList.size() > 0)) {
        for (int i = 0; i < localList.size(); i++)
        {
          LabelValue localLabelValue = (LabelValue)localList.get(i);
          if (localLabelValue.getValue().equals(str4)) {
            paramHttpServletRequest.setAttribute("name", localLabelValue.getLabel());
          }
        }
      }
      paramHttpServletRequest.setAttribute("breedID", str4);
      localTradeBreedRuleGCForm.setCrud(str1);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localTradeBreedRuleGCForm);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return searchFirmBreedMaxHoldQty(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    System.out.println("jump");
    return paramActionMapping.findForward("editFirmBreedMaxHoldQty");
  }
  
  public ActionForward saveFirmBreedMaxHoldQty(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveFirmBreedMaxHoldQty' method");
    }
    TradeBreedRuleGCForm localTradeBreedRuleGCForm = (TradeBreedRuleGCForm)paramActionForm;
    String str1 = localTradeBreedRuleGCForm.getCrud();
    String str2 = paramHttpServletRequest.getParameter("logos");
    TradeRuleManager localTradeRuleManager = (TradeRuleManager)getBean("tradeRuleManager");
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    TradeBreedRuleGC localTradeBreedRuleGC = (TradeBreedRuleGC)convert(localTradeBreedRuleGCForm);
    try
    {
      TradeRuleGC localTradeRuleGC;
      List localList;
      int i;
      Map localMap;
      if (str1.trim().equals("create"))
      {
        if ((str2 != null) && (str2.equals("true")))
        {
          localTradeRuleGC = new TradeRuleGC();
          BeanUtils.copyProperties(localTradeRuleGC, localTradeBreedRuleGC);
          localList = localTradeBreedRuleManager.getFirmCommodityIDFromMaxHoldQtyForAdd(localTradeBreedRuleGC.getFirmID(), localTradeBreedRuleGC.getBreedID());
          for (i = 0; i < localList.size(); i++)
          {
            localMap = (Map)localList.get(i);
            localTradeRuleGC.setCommodityID((String)localMap.get("COMMODITYID"));
            localTradeRuleManager.insertFirmMaxHoldQty(localTradeRuleGC);
          }
          localTradeBreedRuleManager.insertFirmBreedMaxHoldQty(localTradeBreedRuleGC);
          addSysLog(paramHttpServletRequest, "增加品种特殊订货量");
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
        else
        {
          localTradeBreedRuleManager.insertFirmBreedMaxHoldQty(localTradeBreedRuleGC);
          addSysLog(paramHttpServletRequest, "增加品种特殊订货量");
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
      }
      else if (str1.trim().equals("update")) {
        if ((str2 != null) && (str2.equals("true")))
        {
          localTradeRuleGC = new TradeRuleGC();
          BeanUtils.copyProperties(localTradeRuleGC, localTradeBreedRuleGC);
          localList = localTradeBreedRuleManager.getFirmCommodityIDFromMaxHoldQty(localTradeBreedRuleGC.getFirmID(), localTradeBreedRuleGC.getBreedID());
          for (i = 0; i < localList.size(); i++)
          {
            localMap = (Map)localList.get(i);
            localTradeRuleGC.setCommodityID((String)localMap.get("COMMODITYID"));
            localTradeRuleManager.updateFirmMaxHoldQty(localTradeRuleGC);
          }
          localTradeBreedRuleManager.updateFirmBreedMaxHoldQty(localTradeBreedRuleGC);
          addSysLog(paramHttpServletRequest, "修改品种特殊订货量");
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
        else
        {
          localTradeBreedRuleManager.updateFirmBreedMaxHoldQty(localTradeBreedRuleGC);
          addSysLog(paramHttpServletRequest, "修改品种特殊订货量");
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      getSelectAttribute(paramHttpServletRequest);
      return paramActionMapping.findForward("editFirmBreedMaxHoldQty");
    }
    return searchFirmBreedMaxHoldQty(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward deleteFirmBreedMaxHoldQty(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deleteFirmBreedMaxHoldQty' method");
    }
    TradeBreedRuleManager localTradeBreedRuleManager = (TradeBreedRuleManager)getBean("tradeBreedRuleManager");
    TradeRuleManager localTradeRuleManager = (TradeRuleManager)getBean("tradeRuleManager");
    String str1 = paramHttpServletRequest.getParameter("logos");
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
    int i = 0;
    if (arrayOfString1 != null)
    {
      this.log.debug("==ids.length:" + arrayOfString1.length);
      String str4 = "";
      for (int j = 0; j < arrayOfString1.length; j++)
      {
        String[] arrayOfString2 = arrayOfString1[j].split(",");
        String str2 = arrayOfString2[1];
        String str3 = arrayOfString2[0];
        try
        {
          if ((str1 != null) && (str1.equals("true")))
          {
            List localList = localTradeBreedRuleManager.getFirmCommodityIDFromMaxHoldQty(str3, str2);
            for (int k = 0; k < localList.size(); k++)
            {
              Map localMap = (Map)localList.get(k);
              localTradeRuleManager.deleteFirmMaxHoldQtyById((String)localMap.get("FIRMID"), (String)localMap.get("COMMODITYID"));
            }
            localTradeBreedRuleManager.deleteFirmBreedMaxHoldQtyById(str3, str2);
            addSysLog(paramHttpServletRequest, "删除品种特殊订货量");
            i++;
          }
          else
          {
            localTradeBreedRuleManager.deleteFirmBreedMaxHoldQtyById(str3, str2);
            addSysLog(paramHttpServletRequest, "删除品种特殊订货量");
            i++;
          }
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str4 = str4 + arrayOfString1[j] + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + arrayOfString1[j] + "]与其他数据关联，删除失败！");
        }
      }
      if (!str4.equals(""))
      {
        str4 = str4.substring(0, str4.length() - 1);
        str4 = str4 + "与其他数据关联，不能删除！";
      }
      str4 = str4 + "成功删除" + i + "条纪录！";
      paramHttpServletRequest.setAttribute("prompt", str4);
    }
    return searchFirmBreedMaxHoldQty(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  private void getSelectAttribute(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
    paramHttpServletRequest.setAttribute("breedSelect", localLookupManager.getSelectLabelValueByTable("T_A_breed", "BreedID", "BreedID", " order by breedname "));
  }
}
