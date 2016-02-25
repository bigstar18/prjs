package gnnt.MEBS.finance.web;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.finance.service.AccountService;
import gnnt.MEBS.finance.unit.Account;
import gnnt.MEBS.finance.util.SysData;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

public class AccountController
  extends MultiActionController
{
  private final transient Log logger = LogFactory.getLog(AccountController.class);
  
  public ModelAndView accountAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'accountAdd' method...");
    AccountService localAccountService = (AccountService)SysData.getBean("f_accountService");
    String str1 = paramHttpServletRequest.getParameter("code");
    String str2 = paramHttpServletRequest.getParameter("name");
    String str3 = paramHttpServletRequest.getParameter("DCFlag");
    String str4 = paramHttpServletRequest.getParameter("accountLevel");
    Account localAccount = new Account();
    localAccount.setCode(str1);
    localAccount.setName(str2);
    localAccount.setDCFlag(str3);
    localAccount.setAccountLevel(new Integer(str4));
    int i = localAccountService.getAccount(str1);
    ModelAndView localModelAndView = null;
    if (i == 0)
    {
      localAccountService.createAccount(localAccount);
      localModelAndView = new ModelAndView("finance/public/done", "resultMsg", "创建科目成功！");
    }
    else
    {
      localModelAndView = new ModelAndView("finance/public/done", "resultMsg", "创建科目成功！该科目已存在");
    }
    return localModelAndView;
  }
  
  public ModelAndView accountEditForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'accountEditForward' method...");
    AccountService localAccountService = (AccountService)SysData.getBean("f_accountService");
    String str = paramHttpServletRequest.getParameter("code");
    Account localAccount = localAccountService.getAccountByCode(str);
    return new ModelAndView("finance/account/editAccount", "account", localAccount);
  }
  
  public ModelAndView accountMod(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'accountMod' method...");
    AccountService localAccountService = (AccountService)SysData.getBean("f_accountService");
    String str1 = paramHttpServletRequest.getParameter("code");
    String str2 = paramHttpServletRequest.getParameter("name");
    String str3 = paramHttpServletRequest.getParameter("DCFlag");
    String str4 = paramHttpServletRequest.getParameter("accountLevel");
    Account localAccount = new Account();
    localAccount.setCode(str1);
    localAccount.setName(str2);
    localAccount.setDCFlag(str3);
    localAccount.setAccountLevel(new Integer(str4));
    localAccountService.updateAccount(localAccount);
    return new ModelAndView("finance/public/done", "resultMsg", "修改科目成功！");
  }
  
  public ModelAndView accountDelete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'accountDelete' method...");
    AccountService localAccountService = (AccountService)SysData.getBean("f_accountService");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
    String str1 = "";
    String str2 = AclCtrl.getLogonID(paramHttpServletRequest);
    for (int i = 0; i < arrayOfString.length; i++)
    {
      int j = 0;
      try
      {
        j = localAccountService.deleteAccount(arrayOfString[i], str2);
      }
      catch (RuntimeException localRuntimeException)
      {
        localRuntimeException.printStackTrace();
      }
      if (j < 1) {
        str1 = str1 + arrayOfString[i] + ",";
      }
    }
    if (!"".equals(str1)) {
      str1 = str1.substring(0, str1.length() - 1) + "有相关联凭证不能删除";
    } else {
      str1 = "删除科目成功！";
    }
    return new ModelAndView("finance/public/done", "resultMsg", str1);
  }
  
  public ModelAndView generateFirmAccounts(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'generateFirmAccounts' method...");
    AccountService localAccountService = (AccountService)SysData.getBean("f_accountService");
    localAccountService.generateFirmAccounts();
    return new ModelAndView("finance/public/done", "resultMsg", "创建交易商科目完成！");
  }
  
  public ModelAndView accountList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'accountList' method...");
    AccountService localAccountService = (AccountService)SysData.getBean("f_accountService");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, 15, "code", false);
    }
    List localList = localAccountService.getAccounts(localQueryConditions, localPageInfo);
    Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    ModelAndView localModelAndView = new ModelAndView("finance/account/listAccount", "resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView getAccountName(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'getAccountName' method...");
    paramHttpServletResponse.setHeader("Cache-Control", "no-cache");
    paramHttpServletResponse.setHeader("Cache-Control", "no-store");
    paramHttpServletResponse.setHeader("Pragma", "no-cache");
    paramHttpServletResponse.setContentType("text/xml");
    paramHttpServletResponse.setCharacterEncoding("GBK");
    AccountService localAccountService = (AccountService)SysData.getBean("f_accountService");
    String str1 = null;
    String str2 = paramHttpServletRequest.getParameter("code");
    if (str2 != null)
    {
      localObject = localAccountService.getLeafAccountByCode(str2);
      str1 = ((Account)localObject).getName();
    }
    Object localObject = paramHttpServletResponse.getWriter();
    ((PrintWriter)localObject).flush();
    ((PrintWriter)localObject).print(str1);
    ((PrintWriter)localObject).close();
    return null;
  }
}
