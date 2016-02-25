package gnnt.MEBS.finance.web;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.finance.base.web.Utility;
import gnnt.MEBS.finance.service.ViewService;
import gnnt.MEBS.finance.service.VoucherService;
import gnnt.MEBS.finance.unit.Channel;
import gnnt.MEBS.finance.unit.Summary;
import gnnt.MEBS.finance.unit.Voucher;
import gnnt.MEBS.finance.unit.VoucherEntry;
import gnnt.MEBS.finance.util.SysData;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.RequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

public class VoucherController
  extends MultiActionController
{
  private final transient Log logger = LogFactory.getLog(VoucherController.class);
  
  public ModelAndView summaryCreatForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'summaryCreatForward' method...");
    ViewService localViewService = (ViewService)SysData.getBean("f_viewService");
    List localList = localViewService.getLedgerFieldList();
    ModelAndView localModelAndView = new ModelAndView("finance/voucher/createSummary");
    localModelAndView.addObject("fieldList", localList);
    return localModelAndView;
  }
  
  public ModelAndView summaryAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'summaryAdd' method...");
    String str1 = paramHttpServletRequest.getParameter("summaryNo");
    String str2 = paramHttpServletRequest.getParameter("summary");
    String str3 = paramHttpServletRequest.getParameter("voucherType");
    String str4 = paramHttpServletRequest.getParameter("ledgerItem");
    String str5 = paramHttpServletRequest.getParameter("fundDCFlag");
    String str6 = paramHttpServletRequest.getParameter("appendAccount");
    String str7 = paramHttpServletRequest.getParameter("accountCodeOpp");
    Summary localSummary = new Summary();
    localSummary.setSummaryNo(str1);
    localSummary.setSummary(str2);
    localSummary.setLedgerItem(str4);
    localSummary.setFundDCFlag(str5);
    localSummary.setAppendAccount(str6);
    localSummary.setAccountCodeOpp(str7);
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    int i = localVoucherService.getSummary(str1);
    ModelAndView localModelAndView = null;
    if (i == 0)
    {
      localVoucherService.createSummary(localSummary);
      localModelAndView = new ModelAndView("finance/public/done", "resultMsg", "创建摘要成功！");
    }
    else
    {
      localModelAndView = new ModelAndView("finance/public/done", "resultMsg", "创建摘要失败！该摘要已存在");
    }
    return localModelAndView;
  }
  
  public ModelAndView summaryEditForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'summaryEditForward' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    ViewService localViewService = (ViewService)SysData.getBean("f_viewService");
    String str = paramHttpServletRequest.getParameter("summaryNo");
    Summary localSummary = localVoucherService.getSummaryByNo(str);
    List localList = localViewService.getLedgerFieldList();
    ModelAndView localModelAndView = new ModelAndView("finance/voucher/editSummary");
    localModelAndView.addObject("summary", localSummary);
    localModelAndView.addObject("fieldList", localList);
    return localModelAndView;
  }
  
  public ModelAndView summaryMod(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'summaryMod' method...");
    String str1 = paramHttpServletRequest.getParameter("summaryNo");
    String str2 = paramHttpServletRequest.getParameter("summary");
    String str3 = paramHttpServletRequest.getParameter("voucherType");
    String str4 = paramHttpServletRequest.getParameter("ledgerItem");
    String str5 = paramHttpServletRequest.getParameter("fundDCFlag");
    String str6 = paramHttpServletRequest.getParameter("appendAccount");
    Summary localSummary = new Summary();
    localSummary.setSummaryNo(str1);
    localSummary.setSummary(str2);
    localSummary.setLedgerItem(str4);
    localSummary.setFundDCFlag(str5);
    localSummary.setAppendAccount(str6);
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    localVoucherService.updateSummary(localSummary);
    return new ModelAndView("finance/public/done", "resultMsg", "更新摘要成功！");
  }
  
  public ModelAndView summaryDelete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'summaryDelete' method...");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
    String str1 = "";
    String str2 = AclCtrl.getLogonID(paramHttpServletRequest);
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    for (int i = 0; i < arrayOfString.length; i++)
    {
      int j = j = localVoucherService.deleteSummary(arrayOfString[i], str2);
      if (j < 1) {
        str1 = str1 + arrayOfString[i] + ",";
      }
    }
    if (!"".equals(str1)) {
      str1 = str1.substring(0, str1.length() - 1) + "有相关联凭证不能删除";
    } else {
      str1 = "删除摘要成功！";
    }
    return new ModelAndView("finance/public/done", "resultMsg", str1);
  }
  
  public ModelAndView summaryList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'summaryList' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    ViewService localViewService = (ViewService)SysData.getBean("f_viewService");
    List localList1 = localViewService.getTrademoduleList();
    List localList2 = localViewService.getLedgerFieldList();
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, 15, "summaryNo", false);
    }
    List localList3 = localVoucherService.getSummarys(localQueryConditions, localPageInfo);
    String str = paramHttpServletRequest.getParameter("targetView");
    if (str == null) {
      str = "voucher/listSummary";
    }
    Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    ModelAndView localModelAndView = new ModelAndView("finance/" + str, "resultList", localList3);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    localModelAndView.addObject("fieldList", localList2);
    localModelAndView.addObject("moduleList", localList1);
    return localModelAndView;
  }
  
  public ModelAndView voucherAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'voucherAdd' method...");
    Voucher localVoucher = new Voucher();
    localVoucher.setInputTime(new Date());
    localVoucher.setInputUser(AclCtrl.getLogonID(paramHttpServletRequest));
    localVoucher.setStatus("editing");
    fillVoucher(paramHttpServletRequest, localVoucher);
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    localVoucherService.createVoucher(localVoucher);
    return new ModelAndView("finance/public/done", "resultMsg", "创建凭证成功！凭证号：" + localVoucher.getVoucherNo());
  }
  
  public ModelAndView voucherMod(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'voucherMod' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    Long localLong = RequestUtils.getLongParameter(paramHttpServletRequest, "voucherNo");
    Voucher localVoucher = localVoucherService.getVoucherByNo(localLong);
    fillVoucher(paramHttpServletRequest, localVoucher);
    localVoucherService.updateVoucher(localVoucher);
    return new ModelAndView("finance/public/done", "resultMsg", "更新凭证成功！");
  }
  
  private void fillVoucher(HttpServletRequest paramHttpServletRequest, Voucher paramVoucher)
  {
    ParamUtil.bindData(paramHttpServletRequest, paramVoucher);
    Long[] arrayOfLong = Utility.getLongParameters(paramHttpServletRequest, "entryId");
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("entrySummary");
    String[] arrayOfString2 = RequestUtils.getStringParameters(paramHttpServletRequest, "accountCode");
    String[] arrayOfString3 = RequestUtils.getStringParameters(paramHttpServletRequest, "accountName");
    String[] arrayOfString4 = RequestUtils.getStringParameters(paramHttpServletRequest, "debitAmount");
    String[] arrayOfString5 = RequestUtils.getStringParameters(paramHttpServletRequest, "creditAmount");
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < arrayOfString2.length; i++)
    {
      VoucherEntry localVoucherEntry = new VoucherEntry();
      localVoucherEntry.setEntryId(arrayOfLong[i]);
      localVoucherEntry.setEntrySummary(arrayOfString1[i]);
      localVoucherEntry.setAccountCode(arrayOfString2[i]);
      localVoucherEntry.setDebitAmount(new BigDecimal(arrayOfString4[i]));
      localVoucherEntry.setCreditAmount(new BigDecimal(arrayOfString5[i]));
      localArrayList.add(localVoucherEntry);
    }
    paramVoucher.setVoucherEntrys(localArrayList);
  }
  
  public ModelAndView voucherDelete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'deleteVoucher' method...");
    long[] arrayOfLong = RequestUtils.getLongParameters(paramHttpServletRequest, "delCheck");
    String str = AclCtrl.getLogonID(paramHttpServletRequest);
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    for (int i = 0; i < arrayOfLong.length; i++) {
      localVoucherService.deleteVoucher(new Long(arrayOfLong[i]), str);
    }
    return new ModelAndView("finance/public/done", "resultMsg", "删除凭证成功！");
  }
  
  public ModelAndView submitAuditVoucher(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'submitAuditVoucher' method...");
    String str = paramHttpServletRequest.getParameter("all");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    if ((str != null) && ("true".equals(str)))
    {
      localVoucherService.submitAllVoucherForAudit();
    }
    else
    {
      long[] arrayOfLong = RequestUtils.getLongParameters(paramHttpServletRequest, "delCheck");
      for (int i = 0; i < arrayOfLong.length; i++) {
        localVoucherService.submitVoucherForAudit(new Long(arrayOfLong[i]));
      }
    }
    return new ModelAndView("finance/public/done", "resultMsg", "凭证已提交审核！");
  }
  
  public ModelAndView auditVoucher(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'auditVoucher' method...");
    Long localLong = RequestUtils.getLongParameter(paramHttpServletRequest, "voucherNo");
    Boolean localBoolean = RequestUtils.getBooleanParameter(paramHttpServletRequest, "isPass");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    Voucher localVoucher = localVoucherService.getVoucherByNo(localLong);
    localVoucher.setAuditor(AclCtrl.getLogonID(paramHttpServletRequest));
    localVoucherService.updateVoucherNotEntrys(localVoucher);
    int i = localVoucherService.auditVoucher(localLong, localBoolean.booleanValue());
    String str = "";
    if (i == -1) {
      str = "凭证审核失败！请确认摘要与科目是否正确。";
    } else if (i == -2) {
      str = "凭证审核失败！造成201余额为负值。";
    } else {
      str = "凭证审核成功！";
    }
    return new ModelAndView("finance/public/done", "resultMsg", str);
  }
  
  public ModelAndView putVoucherToAccountBook(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'putVoucherToAccountBook' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    String str = null;
    if (localVoucherService.voucherToBalance(null)) {
      str = "凭证入账成功！";
    } else {
      str = "还有编辑中或待审核的凭证，凭证入账簿未成功！";
    }
    return new ModelAndView("finance/public/done", "resultMsg", str);
  }
  
  public ModelAndView voucherList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'voucherList' method...");
    String str1 = AclCtrl.getLogonID(paramHttpServletRequest);
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    String str2 = paramHttpServletRequest.getParameter("sign");
    if (str2 != null)
    {
      if (localQueryConditions == null) {
        localQueryConditions = new QueryConditions();
      }
      if ("edit".equals(str2)) {
        localQueryConditions.addCondition("status", "=", "editing");
      }
      if ("confirm".equals(str2)) {
        localQueryConditions.addCondition("status", "=", "editing");
      }
      if ("audit".equals(str2)) {
        localQueryConditions.addCondition("status", "=", "auditing");
      }
    }
    String str3 = paramHttpServletRequest.getParameter("accountCode");
    String str4 = null;
    if ((str3 != null) && (!"".equals(str3.trim()))) {
      str4 = " and VoucherNo in (select VoucherNo from F_VoucherEntry where AccountCode like '" + str3 + "')";
    }
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, 15, "voucherNo", false);
    }
    List localList = localVoucherService.getVouchers(localQueryConditions, localPageInfo, str4);
    Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    ModelAndView localModelAndView = new ModelAndView("finance/voucher/listVoucher", "resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    localModelAndView.addObject("sign", str2);
    localModelAndView.addObject("accountCode", str3);
    localModelAndView.addObject("logonUser", str1);
    return localModelAndView;
  }
  
  public ModelAndView voucherViewForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'voucherViewForward' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    String str1 = paramHttpServletRequest.getParameter("voucherNo");
    Voucher localVoucher = localVoucherService.getVoucherByNo(new Long(str1));
    String str2 = paramHttpServletRequest.getParameter("sign");
    if ((str2 == null) || (str2.length() == 0)) {
      str2 = "view";
    }
    ModelAndView localModelAndView = new ModelAndView("finance/voucher/viewVoucher", "voucher", localVoucher);
    localModelAndView.addObject("sign", str2);
    return localModelAndView;
  }
  
  public ModelAndView voucherEditForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'voucherEditForward' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    String str1 = paramHttpServletRequest.getParameter("voucherNo");
    Voucher localVoucher = localVoucherService.getVoucherByNo(new Long(str1));
    String str2 = paramHttpServletRequest.getParameter("sign");
    if ((str2 == null) || (str2.length() == 0)) {
      str2 = "view";
    }
    ModelAndView localModelAndView = new ModelAndView("finance/voucher/editVoucher", "voucher", localVoucher);
    localModelAndView.addObject("sign", str2);
    return localModelAndView;
  }
  
  public ModelAndView voucherFastAddDirect(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'voucherFastAddDirect' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    List localList = localVoucherService.getVoucherModel();
    return new ModelAndView("finance/voucher/createVoucherFast", "list", localList);
  }
  
  public ModelAndView voucherBaseList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'voucherBaseList' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    ViewService localViewService = (ViewService)SysData.getBean("f_viewService");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, 15, "voucherNo", false);
    }
    List localList = localViewService.getVoucherBase(localQueryConditions, localPageInfo);
    Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    ModelAndView localModelAndView = new ModelAndView("finance/voucher/listVoucherBase", "resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView accountVoucherDirect(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'accountVoucherDirect' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    Date localDate = localVoucherService.getMaxBalanceDate();
    String str = paramHttpServletRequest.getParameter("sign");
    ModelAndView localModelAndView = new ModelAndView("finance/voucher/accountVoucher", "maxDate", localDate);
    localModelAndView.addObject("sign", str);
    return localModelAndView;
  }
  
  public ModelAndView fundFlowIntoVoucher(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'fundFlowIntoVoucher' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    String str = null;
    int i = localVoucherService.fundFlowIntoVoucher();
    if (i >= 0) {
      str = "生成电脑凭证成功！";
    } else {
      str = "生成电脑凭证失败";
    }
    this.logger.debug("result:" + i);
    ModelAndView localModelAndView = new ModelAndView("finance/public/doneIntoVoucher", "resultMsg", str);
    localModelAndView.addObject("result", new Integer(i));
    return localModelAndView;
  }
  
  public ModelAndView voucherCheckDate(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'voucherCheckDate' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    Object localObject = null;
    String str1 = paramHttpServletRequest.getParameter("noCheck");
    List localList1 = null;
    if ((str1 == null) || (!"Y".equals(str1))) {
      localList1 = localVoucherService.voucherCheckDateList();
    }
    String str2 = "";
    String str3 = null;
    String str4 = null;
    List localList2 = null;
    List localList3 = null;
    int i = 2;
    if (localList1 != null)
    {
      str3 = localVoucherService.voucherCheckMinDate();
      str4 = localVoucherService.voucherCheckMaxDate();
      i = localVoucherService.voucherCheckMaxDateIsToday();
      this.logger.debug("sign:" + i);
      this.logger.debug("maxDate:" + str4);
      localList2 = localVoucherService.voucherCheckList();
      this.logger.debug(Integer.valueOf(localList2.size()));
      localList3 = localVoucherService.voucherCheckNoList();
      str2 = "/voucher/setVoucherBDate";
    }
    else
    {
      str2 = "/voucher/balance";
    }
    ModelAndView localModelAndView = new ModelAndView("finance/" + str2, "resultList", localList1);
    localModelAndView.addObject("minDate", str3);
    localModelAndView.addObject("maxDate", str4);
    localModelAndView.addObject("sign", i + "");
    localModelAndView.addObject("listValue", localList2);
    localModelAndView.addObject("listNoValue", localList3);
    return localModelAndView;
  }
  
  public ModelAndView setVoucherBDate(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'setVoucherBDate' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    String str1 = paramHttpServletRequest.getParameter("minDate");
    String str2 = paramHttpServletRequest.getParameter("b_date");
    String str3 = paramHttpServletRequest.getParameter("maxDate");
    String str4 = paramHttpServletRequest.getParameter("sign");
    String str5 = null;
    String str6 = null;
    String str7 = AclCtrl.getLogonID(paramHttpServletRequest);
    this.logger.debug("sign:" + str4);
    this.logger.debug("b_date:" + str2);
    if ("true".equals(str4))
    {
      str5 = str3;
      this.logger.debug("endDate:" + str5);
    }
    else
    {
      str5 = str2;
    }
    int i = localVoucherService.setVoucherBDate(str1, str5, str7);
    if (i >= 0) {
      str6 = "设置结算日期成功！";
    } else {
      str6 = "设置结算日期失败";
    }
    ModelAndView localModelAndView = new ModelAndView("finance/public/doneIntoVoucher", "resultMsg", str6);
    localModelAndView.addObject("result", new Integer(i));
    localModelAndView.addObject("result1", new Integer(i));
    return localModelAndView;
  }
  
  public ModelAndView balanceVoucher(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'balanceVoucher' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    String str1 = null;
    String str2 = AclCtrl.getLogonID(paramHttpServletRequest);
    int i = localVoucherService.balanceVoucher(null, str2);
    if (i == 1) {
      str1 = "结算成功！";
    } else if (i == 0) {
      str1 = "操作异常";
    } else if (i == -1) {
      str1 = "订单结算不成功";
    } else if (i == -2) {
      str1 = "存在小于等于订单当前结算日的流水，未设结算日";
    } else if (i == -3) {
      str1 = "警告：系统已经在结算中，不能多人同时结算";
    }
    ModelAndView localModelAndView = new ModelAndView("finance/public/done", "resultMsg", str1);
    return localModelAndView;
  }
  
  public ModelAndView fastVoucher(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'fastVoucher' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    ModelAndView localModelAndView = null;
    try
    {
      String str1 = null;
      String str2 = paramHttpServletRequest.getParameter("summaryNo");
      String str3 = paramHttpServletRequest.getParameter("summary");
      String str4 = paramHttpServletRequest.getParameter("debitCode");
      String str5 = paramHttpServletRequest.getParameter("creditCode");
      String str6 = paramHttpServletRequest.getParameter("contractNo");
      String str7 = paramHttpServletRequest.getParameter("money");
      String str8 = AclCtrl.getLogonID(paramHttpServletRequest);
      int i = localVoucherService.createVoucherFast(str2, str3, str4, str5, str6, str8, str7);
      if (i > 0) {
        str1 = "快捷创建凭证成功！";
      } else {
        str1 = "快捷创建凭证失败！";
      }
      localModelAndView = new ModelAndView("finance/public/doneFast", "resultMsg", str1);
      localModelAndView.addObject("result", new Integer(i));
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localModelAndView;
  }
  
  public ModelAndView channelList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'channelList' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, 15, "code", false);
    }
    List localList = localVoucherService.getChannels(localQueryConditions, localPageInfo);
    Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    ModelAndView localModelAndView = new ModelAndView("finance/voucher/listChannel", "resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView channelAddForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'channelAddForward' method...");
    ModelAndView localModelAndView = new ModelAndView("finance/voucher/createChannel");
    return localModelAndView;
  }
  
  public ModelAndView channelAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'createChannel' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    Channel localChannel = new Channel();
    ParamUtil.bindData(paramHttpServletRequest, localChannel);
    localVoucherService.createChannel(localChannel);
    return new ModelAndView("finance/public/done", "resultMsg", "创建模板成功！");
  }
  
  public ModelAndView channelModForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'channelModForward' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    String str = paramHttpServletRequest.getParameter("code");
    Channel localChannel = localVoucherService.getChannelByCode(str);
    ModelAndView localModelAndView = new ModelAndView("finance/voucher/editChannel");
    localModelAndView.addObject("channel", localChannel);
    return localModelAndView;
  }
  
  public ModelAndView channelMod(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'updateChannel' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    Channel localChannel = new Channel();
    ParamUtil.bindData(paramHttpServletRequest, localChannel);
    localVoucherService.updateChannel(localChannel);
    return new ModelAndView("finance/public/done", "resultMsg", "修改通道成功！");
  }
  
  public ModelAndView channelDelete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'deleteChannel' method...");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    String[] arrayOfString = RequestUtils.getStringParameters(paramHttpServletRequest, "delCheck");
    for (int i = 0; i < arrayOfString.length; i++) {
      localVoucherService.deleteChannel(arrayOfString[i]);
    }
    return new ModelAndView("finance/public/done", "resultMsg", "删除模板成功！");
  }
  
  public ModelAndView getSummary(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'getSummary' method...");
    paramHttpServletResponse.setHeader("Cache-Control", "no-cache");
    paramHttpServletResponse.setHeader("Cache-Control", "no-store");
    paramHttpServletResponse.setHeader("Pragma", "no-cache");
    paramHttpServletResponse.setContentType("text/xml");
    paramHttpServletResponse.setCharacterEncoding("GBK");
    VoucherService localVoucherService = (VoucherService)SysData.getBean("f_voucherService");
    String str1 = paramHttpServletRequest.getParameter("summaryNo");
    String str2 = null;
    if (str1 != null)
    {
      localObject = localVoucherService.getSummaryByNo(str1);
      str2 = ((Summary)localObject).getSummary();
    }
    Object localObject = paramHttpServletResponse.getWriter();
    ((PrintWriter)localObject).flush();
    ((PrintWriter)localObject).print(str2);
    ((PrintWriter)localObject).close();
    return null;
  }
}
