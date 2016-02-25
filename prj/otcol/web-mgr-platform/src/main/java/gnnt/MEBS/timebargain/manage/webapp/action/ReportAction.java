package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.timebargain.manage.model.StatQuery;
import gnnt.MEBS.timebargain.manage.service.CustomerManager;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.service.ReportManager;
import gnnt.MEBS.timebargain.manage.util.DateUtil;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import gnnt.MEBS.timebargain.manage.webapp.util.QueryUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ReportAction
  extends BaseAction
{
  private static final String ALARM_REPORT_JASPER_FILE = "/report/alarm_report.jasper";
  private static final String HOLDDETAIL_REPORT_JASPER_FILE = "/report/holdDetail_report.jasper";
  private static final String HISHOLDDETAIL_REPORT_JASPER_FILE = "/report/hisHoldDetail_report.jasper";
  private static final String HISORDERS_REPORT_JASPER_FILE = "/report/hisOrders_report.jasper";
  private static final String ACCOUNT_REPORT_JASPER_FILE = "/report/account_report.jasper";
  private static final String ACCOUNT_REPORT_JASPER_FILE1 = "/report/account_report1.jasper";
  private static final String BREED_REPORT_JASPER_FILE = "/report/breed_report.jasper";
  private static final String FEEMONTH_REPORT_JASPER_FILE = "/report/feeMonth_report.jasper";
  private static final String FEEMONTH_REPORT_JASPER_FILE1 = "/report/feeMonth_report1.jasper";
  private static final String CMDTYHOLD_REPORT_JASPER_FILE = "/report/cmdtyHold_report.jasper";
  private static final String WEEK_REPORT_JASPER_FILE = "/report/week_report.jasper";
  private static final String WEEK_REPORT_SUBREPORT_HOLD_JASPER_FILE = "/report/week_report_subreport_hold.jasper";
  private static final String WEEK_REPORT_SUBREPORT_TRADE_JASPER_FILE = "/report/week_report_subreport_trade.jasper";
  private static final String TESTXML_REPORT_JASPER_FILE = "/report/testXml.jasper";
  
  public ActionForward editAccount(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editAccount' method");
    }
    try
    {
      paramHttpServletRequest.setAttribute("today", DateUtil.getCurDate());
    }
    catch (Exception localException)
    {
      this.log.error("===>editAccount err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("editAccount");
  }
  
  public ActionForward topAccount(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topAccount' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    paramHttpServletRequest.setAttribute("today", DateUtil.getCurDate());
    return paramActionMapping.findForward("topAccount");
  }
  
  public ActionForward listAccount(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listAccount' method");
    }
    ReportManager localReportManager = (ReportManager)getBean("reportManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localReportManager.getAccountList(localQueryConditions);
      paramHttpServletRequest.setAttribute("accountList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listAccount");
  }
  
  public ActionForward printAccount1(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'printAccount1' method");
    }
    ReportManager localReportManager = (ReportManager)getBean("reportManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    String str = "";
    if (paramHttpServletRequest.getParameter("clearDate") != null) {
      str = paramHttpServletRequest.getParameter("clearDate");
    }
    paramHttpServletRequest.setAttribute("clearDate", str);
    try
    {
      Map localMap = localReportManager.getAccountUpdate(localQueryConditions, str);
      paramHttpServletRequest.setAttribute("parameter", localMap);
    }
    catch (Exception localException)
    {
      this.log.error("输出总账户统计报表出错：" + localException.getMessage());
      rendText(paramHttpServletResponse, "输出总账户统计报表出错,原因：" + localException.getMessage());
    }
    return paramActionMapping.findForward("printAccount1");
  }
  
  public ActionForward editWeek(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editWeek' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    try
    {
      getSelectAttribute(paramHttpServletRequest);
      paramHttpServletRequest.setAttribute("maxCustomerID", localCustomerManager.getMaxCustomerID(null));
      paramHttpServletRequest.setAttribute("minCustomerID", localCustomerManager.getMinCustomerID(null));
      paramHttpServletRequest.setAttribute("today", DateUtil.getCurDate());
    }
    catch (Exception localException)
    {
      this.log.error("===>editWeek err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("editWeek");
  }
  
  public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return null;
  }
  
  public ActionForward topHoldDetail(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topHoldDetail' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    paramHttpServletRequest.setAttribute("today", DateUtil.getCurDate());
    return paramActionMapping.findForward("topHoldDetail");
  }
  
  public ActionForward listHoldDetail(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listHoldDetail' method");
    }
    ReportManager localReportManager = (ReportManager)getBean("reportManager");
    try
    {
      String str1 = paramHttpServletRequest.getParameter("firmID");
      String str2 = paramHttpServletRequest.getParameter("clearDate");
      StatQuery localStatQuery = new StatQuery();
      localStatQuery.setFirmID(str1);
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      localStatQuery.setBeginDate(localSimpleDateFormat.parse(str2));
      List localList = localReportManager.getHisHoldDetail(localStatQuery);
      paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
      paramHttpServletRequest.setAttribute("holdDetailList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listHoldDetail");
  }
  
  public ActionForward printHoldDetailOther(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'printHoldDetailOther' method");
    }
    ReportManager localReportManager = (ReportManager)getBean("reportManager");
    String str1 = "";
    String str2 = paramHttpServletRequest.getParameter("ids");
    if ((str2 != null) && (!"".equals(str2))) {
      str1 = str2.substring(0, str2.length() - 1);
    }
    if (paramHttpServletRequest.getParameter("id") != null) {
      str1 = "'" + paramHttpServletRequest.getParameter("id") + "'";
    }
    String str3 = paramHttpServletRequest.getParameter("firmID");
    String str4 = paramHttpServletRequest.getParameter("clearDate");
    StatQuery localStatQuery = new StatQuery();
    localStatQuery.setFirmID(str3);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    if ((str4 != null) && (!"".equals(str4))) {
      localStatQuery.setBeginDate(localSimpleDateFormat.parse(str4));
    }
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("dat1", str4);
      List localList = localReportManager.getHoldDetailOther1(localStatQuery, str1);
      paramHttpServletRequest.setAttribute("lst", localList);
      paramHttpServletRequest.setAttribute("parameter", localHashMap);
      paramHttpServletRequest.setAttribute("stat", localStatQuery);
    }
    catch (Exception localException)
    {
      this.log.error("输出订货明细报表出错：" + localException.getMessage());
      rendText(paramHttpServletResponse, "输出订货明细报表出错,原因：" + localException.getMessage());
    }
    return paramActionMapping.findForward("printHoldDetailOther");
  }
  
  public ActionForward topAlarm(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topAlarm' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    paramHttpServletRequest.setAttribute("today", DateUtil.getCurDate());
    return paramActionMapping.findForward("topAlarm");
  }
  
  public ActionForward listAlarm(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listAlarm' method");
    }
    ReportManager localReportManager = (ReportManager)getBean("reportManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localReportManager.getAlarm(localQueryConditions);
      paramHttpServletRequest.setAttribute("alarmList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listAlarm");
  }
  
  public ActionForward topHisOrders(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topHisOrders' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    paramHttpServletRequest.setAttribute("today", DateUtil.getCurDate());
    return paramActionMapping.findForward("topHisOrders");
  }
  
  public ActionForward listHisOrders(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listHisOrders' method");
    }
    ReportManager localReportManager = (ReportManager)getBean("reportManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localReportManager.getHisOrders(localQueryConditions);
      paramHttpServletRequest.setAttribute("BS_FLAG1", CommonDictionary.BS_FLAG1);
      paramHttpServletRequest.setAttribute("ORDERTYPE", CommonDictionary.ORDERTYPE);
      paramHttpServletRequest.setAttribute("hisOrdersList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listHisOrders");
  }
  
  public ActionForward printHisOrdersOther(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'printHisOrders' method");
    }
    ReportManager localReportManager = (ReportManager)getBean("reportManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localReportManager.getHisOrders(localQueryConditions);
      HashMap localHashMap = new HashMap();
      localHashMap.put("dat1", localQueryConditions.getConditionValue("a.ClearDate"));
      paramHttpServletRequest.setAttribute("parameter", localHashMap);
      paramHttpServletRequest.setAttribute("lst", localList);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("输出历史委托报表出错：" + localException.getMessage());
      rendText(paramHttpServletResponse, "输出历史委托报表出错,原因：" + localException.getMessage());
    }
    return paramActionMapping.findForward("printHisOrders");
  }
  
  public ActionForward topBreed(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topBreed' method");
    }
    paramHttpServletRequest.setAttribute("today", DateUtil.getCurDate());
    return paramActionMapping.findForward("topBreed");
  }
  
  public ActionForward listBreed(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listBreed' method");
    }
    ReportManager localReportManager = (ReportManager)getBean("reportManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localReportManager.getBreed(localQueryConditions);
      paramHttpServletRequest.setAttribute("breedList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listBreed");
  }
  
  public ActionForward printBreed(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'printBreed' method");
    }
    ReportManager localReportManager = (ReportManager)getBean("reportManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localReportManager.getBreed(localQueryConditions);
      HashMap localHashMap = new HashMap();
      localHashMap.put("ClearDate", localQueryConditions.getConditionValue("a.ClearDate"));
      paramHttpServletRequest.setAttribute("lst", localList);
      paramHttpServletRequest.setAttribute("parameter", localHashMap);
    }
    catch (Exception localException)
    {
      this.log.error("输出分品种汇总报表出错：" + localException.getMessage());
      rendText(paramHttpServletResponse, "输出分品种汇总报表出错,原因：" + localException.getMessage());
    }
    return paramActionMapping.findForward("printBreed");
  }
  
  public ActionForward topFeeMonth(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topFeeMonth' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    paramHttpServletRequest.setAttribute("month", DateUtil.formatDate(new Date(), "yyyyMM"));
    paramHttpServletRequest.setAttribute("lastDay", getLastDay());
    paramHttpServletRequest.setAttribute("today", DateUtil.getCurDate());
    return paramActionMapping.findForward("topFeeMonth");
  }
  
  public ActionForward listFeeMonth1(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listFeeMonth1' method");
    }
    ReportManager localReportManager = (ReportManager)getBean("reportManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localReportManager.getFeeMonth(localQueryConditions);
      paramHttpServletRequest.setAttribute("feeMonthList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listFeeMonth");
  }
  
  public ActionForward listFeeMonth2(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listFeeMonth2' method");
    }
    ReportManager localReportManager = (ReportManager)getBean("reportManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localReportManager.getFeeMonth1(localQueryConditions);
      paramHttpServletRequest.setAttribute("feeMonthList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listFeeMonth");
  }
  
  public ActionForward printFeeMonth1(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'printFeeMonth1' method");
    }
    ReportManager localReportManager = (ReportManager)getBean("reportManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      List localList = localReportManager.getFeeMonth1(localQueryConditions);
      HashMap localHashMap = new HashMap();
      localHashMap.put("queryStart", localQueryConditions.getConditionValue("a.ClearDate", ">="));
      localHashMap.put("queryEnd", localQueryConditions.getConditionValue("a.ClearDate", "<="));
      paramHttpServletRequest.setAttribute("parameter", localHashMap);
      paramHttpServletRequest.setAttribute("lst", localList);
    }
    catch (Exception localException)
    {
      this.log.error("输出月手续费统计报表出错：" + localException.getMessage());
      rendText(paramHttpServletResponse, "输出月手续费统计报表出错,原因：" + localException.getMessage());
    }
    return paramActionMapping.findForward("printFeeMonth1");
  }
  
  public ActionForward topCmdtyHold(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topCmdtyHold' method");
    }
    paramHttpServletRequest.setAttribute("today", DateUtil.getCurDate());
    return paramActionMapping.findForward("topCmdtyHold");
  }
  
  public ActionForward listCmdtyHold(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listCmdtyHold' method");
    }
    ReportManager localReportManager = (ReportManager)getBean("reportManager");
    String str1 = paramHttpServletRequest.getParameter("commodityID");
    String str2 = paramHttpServletRequest.getParameter("clearDate");
    StatQuery localStatQuery = new StatQuery();
    localStatQuery.setCommodityID(str1);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    if ((str2 != null) && (!"".equals(str2))) {
      localStatQuery.setBeginDate(localSimpleDateFormat.parse(str2));
    }
    try
    {
      List localList = localReportManager.getCmdtyHold(localStatQuery);
      paramHttpServletRequest.setAttribute("cmdtyHoldList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listCmdtyHold");
  }
  
  public ActionForward printCmdtyHold(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'printCmdtyHold' method");
    }
    ReportManager localReportManager = (ReportManager)getBean("reportManager");
    String str1 = paramHttpServletRequest.getParameter("commodityID");
    String str2 = paramHttpServletRequest.getParameter("clearDate");
    StatQuery localStatQuery = new StatQuery();
    localStatQuery.setCommodityID(str1);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    if ((str2 != null) && (!"".equals(str2))) {
      localStatQuery.setBeginDate(localSimpleDateFormat.parse(str2));
    }
    try
    {
      List localList = localReportManager.getCmdtyHold(localStatQuery);
      HashMap localHashMap = new HashMap();
      localHashMap.put("ClearDate", str2);
      paramHttpServletRequest.setAttribute("parameter", localHashMap);
      paramHttpServletRequest.setAttribute("lst", localList);
    }
    catch (Exception localException)
    {
      this.log.error("输出商品持仓数明细单报表出错：" + localException.getMessage());
      rendText(paramHttpServletResponse, "输出商品持仓数明细单报表出错,原因：" + localException.getMessage());
    }
    return paramActionMapping.findForward("printCmdtyHold");
  }
  
  public ActionForward topWeekDetail(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'topWeekDetail' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    paramHttpServletRequest.setAttribute("today", DateUtil.getCurDate());
    return paramActionMapping.findForward("topWeekDetail");
  }
  
  public ActionForward listWeek1Detail(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'listWeek1Detail' method");
    }
    ReportManager localReportManager = (ReportManager)getBean("reportManager");
    QueryConditions localQueryConditions = QueryUtil.getQueryConditionsFromRequest(paramHttpServletRequest);
    try
    {
      String str = paramHttpServletRequest.getParameter("type");
      List localList = localReportManager.getWeek1Detail(localQueryConditions, str);
      paramHttpServletRequest.setAttribute("week1DetailList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listWeek1Detail");
  }
  
  private void getSelectAttribute(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
  }
}
