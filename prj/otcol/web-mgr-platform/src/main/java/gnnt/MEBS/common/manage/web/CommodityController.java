package gnnt.MEBS.common.manage.web;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.common.manage.model.Apply_T_CommodityFee;
import gnnt.MEBS.common.manage.model.Apply_T_CommodityMargin;
import gnnt.MEBS.common.manage.service.ApplyManage;
import gnnt.MEBS.common.manage.service.CommodityManage;
import gnnt.MEBS.common.manage.util.CommonDictionary;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.finance.base.util.SysData;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class CommodityController
  extends MultiActionController
{
  private final transient Log logger = LogFactory.getLog(CommodityController.class);
  
  public ModelAndView listCommodityFeeApp(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'listCommodityFeeApp' method");
    }
    String str1 = paramHttpServletRequest.getParameter("commodityID");
    String str2 = paramHttpServletRequest.getParameter("status");
    String str3 = AclCtrl.getLogonID(paramHttpServletRequest);
    Apply_T_CommodityFee localApply_T_CommodityFee = new Apply_T_CommodityFee();
    localApply_T_CommodityFee.setCommodityID(str1);
    if ((str2 != null) && (!"".equals(str2))) {
      localApply_T_CommodityFee.setStatus(Integer.parseInt(str2));
    }
    localApply_T_CommodityFee.setProposer(str3);
    localApply_T_CommodityFee.setApplyType(3);
    ApplyManage localApplyManage = (ApplyManage)SysData.getBean("applyManager");
    ModelAndView localModelAndView = null;
    localModelAndView = new ModelAndView("common/timebargain/commodityFee/app/commodityFeeApp_list", "resultList", "");
    try
    {
      List localList = localApplyManage.getApplys(localApply_T_CommodityFee);
      localModelAndView.addObject("commodityFeeList", localList);
      localModelAndView.addObject("FEEALGR", CommonDictionary.FEEALGR);
      localModelAndView.addObject("SETTLEFEEALGR", CommonDictionary.SETTLEFEEALGR);
      localModelAndView.addObject("VIRTUALFUNDSTATUS", CommonDictionary.VIRTUALFUNDSTATUS);
    }
    catch (Exception localException)
    {
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    return localModelAndView;
  }
  
  public ModelAndView editCommodityFeeApp(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'editCommodityFeeApp' method");
    }
    ModelAndView localModelAndView = new ModelAndView("common/timebargain/commodityFee/app/commodityFeeApp_form", "resultList", "");
    return localModelAndView;
  }
  
  public ModelAndView saveCommodityFeeApp(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'saveCommodityFeeApp' method");
    }
    ApplyManage localApplyManage = (ApplyManage)SysData.getBean("applyManager");
    String str = AclCtrl.getLogonID(paramHttpServletRequest);
    Apply_T_CommodityFee localApply_T_CommodityFee = new Apply_T_CommodityFee();
    ModelAndView localModelAndView = new ModelAndView("common/timebargain/commodityFee/app/commodityFeeApp_form_save", "resultList", "");
    try
    {
      ParamUtil.bindData(paramHttpServletRequest, localApply_T_CommodityFee);
      localApply_T_CommodityFee.setApplyType(3);
      localApply_T_CommodityFee.setStatus(1);
      localApply_T_CommodityFee.setProposer(str);
      localApplyManage.insertApply(localApply_T_CommodityFee);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    return localModelAndView;
  }
  
  public ModelAndView listCommodityFeeChe(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'listCommodityFeeChe' method");
    }
    String str1 = paramHttpServletRequest.getParameter("commodityID");
    String str2 = paramHttpServletRequest.getParameter("status");
    String str3 = AclCtrl.getLogonID(paramHttpServletRequest);
    Apply_T_CommodityFee localApply_T_CommodityFee = new Apply_T_CommodityFee();
    localApply_T_CommodityFee.setCommodityID(str1);
    if ((str2 != null) && (!"".equals(str2))) {
      localApply_T_CommodityFee.setStatus(Integer.parseInt(str2));
    }
    localApply_T_CommodityFee.setProposer(str3);
    localApply_T_CommodityFee.setApplyType(3);
    ApplyManage localApplyManage = (ApplyManage)SysData.getBean("applyManager");
    ModelAndView localModelAndView = null;
    localModelAndView = new ModelAndView("common/timebargain/commodityFee/check/commodityFeeChe_list", "resultList", "");
    try
    {
      List localList = localApplyManage.getApplys(localApply_T_CommodityFee);
      localModelAndView.addObject("commodityFeeList", localList);
      localModelAndView.addObject("FEEALGR", CommonDictionary.FEEALGR);
      localModelAndView.addObject("SETTLEFEEALGR", CommonDictionary.SETTLEFEEALGR);
      localModelAndView.addObject("VIRTUALFUNDSTATUS", CommonDictionary.VIRTUALFUNDSTATUS);
    }
    catch (Exception localException)
    {
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    return localModelAndView;
  }
  
  public ModelAndView checkCommodityFee(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'checkCommodityFee' method");
    }
    Apply_T_CommodityFee localApply_T_CommodityFee = new Apply_T_CommodityFee();
    String str = AclCtrl.getLogonID(paramHttpServletRequest);
    ModelAndView localModelAndView = null;
    localModelAndView = new ModelAndView("common/timebargain/commodityFee/check/commodityFeeChe_form_save", "resultList", "");
    try
    {
      ParamUtil.bindData(paramHttpServletRequest, localApply_T_CommodityFee);
      localApply_T_CommodityFee.setApprover(str);
      CommodityManage localCommodityManage = (CommodityManage)SysData.getBean("commodityManager");
      int i = localCommodityManage.updateCommodityFeeCheck(localApply_T_CommodityFee);
      if (i == -1) {
        localModelAndView.addObject("prompt", "此记录已审核完毕！");
      } else if ((i != 0) && (i == 1)) {
        localModelAndView.addObject("prompt", "商品代码不存在！");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    return localModelAndView;
  }
  
  public ModelAndView listCommodityMarginApp(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'listCommodityMarginApp' method");
    }
    String str1 = paramHttpServletRequest.getParameter("commodityID");
    String str2 = paramHttpServletRequest.getParameter("status");
    String str3 = AclCtrl.getLogonID(paramHttpServletRequest);
    Apply_T_CommodityMargin localApply_T_CommodityMargin = new Apply_T_CommodityMargin();
    localApply_T_CommodityMargin.setCommodityID(str1);
    if ((str2 != null) && (!"".equals(str2))) {
      localApply_T_CommodityMargin.setStatus(Integer.parseInt(str2));
    }
    localApply_T_CommodityMargin.setProposer(str3);
    localApply_T_CommodityMargin.setApplyType(4);
    ApplyManage localApplyManage = (ApplyManage)SysData.getBean("applyManager");
    ModelAndView localModelAndView = null;
    localModelAndView = new ModelAndView("common/timebargain/commodityMargin/app/commodityMarginApp_list", "resultList", "");
    try
    {
      List localList = localApplyManage.getApplys(localApply_T_CommodityMargin);
      localModelAndView.addObject("commodityMarginList", localList);
      localModelAndView.addObject("FEEALGR", CommonDictionary.FEEALGR);
      localModelAndView.addObject("MARGINPRICETYPE", CommonDictionary.MARGINPRICETYPE);
      localModelAndView.addObject("SETTLEFEEALGR", CommonDictionary.SETTLEFEEALGR);
      localModelAndView.addObject("VIRTUALFUNDSTATUS", CommonDictionary.VIRTUALFUNDSTATUS);
    }
    catch (Exception localException)
    {
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    return localModelAndView;
  }
  
  public ModelAndView editCommodityMarginApp(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'editCommodityMarginApp' method");
    }
    ModelAndView localModelAndView = new ModelAndView("common/timebargain/commodityMargin/app/commodityMarginApp_form", "resultList", "");
    return localModelAndView;
  }
  
  public ModelAndView saveCommodityMarginApp(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'saveCommodityMarginApp' method");
    }
    ApplyManage localApplyManage = (ApplyManage)SysData.getBean("applyManager");
    String str1 = AclCtrl.getLogonID(paramHttpServletRequest);
    Apply_T_CommodityMargin localApply_T_CommodityMargin = new Apply_T_CommodityMargin();
    ModelAndView localModelAndView = new ModelAndView("common/timebargain/commodityMargin/app/commodityMarginApp_form_save", "resultList", "");
    try
    {
      ParamUtil.bindData(paramHttpServletRequest, localApply_T_CommodityMargin);
      String str2 = paramHttpServletRequest.getParameter("type");
      String str3 = paramHttpServletRequest.getParameter("type1");
      String str4 = paramHttpServletRequest.getParameter("type2");
      String str5 = paramHttpServletRequest.getParameter("type3");
      String str6 = paramHttpServletRequest.getParameter("type4");
      if (("1".equals(str2)) && (localApply_T_CommodityMargin.getMarginItem1() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItem1())) && (localApply_T_CommodityMargin.getMarginItemAssure1() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItemAssure1())))
      {
        localApply_T_CommodityMargin.setMarginItem1_S(localApply_T_CommodityMargin.getMarginItem1());
        localApply_T_CommodityMargin.setMarginItemAssure1_S(localApply_T_CommodityMargin.getMarginItemAssure1());
      }
      if (("1".equals(str3)) && (localApply_T_CommodityMargin.getMarginItem2() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItem2())) && (localApply_T_CommodityMargin.getMarginItemAssure2() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItemAssure2())))
      {
        localApply_T_CommodityMargin.setMarginItem2_S(localApply_T_CommodityMargin.getMarginItem2());
        localApply_T_CommodityMargin.setMarginItemAssure2_S(localApply_T_CommodityMargin.getMarginItemAssure2());
      }
      if (("1".equals(str4)) && (localApply_T_CommodityMargin.getMarginItem3() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItem3())) && (localApply_T_CommodityMargin.getMarginItemAssure3() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItemAssure3())))
      {
        localApply_T_CommodityMargin.setMarginItem3_S(localApply_T_CommodityMargin.getMarginItem3());
        localApply_T_CommodityMargin.setMarginItemAssure3_S(localApply_T_CommodityMargin.getMarginItemAssure3());
      }
      if (("1".equals(str5)) && (localApply_T_CommodityMargin.getMarginItem4() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItem4())) && (localApply_T_CommodityMargin.getMarginItemAssure4() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItemAssure4())))
      {
        localApply_T_CommodityMargin.setMarginItem4_S(localApply_T_CommodityMargin.getMarginItem4());
        localApply_T_CommodityMargin.setMarginItemAssure4_S(localApply_T_CommodityMargin.getMarginItemAssure4());
      }
      if (("1".equals(str6)) && (localApply_T_CommodityMargin.getMarginItem5() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItem5())) && (localApply_T_CommodityMargin.getMarginItemAssure5() != null) && (!"".equals(localApply_T_CommodityMargin.getMarginItemAssure5())))
      {
        localApply_T_CommodityMargin.setMarginItem5_S(localApply_T_CommodityMargin.getMarginItem5());
        localApply_T_CommodityMargin.setMarginItemAssure5_S(localApply_T_CommodityMargin.getMarginItemAssure5());
      }
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String str7 = paramHttpServletRequest.getParameter("settleDate7");
      String str8 = paramHttpServletRequest.getParameter("settleDate1");
      String str9 = paramHttpServletRequest.getParameter("settleDate2");
      String str10 = paramHttpServletRequest.getParameter("settleDate3");
      String str11 = paramHttpServletRequest.getParameter("settleDate4");
      String str12 = paramHttpServletRequest.getParameter("settleDate5");
      if ((str7 != null) && (!"".equals(str7))) {
        localApply_T_CommodityMargin.setSettleDate(str7);
      }
      if ((str8 != null) && (!"".equals(str8)))
      {
        localApply_T_CommodityMargin.setSettleDate1(str8);
        localApply_T_CommodityMargin.setMarketDate(str8);
      }
      if ((str9 != null) && (!"".equals(str9))) {
        localApply_T_CommodityMargin.setSettleDate2(str9);
      }
      if ((str10 != null) && (!"".equals(str10))) {
        localApply_T_CommodityMargin.setSettleDate3(str10);
      }
      if ((str11 != null) && (!"".equals(str11))) {
        localApply_T_CommodityMargin.setSettleDate4(str11);
      }
      if ((str12 != null) && (!"".equals(str12))) {
        localApply_T_CommodityMargin.setSettleDate5(str12);
      }
      localApply_T_CommodityMargin.setApplyType(4);
      localApply_T_CommodityMargin.setStatus(1);
      localApply_T_CommodityMargin.setProposer(str1);
      localApplyManage.insertApply(localApply_T_CommodityMargin);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    return localModelAndView;
  }
  
  public ModelAndView listCommodityMarginChe(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'listCommodityMarginChe' method");
    }
    String str1 = paramHttpServletRequest.getParameter("commodityID");
    String str2 = paramHttpServletRequest.getParameter("status");
    String str3 = AclCtrl.getLogonID(paramHttpServletRequest);
    Apply_T_CommodityMargin localApply_T_CommodityMargin = new Apply_T_CommodityMargin();
    localApply_T_CommodityMargin.setCommodityID(str1);
    if ((str2 != null) && (!"".equals(str2))) {
      localApply_T_CommodityMargin.setStatus(Integer.parseInt(str2));
    }
    localApply_T_CommodityMargin.setApplyType(4);
    ApplyManage localApplyManage = (ApplyManage)SysData.getBean("applyManager");
    ModelAndView localModelAndView = null;
    localModelAndView = new ModelAndView("common/timebargain/commodityMargin/check/commodityMarginChe_list", "resultList", "");
    try
    {
      List localList = localApplyManage.getApplys(localApply_T_CommodityMargin);
      localModelAndView.addObject("commodityMarginList", localList);
      localModelAndView.addObject("FEEALGR", CommonDictionary.FEEALGR);
      localModelAndView.addObject("MARGINPRICETYPE", CommonDictionary.MARGINPRICETYPE);
      localModelAndView.addObject("SETTLEFEEALGR", CommonDictionary.SETTLEFEEALGR);
      localModelAndView.addObject("VIRTUALFUNDSTATUS", CommonDictionary.VIRTUALFUNDSTATUS);
    }
    catch (Exception localException)
    {
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    return localModelAndView;
  }
  
  public ModelAndView checkCommodityMargin(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("Entering 'checkCommodityMargin' method");
    }
    Apply_T_CommodityMargin localApply_T_CommodityMargin = new Apply_T_CommodityMargin();
    String str = AclCtrl.getLogonID(paramHttpServletRequest);
    ModelAndView localModelAndView = null;
    localModelAndView = new ModelAndView("common/timebargain/commodityMargin/check/commodityMarginChe_form_save", "resultList", "");
    try
    {
      ParamUtil.bindData(paramHttpServletRequest, localApply_T_CommodityMargin);
      localApply_T_CommodityMargin.setApprover(str);
      CommodityManage localCommodityManage = (CommodityManage)SysData.getBean("commodityManager");
      int i = localCommodityManage.updateCommodityMarginCheck(localApply_T_CommodityMargin);
      if (i == -1) {
        localModelAndView.addObject("prompt", "此记录已审核完毕！");
      } else if ((i != 0) && (i == 1)) {
        localModelAndView.addObject("prompt", "商品代码不存在！");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.logger.error("==err:" + localException);
      localModelAndView.addObject("prompt", localException.getMessage());
    }
    return localModelAndView;
  }
}
