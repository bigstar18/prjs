package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.member.firm.services.FirmService;
import gnnt.MEBS.member.firm.util.SysData;
import gnnt.MEBS.timebargain.manage.model.StatQuery;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.service.StatQueryManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import gnnt.MEBS.timebargain.manage.webapp.form.StatQueryForm;
import gnnt.MEBS.timebargain.manage.webapp.util.QueryUtil;
import gnnt.MEBS.timebargain.server.model.Settle;
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
import org.ecside.table.limit.Limit;
import org.ecside.table.limit.Sort;
import org.ecside.util.ECSideUtils;
import org.ecside.util.RequestUtils;
import org.springframework.dao.DataIntegrityViolationException;

public class StatQueryAction
  extends BaseAction
{
  public ActionForward topOrder(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topOrder' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    paramHttpServletRequest.setAttribute("lastDay", getThisDay());
    List localList = ((FirmService)SysData.getBean("m_firmService")).getFirmCategoryList(null, null, null);
    paramHttpServletRequest.setAttribute("resultList", localList);
    return paramActionMapping.findForward("topOrder");
  }
  
  public ActionForward listOrder(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listOrder' method");
    }
    List localList1 = ((FirmService)SysData.getBean("m_firmService")).getFirmCategoryList(null, null, null);
    paramHttpServletRequest.setAttribute("resultList", localList1);
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    QueryConditions localQueryConditions1 = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions2 = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions3 = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions4 = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    Limit localLimit = RequestUtils.getLimit(paramHttpServletRequest);
    Sort localSort = localLimit.getSort();
    Map localMap = localSort.getSortValueMap();
    int i = 0;
    int j = RequestUtils.getTotalRowsFromRequest(paramHttpServletRequest);
    try
    {
      if (j < 0) {
        j = localStatQueryManager.getOrderCount(localQueryConditions2);
      }
      localLimit.setRowAttributes(j, 20);
      int k = localLimit.getRowStart() + i + 1;
      int m = localLimit.getRowEnd() + i == 0 ? 20 : localLimit.getRowEnd() + i;
      String str = ECSideUtils.getDefaultSortSQL(localMap);
      localQueryConditions3.addCondition("orderby", "=", str);
      List localList2 = localStatQueryManager.getOrders(localQueryConditions3, k, m);
      List localList3 = localStatQueryManager.getOrderSums(localQueryConditions4);
      List localList4 = localStatQueryManager.getOrderSum(localQueryConditions4);
      if ((localList4 != null) && (localList4.size() > 0))
      {
        paramHttpServletRequest.setAttribute("QuantitySum", ((Map)localList4.get(0)).get("QuantitySum") + "");
        paramHttpServletRequest.setAttribute("TradeQtySum", ((Map)localList4.get(0)).get("TradeQtySum") + "");
      }
      if ((localList3 != null) && (localList3.size() > 0))
      {
        paramHttpServletRequest.setAttribute("sumQuantity", ((Map)localList3.get(0)).get("sumQuantity") + "");
        paramHttpServletRequest.setAttribute("sumTradeQty", ((Map)localList3.get(0)).get("sumTradeQty") + "");
      }
      if (j < 0) {
        j = localStatQueryManager.getOrderCount(localQueryConditions2);
      }
      localLimit.setRowAttributes(j, 20);
      localQueryConditions3.addCondition("orderby", "=", str);
      if ((localList4 != null) && (localList4.size() > 0))
      {
        paramHttpServletRequest.setAttribute("QuantitySum", ((Map)localList4.get(0)).get("QuantitySum") + "");
        paramHttpServletRequest.setAttribute("TradeQtySum", ((Map)localList4.get(0)).get("TradeQtySum") + "");
      }
      paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
      paramHttpServletRequest.setAttribute("ORDERTYPE", CommonDictionary.ORDERTYPE);
      paramHttpServletRequest.setAttribute("ORDER_STATUS", CommonDictionary.ORDER_STATUS);
      paramHttpServletRequest.setAttribute("CLOSEMODE", CommonDictionary.CLOSEMODE);
      paramHttpServletRequest.setAttribute("TIMEFLAG", CommonDictionary.TIMEFLAG);
      paramHttpServletRequest.setAttribute("CLOSEFlAG2", CommonDictionary.CLOSEFlAG2);
      paramHttpServletRequest.setAttribute("BILLTRADETYPE", CommonDictionary.BILLTRADETYPE);
      paramHttpServletRequest.setAttribute("orderList", localList2);
    }
    catch (Exception localException)
    {
      System.out.println("------------------出现异常----------------------");
      localException.printStackTrace();
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listOrder");
  }
  
  public ActionForward topHoldPosition(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topHoldPosition' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    getSelectAttribute(paramHttpServletRequest);
    List localList = localStatQueryManager.getFirmCategory();
    paramHttpServletRequest.setAttribute("lastDay", getThisDay());
    paramHttpServletRequest.setAttribute("firmCategoryList", localList);
    return paramActionMapping.findForward("topHoldPosition");
  }
  
  public ActionForward topHoldPositionDD(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topHoldPositionDD' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    paramHttpServletRequest.setAttribute("lastDay", getThisDay());
    return paramActionMapping.findForward("topHoldPositionDD");
  }
  
  public ActionForward listHoldPosition(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listHoldPosition' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    QueryConditions localQueryConditions1 = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions2 = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions3 = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    Limit localLimit = RequestUtils.getLimit(paramHttpServletRequest);
    Sort localSort = localLimit.getSort();
    Map localMap = localSort.getSortValueMap();
    int i = 0;
    int j = RequestUtils.getTotalRowsFromRequest(paramHttpServletRequest);
    try
    {
      if (j < 0) {
        j = localStatQueryManager.getHoldPositionCount(localQueryConditions1);
      }
      localLimit.setRowAttributes(j, 20);
      int k = localLimit.getRowStart() + i + 1;
      int m = localLimit.getRowEnd() + i == 0 ? 20 : localLimit.getRowEnd() + i;
      String str = ECSideUtils.getDefaultSortSQL(localMap);
      String[] arrayOfString = str.split(" ");
      int n = 0;
      if ((arrayOfString.length >= 4) && ("firmID".equals(arrayOfString[3])))
      {
        arrayOfString[3] = "a.firmID";
        n = 1;
      }
      if ((arrayOfString.length >= 4) && ("CustomerID".equals(arrayOfString[3])))
      {
        arrayOfString[3] = "a.CustomerID";
        str = arrayOfString.toString();
        n = 1;
      }
      if (n != 0)
      {
        str = " ";
        for (int i1 = 0; i1 < arrayOfString.length; i1++) {
          str = str + arrayOfString[i1] + " ";
        }
      }
      localQueryConditions1.addCondition("orderby", "=", str);
      List localList1 = localStatQueryManager.getHoldPositions(localQueryConditions1, k, m);
      List localList2 = localStatQueryManager.getFirmCategory();
      paramHttpServletRequest.setAttribute("firmCategoryList", localList2);
      paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
      paramHttpServletRequest.setAttribute("holdPositionList", localList1);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listHoldPosition");
  }
  
  public ActionForward listHoldPositionDD(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listHoldPositionDD' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localStatQueryManager.getHoldPositions1(localQueryConditions);
      paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
      paramHttpServletRequest.setAttribute("DIDING_TYPE", CommonDictionary.DIDING_TYPE);
      paramHttpServletRequest.setAttribute("holdPositionList", localList);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listHoldPositionDD");
  }
  
  public ActionForward modifyStatusAndQuantity(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'modifyStatusAndQuantity' method");
    }
    return paramActionMapping.findForward("returntop");
  }
  
  public ActionForward topHand(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topHand' method");
    }
    return paramActionMapping.findForward("topHand");
  }
  
  public ActionForward topSettle(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topSettle' method");
    }
    return paramActionMapping.findForward("topSettle");
  }
  
  public ActionForward listHand(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listHand' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localStatQueryManager.getHandSettle(localQueryConditions);
      paramHttpServletRequest.setAttribute("list", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listHand");
  }
  
  public ActionForward listWaitHand(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listWaitHand' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localStatQueryManager.getHandSettle(localQueryConditions);
      paramHttpServletRequest.setAttribute("list", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listWaitHand");
  }
  
  public ActionForward listSettle(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listSettle' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    String str1 = paramHttpServletRequest.getParameter("commodityID");
    String str2 = paramHttpServletRequest.getParameter("firmID");
    String str3 = paramHttpServletRequest.getParameter("BS_Flag");
    String str4 = paramHttpServletRequest.getParameter("settleProcessDate");
    String str5 = paramHttpServletRequest.getParameter("settleType");
    StatQuery localStatQuery = new StatQuery();
    localStatQuery.setCommodityID(str1);
    localStatQuery.setFirmID(str2);
    if ((str3 != null) && (!"".equals(str3))) {
      localStatQuery.setBS_Flag(Short.valueOf(Short.parseShort(str3)));
    }
    localStatQuery.setSettleProcessDate(str4);
    if ((str5 != null) && (!"".equals(str5))) {
      localStatQuery.setSettleType(Short.valueOf(Short.parseShort(str5)));
    }
    try
    {
      List localList = localStatQueryManager.getSettle(localStatQuery);
      paramHttpServletRequest.setAttribute("settleList", localList);
      paramHttpServletRequest.setAttribute("BS_FLAG2", CommonDictionary.BS_FLAG2);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", "请输入正确的日期格式");
    }
    return paramActionMapping.findForward("listSettle");
  }
  
  public ActionForward listHandCheck(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listHandCheck' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    String str = paramHttpServletRequest.getParameter("commodityID");
    try
    {
      Boolean localBoolean = localStatQueryManager.getHandSettleCnt(str);
      paramHttpServletRequest.setAttribute("isOK", localBoolean);
      paramHttpServletRequest.setAttribute("commodityID", str);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listHandCheck");
  }
  
  public ActionForward settleProcessHand(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'settleProcessHand' method");
    }
    String str = paramHttpServletRequest.getParameter("commodityID");
    AgencyRMIBean localAgencyRMIBean = new AgencyRMIBean(paramHttpServletRequest);
    int i = 0;
    try
    {
      i = localAgencyRMIBean.settleProcess(str);
      if (i == 1) {
        paramHttpServletRequest.setAttribute("prompt", "成功！");
      } else if (i == -1) {
        paramHttpServletRequest.setAttribute("prompt", "交收时所需数据不全！");
      } else if (i == -100) {
        paramHttpServletRequest.setAttribute("prompt", "其它错误！");
      } else if (i == -202) {
        paramHttpServletRequest.setAttribute("prompt", "不是闭市或结算完成状态，不能交收！");
      } else if (i == -204) {
        paramHttpServletRequest.setAttribute("prompt", "下单服务器已关闭！");
      }
    }
    catch (Exception localException)
    {
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("settleProcessHand");
  }
  
  public ActionForward saveSettle(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveSettle' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    String str1 = paramHttpServletRequest.getParameter("ids");
    String str2 = paramHttpServletRequest.getParameter("settleQtys");
    String str3 = paramHttpServletRequest.getParameter("fellbackQtys");
    int i = 0;
    String str4 = "";
    if ((str1 != null) && (str2 != null) && (str3 != null))
    {
      String[] arrayOfString1 = str1.split(",");
      String[] arrayOfString2 = str2.split(",");
      String[] arrayOfString3 = str3.split(",");
      if ((arrayOfString1 != null) && (arrayOfString2 != null) && (arrayOfString3 != null))
      {
        for (int j = 0; j < arrayOfString1.length; j++)
        {
          StatQuery localStatQuery = new StatQuery();
          localStatQuery.setId(Integer.valueOf(Integer.parseInt(arrayOfString1[j])));
          localStatQuery.setSettleQty(Long.valueOf(Long.parseLong(arrayOfString2[j])));
          localStatQuery.setFellbackQty(Long.valueOf(Long.parseLong(arrayOfString3[j])));
          try
          {
            localStatQueryManager.updateSettle(localStatQuery);
            addSysLog(paramHttpServletRequest, "修改交收交易客户订货明细表[" + arrayOfString1[j] + "]");
            i++;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            str4 = str4 + arrayOfString1[j] + ",";
          }
        }
        if (!str4.equals(""))
        {
          str4 = str4.substring(0, str4.length() - 1);
          str4 = str4 + "修改失败！";
        }
        str4 = str4 + "成功修改" + i + "条纪录！";
        paramHttpServletRequest.setAttribute("prompt", str4);
      }
    }
    return paramActionMapping.findForward("saveSettle");
  }
  
  public ActionForward listSum(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listSum' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    List localList = localStatQueryManager.getSettleSum();
    paramHttpServletRequest.setAttribute("sumList", localList);
    return paramActionMapping.findForward("listSum");
  }
  
  public ActionForward listTogether(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listTogether' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    String str = paramHttpServletRequest.getParameter("flag");
    paramHttpServletRequest.setAttribute("flag", str);
    List localList = localStatQueryManager.getSettleTogether(str);
    paramHttpServletRequest.setAttribute("togetherList", localList);
    paramHttpServletRequest.setAttribute("BS_FLAG2", CommonDictionary.BS_FLAG2);
    return paramActionMapping.findForward("listTogether");
  }
  
  public ActionForward topTrade(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topTrade' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    paramHttpServletRequest.setAttribute("lastDay", getThisDay());
    List localList = ((FirmService)SysData.getBean("m_firmService")).getFirmCategoryList(null, null, null);
    paramHttpServletRequest.setAttribute("resultList", localList);
    return paramActionMapping.findForward("topTrade");
  }
  
  public ActionForward listTrade(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listTrade' method");
    }
    List localList1 = ((FirmService)SysData.getBean("m_firmService")).getFirmCategoryList(null, null, null);
    paramHttpServletRequest.setAttribute("resultList", localList1);
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    QueryConditions localQueryConditions1 = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions2 = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions3 = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    Limit localLimit = RequestUtils.getLimit(paramHttpServletRequest);
    Sort localSort = localLimit.getSort();
    Map localMap = localSort.getSortValueMap();
    int i = 0;
    int j = RequestUtils.getTotalRowsFromRequest(paramHttpServletRequest);
    try
    {
      if (j < 0) {
        j = localStatQueryManager.getTradesCount(localQueryConditions1);
      }
      String str = ECSideUtils.getDefaultSortSQL(localMap);
      String[] arrayOfString = str.split(" ");
      int k = 0;
      if ((arrayOfString.length >= 3) && ("CustomerID".equals(arrayOfString[3])))
      {
        arrayOfString[3] = "a.CustomerID";
        str = " ";
        k = 1;
      }
      if ((arrayOfString.length >= 3) && ("FirmID".equals(arrayOfString[3])))
      {
        arrayOfString[3] = "a.FirmID";
        str = " ";
        k = 1;
      }
      if (k != 0) {
        for (m = 0; m < arrayOfString.length; m++) {
          str = str + arrayOfString[m] + " ";
        }
      }
      localQueryConditions2.addCondition("orderby", "=", str);
      localLimit.setRowAttributes(j, 20);
      int m = localLimit.getRowStart() + i + 1;
      int n = localLimit.getRowEnd() + i == 0 ? 20 : localLimit.getRowEnd() + i;
      List localList2 = localStatQueryManager.getTrades(localQueryConditions2, m, n);
      List localList3 = localStatQueryManager.getTradesSums(localQueryConditions3);
      List localList4 = localStatQueryManager.getTradesSum(localQueryConditions3);
      if ((localList4 != null) && (localList4.size() > 0))
      {
        paramHttpServletRequest.setAttribute("QuantitySum", ((Map)localList4.get(0)).get("QuantitySum") + "");
        paramHttpServletRequest.setAttribute("Close_PLSum", ((Map)localList4.get(0)).get("Close_PLSum") + "");
        paramHttpServletRequest.setAttribute("TradeFeeSum", ((Map)localList4.get(0)).get("TradeFeeSum") + "");
        paramHttpServletRequest.setAttribute("CloseAddedTaxSum", ((Map)localList4.get(0)).get("CloseAddedTaxSum") + "");
      }
      if ((localList3 != null) && (localList4.size() > 0))
      {
        paramHttpServletRequest.setAttribute("sumQuantity", ((Map)localList3.get(0)).get("sumQuantity") + "");
        paramHttpServletRequest.setAttribute("sumClose_PL", ((Map)localList3.get(0)).get("sumClose_PL") + "");
        paramHttpServletRequest.setAttribute("sumTradeFee", ((Map)localList3.get(0)).get("sumTradeFee") + "");
        paramHttpServletRequest.setAttribute("sumCloseAddedTax", ((Map)localList3.get(0)).get("sumCloseAddedTax") + "");
      }
      paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
      paramHttpServletRequest.setAttribute("ORDERTYPE", CommonDictionary.ORDERTYPE);
      paramHttpServletRequest.setAttribute("TRADETYPE", CommonDictionary.TRADETYPE);
      paramHttpServletRequest.setAttribute("tradeList", localList2);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listTrade");
  }
  
  public ActionForward topCustomerFunds(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topCustomerFunds' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    getSelectAttribute(paramHttpServletRequest);
    List localList = localStatQueryManager.getFirmCategory();
    paramHttpServletRequest.setAttribute("lastDay", getThisDay());
    paramHttpServletRequest.setAttribute("firmCategoryList", localList);
    return paramActionMapping.findForward("topCustomerFunds1");
  }
  
  public ActionForward listCustomerFunds(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listCustomerFunds' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    Limit localLimit = RequestUtils.getLimit(paramHttpServletRequest);
    Sort localSort = localLimit.getSort();
    Map localMap = localSort.getSortValueMap();
    int i = 0;
    int j = RequestUtils.getTotalRowsFromRequest(paramHttpServletRequest);
    try
    {
      if (j < 0) {
        j = localStatQueryManager.getCustomerFundssCount(localQueryConditions);
      }
      localLimit.setRowAttributes(j, 20);
      int k = localLimit.getRowStart() + i + 1;
      int m = localLimit.getRowEnd() + i == 0 ? 20 : localLimit.getRowEnd() + i;
      String str = ECSideUtils.getDefaultSortSQL(localMap);
      String[] arrayOfString = str.split(" ");
      int n = 0;
      if ((arrayOfString.length == 5) && ("FirmID".equalsIgnoreCase(arrayOfString[3])))
      {
        arrayOfString[3] = "a.FirmID";
        n = 1;
      }
      if (n != 0)
      {
        str = " ";
        for (int i1 = 0; i1 < arrayOfString.length; i1++) {
          str = str + arrayOfString[i1] + " ";
        }
      }
      localQueryConditions.addCondition("orderby", "=", str);
      List localList1 = localStatQueryManager.getCustomerFundss(localQueryConditions, k, m);
      List localList2 = localStatQueryManager.getFirmCategory();
      paramHttpServletRequest.setAttribute("firmCategoryList", localList2);
      paramHttpServletRequest.setAttribute("customerFundsList", localList1);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listCustomerFunds");
  }
  
  public ActionForward editCustomerFund(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editCustomerFund' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    String str1 = "";
    if (paramHttpServletRequest.getParameter("CustomerID") != null) {
      str1 = paramHttpServletRequest.getParameter("CustomerID");
    }
    String str2 = "";
    if (paramHttpServletRequest.getParameter("date") != null) {
      str2 = paramHttpServletRequest.getParameter("date");
    }
    try
    {
      List localList = localStatQueryManager.getCustomerFund(localQueryConditions, str1, str2);
      paramHttpServletRequest.setAttribute("customerFund", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("editCustomerFund");
  }
  
  public ActionForward topFundTransfer(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topFundTransfer' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    paramHttpServletRequest.setAttribute("lastDay", getThisDay());
    return paramActionMapping.findForward("topFundTransfer");
  }
  
  public ActionForward listFundTransfer(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listFundTransfer' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localStatQueryManager.getFundTransfer(localQueryConditions);
      paramHttpServletRequest.setAttribute("fundTransferList", localList);
      paramHttpServletRequest.setAttribute("TRANSFER_STATUS", CommonDictionary.TRANSFER_STATUS);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listFundTransfer");
  }
  
  public ActionForward fundTransferStatus(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'FundTransferStatus' method");
    }
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("itemlist");
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
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
          localStatQueryManager.updateFundTransferStatusById(str1);
          addSysLog(paramHttpServletRequest, "设置出金申请[" + str1 + "]");
          i++;
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str2 = str2 + str1 + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + str1 + "]设置出金申请失败！");
        }
        catch (RuntimeException localRuntimeException)
        {
          str2 = str2 + str1 + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + str1 + "]设置出金申请失败！");
        }
      }
      if (!str2.equals(""))
      {
        str2 = str2.substring(0, str2.length() - 1);
        str2 = str2 + "设置出金申请失败！";
      }
      str2 = str2 + "成功设置" + i + "条纪录！";
      paramHttpServletRequest.setAttribute("prompt", str2);
    }
    return listFundTransfer(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward topDailyMoney(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topDailyMoney' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    paramHttpServletRequest.setAttribute("lastDay", getThisDay());
    return paramActionMapping.findForward("topDailyMoney");
  }
  
  public ActionForward listDailyMoney(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listDailyMoney' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localStatQueryManager.getDailyMoneys(localQueryConditions);
      paramHttpServletRequest.setAttribute("OPERATION", CommonDictionary.OPERATION);
      paramHttpServletRequest.setAttribute("dailyMoneyList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listDailyMoney");
  }
  
  public ActionForward topQuotation(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topQuotation' method");
    }
    paramHttpServletRequest.setAttribute("lastDay", getThisDay());
    return paramActionMapping.findForward("topQuotation");
  }
  
  public ActionForward listQuotation(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listQuotation' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    String str1 = paramHttpServletRequest.getParameter("isQryHisHidd");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    Limit localLimit = RequestUtils.getLimit(paramHttpServletRequest);
    Sort localSort = localLimit.getSort();
    Map localMap = localSort.getSortValueMap();
    int i = 0;
    int j = RequestUtils.getTotalRowsFromRequest(paramHttpServletRequest);
    try
    {
      if (j < 0) {
        j = localStatQueryManager.getQuotationCount(localQueryConditions);
      }
      localLimit.setRowAttributes(j, 20);
      int k = localLimit.getRowStart() + i + 1;
      int m = localLimit.getRowEnd() + i == 0 ? 20 : localLimit.getRowEnd() + i;
      String str2 = ECSideUtils.getDefaultSortSQL(localMap);
      localQueryConditions.addCondition("orderby", "=", str2);
      localQueryConditions.addCondition("isQryHisHidd", "=", str1);
      List localList = localStatQueryManager.getQuotations(localQueryConditions, k, m);
      paramHttpServletRequest.setAttribute("quotationList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listQuotation");
  }
  
  public ActionForward topBrokerTrade(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topBrokerTrade' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    paramHttpServletRequest.setAttribute("lastDay", getThisDay());
    return paramActionMapping.findForward("topBrokerTrade");
  }
  
  public ActionForward listBrokerTrade(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listBrokerTrade' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localStatQueryManager.getBrokerTrades(localQueryConditions);
      paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
      paramHttpServletRequest.setAttribute("ORDERTYPE", CommonDictionary.ORDERTYPE);
      paramHttpServletRequest.setAttribute("TRADETYPE", CommonDictionary.TRADETYPE);
      paramHttpServletRequest.setAttribute("listTrade", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listBrokerTrade");
  }
  
  public ActionForward topBrokerFund(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topBrokerFund' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    paramHttpServletRequest.setAttribute("lastDay", getThisDay());
    return paramActionMapping.findForward("topBrokerFund");
  }
  
  public ActionForward listBrokerFund(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listBrokerFund' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localStatQueryManager.getBrokerFunds(localQueryConditions);
      paramHttpServletRequest.setAttribute("listFund", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listBrokerFund");
  }
  
  public ActionForward topfirmHoldPosition(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topHoldPosition' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    getSelectAttribute(paramHttpServletRequest);
    List localList = localStatQueryManager.getFirmCategory();
    getSelectAttribute(paramHttpServletRequest);
    paramHttpServletRequest.setAttribute("lastDay", getThisDay());
    paramHttpServletRequest.setAttribute("firmCategoryList", localList);
    return paramActionMapping.findForward("topfirmHoldPosition");
  }
  
  public ActionForward topBrokerHold(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topBrokerHold' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    paramHttpServletRequest.setAttribute("lastDay", getThisDay());
    return paramActionMapping.findForward("topBrokerHold");
  }
  
  public ActionForward listBrokerHold(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listBrokerHold' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localStatQueryManager.getBrokerHoldPositions(localQueryConditions);
      paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
      paramHttpServletRequest.setAttribute("holdPositionList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listBrokerHold");
  }
  
  public ActionForward listfirmHoldPosition(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listfirmHoldPosition' method");
    }
    QueryConditions localQueryConditions1 = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    QueryConditions localQueryConditions2 = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    Limit localLimit = RequestUtils.getLimit(paramHttpServletRequest);
    Sort localSort = localLimit.getSort();
    Map localMap = localSort.getSortValueMap();
    int i = 0;
    int j = RequestUtils.getTotalRowsFromRequest(paramHttpServletRequest);
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    try
    {
      if (j < 0) {
        j = localStatQueryManager.getFirmHoldPositionsCount(localQueryConditions1);
      }
      localLimit.setRowAttributes(j, 20);
      int k = localLimit.getRowStart() + i + 1;
      int m = localLimit.getRowEnd() + i == 0 ? 20 : localLimit.getRowEnd() + i;
      String str = ECSideUtils.getDefaultSortSQL(localMap);
      String[] arrayOfString = str.split(" ");
      int n = 0;
      if ((arrayOfString.length >= 4) && ("FirmID".equals(arrayOfString[3])))
      {
        arrayOfString[3] = "a.FirmID";
        n = 1;
      }
      if (n != 0)
      {
        str = " ";
        for (int i1 = 0; i1 < arrayOfString.length; i1++) {
          str = str + arrayOfString[i1] + " ";
        }
      }
      localQueryConditions1.addCondition("orderby", "=", str);
      List localList1 = localStatQueryManager.getFirmHoldPositions(localQueryConditions1, k, m);
      List localList2 = localStatQueryManager.getFirmHoldPositionsSum(localQueryConditions2);
      if ((localList2 != null) && (localList2.size() > 0))
      {
        paramHttpServletRequest.setAttribute("sumHoldQtyGageQty", ((Map)localList2.get(0)).get("sumHoldQtyGageQty") + "");
        paramHttpServletRequest.setAttribute("sumGageQty", ((Map)localList2.get(0)).get("sumGageQty") + "");
        paramHttpServletRequest.setAttribute("sumHoldMargin", ((Map)localList2.get(0)).get("sumHoldMargin") + "");
        paramHttpServletRequest.setAttribute("sumFloatingLoss", ((Map)localList2.get(0)).get("sumFloatingLoss") + "");
      }
      List localList3 = localStatQueryManager.getFirmCategory();
      paramHttpServletRequest.setAttribute("firmCategoryList", localList3);
      paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
      paramHttpServletRequest.setAttribute("holdPositionList", localList1);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listfirmHoldPosition");
  }
  
  public ActionForward conferClose(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'conferClose' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    return paramActionMapping.findForward("form");
  }
  
  public ActionForward saveConferClose(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveConferClose' method");
    }
    String str1 = paramHttpServletRequest.getParameter("commodityID");
    String str2 = paramHttpServletRequest.getParameter("price");
    String str3 = paramHttpServletRequest.getParameter("bCustomerID");
    String str4 = paramHttpServletRequest.getParameter("sCustomerID");
    String str5 = paramHttpServletRequest.getParameter("sHoldQty");
    Settle localSettle = new Settle();
    localSettle.setCommodityID(str1);
    localSettle.setBCustomerID(str3);
    localSettle.setSCustomerID(str4);
    if ((str2 != null) && (!"".equals(str2))) {
      localSettle.setPrice(Double.valueOf(Double.parseDouble(str2)));
    }
    localSettle.setBGageQty(Long.valueOf(Long.parseLong("0")));
    if ((str5 != null) && (!"".equals(str5)))
    {
      localSettle.setSHoldQty(Long.valueOf(Long.parseLong(str5)));
      localSettle.setBHoldQty(Long.valueOf(Long.parseLong(str5)));
    }
    localSettle.setSGageQty(Long.valueOf(Long.parseLong("0")));
    StatQueryManager localStatQueryManager1 = (StatQueryManager)getBean("statQueryManager");
    String str6 = localStatQueryManager1.getConferClose(localSettle);
    String str7 = localStatQueryManager1.getConferClose2(localSettle);
    String str8 = localStatQueryManager1.getConferClose3(localSettle);
    try
    {
      if ("0".equals(str6)) {
        paramHttpServletRequest.setAttribute("prompt", "商品代码不存在！");
      }
      if ("2".equals(str7)) {
        paramHttpServletRequest.setAttribute("prompt", "买客户代码不存在！");
      }
      if ("4".equals(str8)) {
        paramHttpServletRequest.setAttribute("prompt", "卖客户代码不存在！");
      }
      if (("1".equals(str6)) && ("3".equals(str7)) && ("5".equals(str8)))
      {
        AgencyRMIBean localAgencyRMIBean = new AgencyRMIBean(paramHttpServletRequest);
        int i = localAgencyRMIBean.conferClose(localSettle);
        if (i == 1)
        {
          paramHttpServletRequest.setAttribute("prompt", "成功！");
          if ((AclCtrl.getLogonID(paramHttpServletRequest) != null) && (!"".equals(AclCtrl.getLogonID(paramHttpServletRequest))))
          {
            StatQuery localStatQuery = new StatQuery();
            localStatQuery.setUserID(AclCtrl.getLogonID(paramHttpServletRequest));
            localStatQuery.setLogType(Short.valueOf(Short.parseShort("1")));
            localStatQuery.setCommodityID(str1);
            localStatQuery.setPrice(Double.valueOf(Double.parseDouble(str2)));
            localStatQuery.setBCustomerID(str3);
            localStatQuery.setBHoldQty(Long.valueOf(Long.parseLong(str5)));
            localStatQuery.setBGageQty(Long.valueOf(Long.parseLong("0")));
            localStatQuery.setSCustomerID(str4);
            localStatQuery.setSHoldQty(Long.valueOf(Long.parseLong(str5)));
            localStatQuery.setSGageQty(Long.valueOf(Long.parseLong("0")));
            StatQueryManager localStatQueryManager2 = (StatQueryManager)getBean("statQueryManager");
            localStatQueryManager2.insertSettle(localStatQuery);
          }
        }
        else if (i == -1)
        {
          paramHttpServletRequest.setAttribute("prompt", "可转让买订货数量不足！");
        }
        else if (i == -2)
        {
          paramHttpServletRequest.setAttribute("prompt", "可转让买抵顶数量不足！");
        }
        else if (i == -3)
        {
          paramHttpServletRequest.setAttribute("prompt", "买方订货量不足！");
        }
        else if (i == -4)
        {
          paramHttpServletRequest.setAttribute("prompt", "转让买抵顶数量大于可买抵顶数量！");
        }
        else if (i == -11)
        {
          paramHttpServletRequest.setAttribute("prompt", "可转让卖订货数量不足！");
        }
        else if (i == -12)
        {
          paramHttpServletRequest.setAttribute("prompt", "可转让卖抵顶数量不足！");
        }
        else if (i == -13)
        {
          paramHttpServletRequest.setAttribute("prompt", "卖方订货量不足！");
        }
        else if (i == -14)
        {
          paramHttpServletRequest.setAttribute("prompt", "卖方抵顶数量不足！");
        }
        else if (i == -15)
        {
          paramHttpServletRequest.setAttribute("prompt", "未查询到对应的持仓数量！");
        }
        else if (i == -100)
        {
          paramHttpServletRequest.setAttribute("prompt", "其它错误！");
        }
        else if (i == -21)
        {
          paramHttpServletRequest.setAttribute("prompt", "买客户代码不存在！");
        }
        else if (i == -31)
        {
          paramHttpServletRequest.setAttribute("prompt", "卖客户代码不存在！");
        }
        else if (i == -41)
        {
          paramHttpServletRequest.setAttribute("prompt", "买卖转让数量不相等！");
        }
        else if (i == -51)
        {
          paramHttpServletRequest.setAttribute("prompt", "协议交收中不支持买抵顶！");
        }
        else if (i == -52)
        {
          paramHttpServletRequest.setAttribute("prompt", "协议交收中不支持卖抵顶！");
        }
        else if (i == -202)
        {
          paramHttpServletRequest.setAttribute("prompt", "系统不是闭市状态，不能协议交收！");
        }
        else if (i == -204)
        {
          paramHttpServletRequest.setAttribute("prompt", "下单服务器已关闭！");
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("saveConferClose");
  }
  
  public ActionForward queryConferClose(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'queryConferClose' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    try
    {
      List localList = localStatQueryManager.getConferCloseLog();
      paramHttpServletRequest.setAttribute("queryConferCloseList", localList);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramActionMapping.findForward("queryConferClose");
  }
  
  public ActionForward aheadSettle(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'aheadSettle' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    try
    {
      List localList = localStatQueryManager.getMarketCode();
      String str = null;
      if ((localList != null) && (localList.size() > 0))
      {
        localObject = (Map)localList.get(0);
        str = (String)((Map)localObject).get("marketCode");
      }
      Object localObject = new StatQueryForm();
      ((StatQueryForm)localObject).setMarketCode(str);
      updateFormBean(paramActionMapping, paramHttpServletRequest, (ActionForm)localObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramActionMapping.findForward("aheadSettleForm");
  }
  
  public ActionForward saveAheadSettle(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveAheadSettle' method");
    }
    String str1 = paramHttpServletRequest.getParameter("commodityID");
    String str2 = paramHttpServletRequest.getParameter("price");
    String str3 = paramHttpServletRequest.getParameter("bCustomerID");
    String str4 = paramHttpServletRequest.getParameter("sCustomerID");
    String str5 = paramHttpServletRequest.getParameter("sHoldQty");
    String str6 = paramHttpServletRequest.getParameter("sGageQty");
    Settle localSettle = new Settle();
    String str7 = "";
    if ((str1 != null) && (!"".equals(str1))) {
      localSettle.setCommodityID(str1);
    }
    localSettle.setBCustomerID(str3);
    localSettle.setSCustomerID(str4);
    if ((str2 != null) && (!"".equals(str2))) {
      localSettle.setPrice(Double.valueOf(Double.parseDouble(str2)));
    }
    localSettle.setBGageQty(Long.valueOf(Long.parseLong("0")));
    if ((str5 != null) && (!"".equals(str5)))
    {
      localSettle.setSHoldQty(Long.valueOf(Long.parseLong(str5)));
      localSettle.setBHoldQty(Long.valueOf(Long.parseLong(str5)));
    }
    if ((str6 != null) && (!"".equals(str6))) {
      localSettle.setSGageQty(Long.valueOf(Long.parseLong(str6)));
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    return paramActionMapping.findForward("saveAheadSettle");
  }
  
  public ActionForward queryAheadSettle(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'queryAheadSettle' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    try
    {
      List localList = localStatQueryManager.getAheadSettleLog();
      paramHttpServletRequest.setAttribute("queryAheadSettleList", localList);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramActionMapping.findForward("queryAheadSettle");
  }
  
  public ActionForward listPair(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listPair' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    String str1 = paramHttpServletRequest.getParameter("year");
    String str2 = paramHttpServletRequest.getParameter("month");
    String str3 = paramHttpServletRequest.getParameter("day");
    String str4 = paramHttpServletRequest.getParameter("crud");
    StatQuery localStatQuery = new StatQuery();
    localStatQuery.setYear(str1);
    localStatQuery.setMonth(str2);
    localStatQuery.setDay(str3);
    localStatQuery.setCommodityID(str4);
    try
    {
      List localList1 = localStatQueryManager.getSettlePairsB(localStatQuery);
      List localList2 = localStatQueryManager.getSettlePairS(localStatQuery);
      paramHttpServletRequest.setAttribute("settlePairsBList", localList1);
      paramHttpServletRequest.setAttribute("settlePairsSList", localList2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramActionMapping.findForward("listPair");
  }
  
  public ActionForward editCustomerFundsTable(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editCustomerFundsTable' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    String str = paramHttpServletRequest.getParameter("firmID");
    StatQuery localStatQuery = new StatQuery();
    localStatQuery.setFirmID(str);
    try
    {
      List localList = localStatQueryManager.getCustomerFundsTable(localStatQuery);
      paramHttpServletRequest.setAttribute("CustomerFundsTable", localList);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramActionMapping.findForward("editCustomerFundsTable");
  }
  
  public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return null;
  }
  
  public ActionForward listPledge(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listPledge' method");
    }
    String str1 = paramHttpServletRequest.getParameter("billID");
    String str2 = paramHttpServletRequest.getParameter("commodityName");
    StatQuery localStatQuery = new StatQuery();
    localStatQuery.setBillID(str1);
    localStatQuery.setCommodityName(str2);
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    try
    {
      List localList = localStatQueryManager.getPledgeList(localStatQuery);
      paramHttpServletRequest.setAttribute("pledgeList", localList);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listPledge");
  }
  
  public ActionForward editPledge(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editPledge' method");
    }
    StatQueryForm localStatQueryForm = (StatQueryForm)paramActionForm;
    String str1 = paramHttpServletRequest.getParameter("crud");
    String str2 = paramHttpServletRequest.getParameter("id");
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    StatQuery localStatQuery = null;
    try
    {
      if ("create".equals(str1)) {
        localStatQuery = new StatQuery();
      } else if ("update".equals(str1)) {
        localStatQuery = localStatQueryManager.getPledgeById(str2);
      }
      BeanUtils.copyProperties(localStatQueryForm, localStatQuery);
      localStatQueryForm.setCrud(str1);
      updateFormBean(paramActionMapping, paramHttpServletRequest, paramActionForm);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("editPledge");
  }
  
  public ActionForward savePledge(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'savePledge' method");
    }
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
    StatQueryForm localStatQueryForm = (StatQueryForm)paramActionForm;
    StatQuery localStatQuery = new StatQuery();
    BeanUtils.copyProperties(localStatQuery, localStatQueryForm);
    String str1 = paramHttpServletRequest.getParameter("crud");
    String str2 = AclCtrl.getLogonID(paramHttpServletRequest);
    localStatQuery.setCreator(str2);
    localStatQuery.setModifier(str2);
    try
    {
      if ("create".equals(str1))
      {
        localStatQueryManager.insertPledge(localStatQuery);
        addSysLog(paramHttpServletRequest, "添加质押资金记录！");
      }
      else if ("update".equals(str1))
      {
        localStatQueryManager.updatePledge(localStatQuery);
        addSysLog(paramHttpServletRequest, "修改质押资金记录！");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("savePledge");
  }
  
  public ActionForward deletePledge(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deletePledge' method");
    }
    StatQueryForm localStatQueryForm = (StatQueryForm)paramActionForm;
    StatQuery localStatQuery = new StatQuery();
    BeanUtils.copyProperties(localStatQuery, localStatQueryForm);
    StatQueryManager localStatQueryManager = (StatQueryManager)getBean("statQueryManager");
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
          localStatQueryManager.deletePledgeById(str1);
          addSysLog(paramHttpServletRequest, "删除质押资金[" + str1 + "]");
          i++;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          paramHttpServletRequest.setAttribute("prompt", "[" + localException.getMessage() + "]删除失败！");
        }
      }
      str2 = str2 + "成功删除" + i + "条纪录！";
      paramHttpServletRequest.setAttribute("prompt", str2);
    }
    return listPledge(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  private void getSelectAttribute(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
  }
}
