package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.timebargain.manage.model.Consigner;
import gnnt.MEBS.timebargain.manage.model.Trader;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.service.TraderManager;
import gnnt.MEBS.timebargain.manage.util.StringUtil;
import gnnt.MEBS.timebargain.manage.webapp.form.ConsignerForm;
import gnnt.MEBS.timebargain.manage.webapp.form.TraderForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.dao.DataIntegrityViolationException;

public class TraderAction
  extends BaseAction
{
  public ActionForward edit(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'edit' method");
    }
    TraderForm localTraderForm = (TraderForm)paramActionForm;
    String str1 = localTraderForm.getCrud();
    String str2 = paramHttpServletRequest.getParameter("firmID");
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    Trader localTrader = null;
    try
    {
      if (!str1.trim().equals("create"))
      {
        localTrader = localTraderManager.getTraderById(localTraderForm.getTraderID());
        if ((localTrader.getKeyCode() != null) && (!"".equals(localTrader.getKeyCode()))) {
          paramHttpServletRequest.setAttribute("keycode", localTrader.getKeyCode());
        }
        if (localTrader.getKeyStatus() != null) {
          paramHttpServletRequest.setAttribute("keyStatus", localTrader.getKeyStatus().toString());
        }
        this.log.debug("edit Trader.Name:" + localTrader.getName());
      }
      else
      {
        localTrader = new Trader();
        localTrader.setFirmID(str2);
      }
      getFirmIDSelectAttribute(paramHttpServletRequest);
      localTraderForm = (TraderForm)convert(localTrader);
      paramHttpServletRequest.setAttribute("firmID", localTraderForm.getFirmID());
      localTraderForm.setCrud(str1);
      localTraderForm.setOldPassword(localTraderForm.getPassword());
      localTraderForm.setConfirmPassword(localTraderForm.getPassword());
      updateFormBean(paramActionMapping, paramHttpServletRequest, localTraderForm);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    return paramActionMapping.findForward("edit");
  }
  
  public ActionForward editCode(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editCode' method");
    }
    TraderForm localTraderForm = (TraderForm)paramActionForm;
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    Trader localTrader = new Trader();
    try
    {
      String str1 = localTraderForm.getTraderID();
      String str2 = paramHttpServletRequest.getParameter("firmID");
      localTrader.setFirmID(str2);
      localTrader.setTraderID(str1);
      paramHttpServletRequest.setAttribute("codeNotChoose", localTraderManager.getCodeNotChoose(localTrader));
      paramHttpServletRequest.setAttribute("operateCode", localTraderManager.getOperateCode(localTrader));
      paramHttpServletRequest.setAttribute("firmID", str2);
      localTraderForm = (TraderForm)convert(localTrader);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localTraderForm);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException);
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", "没有记录！");
    }
    return paramActionMapping.findForward("editCode");
  }
  
  public ActionForward save(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'save' method");
    }
    TraderForm localTraderForm = (TraderForm)paramActionForm;
    String str1 = localTraderForm.getCrud();
    String str2 = localTraderForm.getPassword();
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    Trader localTrader = (Trader)convert(localTraderForm);
    try
    {
      if (str1.trim().equals("create"))
      {
        localTrader.setPassword(StringUtil.encodePassword(str2, "MD5"));
        localTraderManager.insertTrader(localTrader);
        addSysLog(paramHttpServletRequest, "增加交易管理员[" + localTrader.getTraderID() + "]");
        paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        paramHttpServletRequest.setAttribute("ifSave", "save");
      }
      else if (str1.trim().equals("update"))
      {
        localTraderManager.updateTrader(localTrader);
        addSysLog(paramHttpServletRequest, "修改交易管理员[" + localTrader.getTraderID() + "]");
        paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        paramHttpServletRequest.setAttribute("ifSave", "save");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return paramActionMapping.findForward("edit");
    }
    return paramActionMapping.findForward("saveToFirm");
  }
  
  public ActionForward saveCode(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveCode' method");
    }
    TraderForm localTraderForm = (TraderForm)paramActionForm;
    Trader localTrader = (Trader)convert(localTraderForm);
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    try
    {
      localTraderManager.updateOperateCode(localTrader);
      addSysLog(paramHttpServletRequest, "保存可操作交易代码信息[" + localTrader.getTraderID() + "]");
      paramHttpServletRequest.setAttribute("ifSave", "save");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", "操作失败！");
    }
    return paramActionMapping.findForward("update");
  }
  
  public ActionForward delete(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'delete' method");
    }
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
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
          localTraderManager.deleteTraderById(str1);
          paramHttpServletRequest.setAttribute("ifSave", "save");
          addSysLog(paramHttpServletRequest, "删除交易管理员[" + str1 + "]");
          i++;
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str2 = str2 + str1 + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + str1 + "]与其他数据关联，删除失败！");
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
    return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward updateStatusY(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
    String str1 = paramHttpServletRequest.getParameter("crud");
    String str2 = paramHttpServletRequest.getParameter("firmID");
    String[] arrayOfString2 = paramHttpServletRequest.getParameterValues("itemlist");
    if (arrayOfString2 != null)
    {
      this.log.debug("==idsT.length:" + arrayOfString2.length);
      Trader localTrader = new Trader();
      int i = 0;
      String str4 = "";
      for (int j = 0; j < arrayOfString2.length; j++)
      {
        String str3 = arrayOfString2[j];
        try
        {
          localTrader.setTraderID(str3);
          if ("correct".equals(str1)) {
            localTrader.setStatus(Short.valueOf(Short.parseShort("0")));
          } else if ("incorrect".equals(str1)) {
            localTrader.setStatus(Short.valueOf(Short.parseShort("1")));
          }
          localTraderManager.updateStatusTrader(localTrader);
          addSysLog(paramHttpServletRequest, "修改状态[" + str3 + "]");
          i++;
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str4 = str4 + str3 + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + str3 + "]修改失败！");
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
    return paramActionMapping.findForward("update");
  }
  
  public ActionForward search(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'search' method");
    }
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    Trader localTrader = null;
    Object localObject;
    if (!"save".equals(paramHttpServletRequest.getAttribute("ifSave")))
    {
      str1 = paramHttpServletRequest.getParameter("firmID");
      localObject = paramHttpServletRequest.getParameter("firmName");
      String str2 = paramHttpServletRequest.getParameter("traderID");
      String str3 = paramHttpServletRequest.getParameter("name");
      localTrader = new Trader();
      localTrader.setFirmID(str1);
      localTrader.setFirmName((String)localObject);
      localTrader.setTraderID(str2);
      localTrader.setName(str3);
    }
    String str1 = paramHttpServletRequest.getParameter("isQry") == null ? "1" : paramHttpServletRequest.getParameter("isQry");
    try
    {
      if (str1.equals("1"))
      {
        localObject = localTraderManager.getTraders(localTrader);
        paramHttpServletRequest.setAttribute("traderList", localObject);
        paramHttpServletRequest.setAttribute("TRADER_STATUS", CommonDictionary.TRADER_STATUS);
      }
    }
    catch (Exception localException)
    {
      this.log.error("查询Trader表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("list");
  }
  
  public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward top(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'top' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    return paramActionMapping.findForward("top");
  }
  
  public ActionForward searchConsigner(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchConsigner' method");
    }
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    try
    {
      List localList = localTraderManager.getConsigner();
      paramHttpServletRequest.setAttribute("list", localList);
      paramHttpServletRequest.setAttribute("CONSIGNERTYPE", CommonDictionary.CONSIGNERTYPE);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("查询Consigner表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listConsigner");
  }
  
  public ActionForward editConsigner(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editConsigner' method");
    }
    saveToken(paramHttpServletRequest);
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    ConsignerForm localConsignerForm1 = (ConsignerForm)paramActionForm;
    String str1 = localConsignerForm1.getConsignerID();
    String str2 = localConsignerForm1.getCrud();
    Consigner localConsigner = null;
    try
    {
      if ("update".equals(str2))
      {
        localConsigner = localTraderManager.getConsigner(str1);
        this.log.debug("edit consigner.Name:" + localConsigner.getName());
      }
      else if ("create".equals(str2))
      {
        localConsigner = new Consigner();
      }
      ConsignerForm localConsignerForm2 = (ConsignerForm)convert(localConsigner);
      localConsignerForm2.setCrud(str2);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localConsignerForm2);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return searchConsigner(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    return paramActionMapping.findForward("editConsigner");
  }
  
  public ActionForward saveConsigner(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editConsigner' method");
    }
    try
    {
      if (isTokenValid(paramHttpServletRequest))
      {
        ConsignerForm localConsignerForm = (ConsignerForm)paramActionForm;
        String str1 = localConsignerForm.getCrud();
        String str2 = localConsignerForm.getPassword();
        Consigner localConsigner = (Consigner)convert(localConsignerForm);
        TraderManager localTraderManager = (TraderManager)getBean("traderManager");
        if ("create".equals(str1))
        {
          localConsigner.setPassword(StringUtil.encodePassword(str2, "MD5"));
          localTraderManager.insertConsigner(localConsigner);
          addSysLog(paramHttpServletRequest, "增加代为委托员[" + localConsigner.getConsignerID() + "]");
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
        else if ("update".equals(str1))
        {
          localTraderManager.updateConsigner(localConsigner);
          addSysLog(paramHttpServletRequest, "修改代为委托员[" + localConsigner.getConsignerID() + "]");
          paramHttpServletRequest.setAttribute("prompt", "操作成功！");
        }
      }
      resetToken(paramHttpServletRequest);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return searchConsigner(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward deleteConsigner(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deleteConsigner' method");
    }
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
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
          localTraderManager.deleteConsignerById(str1);
          paramHttpServletRequest.setAttribute("ifSave", "save");
          addSysLog(paramHttpServletRequest, "删除代为委托员[" + str1 + "]");
          i++;
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str2 = str2 + str1 + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + str1 + "]与其他数据关联，删除失败！");
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
    return searchConsigner(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward updateStatusConsigner(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("itemlist");
    String str1 = paramHttpServletRequest.getParameter("crud");
    String[] arrayOfString2 = paramHttpServletRequest.getParameterValues("itemlist");
    if (arrayOfString2 != null)
    {
      this.log.debug("==idsT.length:" + arrayOfString2.length);
      Consigner localConsigner = new Consigner();
      int i = 0;
      String str3 = "";
      for (int j = 0; j < arrayOfString2.length; j++)
      {
        String str2 = arrayOfString2[j];
        try
        {
          localConsigner.setConsignerID(str2);
          if ("correct".equals(str1)) {
            localConsigner.setStatus(Short.valueOf(Short.parseShort("0")));
          } else if ("incorrect".equals(str1)) {
            localConsigner.setStatus(Short.valueOf(Short.parseShort("1")));
          }
          localTraderManager.updateStatusConsigner(localConsigner);
          addSysLog(paramHttpServletRequest, "修改状态[" + str2 + "]");
          i++;
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str3 = str3 + str2 + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + str2 + "]修改失败！");
        }
      }
      if (!str3.equals(""))
      {
        str3 = str3.substring(0, str3.length() - 1);
        str3 = str3 + "修改失败！";
      }
      str3 = str3 + "成功修改" + i + "条纪录！";
      paramHttpServletRequest.setAttribute("prompt", str3);
    }
    return searchConsigner(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward editConsignerCode(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editConsignerCode' method");
    }
    ConsignerForm localConsignerForm1 = (ConsignerForm)paramActionForm;
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    Consigner localConsigner = new Consigner();
    try
    {
      String str = localConsignerForm1.getConsignerID();
      localConsigner.setConsignerID(str);
      paramHttpServletRequest.setAttribute("codeNotChoose", localTraderManager.getNotOperateFirm(localConsigner));
      paramHttpServletRequest.setAttribute("operateCode", localTraderManager.getOperateFirm(localConsigner));
      ConsignerForm localConsignerForm2 = (ConsignerForm)paramActionForm;
      updateFormBean(paramActionMapping, paramHttpServletRequest, localConsignerForm2);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException);
      localException.printStackTrace();
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("editConsignerCode");
  }
  
  public ActionForward saveConsignerCode(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveCode' method");
    }
    ConsignerForm localConsignerForm = (ConsignerForm)paramActionForm;
    Consigner localConsigner = (Consigner)convert(localConsignerForm);
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    localTraderManager.updateOperateFirm(localConsigner);
    addSysLog(paramHttpServletRequest, "保存可操作交易商信息[" + localConsigner.getConsignerID() + "]");
    paramHttpServletRequest.setAttribute("ifSave", "save");
    paramHttpServletRequest.setAttribute("prompt", "操作成功！");
    return searchConsigner(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward saveTraderPrivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveTraderPrivilege' method");
    }
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    String str = paramHttpServletRequest.getParameter("traderID");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("privilegeCode_B");
    try
    {
      localTraderManager.deleteTraderPrivilege(str);
      paramHttpServletRequest.setAttribute("prompt", "删除成功！");
      if ((arrayOfString != null) && (arrayOfString.length > 0)) {
        for (int i = 0; i < arrayOfString.length; i++)
        {
          Trader localTrader = new Trader();
          localTrader.setTraderID(str);
          if ((arrayOfString[i] != null) && (!"".equals(arrayOfString[i]))) {
            localTrader.setPrivilegeCode_B(Short.valueOf(Short.parseShort(arrayOfString[i])));
          }
          localTraderManager.insertTraderPrivilege(localTrader);
          addSysLog(paramHttpServletRequest, "修改交易员权限");
          paramHttpServletRequest.setAttribute("prompt", "修改成功！");
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("查询TradePrivilege表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("saveToFirm");
  }
  
  public ActionForward tradePrivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'tradePrivilege' method");
    }
    String str1 = (String)paramHttpServletRequest.getAttribute("save");
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    List localList = null;
    String str3 = paramHttpServletRequest.getParameter("firmID");
    try
    {
      String str2;
      if ("save".equals(str1))
      {
        str2 = (String)paramHttpServletRequest.getAttribute("traderID");
        localList = localTraderManager.getTraderPrivilege(str2);
      }
      else
      {
        str2 = paramHttpServletRequest.getParameter("traderID");
        localList = localTraderManager.getTraderPrivilege(str2);
      }
      paramHttpServletRequest.setAttribute("traderPrivilegeList", localList);
      paramHttpServletRequest.setAttribute("FIRMPRIVILEGE_B", CommonDictionary.FIRMPRIVILEGE_B);
      paramHttpServletRequest.setAttribute("FIRMPRIVILEGE_S", CommonDictionary.FIRMPRIVILEGE_S);
      paramHttpServletRequest.setAttribute("traderID", str2);
      paramHttpServletRequest.setAttribute("firmID", str3);
      saveToken(paramHttpServletRequest);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("查询TradePrivilege表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("tradePrivilege");
  }
  
  public ActionForward updateTraderPrivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'updateTraderPrivilege' method");
    }
    TraderForm localTraderForm = (TraderForm)paramActionForm;
    String str1 = paramHttpServletRequest.getParameter("crud");
    String str2 = paramHttpServletRequest.getParameter("traderID");
    String str3 = paramHttpServletRequest.getParameter("id");
    Trader localTrader1 = new Trader();
    Trader localTrader2 = null;
    LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
    paramHttpServletRequest.setAttribute("breedSelect", localLookupManager.getSelectLabelValueByTable("T_A_BREED", "breedName", "breedID", " order by breedID "));
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    if ("update".equals(str1))
    {
      if ((str3 != null) && (!"".equals(str3))) {
        localTrader1.setId(Short.valueOf(Short.parseShort(str3)));
      }
      localTrader2 = localTraderManager.getTraderPrivilegeById(localTrader1);
    }
    if ("create".equals(str1))
    {
      localTrader2 = new Trader();
      localTrader2.setTypeID(str2);
    }
    BeanUtils.copyProperties(localTraderForm, localTrader2);
    localTraderForm.setCrud(str1);
    updateFormBean(paramActionMapping, paramHttpServletRequest, localTraderForm);
    saveToken(paramHttpServletRequest);
    return paramActionMapping.findForward("updateTraderPrivilege");
  }
  
  public ActionForward saveNewTraderPrivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveNewTraderPrivilege' method");
    }
    TraderForm localTraderForm = (TraderForm)paramActionForm;
    Trader localTrader = new Trader();
    BeanUtils.copyProperties(localTrader, localTraderForm);
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    String str = paramHttpServletRequest.getParameter("crud");
    try
    {
      if (isTokenValid(paramHttpServletRequest))
      {
        if ("create".equals(str))
        {
          localTraderManager.insertNewTraderPrivilege(localTrader);
          paramHttpServletRequest.setAttribute("prompt", "操作成功");
          addSysLog(paramHttpServletRequest, "增加交易员权限");
        }
        if ("update".equals(str))
        {
          localTraderManager.updateNewTraderPrivilege(localTrader);
          paramHttpServletRequest.setAttribute("prompt", "操作成功");
          addSysLog(paramHttpServletRequest, "修改交易员权限");
        }
        paramHttpServletRequest.setAttribute("traderID", localTraderForm.getTypeID());
        paramHttpServletRequest.setAttribute("save", "save");
      }
      resetToken(paramHttpServletRequest);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("查询TradePrivilege表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("saveNewTraderPrivilege");
  }
  
  public ActionForward deleteTraderPrivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deleteTraderPrivilege' method");
    }
    TraderManager localTraderManager = (TraderManager)getBean("traderManager");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("itemlist");
    Trader localTrader = new Trader();
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
          if ((str1 != null) && (!"".equals(str1)))
          {
            localTrader.setId(Short.valueOf(Short.parseShort(str1)));
            localTraderManager.deleteTraderPrivilegeById(localTrader);
            addSysLog(paramHttpServletRequest, "删除交易员权限[" + str1 + "]");
            i++;
          }
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          localDataIntegrityViolationException.printStackTrace();
          str2 = str2 + str1 + ",";
        }
        catch (RuntimeException localRuntimeException)
        {
          localRuntimeException.printStackTrace();
          str2 = str2 + str1 + ",";
        }
      }
      str2 = str2 + "成功删除" + i + "条纪录！";
      paramHttpServletRequest.setAttribute("prompt", str2);
      String str3 = paramHttpServletRequest.getParameter("traderID");
      paramHttpServletRequest.setAttribute("save", "save");
      paramHttpServletRequest.setAttribute("traderID", str3);
    }
    return tradePrivilege(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  private void getSelectAttribute(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
    paramHttpServletRequest.setAttribute("customerGroupSelect", localLookupManager.getSelectLabelValueByTable("FirmGroup", "GroupName", "GroupID", (short)1));
  }
  
  private void getFirmIDSelectAttribute(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
    paramHttpServletRequest.setAttribute("firmIDSelect", localLookupManager.getSelectLabelValueByTable("T_FIRM", "firmID", "firmID", " order by firmID "));
  }
}
