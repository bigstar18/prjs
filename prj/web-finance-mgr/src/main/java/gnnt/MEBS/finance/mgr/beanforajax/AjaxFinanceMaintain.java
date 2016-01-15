package gnnt.MEBS.finance.mgr.beanforajax;

import com.opensymphony.xwork2.ActionContext;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.finance.mgr.model.Account;
import gnnt.MEBS.finance.mgr.model.SummaryF;
import gnnt.MEBS.finance.mgr.model.VoucherModel;
import gnnt.MEBS.finance.mgr.service.AccountService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxFinanceMaintain")
@Scope("request")
public class AjaxFinanceMaintain extends AjaxCheckDemo
{

  @Autowired
  @Qualifier("accountService")
  private AccountService accountService;

  public String formCheckVoucherModelByCode()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("entity.code").trim();
    String str2 = localHttpServletRequest.getParameter("entity.summaryNo").trim();
    String str3 = localHttpServletRequest.getParameter("entity.debitCode").trim();
    if ((str3.indexOf("-") > 0) || (str3.indexOf("*") > 0))
      str3 = str3.substring(0, str3.length() - 1);
    String str4 = localHttpServletRequest.getParameter("entity.creditCode").trim();
    if ((str4.indexOf("-") > 0) || (str4.indexOf("*") > 0))
      str4 = str4.substring(0, str4.length() - 1);
    boolean bool1 = existVoucherModelCode(str1);
    boolean bool2 = existSummaryNo(str2);
    boolean bool3 = existAccountCode(str3);
    boolean bool4 = existAccountCode(str4);
    this.jsonValidateReturn = new JSONArray();
    int i = 0;
    if (bool1)
    {
      this.jsonValidateReturn.add(getJSONArray(new Object[] { "code", Boolean.valueOf(false), "模板代码已存在" }));
      i++;
    }
    if (!bool2)
    {
      this.jsonValidateReturn.add(getJSONArray(new Object[] { "summaryNo", Boolean.valueOf(false), "摘要号不存在" }));
      i++;
    }
    if (!bool3)
    {
      this.jsonValidateReturn.add(getJSONArray(new Object[] { "debitCode", Boolean.valueOf(false), "借方科目代码不存在" }));
      i++;
    }
    if (!bool4)
    {
      this.jsonValidateReturn.add(getJSONArray(new Object[] { "creditCode", Boolean.valueOf(false), "贷方科目代码不存在" }));
      i++;
    }
    if (i == 0)
      this.jsonValidateReturn = getJSONArray(new Object[] { "true" });
    return this.SUCCESS;
  }

  private boolean existVoucherModelCode(String paramString)
  {
    boolean bool = false;
    if ((paramString == null) || (paramString.trim().length() <= 0))
      return bool;
    PageRequest localPageRequest = new PageRequest(" and primary.code='" + paramString + "'");
    Page localPage = getService().getPage(localPageRequest, new VoucherModel());
    if ((localPage.getResult() != null) && (localPage.getResult().size() > 0))
      bool = true;
    return bool;
  }

  public String formCheckSummaryByNo()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("entity.summaryNo").trim();
    String str2 = localHttpServletRequest.getParameter("entity.accountCodeOpp").trim();
    if ((str2.indexOf("#") > 0) || (str2.indexOf("*") > 0))
      str2 = str2.substring(0, str2.length() - 1);
    boolean bool1 = true;
    if ((str2 != "") && (str2 != null) && (!str2.equals("spec")))
      bool1 = existAccountCode(str2);
    boolean bool2 = existSummaryNo(str1);
    this.jsonValidateReturn = new JSONArray();
    int i = 0;
    if (bool2)
    {
      this.jsonValidateReturn.add(getJSONArray(new Object[] { "summaryNo", Boolean.valueOf(false), "摘要号已存在" }));
      i++;
    }
    if (!bool1)
    {
      this.jsonValidateReturn.add(getJSONArray(new Object[] { "accountCodeOpp", Boolean.valueOf(false), "对方科目代码不存在" }));
      i++;
    }
    if (i == 0)
      this.jsonValidateReturn = getJSONArray(new Object[] { "true" });
    return this.SUCCESS;
  }

  private boolean existSummaryNo(String paramString)
  {
    boolean bool = false;
    if ((paramString == null) || (paramString.trim().length() <= 0))
      return bool;
    PageRequest localPageRequest = new PageRequest(" and primary.summaryNo='" + paramString + "'");
    Page localPage = getService().getPage(localPageRequest, new SummaryF());
    if ((localPage.getResult() != null) && (localPage.getResult().size() > 0))
      bool = true;
    return bool;
  }

  public String formCheckAccountByCode()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str = localHttpServletRequest.getParameter("entity.code").trim();
    boolean bool = existAccountCode(str);
    if (bool)
      this.jsonValidateReturn = getJSONArrayList(new JSONArray[] { getJSONArray(new Object[] { "code", Boolean.valueOf(false), "科目代码已存在" }) });
    else
      this.jsonValidateReturn = getJSONArray(new Object[] { "true" });
    return this.SUCCESS;
  }

  private boolean existAccountCode(String paramString)
  {
    boolean bool = false;
    if ((paramString == null) || (paramString.trim().length() <= 0))
      return bool;
    PageRequest localPageRequest = new PageRequest(" and primary.code='" + paramString + "'");
    Page localPage = getService().getPage(localPageRequest, new Account());
    if ((localPage.getResult() != null) && (localPage.getResult().size() > 0))
      bool = true;
    return bool;
  }

  public String getStatusJson()
  {
    List localList = getService().getListBySql("select status from f_systemstatus");
    int i = 0;
    String str = "";
    if (((localList != null ? 1 : 0) & (localList.size() > 0 ? 1 : 0)) != 0)
      i = Integer.parseInt(((Map)localList.get(0)).get("STATUS").toString());
    if (i == 0)
      str = "结算状态：未执行";
    if (i == 1)
      str = "结算状态：执行中";
    if (i == 2)
      str = "结算状态：结算完成";
    this.jsonValidateReturn = new JSONArray();
    this.jsonValidateReturn.add(str);
    return this.SUCCESS;
  }

  public String getSummaryByNo()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str = localHttpServletRequest.getParameter("summaryNo");
    SummaryF localSummaryF = new SummaryF();
    localSummaryF.setSummaryNo(str);
    localSummaryF = (SummaryF)getService().get(localSummaryF);
    if (localSummaryF != null)
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add(localSummaryF.getSummary());
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("-1");
    }
    return this.SUCCESS;
  }

  public String getAccountByCode()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str = localHttpServletRequest.getParameter("accountCode");
    Account localAccount = new Account();
    localAccount.setCode(str);
    localAccount = (Account)getService().get(localAccount);
    if (localAccount != null)
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add(localAccount.getName());
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("-1");
    }
    return this.SUCCESS;
  }

  public String getAccountName()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str = localHttpServletRequest.getParameter("accountCode");
    Account localAccount = this.accountService.getLeafAccountByCode(str);
    if (localAccount != null)
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add(localAccount.getName());
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("-1");
    }
    return this.SUCCESS;
  }

  public String getFirm()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("firmId");
    List localList = getService().getListBySql("select count(*) from m_firm where firmId='" + str1 + "'");
    String str2 = ((Map)localList.get(0)).get("COUNT(*)").toString();
    if (str2.equals("0"))
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("-1");
    }
    return this.SUCCESS;
  }

  public AccountService getAccountService()
  {
    return this.accountService;
  }
}