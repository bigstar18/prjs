package gnnt.MEBS.delivery.action;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.delivery.model.Commodity;
import gnnt.MEBS.delivery.model.LogValue;
import gnnt.MEBS.delivery.model.PaymentProps;
import gnnt.MEBS.delivery.services.CommodityService;
import gnnt.MEBS.delivery.services.PaymentPropsService;
import gnnt.MEBS.delivery.util.SysData;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class PaymentPropsController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(PaymentPropsController.class);
  
  public ModelAndView paymentPropsList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter paymentPropsList-------------");
    QueryConditions localQueryConditions1 = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "moduleid", false);
    }
    PaymentPropsService localPaymentPropsService = (PaymentPropsService)SysData.getBean("w_paymentPropsService");
    List localList1 = localPaymentPropsService.getPaymentPropsList(localQueryConditions1, localPageInfo);
    HashMap localHashMap = (HashMap)SysData.getBean("w_moduleNameMap");
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    QueryConditions localQueryConditions2 = QueryHelper.getQueryConditionsFromRequestAttribute(paramHttpServletRequest, AttributeForCommodityList);
    List localList2 = localCommodityService.getCommodityList(localQueryConditions2, null);
    Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "paymentProps/paymentPropsList", "resultList", localList1);
    localModelAndView.addObject("PayCommoditysList", localList2);
    localModelAndView.addObject("moduleNameMap", localHashMap);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView paymentPropsView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter paymentPropsView-------------");
    String str1 = paramHttpServletRequest.getParameter("requestModuleID");
    String str2 = paramHttpServletRequest.getParameter("requestBreedID");
    String str3 = paramHttpServletRequest.getParameter("requestSettleDayNo");
    PaymentPropsService localPaymentPropsService = (PaymentPropsService)SysData.getBean("w_paymentPropsService");
    PaymentProps localPaymentProps = localPaymentPropsService.getPaymentPropsByCondition(str1, str2, str3);
    HashMap localHashMap = (HashMap)SysData.getBean("w_moduleNameMap");
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    Commodity localCommodity = localCommodityService.getCommodityById(localPaymentProps.getBreedID() + "", false);
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "paymentProps/paymentPropsView", "pay", localPaymentProps);
    localModelAndView.addObject("moduleNameMap", localHashMap);
    localModelAndView.addObject("commodity", localCommodity);
    return localModelAndView;
  }
  
  public ModelAndView paymentPropsAddForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter paymentPropsAddForward-------------");
    HashMap localHashMap = (HashMap)SysData.getBean("w_moduleNameMap");
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    List localList = localCommodityService.getCommodityListMap();
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "paymentProps/paymentPropsAdd", "payCommoditysList", localList);
    localModelAndView.addObject("moduleNameMap", localHashMap);
    return localModelAndView;
  }
  
  public ModelAndView paymentPropsAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter paymentPropsAddAction-------------");
    String str1 = paramHttpServletRequest.getParameter("moduleID");
    String str2 = paramHttpServletRequest.getParameter("breedID");
    String str3 = paramHttpServletRequest.getParameter("settleDayNo");
    String str4 = paramHttpServletRequest.getParameter("buyPayoutPct");
    String str5 = paramHttpServletRequest.getParameter("sellIncomePct");
    PaymentProps localPaymentProps = new PaymentProps();
    localPaymentProps.setModuleID(str1);
    if ((str2 != null) && (!"".equals(str2))) {
      localPaymentProps.setBreedID(Long.parseLong(str2));
    }
    if ((str3 != null) && (!"".equals(str3))) {
      localPaymentProps.setSettleDayNo(Integer.parseInt(str3));
    }
    if ((str4 != null) && (!"".equals(str4))) {
      localPaymentProps.setBuyPayoutPct(Double.parseDouble(str4));
    }
    if ((str5 != null) && (!"".equals(str5))) {
      localPaymentProps.setSellIncomePct(Double.parseDouble(str5));
    }
    LogValue localLogValue = new LogValue();
    localLogValue.setOperator(AclCtrl.getLogonID(paramHttpServletRequest));
    localLogValue.setOperatime(new Date());
    localLogValue.setModule("0");
    localLogValue.setType(0);
    localLogValue.setContent("添加货款收付设置记录");
    PaymentPropsService localPaymentPropsService = (PaymentPropsService)SysData.getBean("w_paymentPropsService");
    String str6 = "";
    try
    {
      localPaymentPropsService.insertPaymentProps(localPaymentProps, localLogValue);
      str6 = "添加成功！";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str6 = "添加失败！";
    }
    ModelAndView localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "servlet/paymentPropsController." + Condition.POSTFIX + "?funcflg=paymentPropsList&resultMsg=" + str6);
    return localModelAndView;
  }
  
  public ModelAndView paymentPropsUpdateForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter paymentPropsUpdateForward-------------");
    String str1 = paramHttpServletRequest.getParameter("requestModuleID");
    String str2 = paramHttpServletRequest.getParameter("requestBreedID");
    String str3 = paramHttpServletRequest.getParameter("requestSettleDayNo");
    PaymentPropsService localPaymentPropsService = (PaymentPropsService)SysData.getBean("w_paymentPropsService");
    PaymentProps localPaymentProps = localPaymentPropsService.getPaymentPropsByCondition(str1, str2, str3);
    HashMap localHashMap = (HashMap)SysData.getBean("w_moduleNameMap");
    CommodityService localCommodityService = (CommodityService)SysData.getBean("w_commodityService");
    Commodity localCommodity = localCommodityService.getCommodityById(localPaymentProps.getBreedID() + "", false);
    ModelAndView localModelAndView = new ModelAndView(Condition.DELIVERYPATH + "paymentProps/paymentPropsUpdate", "pay", localPaymentProps);
    localModelAndView.addObject("moduleNameMap", localHashMap);
    localModelAndView.addObject("commodity", localCommodity);
    return localModelAndView;
  }
  
  public ModelAndView paymentPropsUpdate(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter paymentPropsUpdateAction-------------");
    String str1 = paramHttpServletRequest.getParameter("moduleID");
    String str2 = paramHttpServletRequest.getParameter("breedID");
    String str3 = paramHttpServletRequest.getParameter("settleDayNo");
    String str4 = paramHttpServletRequest.getParameter("buyPayoutPct");
    String str5 = paramHttpServletRequest.getParameter("sellIncomePct");
    PaymentProps localPaymentProps = new PaymentProps();
    localPaymentProps.setModuleID(str1);
    if ((str2 != null) && (!"".equals(str2))) {
      localPaymentProps.setBreedID(Long.parseLong(str2));
    }
    if ((str3 != null) && (!"".equals(str3))) {
      localPaymentProps.setSettleDayNo(Integer.parseInt(str3));
    }
    if ((str4 != null) && (!"".equals(str4))) {
      localPaymentProps.setBuyPayoutPct(Double.parseDouble(str4));
    }
    if ((str5 != null) && (!"".equals(str5))) {
      localPaymentProps.setSellIncomePct(Double.parseDouble(str5));
    }
    LogValue localLogValue = new LogValue();
    localLogValue.setOperator(AclCtrl.getLogonID(paramHttpServletRequest));
    localLogValue.setOperatime(new Date());
    localLogValue.setModule("0");
    localLogValue.setType(0);
    localLogValue.setContent("修改货款收付设置记录");
    PaymentPropsService localPaymentPropsService = (PaymentPropsService)SysData.getBean("w_paymentPropsService");
    String str6 = "";
    try
    {
      localPaymentPropsService.updatePaymentProps(localPaymentProps, localLogValue);
      str6 = "修改成功！";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str6 = "修改失败！";
    }
    ModelAndView localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "servlet/paymentPropsController." + Condition.POSTFIX + "?funcflg=paymentPropsList&resultMsg=" + str6);
    return localModelAndView;
  }
  
  public ModelAndView paymentPropsDelete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("---------enter paymentPropsDelete-------------");
    PaymentPropsService localPaymentPropsService = (PaymentPropsService)SysData.getBean("w_paymentPropsService");
    PaymentProps localPaymentProps = new PaymentProps();
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("delCheck");
    this.logger.debug("---------ids[0]:" + arrayOfString1[0]);
    LogValue localLogValue = new LogValue();
    localLogValue.setOperator(AclCtrl.getLogonID(paramHttpServletRequest));
    localLogValue.setOperatime(new Date());
    localLogValue.setModule("0");
    localLogValue.setType(0);
    localLogValue.setContent("删除货款收付设置记录");
    String str1 = "";
    if (arrayOfString1 != null) {
      for (int i = 0; i < arrayOfString1.length; i++)
      {
        String str2 = arrayOfString1[i];
        if ((str2 != null) && (!"".equals(str2)))
        {
          String[] arrayOfString2 = str2.split(",");
          localPaymentProps.setModuleID(arrayOfString2[0]);
          String str3 = arrayOfString2[1];
          String str4 = arrayOfString2[2];
          if ((str3 != null) && (!"".equals(str3))) {
            localPaymentProps.setBreedID(Long.parseLong(str3));
          }
          if ((str4 != null) && (!"".equals(str4))) {
            localPaymentProps.setSettleDayNo(Integer.parseInt(str4));
          }
          try
          {
            localPaymentPropsService.deletePaymentProps(localPaymentProps, localLogValue);
            str1 = "删除成功！";
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            str1 = "删除失败！";
          }
        }
      }
    }
    ModelAndView localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "servlet/paymentPropsController." + Condition.POSTFIX + "?funcflg=paymentPropsList&resultMsg=" + str1);
    return localModelAndView;
  }
}
