package gnnt.MEBS.finance.mgr.action;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.finance.mgr.model.Account;
import gnnt.MEBS.finance.mgr.service.AccountService;
import gnnt.MEBS.logonService.util.Tool;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("financialQueryAction")
@Scope("request")
public class FinancialQueryAction extends EcsideAction
{
  private static final long serialVersionUID = 2689289631806833605L;

  @Autowired
  @Qualifier("accountService")
  private AccountService accountService;
  private Account account;

  public String ledger()
    throws Exception
  {
    this.logger.debug("enter ledger");
    String str1 = this.request.getParameter("accountCode");
    String str2 = this.request.getParameter("beginDate");
    String str3 = this.request.getParameter("endDate");
    PageRequest localPageRequest = super.getPageRequest(this.request);
    String str4 = null;
    String str5 = null;
    if ((str1 != null) && (str2 != null) && (str3 != null))
    {
      Account localAccount = new Account();
      localAccount.setCode(str1);
      this.account = ((Account)getService().get(localAccount));
      if (this.account != null)
      {
        str4 = this.account.getDcFlag();
        str5 = "D".equals(str4) ? "借" : "贷";
      }
      List localList = this.accountService.queryLedger(str1, str2, str3);
      Map localMap = this.accountService.queryDailyBalance(str1, str2);
      BigDecimal localBigDecimal1 = (BigDecimal)localMap.get("lastDayBalance");
      BigDecimal localBigDecimal2 = (BigDecimal)localMap.get("debitAmount");
      BigDecimal localBigDecimal3 = (BigDecimal)localMap.get("creditAmount");
      BigDecimal localBigDecimal4 = (BigDecimal)localMap.get("balance");
      if ((localList != null) && (localList.size() > 0))
        localPageRequest.setPageSize(localList.size());
      Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList.size(), localList);
      this.request.setAttribute("pageInfo", localPage);
      this.request.setAttribute("accountCode", str1);
      this.request.setAttribute("beginDate", str2);
      this.request.setAttribute("endDate", str3);
      this.request.setAttribute("dCFlag", str4);
      this.request.setAttribute("dCFlagName", str5);
      this.request.setAttribute("lastDayBalance", localBigDecimal1);
      this.request.setAttribute("debitAmount", localBigDecimal2);
      this.request.setAttribute("creditAmount", localBigDecimal3);
      this.request.setAttribute("balance", localBigDecimal4);
      this.request.setAttribute("dataSize", Integer.valueOf(localList.size()));
    }
    return "success";
  }

  public String ledgerSum()
    throws Exception
  {
    this.logger.debug("enter ledgerSum");
    String str1 = this.request.getParameter("accountCode");
    String str2 = this.request.getParameter("beginDate");
    String str3 = this.request.getParameter("endDate");
    PageRequest localPageRequest = super.getPageRequest(this.request);
    if ((str1 != null) && (str2 != null) && (str3 != null))
    {
      List localList = this.accountService.queryLedgerSum(str1, str2, str3);
      Map localMap = this.accountService.queryAccountBalance(str1, str2, str3);
      if ((localList != null) && (localList.size() > 0))
        localPageRequest.setPageSize(localList.size());
      Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList.size(), localList);
      this.request.setAttribute("pageInfo", localPage);
      this.request.setAttribute("accountCode", str1);
      this.request.setAttribute("beginDate", str2);
      this.request.setAttribute("endDate", str3);
      this.request.setAttribute("balance", localMap);
      this.request.setAttribute("dataSize", Integer.valueOf(localList.size()));
    }
    return "success";
  }

  public String ledgerQuery()
    throws Exception
  {
    this.logger.debug("enter ledgerQuery");
    PageRequest localPageRequest = super.getPageRequest(this.request);
    QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
    if (localQueryConditions.getConditionValue("primary.bdate", ">=") == null)
      localQueryConditions.addCondition("primary.bdate", ">=", Tool.strToDate(Tools.fmtDate(new Date())));
    if (localQueryConditions.getConditionValue("primary.bdate", "<=") == null)
      localQueryConditions.addCondition("primary.bdate", "<=", Tool.strToDate(Tools.fmtDate(new Date())));
    listByLimit(localPageRequest);
    return "success";
  }

  public String balanceQuery()
    throws Exception
  {
    this.logger.debug("enter balanceQuery");
    PageRequest localPageRequest = super.getPageRequest(this.request);
    QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
    if (localQueryConditions.getConditionValue("primary.bdate", ">=") == null)
      localQueryConditions.addCondition("primary.bdate", ">=", Tool.strToDate(Tools.fmtDate(new Date())));
    if (localQueryConditions.getConditionValue("primary.bdate", "<=") == null)
      localQueryConditions.addCondition("primary.bdate", "<=", Tool.strToDate(Tools.fmtDate(new Date())));
    listByLimit(localPageRequest);
    return "success";
  }

  public String balanceDayReport()
  {
    this.logger.debug("enter balanceDayReport");
    return "success";
  }

  public String listDebitBalanceDayReport()
    throws Exception
  {
    this.logger.debug("enter DebitBalanceDayReport");
    PageRequest localPageRequest = super.getPageRequest(this.request);
    QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
    String str = this.request.getParameter("bdate");
    if (str != null)
      localQueryConditions.addCondition("bdate", "=", Tools.strToDate(str));
    localQueryConditions.addCondition("account.accountLevel", "=", "1");
    localQueryConditions.addCondition("account.dcFlag", "=", "D");
    listByLimit(localPageRequest);
    return "success";
  }

  public String listCreditBalanceDayReport()
    throws Exception
  {
    this.logger.debug("enter CreditBalanceDayReport");
    PageRequest localPageRequest = super.getPageRequest(this.request);
    QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
    String str = this.request.getParameter("bdate");
    if (str != null)
      localQueryConditions.addCondition("bdate", "=", Tools.strToDate(str));
    localQueryConditions.addCondition("account.accountLevel", "=", "1");
    localQueryConditions.addCondition("account.dcFlag", "=", "C");
    listByLimit(localPageRequest);
    return "success";
  }

  public AccountService getAccountService()
  {
    return this.accountService;
  }

  public Account getAccount()
  {
    return this.account;
  }

  public void setAccount(Account paramAccount)
  {
    this.account = paramAccount;
  }
}