package gnnt.MEBS.integrated.mgr.beanforajax;

import com.opensymphony.xwork2.ActionContext;
import gnnt.MEBS.common.mgr.beanforajax.BaseAjax;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.Role;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.model.CertificateType;
import gnnt.MEBS.integrated.mgr.model.FirmCategory;
import gnnt.MEBS.integrated.mgr.model.Industry;
import gnnt.MEBS.integrated.mgr.model.SystemProps;
import gnnt.MEBS.integrated.mgr.model.Zone;
import gnnt.MEBS.integrated.mgr.model.commodity.Breed;
import gnnt.MEBS.integrated.mgr.model.commodity.BreedProps;
import gnnt.MEBS.integrated.mgr.model.commodity.Category;
import gnnt.MEBS.integrated.mgr.model.commodity.CategoryProperty;
import gnnt.MEBS.integrated.mgr.model.commodity.PropertyType;
import gnnt.MEBS.integrated.mgr.model.usermanage.AgentTrader;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirm;
import gnnt.MEBS.integrated.mgr.model.usermanage.Trader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxCheck")
@Scope("request")
public class AjaxCheck
  extends BaseAjax
{
  private JSONArray genJSON(ArrayList<AjaxValidationFormResponse> paramArrayList)
  {
    JSONArray localJSONArray = new JSONArray();
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      AjaxValidationFormResponse localAjaxValidationFormResponse = (AjaxValidationFormResponse)paramArrayList.get(i);
      localJSONArray.add(localAjaxValidationFormResponse.toJSONArray());
    }
    return localJSONArray;
  }
  
  private boolean existFirmFirmId(String paramString)
  {
    boolean bool = false;
    if ((paramString == null) || (paramString.trim().length() <= 0)) {
      return bool;
    }
    PageRequest localPageRequest1 = new PageRequest(" and primary.firmId='" + paramString + "'");
    Page localPage1 = getService().getPage(localPageRequest1, new MFirm());
    if ((localPage1.getResult() != null) && (localPage1.getResult().size() > 0)) {
      bool = true;
    }
    PageRequest localPageRequest2 = new PageRequest(" and primary.traderId='" + paramString + "'");
    Page localPage2 = getService().getPage(localPageRequest2, new Trader());
    if ((localPage2.getResult() != null) && (localPage2.getResult().size() > 0))
    {
      bool = true;
      this.logger.debug("enter boolean");
    }
    return bool;
  }
  
  public String checkFirmByFirmId()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    AjaxValidationFieldResponse localAjaxValidationFieldResponse = new AjaxValidationFieldResponse(str1, Boolean.valueOf(!existFirmFirmId(str2)));
    this.jsonValidateReturn = localAjaxValidationFieldResponse.toJSONArray();
    return "success";
  }
  
  public void checkFirmByFirmIdForm(HttpServletRequest paramHttpServletRequest, ArrayList<AjaxValidationFormResponse> paramArrayList)
  {
    String str1 = paramHttpServletRequest.getParameter("entity.firmId");
    if (existFirmFirmId(str1)) {
      paramArrayList.add(new AjaxValidationFormResponse("id", Boolean.valueOf(false), "输入的交易商代码已存在"));
    }
    String str2 = "^[a-zA-Z0-9|_]+$";
    Pattern localPattern = Pattern.compile(str2);
    Matcher localMatcher = localPattern.matcher(str1);
    if (!localMatcher.find()) {
      paramArrayList.add(new AjaxValidationFormResponse("id", Boolean.valueOf(false), "交易商代码由字母数字下划线组成"));
    }
  }
  
  private boolean existFirmUserId(String paramString)
  {
    boolean bool = false;
    if ((paramString == null) || (paramString.trim().length() <= 0)) {
      return bool;
    }
    PageRequest localPageRequest = new PageRequest(" and primary.userId='" + paramString + "'");
    Page localPage = getService().getPage(localPageRequest, new Trader());
    if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
      bool = true;
    }
    return bool;
  }
  
  public String checkFirmByUserId()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    AjaxValidationFieldResponse localAjaxValidationFieldResponse = new AjaxValidationFieldResponse(str1, Boolean.valueOf(!existFirmUserId(str2)));
    this.jsonValidateReturn = localAjaxValidationFieldResponse.toJSONArray();
    return "success";
  }
  
  public void checkFirmUserIdForm(HttpServletRequest paramHttpServletRequest, ArrayList<AjaxValidationFormResponse> paramArrayList)
  {
    String str = paramHttpServletRequest.getParameter("username");
    if (existFirmUserId(str)) {
      paramArrayList.add(new AjaxValidationFormResponse("username", Boolean.valueOf(false), "输入的用户名已存在"));
    }
  }
  
  public boolean checkFirmIdLength(String paramString)
  {
    boolean bool = true;
    if (paramString != null)
    {
      int i = paramString.length();
      SystemProps localSystemProps = new SystemProps();
      localSystemProps.setPropsKey("Offset");
      localSystemProps = (SystemProps)getService().get(localSystemProps);
      String str = localSystemProps.getFirmIdLength();
      int j = Integer.parseInt(str.trim());
      if (i > j) {
        return false;
      }
    }
    return bool;
  }
  
  public void checkFirmIdLength(HttpServletRequest paramHttpServletRequest, ArrayList<AjaxValidationFormResponse> paramArrayList)
  {
    String str1 = paramHttpServletRequest.getParameter("entity.firmId");
    SystemProps localSystemProps = new SystemProps();
    localSystemProps.setPropsKey("Offset");
    localSystemProps = (SystemProps)getService().get(localSystemProps);
    String str2 = localSystemProps.getFirmIdLength();
    int i = Integer.parseInt(str2.trim());
    if (!checkFirmIdLength(str1)) {
      paramArrayList.add(new AjaxValidationFormResponse("id", Boolean.valueOf(false), "交易商代码长度应小于" + i));
    }
  }
  
  public String checkFirmForm()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    checkFirmByFirmIdForm(localHttpServletRequest, localArrayList);
    checkFirmUserIdForm(localHttpServletRequest, localArrayList);
    checkFirmIdLength(localHttpServletRequest, localArrayList);
    if (localArrayList.size() > 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  public String checkRegiesterUserId()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    String str = localHttpServletRequest.getParameter("userId");
    if (existFirmUserId(str)) {
      localArrayList.add(new AjaxValidationFormResponse(str, Boolean.valueOf(false), "输入的用户名已存在"));
    }
    if (localArrayList.size() > 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  private boolean existFirmBankAccount(String paramString1, String paramString2)
  {
    boolean bool = false;
    if (!"".equals(paramString1)) {
      if (paramString1.equals(paramString2))
      {
        bool = false;
      }
      else
      {
        PageRequest localPageRequest = new PageRequest(" and primary.bankAccount='" + Tools.strToLong(paramString1) + "'");
        Page localPage = getService().getPage(localPageRequest, new MFirm());
        if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
          bool = true;
        }
      }
    }
    return bool;
  }
  
  public String checkFirmByBankAccount()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    String str3 = localHttpServletRequest.getParameter("oldBankAccount");
    AjaxValidationFieldResponse localAjaxValidationFieldResponse = new AjaxValidationFieldResponse(str1, Boolean.valueOf(!existFirmBankAccount(str2, str3)));
    this.jsonValidateReturn = localAjaxValidationFieldResponse.toJSONArray();
    return "success";
  }
  
  public String checkBankAccountForm()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    String str1 = localHttpServletRequest.getParameter("entity.bankAccount");
    String str2 = localHttpServletRequest.getParameter("oldBankAccount");
    if (existFirmBankAccount(str1, str2)) {
      localArrayList.add(new AjaxValidationFormResponse("bankAccount", Boolean.valueOf(false), "输入的银行账户已存在"));
    }
    if (localArrayList.size() > 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  private boolean existUserByUserId(String paramString)
  {
    boolean bool = false;
    if (!"".equals(paramString))
    {
      PageRequest localPageRequest = new PageRequest(" and primary.userId='" + paramString + "'");
      Page localPage = getService().getPage(localPageRequest, new User());
      if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
        bool = true;
      }
    }
    return bool;
  }
  
  public String checkUserByUserId()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    AjaxValidationFieldResponse localAjaxValidationFieldResponse = new AjaxValidationFieldResponse(str1, Boolean.valueOf(!existUserByUserId(str2)));
    this.jsonValidateReturn = localAjaxValidationFieldResponse.toJSONArray();
    return "success";
  }
  
  public String checkUserForm()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    String str = localHttpServletRequest.getParameter("entity.userId");
    if (existUserByUserId(str)) {
      localArrayList.add(new AjaxValidationFormResponse("userId", Boolean.valueOf(false), "输入的用户代码已存在"));
    }
    if (localArrayList.size() != 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  private boolean exitRoleName(String paramString1, String paramString2)
  {
    boolean bool = false;
    if (!"".equals(paramString1))
    {
      if ((paramString2 != null) && (paramString2.trim().equals(paramString1))) {
        return false;
      }
      PageRequest localPageRequest = new PageRequest(" and primary.name= '" + paramString1 + "'");
      Page localPage = getService().getPage(localPageRequest, new Role());
      if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
        bool = true;
      }
    }
    return bool;
  }
  
  public String checkRoleByName()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    String str3 = localHttpServletRequest.getParameter("oldName");
    AjaxValidationFieldResponse localAjaxValidationFieldResponse = new AjaxValidationFieldResponse(str1, Boolean.valueOf(!exitRoleName(str2, str3)));
    this.jsonValidateReturn = localAjaxValidationFieldResponse.toJSONArray();
    return "success";
  }
  
  public String checkRoleForm()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    String str1 = localHttpServletRequest.getParameter("entity.name");
    String str2 = localHttpServletRequest.getParameter("oldName");
    if (exitRoleName(str1, str2)) {
      localArrayList.add(new AjaxValidationFormResponse("name", Boolean.valueOf(false), "输入的角色名称已存在"));
    }
    if (localArrayList.size() != 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  public boolean existMessageByUserId(String paramString1, String paramString2)
    throws Exception
  {
    if ((paramString2 == null) || (paramString2.trim().length() <= 0)) {
      return false;
    }
    if ((!"4".equals(paramString2)) && (!"5".equals(paramString2))) {
      return true;
    }
    if ((paramString1 == null) || (paramString1.trim().length() <= 0)) {
      return false;
    }
    boolean bool = true;
    PageRequest localPageRequest = null;
    Page localPage;
    if ("4".equals(paramString2))
    {
      localPageRequest = new PageRequest(" and primary.traderId='" + paramString1 + "'");
      localPage = getService().getPage(localPageRequest, new Trader());
      if ((localPage.getResult() == null) || (localPage.getResult().size() <= 0)) {
        bool = false;
      }
    }
    else
    {
      localPageRequest = new PageRequest(" and primary.userId='" + paramString1 + "'");
      localPage = getService().getPage(localPageRequest, new User());
      if ((localPage.getResult() == null) || (localPage.getResult().size() <= 0)) {
        bool = false;
      }
    }
    return bool;
  }
  
  public String checkMessageByUserId()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    String str3 = localHttpServletRequest.getParameter("recieverType");
    AjaxValidationFieldResponse localAjaxValidationFieldResponse = new AjaxValidationFieldResponse(str1, Boolean.valueOf(existMessageByUserId(str2, str3)));
    this.jsonValidateReturn = localAjaxValidationFieldResponse.toJSONArray();
    return "success";
  }
  
  public String checkMessageForm()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    String str1 = localHttpServletRequest.getParameter("entity.traderId");
    String str2 = localHttpServletRequest.getParameter("entity.recieverType");
    if ((!"4".equals(str2)) && (!"5".equals(str2))) {
      localArrayList.add(new AjaxValidationFormResponse("userId", Boolean.valueOf(true), ""));
    } else if (!existMessageByUserId(str1, str2)) {
      localArrayList.add(new AjaxValidationFormResponse("userId", Boolean.valueOf(false), "此接收人不存在"));
    }
    if (localArrayList.size() != 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  private MFirm getMFirmById(String paramString)
  {
    MFirm localMFirm = new MFirm();
    localMFirm.setFirmId(paramString);
    return (MFirm)getService().get(localMFirm);
  }
  
  public String checkFirmId()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    MFirm localMFirm = getMFirmById(str2);
    AjaxValidationFormResponse localAjaxValidationFormResponse = null;
    if (localMFirm == null) {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "交易商不存在");
    } else if ("D".equalsIgnoreCase(localMFirm.getStatus())) {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "交易商被禁用");
    } else if ("E".equalsIgnoreCase(localMFirm.getStatus())) {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "交易商已删除");
    } else if ("N".equalsIgnoreCase(localMFirm.getStatus())) {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(true), "交易商可用");
    } else {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "交易商状态不正确[" + localMFirm.getStatus() + "]");
    }
    this.jsonValidateReturn = localAjaxValidationFormResponse.toJSONArray();
    return "success";
  }
  
  private void checkFirmIdForm(HttpServletRequest paramHttpServletRequest, ArrayList<AjaxValidationFormResponse> paramArrayList)
    throws Exception
  {
    String str = paramHttpServletRequest.getParameter("entity.mfirm.firmId");
    MFirm localMFirm = getMFirmById(str);
    if (localMFirm == null) {
      paramArrayList.add(new AjaxValidationFormResponse("firmId", Boolean.valueOf(false), "交易商不存在"));
    } else if ("D".equalsIgnoreCase(localMFirm.getStatus())) {
      paramArrayList.add(new AjaxValidationFormResponse("firmId", Boolean.valueOf(false), "交易商被禁用"));
    } else if ("E".equalsIgnoreCase(localMFirm.getStatus())) {
      paramArrayList.add(new AjaxValidationFormResponse("firmId", Boolean.valueOf(false), "交易商已删除"));
    } else if (!"N".equalsIgnoreCase(localMFirm.getStatus())) {
      paramArrayList.add(new AjaxValidationFormResponse("firmId", Boolean.valueOf(false), "交易商状态不正确[" + localMFirm.getStatus() + "]"));
    }
  }
  
  private boolean existTraderUserId(String paramString)
    throws Exception
  {
    boolean bool = true;
    PageRequest localPageRequest = new PageRequest(" and primary.userId='" + paramString + "'");
    Page localPage = getService().getPage(localPageRequest, new Trader());
    if ((localPage.getResult() == null) || (localPage.getResult().size() <= 0)) {
      bool = false;
    }
    return bool;
  }
  
  public String checkNoTraderUserId()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    AjaxValidationFormResponse localAjaxValidationFormResponse = null;
    if (!existTraderUserId(str2)) {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(true), "本用户名可以使用");
    } else {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "本用户名已被其他人占用");
    }
    this.jsonValidateReturn = localAjaxValidationFormResponse.toJSONArray();
    return "success";
  }
  
  private void checkTraderUserIdForm(HttpServletRequest paramHttpServletRequest, ArrayList<AjaxValidationFormResponse> paramArrayList)
    throws Exception
  {
    String str = paramHttpServletRequest.getParameter("entity.userId");
    if (existTraderUserId(str)) {
      paramArrayList.add(new AjaxValidationFormResponse("userId", Boolean.valueOf(false), "本用户名已被其他交易员使用"));
    }
  }
  
  private boolean existTraderId(String paramString)
  {
    boolean bool = false;
    Trader localTrader = new Trader();
    localTrader.setTraderId(paramString);
    if (getService().get(localTrader) != null) {
      bool = true;
    }
    return bool;
  }
  
  public String checkNoTraderId()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    String str3 = localHttpServletRequest.getParameter("firmId");
    AjaxValidationFormResponse localAjaxValidationFormResponse = null;
    if ((str3 != null) && (str3.trim().length() > 0))
    {
      if (!existTraderId(str3 + str2)) {
        localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(true), "交易员编号可用");
      } else {
        localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "交易员编号已占用");
      }
    }
    else {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "请先输入交易商代码");
    }
    this.jsonValidateReturn = localAjaxValidationFormResponse.toJSONArray();
    return "success";
  }
  
  private void checkTraderIdForm(HttpServletRequest paramHttpServletRequest, ArrayList<AjaxValidationFormResponse> paramArrayList)
  {
    String str1 = paramHttpServletRequest.getParameter("entity.mfirm.firmId");
    String str2 = paramHttpServletRequest.getParameter("traderNumber");
    if ((str1 != null) && (str1.trim().length() > 0))
    {
      if (existTraderId(str1 + str2)) {
        paramArrayList.add(new AjaxValidationFormResponse("traderNumber", Boolean.valueOf(false), "交易员编号已占用"));
      }
    }
    else {
      paramArrayList.add(new AjaxValidationFormResponse("traderNumber", Boolean.valueOf(false), "请先输入交易商代码"));
    }
  }
  
  public String checkAddTraderForm()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    checkFirmIdForm(localHttpServletRequest, localArrayList);
    checkTraderUserIdForm(localHttpServletRequest, localArrayList);
    checkTraderIdForm(localHttpServletRequest, localArrayList);
    if (localArrayList.size() != 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  private boolean existAgentTrader(String paramString)
    throws Exception
  {
    boolean bool = true;
    PageRequest localPageRequest = new PageRequest(" and primary.agentTraderId='" + paramString + "'");
    Page localPage = getService().getPage(localPageRequest, new AgentTrader());
    if ((localPage.getResult() == null) || (localPage.getResult().size() <= 0)) {
      bool = false;
    }
    return bool;
  }
  
  public String checkAgentTraderId()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    AjaxValidationFormResponse localAjaxValidationFormResponse = null;
    if (!existAgentTrader(str2)) {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(true), "本代为交易员代码可以使用");
    } else {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "本代为交易员代码已被其他人占用");
    }
    this.jsonValidateReturn = localAjaxValidationFormResponse.toJSONArray();
    return "success";
  }
  
  private void checkAgentTraderIdForm(HttpServletRequest paramHttpServletRequest, ArrayList<AjaxValidationFormResponse> paramArrayList)
    throws Exception
  {
    String str = paramHttpServletRequest.getParameter("entity.agentTraderId");
    boolean bool = existAgentTrader(str);
    if (bool) {
      paramArrayList.add(new AjaxValidationFormResponse("agentTraderId", Boolean.valueOf(false), "本代为交易员代码已经存在"));
    }
  }
  
  public String checkAddAgentTraderForm()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    checkAgentTraderIdForm(localHttpServletRequest, localArrayList);
    if (localArrayList.size() != 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  public boolean exitCateorySortNo(String paramString1, String paramString2, String paramString3)
  {
    boolean bool = false;
    if (!"".equals(paramString1)) {
      if (paramString1.equals(paramString2))
      {
        bool = false;
      }
      else
      {
        PageRequest localPageRequest = new PageRequest(" and primary.sortNo=" + Tools.strToLong(paramString1) + " and primary.parentCategory.categoryId=" + Tools.strToInt(paramString3) + " and primary.status=1");
        Page localPage = getService().getPage(localPageRequest, new Category());
        if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
          bool = true;
        }
      }
    }
    return bool;
  }
  
  public String checkCategoryBySortNo()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    String str3 = localHttpServletRequest.getParameter("oldSortNo");
    String str4 = localHttpServletRequest.getParameter("parentCategoryId");
    AjaxValidationFieldResponse localAjaxValidationFieldResponse = new AjaxValidationFieldResponse(str1, Boolean.valueOf(!exitCateorySortNo(str2, str3, str4)));
    this.jsonValidateReturn = localAjaxValidationFieldResponse.toJSONArray();
    return "success";
  }
  
  public String checkCategoryForm()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    String str1 = localHttpServletRequest.getParameter("entity.sortNo");
    String str2 = localHttpServletRequest.getParameter("oldSortNo");
    String str3 = localHttpServletRequest.getParameter("entity.parentCategory.categoryId");
    if (exitCateorySortNo(str1, str2, str3)) {
      localArrayList.add(new AjaxValidationFormResponse("sortNo", Boolean.valueOf(false), "输入的排序号已存在"));
    }
    if (localArrayList.size() != 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  public boolean existCategoryPropName(String paramString1, String paramString2, String paramString3)
  {
    PageRequest localPageRequest;
    Page localPage;
    if ((!"".equals(paramString3)) && (!"".equals(paramString1)))
    {
      localPageRequest = new PageRequest(" and primary.propertyName='" + paramString1 + "'  and  primary.propertyId=" + Long.parseLong(paramString3));
      localPage = getService().getPage(localPageRequest, new CategoryProperty());
      if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
        return false;
      }
    }
    if ((!"".equals(paramString1)) && (!"".equals(paramString2)))
    {
      localPageRequest = new PageRequest(" and primary.propertyName='" + paramString1 + "' and primary.category.categoryId=" + Long.parseLong(paramString2));
      localPage = getService().getPage(localPageRequest, new CategoryProperty());
      if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
        return true;
      }
    }
    return false;
  }
  
  public String checkCategoryPropName()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue").trim();
    String str3 = localHttpServletRequest.getParameter("categoryId");
    String str4 = localHttpServletRequest.getParameter("propertyId");
    AjaxValidationFieldResponse localAjaxValidationFieldResponse = new AjaxValidationFieldResponse(str1, Boolean.valueOf(!existCategoryPropName(str2, str3, str4)));
    this.jsonValidateReturn = localAjaxValidationFieldResponse.toJSONArray();
    return "success";
  }
  
  public boolean existCategoryPropBySortNo(String paramString1, String paramString2, String paramString3)
  {
    if ((!"".equals(paramString1)) && (!"".equals(paramString3)))
    {
      if (paramString1.equals(paramString2)) {
        return false;
      }
      PageRequest localPageRequest = new PageRequest(" and primary.sortNo=" + Long.parseLong(paramString1) + " and primary.category.categoryId=" + Long.parseLong(paramString3));
      Page localPage = getService().getPage(localPageRequest, new CategoryProperty());
      if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
        return true;
      }
    }
    return false;
  }
  
  public String checkCategoryPropBySortNo()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    String str3 = localHttpServletRequest.getParameter("oldSortNo");
    String str4 = localHttpServletRequest.getParameter("categoryId");
    AjaxValidationFieldResponse localAjaxValidationFieldResponse = new AjaxValidationFieldResponse(str1, Boolean.valueOf(!existCategoryPropBySortNo(str2, str3, str4)));
    this.jsonValidateReturn = localAjaxValidationFieldResponse.toJSONArray();
    return "success";
  }
  
  public String checkCategoryPropForm()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    String str1 = localHttpServletRequest.getParameter("entity.sortNo");
    String str2 = localHttpServletRequest.getParameter("oldSortNo");
    String str3 = localHttpServletRequest.getParameter("entity.propertyName");
    String str4 = localHttpServletRequest.getParameter("categoryId");
    String str5 = localHttpServletRequest.getParameter("entity.propertyId");
    if (existCategoryPropBySortNo(str1, str2, str4)) {
      localArrayList.add(new AjaxValidationFormResponse("sortNo", Boolean.valueOf(false), "输入的排序号已存在"));
    }
    if (existCategoryPropName(str3, str4, str5)) {
      localArrayList.add(new AjaxValidationFormResponse("name", Boolean.valueOf(false), "输入的属性名称已存在"));
    }
    if (localArrayList.size() != 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  public boolean exitBreedSortNo(String paramString1, String paramString2, String paramString3)
  {
    boolean bool = false;
    if (!"".equals(paramString1)) {
      if (paramString1.equals(paramString2))
      {
        bool = false;
      }
      else
      {
        PageRequest localPageRequest = new PageRequest(" and primary.sortNo=" + Tools.strToLong(paramString1) + " and primary.category.categoryId=" + Tools.strToInt(paramString3) + " and primary.status=1");
        Page localPage = getService().getPage(localPageRequest, new Breed());
        if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
          bool = true;
        }
      }
    }
    System.out.println(bool);
    return bool;
  }
  
  public String checkBreedBySortNo()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    String str3 = localHttpServletRequest.getParameter("oldSortNo");
    String str4 = localHttpServletRequest.getParameter("categoryId");
    AjaxValidationFieldResponse localAjaxValidationFieldResponse = new AjaxValidationFieldResponse(str1, Boolean.valueOf(!exitBreedSortNo(str2, str3, str4)));
    this.jsonValidateReturn = localAjaxValidationFieldResponse.toJSONArray();
    return "success";
  }
  
  public String checkBreedForm()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    String str1 = localHttpServletRequest.getParameter("entity.sortNo");
    String str2 = localHttpServletRequest.getParameter("oldSortNo");
    String str3 = localHttpServletRequest.getParameter("entity.category.categoryId");
    if (exitBreedSortNo(str1, str2, str3)) {
      localArrayList.add(new AjaxValidationFormResponse("sortNo", Boolean.valueOf(false), "输入的排序号已存在"));
    }
    if (localArrayList.size() != 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  public boolean exitBreedPropsSortNo(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    boolean bool = false;
    if (!"".equals(paramString1)) {
      if (paramString1.equals(paramString2))
      {
        bool = false;
      }
      else
      {
        PageRequest localPageRequest = new PageRequest(" and primary.sortNo=" + Tools.strToLong(paramString1) + " and primary.categoryProperty.propertyId=" + Tools.strToInt(paramString4) + " and primary.breed.breedId= " + Tools.strToInt(paramString3));
        Page localPage = getService().getPage(localPageRequest, new BreedProps());
        if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
          bool = true;
        }
      }
    }
    return bool;
  }
  
  public String checkBreedPropsBySortNo()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    String str3 = localHttpServletRequest.getParameter("oldSortNo");
    String str4 = localHttpServletRequest.getParameter("breedId");
    String str5 = localHttpServletRequest.getParameter("propertyId");
    AjaxValidationFieldResponse localAjaxValidationFieldResponse = new AjaxValidationFieldResponse(str1, Boolean.valueOf(!exitBreedPropsSortNo(str2, str3, str4, str5)));
    this.jsonValidateReturn = localAjaxValidationFieldResponse.toJSONArray();
    return "success";
  }
  
  private boolean existBreedPropByValue(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    boolean bool = false;
    if ((!"".equals(paramString1)) && (!paramString1.equals(paramString2)))
    {
      PageRequest localPageRequest = new PageRequest(" and primary.propertyValue='" + paramString1 + "' and primary.categoryProperty.propertyId=" + Tools.strToInt(paramString3) + " and primary.breed.breedId=" + Tools.strToLong(paramString4));
      Page localPage = getService().getPage(localPageRequest, new BreedProps());
      if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
        bool = true;
      }
    }
    return bool;
  }
  
  public String checkBreedPropByValue()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    String str3 = localHttpServletRequest.getParameter("oldPropValue");
    String str4 = localHttpServletRequest.getParameter("breedId");
    String str5 = localHttpServletRequest.getParameter("propertyId");
    AjaxValidationFieldResponse localAjaxValidationFieldResponse = new AjaxValidationFieldResponse(str1, Boolean.valueOf(!existBreedPropByValue(str2, str3, str5, str4)));
    this.jsonValidateReturn = localAjaxValidationFieldResponse.toJSONArray();
    return "success";
  }
  
  public String checkPropertyPropForm()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    String str1 = localHttpServletRequest.getParameter("entity.propertyValue");
    String str2 = localHttpServletRequest.getParameter("oldPropValue");
    String str3 = localHttpServletRequest.getParameter("entity.breed.breedId");
    String str4 = localHttpServletRequest.getParameter("checkValue");
    String str5 = localHttpServletRequest.getParameter("entity.categoryProperty.propertyId");
    String str6 = localHttpServletRequest.getParameter("entity.sortNo");
    String str7 = localHttpServletRequest.getParameter("oldSortNo");
    if ((str4 == null) && (existBreedPropByValue(str1, str2, str5, str3))) {
      localArrayList.add(new AjaxValidationFormResponse("propertyValue", Boolean.valueOf(false), "输入的属性值已存在"));
    } else if (("true".equals(str4)) && (existBreedPropByValue(str1, str2, str5, str3))) {
      localArrayList.add(new AjaxValidationFormResponse("propertyValue", Boolean.valueOf(false), "输入的属性值已存在"));
    }
    if (exitBreedPropsSortNo(str6, str7, str3, str5)) {
      localArrayList.add(new AjaxValidationFormResponse("sortNo", Boolean.valueOf(false), "输入的排序号已存在"));
    }
    if (localArrayList.size() != 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  private boolean existZone(String paramString)
    throws Exception
  {
    boolean bool = true;
    PageRequest localPageRequest = new PageRequest(" and primary.code='" + paramString + "'");
    Page localPage = getService().getPage(localPageRequest, new Zone());
    if ((localPage.getResult() == null) || (localPage.getResult().size() <= 0)) {
      bool = false;
    }
    return bool;
  }
  
  private boolean existZoneSortNo(String paramString1, String paramString2)
    throws Exception
  {
    boolean bool = true;
    PageRequest localPageRequest = new PageRequest(" and primary.sortNo= '" + paramString1 + "'");
    Page localPage = getService().getPage(localPageRequest, new Zone());
    if ((localPage.getResult() == null) || (localPage.getResult().size() <= 0)) {
      bool = false;
    }
    if ((!"".equals(paramString1)) && (paramString1.equals(paramString2))) {
      bool = false;
    }
    return bool;
  }
  
  public String checkZoneByCode()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    AjaxValidationFormResponse localAjaxValidationFormResponse = null;
    if (!existZone(str2)) {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(true), "本地域编号可以使用");
    } else {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "本地域编号已经被占用");
    }
    this.jsonValidateReturn = localAjaxValidationFormResponse.toJSONArray();
    return "success";
  }
  
  public String checkZoneBySortNo()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    String str3 = localHttpServletRequest.getParameter("oldSortNo");
    System.out.println("oldSortNo: " + str3);
    AjaxValidationFormResponse localAjaxValidationFormResponse = null;
    if (!existZoneSortNo(str2, str3)) {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(true), "本排序号可以使用");
    } else {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "本排序号已经被占用");
    }
    this.jsonValidateReturn = localAjaxValidationFormResponse.toJSONArray();
    return "success";
  }
  
  private void checkZoneFormByCode(HttpServletRequest paramHttpServletRequest, ArrayList<AjaxValidationFormResponse> paramArrayList)
    throws Exception
  {
    String str = paramHttpServletRequest.getParameter("entity.code");
    boolean bool = existZone(str);
    if (bool) {
      paramArrayList.add(new AjaxValidationFormResponse("code", Boolean.valueOf(false), "本地域编号已经被使用"));
    }
  }
  
  private void checkZoneFormBySortNo(HttpServletRequest paramHttpServletRequest, ArrayList<AjaxValidationFormResponse> paramArrayList)
    throws Exception
  {
    String str1 = paramHttpServletRequest.getParameter("entity.sortNo");
    String str2 = paramHttpServletRequest.getParameter("oldSortNo");
    System.out.println("oldSortNo: " + str2);
    boolean bool = existZoneSortNo(str1, str2);
    if (bool) {
      paramArrayList.add(new AjaxValidationFormResponse("sortNo", Boolean.valueOf(false), "本排序号已经被使用"));
    }
  }
  
  public String checkAddZoneForm()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    checkZoneFormByCode(localHttpServletRequest, localArrayList);
    checkZoneFormBySortNo(localHttpServletRequest, localArrayList);
    if (localArrayList.size() != 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  private boolean existIndustry(String paramString)
    throws Exception
  {
    boolean bool = true;
    PageRequest localPageRequest = new PageRequest(" and primary.code='" + paramString + "'");
    Page localPage = getService().getPage(localPageRequest, new Industry());
    if ((localPage.getResult() == null) || (localPage.getResult().size() <= 0)) {
      bool = false;
    }
    return bool;
  }
  
  private boolean existIndustrySortNo(String paramString1, String paramString2)
    throws Exception
  {
    boolean bool = true;
    PageRequest localPageRequest = new PageRequest(" and primary.sortNo= '" + paramString1 + "'");
    Page localPage = getService().getPage(localPageRequest, new Industry());
    if ((localPage.getResult() == null) || (localPage.getResult().size() <= 0)) {
      bool = false;
    }
    if ((!"".equals(paramString1)) && (paramString1.equals(paramString2))) {
      bool = false;
    }
    return bool;
  }
  
  public String checkIndustryByCode()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    AjaxValidationFormResponse localAjaxValidationFormResponse = null;
    if (!existIndustry(str2)) {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(true), "本行业编号可以使用");
    } else {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "本行业编号已经被占用");
    }
    this.jsonValidateReturn = localAjaxValidationFormResponse.toJSONArray();
    return "success";
  }
  
  public String checkIndustryBySortNo()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    String str3 = localHttpServletRequest.getParameter("oldSortNo");
    AjaxValidationFormResponse localAjaxValidationFormResponse = null;
    if (!existIndustrySortNo(str2, str3)) {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(true), "本排序号可以使用");
    } else {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "本排序号已经被占用");
    }
    this.jsonValidateReturn = localAjaxValidationFormResponse.toJSONArray();
    return "success";
  }
  
  private void checkIndustryFormByCode(HttpServletRequest paramHttpServletRequest, ArrayList<AjaxValidationFormResponse> paramArrayList)
    throws Exception
  {
    String str = paramHttpServletRequest.getParameter("entity.code");
    boolean bool = existIndustry(str);
    if (bool) {
      paramArrayList.add(new AjaxValidationFormResponse("code", Boolean.valueOf(false), "本行业编号已经被使用"));
    }
  }
  
  private void checkIndustryFormBySortNo(HttpServletRequest paramHttpServletRequest, ArrayList<AjaxValidationFormResponse> paramArrayList)
    throws Exception
  {
    String str1 = paramHttpServletRequest.getParameter("entity.sortNo");
    String str2 = paramHttpServletRequest.getParameter("oldSortNo");
    boolean bool = existIndustrySortNo(str1, str2);
    if (bool) {
      paramArrayList.add(new AjaxValidationFormResponse("sortNo", Boolean.valueOf(false), "本排序号已经被使用"));
    }
  }
  
  public String checkAddIndustryForm()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    checkIndustryFormByCode(localHttpServletRequest, localArrayList);
    checkIndustryFormBySortNo(localHttpServletRequest, localArrayList);
    if (localArrayList.size() != 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  private boolean existCertificateType(String paramString)
    throws Exception
  {
    boolean bool = true;
    PageRequest localPageRequest = new PageRequest(" and primary.code='" + paramString + "'");
    Page localPage = getService().getPage(localPageRequest, new CertificateType());
    if ((localPage.getResult() == null) || (localPage.getResult().size() <= 0)) {
      bool = false;
    }
    return bool;
  }
  
  private boolean existCertificateTypeSortNo(String paramString1, String paramString2)
    throws Exception
  {
    boolean bool = true;
    PageRequest localPageRequest = new PageRequest(" and primary.sortNo= '" + paramString1 + "'");
    Page localPage = getService().getPage(localPageRequest, new CertificateType());
    if ((localPage.getResult() == null) || (localPage.getResult().size() <= 0)) {
      bool = false;
    }
    if ((!"".equals(paramString1)) && (paramString1.equals(paramString2))) {
      bool = false;
    }
    return bool;
  }
  
  public String checkCertificateTypeByCode()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    AjaxValidationFormResponse localAjaxValidationFormResponse = null;
    if (!existCertificateType(str2)) {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(true), "本证件类型编号可以使用");
    } else {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "本证件类型编号已经被占用");
    }
    this.jsonValidateReturn = localAjaxValidationFormResponse.toJSONArray();
    return "success";
  }
  
  public String checkCertificateTypeBySortNo()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    String str3 = localHttpServletRequest.getParameter("oldSortNo");
    this.logger.debug("sortNo: " + str2 + "oldSortNo: " + str3);
    AjaxValidationFormResponse localAjaxValidationFormResponse = null;
    if (!existCertificateTypeSortNo(str2, str3)) {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(true), "本排序号可以使用");
    } else {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "本排序号已经被占用");
    }
    this.jsonValidateReturn = localAjaxValidationFormResponse.toJSONArray();
    return "success";
  }
  
  private void checkCertificateTypeFormByCode(HttpServletRequest paramHttpServletRequest, ArrayList<AjaxValidationFormResponse> paramArrayList)
    throws Exception
  {
    String str = paramHttpServletRequest.getParameter("entity.code");
    boolean bool = existCertificateType(str);
    if (bool) {
      paramArrayList.add(new AjaxValidationFormResponse("code", Boolean.valueOf(false), "本证件类型编号已经被使用"));
    }
  }
  
  private void checkCertificateTypeFormBySortNo(HttpServletRequest paramHttpServletRequest, ArrayList<AjaxValidationFormResponse> paramArrayList)
    throws Exception
  {
    String str1 = paramHttpServletRequest.getParameter("entity.sortNo");
    String str2 = paramHttpServletRequest.getParameter("oldSortNo");
    boolean bool = existCertificateTypeSortNo(str1, str2);
    if (bool) {
      paramArrayList.add(new AjaxValidationFormResponse("sortNo", Boolean.valueOf(false), "本排序号已经被使用"));
    }
  }
  
  public String checkAddCertificateTypeForm()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    checkCertificateTypeFormByCode(localHttpServletRequest, localArrayList);
    checkCertificateTypeFormBySortNo(localHttpServletRequest, localArrayList);
    if (localArrayList.size() != 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  private boolean existFirmCategory(String paramString)
    throws Exception
  {
    boolean bool = true;
    PageRequest localPageRequest = new PageRequest(" and primary.name='" + paramString + "'");
    Page localPage = getService().getPage(localPageRequest, new FirmCategory());
    if ((localPage.getResult() == null) || (localPage.getResult().size() <= 0)) {
      bool = false;
    }
    return bool;
  }
  
  private boolean existFirmCategorySortNo(String paramString1, String paramString2)
    throws Exception
  {
    boolean bool = true;
    PageRequest localPageRequest = new PageRequest(" and primary.sortNo= '" + paramString1 + "'");
    Page localPage = getService().getPage(localPageRequest, new FirmCategory());
    if ((localPage.getResult() == null) || (localPage.getResult().size() <= 0)) {
      bool = false;
    }
    if ((!"".equals(paramString1)) && (paramString1.equals(paramString2))) {
      bool = false;
    }
    return bool;
  }
  
  public String checkFirmCategoryByName()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    AjaxValidationFormResponse localAjaxValidationFormResponse = null;
    if (!existFirmCategory(str2)) {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(true), "本类别名称可以使用");
    } else {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "本类别名称已经被占用");
    }
    this.jsonValidateReturn = localAjaxValidationFormResponse.toJSONArray();
    return "success";
  }
  
  public String checkFirmCategoryBySortNo()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    String str3 = localHttpServletRequest.getParameter("oldSortNo");
    AjaxValidationFormResponse localAjaxValidationFormResponse = null;
    if (!existFirmCategorySortNo(str2, str3)) {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(true), "本排序号可以使用");
    } else {
      localAjaxValidationFormResponse = new AjaxValidationFormResponse(str1, Boolean.valueOf(false), "本排序号已经被占用");
    }
    this.jsonValidateReturn = localAjaxValidationFormResponse.toJSONArray();
    return "success";
  }
  
  private void checkFirmCategoryFormByCode(HttpServletRequest paramHttpServletRequest, ArrayList<AjaxValidationFormResponse> paramArrayList)
    throws Exception
  {
    String str = paramHttpServletRequest.getParameter("entity.id");
    boolean bool = existFirmCategory(str);
    if (bool) {
      paramArrayList.add(new AjaxValidationFormResponse("code", Boolean.valueOf(false), "本交易商类别名称已经被使用"));
    }
  }
  
  private void checkFirmCategoryFormBySortNo(HttpServletRequest paramHttpServletRequest, ArrayList<AjaxValidationFormResponse> paramArrayList)
    throws Exception
  {
    String str1 = paramHttpServletRequest.getParameter("entity.sortNo");
    String str2 = paramHttpServletRequest.getParameter("oldSortNo");
    boolean bool = existFirmCategorySortNo(str1, str2);
    if (bool) {
      paramArrayList.add(new AjaxValidationFormResponse("sortNo", Boolean.valueOf(false), "本排序号已经被使用"));
    }
  }
  
  public String checkAddFirmCategoryForm()
    throws Exception
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    checkFirmCategoryFormByCode(localHttpServletRequest, localArrayList);
    checkFirmCategoryFormBySortNo(localHttpServletRequest, localArrayList);
    if (localArrayList.size() != 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  private boolean existCategoryPropTypeName(String paramString1, String paramString2)
  {
    boolean bool = true;
    PageRequest localPageRequest = new PageRequest(" and primary.name= '" + paramString1 + "'");
    Page localPage = getService().getPage(localPageRequest, new PropertyType());
    if ((localPage.getResult() == null) || (localPage.getResult().size() <= 0)) {
      bool = false;
    }
    if ((!"".equals(paramString1)) && (paramString1.equals(paramString2))) {
      bool = false;
    }
    return bool;
  }
  
  private boolean existCategoryPropTypeSortNo(String paramString1, String paramString2)
  {
    boolean bool = true;
    PageRequest localPageRequest = new PageRequest(" and primary.sortNo= '" + paramString1 + "'");
    Page localPage = getService().getPage(localPageRequest, new PropertyType());
    if ((localPage.getResult() == null) || (localPage.getResult().size() <= 0)) {
      bool = false;
    }
    if ((!"".equals(paramString1)) && (paramString1.equals(paramString2))) {
      bool = false;
    }
    return bool;
  }
  
  public String checkCategoryPropTypeByName()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    String str3 = localHttpServletRequest.getParameter("oldName");
    AjaxValidationFieldResponse localAjaxValidationFieldResponse = new AjaxValidationFieldResponse(str1, Boolean.valueOf(!existCategoryPropTypeName(str2, str3)));
    this.jsonValidateReturn = localAjaxValidationFieldResponse.toJSONArray();
    return "success";
  }
  
  public String checkCategoryPropTypeBySortNo()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    String str1 = localHttpServletRequest.getParameter("fieldId");
    String str2 = localHttpServletRequest.getParameter("fieldValue");
    String str3 = localHttpServletRequest.getParameter("oldSortNo");
    AjaxValidationFieldResponse localAjaxValidationFieldResponse = new AjaxValidationFieldResponse(str1, Boolean.valueOf(!existCategoryPropTypeSortNo(str2, str3)));
    this.jsonValidateReturn = localAjaxValidationFieldResponse.toJSONArray();
    return "success";
  }
  
  public String checkCategoryPropTypeForm()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    ArrayList localArrayList = new ArrayList();
    String str1 = localHttpServletRequest.getParameter("entity.name");
    String str2 = localHttpServletRequest.getParameter("oldName");
    String str3 = localHttpServletRequest.getParameter("entity.sortNo");
    String str4 = localHttpServletRequest.getParameter("oldSortNo");
    if (existCategoryPropTypeName(str1, str2)) {
      localArrayList.add(new AjaxValidationFormResponse("name", Boolean.valueOf(false), "输入的商品分类属性类型名称已存在"));
    }
    if (existCategoryPropTypeSortNo(str3, str4)) {
      localArrayList.add(new AjaxValidationFormResponse("sortNo", Boolean.valueOf(false), "输入的排序号已存在"));
    }
    if (localArrayList.size() != 0)
    {
      this.jsonValidateReturn = genJSON(localArrayList);
    }
    else
    {
      this.jsonValidateReturn = new JSONArray();
      this.jsonValidateReturn.add("true");
    }
    return "success";
  }
  
  public class AjaxValidationFormResponse
  {
    private String id;
    private Boolean status;
    private String error;
    
    public AjaxValidationFormResponse(String paramString1, Boolean paramBoolean, String paramString2)
    {
      this.id = paramString1;
      this.status = paramBoolean;
      this.error = paramString2;
    }
    
    public JSONArray toJSONArray()
    {
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.add(this.id);
      localJSONArray.add(this.status);
      localJSONArray.add(this.error);
      return localJSONArray;
    }
  }
  
  public class AjaxValidationFieldResponse
  {
    private String id;
    private Boolean status;
    
    public AjaxValidationFieldResponse(String paramString, Boolean paramBoolean)
    {
      this.id = paramString;
      this.status = paramBoolean;
    }
    
    public AjaxValidationFieldResponse(String paramString1, boolean paramBoolean, String paramString2) {}
    
    public JSONArray toJSONArray()
    {
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.add(this.id);
      localJSONArray.add(this.status);
      return localJSONArray;
    }
  }
}
