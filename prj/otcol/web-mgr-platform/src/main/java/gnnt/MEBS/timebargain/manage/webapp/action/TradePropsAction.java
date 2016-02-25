package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.timebargain.manage.model.TradeProps;
import gnnt.MEBS.timebargain.manage.service.CustomerManager;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.service.TradePropsManager;
import gnnt.MEBS.timebargain.manage.service.xtgl.UserManager;
import gnnt.MEBS.timebargain.manage.webapp.form.TradePropsForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.dao.DataIntegrityViolationException;

public class TradePropsAction
  extends BaseAction
{
  /**
   * @deprecated
   */
  public ActionForward save(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'save' method");
    }
    TradePropsForm localTradePropsForm = (TradePropsForm)paramActionForm;
    String str1 = localTradePropsForm.getModuleName();
    try
    {
      Object localObject;
      String str2;
      if (str1.equals("1"))
      {
        localObject = (CustomerManager)getBean("customerManager");
        str2 = paramHttpServletRequest.getParameter("addInFund");
        if ((str2 == null) || (str2.trim().equals(""))) {
          str2 = "0";
        }
        String str3 = paramHttpServletRequest.getParameter("addOutFund");
        if ((str3 == null) || (str3.trim().equals(""))) {
          str3 = "0";
        }
        ((CustomerManager)localObject).addCustomerBalance(localTradePropsForm.getCustomerID(), Double.valueOf(str2), Double.valueOf(str3));
        if (Double.parseDouble(str2) != 0.0D) {
          addSysLog(paramHttpServletRequest, "02", "为客户代码" + localTradePropsForm.getCustomerID() + "入资金" + str2);
        }
        if (Double.parseDouble(str3) != 0.0D) {
          addSysLog(paramHttpServletRequest, "02", "为客户代码" + localTradePropsForm.getCustomerID() + "出资金" + str3);
        }
      }
      else if (str1.equals("2"))
      {
        localObject = (CustomerManager)getBean("customerManager");
        str2 = paramHttpServletRequest.getParameter("addVirtualFunds");
        if ((str2 == null) || (str2.trim().equals(""))) {
          str2 = "0";
        }
        ((CustomerManager)localObject).addCustomerVirtualFunds(localTradePropsForm.getCustomerID(), Double.valueOf(str2));
        addSysLog(paramHttpServletRequest, "02", "为客户代码" + localTradePropsForm.getCustomerID() + "入虚拟资金" + str2);
      }
      else if (str1.equals("3"))
      {
        localObject = (TradePropsManager)getBean("tradePropsManager");
        str2 = paramHttpServletRequest.getParameter("newMaxOverdraft");
        if ((str2 == null) || (str2.trim().equals(""))) {
          str2 = "0";
        }
        ((TradePropsManager)localObject).updateCustomerMaxOverdraft(localTradePropsForm.getCustomerID(), Double.valueOf(str2));
        addSysLog(paramHttpServletRequest, "02", "为客户代码" + localTradePropsForm.getCustomerID() + "改变最大透支额度为" + str2);
      }
      else if (str1.equals("4"))
      {
        localObject = (TradePropsManager)getBean("tradePropsManager");
        str2 = paramHttpServletRequest.getParameter("newMinClearDeposit");
        if ((str2 == null) || (str2.trim().equals(""))) {
          str2 = "0";
        }
        ((TradePropsManager)localObject).updateCustomermMinClearDeposit(localTradePropsForm.getCustomerID(), Double.valueOf(str2));
        addSysLog(paramHttpServletRequest, "02", "为客户代码" + localTradePropsForm.getCustomerID() + "改变最低结算准备金为" + str2);
      }
      else if (str1.equals("5"))
      {
        localObject = (TradePropsManager)getBean("tradePropsManager");
        str2 = paramHttpServletRequest.getParameter("newMaxHoldQty");
        if ((str2 == null) || (str2.trim().equals(""))) {
          str2 = "0";
        }
        ((TradePropsManager)localObject).updateCustomerMaxHoldQty(localTradePropsForm.getCustomerID(), Long.valueOf(str2));
        addSysLog(paramHttpServletRequest, "02", "为客户代码" + localTradePropsForm.getCustomerID() + "改变最大订货量为" + str2);
      }
      paramHttpServletRequest.setAttribute("prompt", "操作成功！");
    }
    catch (Exception localException)
    {
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("msg");
  }
  
  /**
   * @deprecated
   */
  public ActionForward qryInfo(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'qryInfo' method");
    }
    UserManager localUserManager = (UserManager)getBean("userManager");
    try
    {
      List localList = localUserManager.getSysLogs("02");
      paramHttpServletRequest.setAttribute("qryInfoList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查看风险控制情况出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("list");
  }
  
  public ActionForward editDefProps(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editDefProps' method");
    }
    TradePropsForm localTradePropsForm = (TradePropsForm)paramActionForm;
    try
    {
      TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
      TradeProps localTradeProps = localTradePropsManager.getCustomerDefProps();
      localTradePropsForm = (TradePropsForm)convert(localTradeProps);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localTradePropsForm);
    }
    catch (Exception localException)
    {
      this.log.error("===>edit err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("editDef");
  }
  
  public ActionForward saveDefProps(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveDefProps' method");
    }
    TradePropsForm localTradePropsForm = (TradePropsForm)paramActionForm;
    try
    {
      TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
      TradeProps localTradeProps = (TradeProps)convert(localTradePropsForm);
      localTradePropsManager.updateCustomerDefProps(localTradeProps);
      addSysLog(paramHttpServletRequest, "02", "修改客户缺省交易属性，最大订货量：" + localTradeProps.getMaxHoldQty() + "，最低结算准备金：" + localTradeProps.getMinClearDeposit() + "，最大透支额度：" + localTradeProps.getMaxOverdraft());
      String str = "";
      str = paramHttpServletRequest.getParameter("crud");
      if (str.equals("s")) {
        localTradePropsManager.insertFirmValidTradeProps();
      }
      paramHttpServletRequest.setAttribute("prompt", "操作成功！");
    }
    catch (Exception localException)
    {
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("msg");
  }
  
  public ActionForward searchGroupProps(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchGroupProps' method");
    }
    TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
    try
    {
      List localList = localTradePropsManager.getGroupTradePropss(null);
      paramHttpServletRequest.setAttribute("groupTradePropsList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询GroupTradeProps表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listGroup");
  }
  
  public ActionForward searchGroupProps1(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchGroupProps1' method");
    }
    TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
    try
    {
      TradeProps localTradeProps = new TradeProps();
      String str = paramHttpServletRequest.getParameter("groupID");
      Long localLong = (str == null) || (str.equals("")) ? null : Long.valueOf(str);
      localTradeProps.setGroupID(localLong);
      List localList = localTradePropsManager.getGroupTradePropss(localTradeProps);
      paramHttpServletRequest.setAttribute("groupTradePropsList", localList);
      getSelectAttribute(paramHttpServletRequest);
    }
    catch (Exception localException)
    {
      this.log.error("查询GroupTradeProps表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listGroup1");
  }
  
  public ActionForward topGroupProps(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'top' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    return paramActionMapping.findForward("topCustomer");
  }
  
  public ActionForward editGroupProps(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editGroupProps' method");
    }
    TradePropsForm localTradePropsForm = (TradePropsForm)paramActionForm;
    String str = localTradePropsForm.getCrud();
    TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
    TradeProps localTradeProps = null;
    try
    {
      getSelectAttribute(paramHttpServletRequest);
      if (!str.trim().equals("create")) {
        localTradeProps = localTradePropsManager.getGroupTradePropsById(new Long(localTradePropsForm.getGroupID()));
      } else {
        localTradeProps = new TradeProps();
      }
      localTradePropsForm = (TradePropsForm)convert(localTradeProps);
      localTradePropsForm.setCrud(str);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localTradePropsForm);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return searchGroupProps(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    return paramActionMapping.findForward("editGroup");
  }
  
  public ActionForward deleteGroupProps(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deleteGroupProps' method");
    }
    TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
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
          localTradePropsManager.deleteGroupTradePropsById(new Long(str1));
          addSysLog(paramHttpServletRequest, "02", "删除客户组交易属性信息[" + str1 + "]");
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
    return searchGroupProps(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward saveGroupProps(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveGroupProps' method");
    }
    TradePropsForm localTradePropsForm = (TradePropsForm)paramActionForm;
    String str1 = localTradePropsForm.getCrud();
    TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
    TradeProps localTradeProps = (TradeProps)convert(localTradePropsForm);
    try
    {
      if (str1.trim().equals("create"))
      {
        localTradePropsManager.insertGroupTradeProps(localTradeProps);
        addSysLog(paramHttpServletRequest, "02", "增加客户组[" + localTradeProps.getGroupID() + "]交易属性," + "最大订货量:" + localTradeProps.getMaxHoldQty() + "最低结算保证金:" + localTradeProps.getMinClearDeposit() + "最大透支额度:" + localTradeProps.getMaxOverdraft());
      }
      else if (str1.trim().equals("update"))
      {
        localTradePropsManager.updateGroupTradeProps(localTradeProps);
        addSysLog(paramHttpServletRequest, "02", "修改客户组[" + localTradeProps.getGroupID() + "]交易属性," + "最大订货量:" + localTradeProps.getMaxHoldQty() + "最低结算保证金:" + localTradeProps.getMinClearDeposit() + "最大透支额度:" + localTradeProps.getMaxOverdraft());
      }
      String str2 = "";
      str2 = paramHttpServletRequest.getParameter("moduleName");
      if (str2.equals("s")) {
        localTradePropsManager.insertFirmValidTradeProps();
      }
      paramHttpServletRequest.setAttribute("prompt", "操作成功！");
    }
    catch (Exception localException)
    {
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      getSelectAttribute(paramHttpServletRequest);
      return paramActionMapping.findForward("editGroup");
    }
    return searchGroupProps(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward searchCustomerProps(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchCustomerProps' method");
    }
    TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
    try
    {
      List localList = localTradePropsManager.getCustomerTradePropss(null);
      paramHttpServletRequest.setAttribute("customerTradePropsList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询CustomerTradeProps表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listCustomer");
  }
  
  public ActionForward editCustomerProps(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editCustomerProps' method");
    }
    TradePropsForm localTradePropsForm = (TradePropsForm)paramActionForm;
    String str = localTradePropsForm.getCrud();
    TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
    TradeProps localTradeProps = null;
    try
    {
      if (!str.trim().equals("create")) {
        localTradeProps = localTradePropsManager.getCustomerTradePropsById(localTradePropsForm.getCustomerID());
      } else {
        localTradeProps = new TradeProps();
      }
      localTradePropsForm = (TradePropsForm)convert(localTradeProps);
      localTradePropsForm.setCrud(str);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localTradePropsForm);
    }
    catch (Exception localException)
    {
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return searchCustomerProps(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    return paramActionMapping.findForward("editCustomer");
  }
  
  public ActionForward deleteCustomerProps(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deleteCustomerProps' method");
    }
    TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
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
          localTradePropsManager.deleteCustomerTradePropsById(str1);
          addSysLog(paramHttpServletRequest, "02", "删除客户交易属性信息[" + str1 + "]");
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
    return searchCustomerProps(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward saveCustomerProps(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveCustomerProps' method");
    }
    TradePropsForm localTradePropsForm = (TradePropsForm)paramActionForm;
    String str1 = localTradePropsForm.getCrud();
    TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
    TradeProps localTradeProps = (TradeProps)convert(localTradePropsForm);
    try
    {
      if (str1.trim().equals("create"))
      {
        localTradePropsManager.insertCustomerTradeProps(localTradeProps);
        addSysLog(paramHttpServletRequest, "02", "增加客户[" + localTradeProps.getCustomerID() + "]交易属性," + "最大订货量:" + localTradeProps.getMaxHoldQty() + "最低结算保证金:" + localTradeProps.getMinClearDeposit() + "最大透支额度:" + localTradeProps.getMaxOverdraft());
      }
      else if (str1.trim().equals("update"))
      {
        localTradePropsManager.updateCustomerTradeProps(localTradeProps);
        addSysLog(paramHttpServletRequest, "02", "修改客户[" + localTradeProps.getCustomerID() + "]交易属性," + "最大订货量:" + localTradeProps.getMaxHoldQty() + "最低结算保证金:" + localTradeProps.getMinClearDeposit() + "最大透支额度:" + localTradeProps.getMaxOverdraft());
      }
      String str2 = "";
      str2 = paramHttpServletRequest.getParameter("moduleName");
      if (str2.equals("s")) {
        localTradePropsManager.insertFirmValidTradeProps();
      }
      paramHttpServletRequest.setAttribute("prompt", "操作成功！");
    }
    catch (Exception localException)
    {
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return paramActionMapping.findForward("editCustomer");
    }
    return searchCustomerProps(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward searchTradeProps(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchTradeProps' method");
    }
    TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
    try
    {
      List localList = localTradePropsManager.getTradePropss(null);
      paramHttpServletRequest.setAttribute("tradePropsList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listTradeProps");
  }
  
  public ActionForward searchTFirm(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchTFirm' method");
    }
    String str = paramHttpServletRequest.getParameter("customerID");
    TradeProps localTradeProps = new TradeProps();
    TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
    try
    {
      localTradeProps.setCustomerID(str);
      List localList = localTradePropsManager.getTradePropsTFirm(localTradeProps);
      paramHttpServletRequest.setAttribute("tradePropsTFirmList", localList);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listTradePropsTFirm");
  }
  
  public ActionForward editTFirm(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'editTFirm' method");
    }
    TradePropsForm localTradePropsForm = (TradePropsForm)paramActionForm;
    String str1 = paramHttpServletRequest.getParameter("firmID");
    TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
    String str2 = "";
    try
    {
      TradeProps localTradeProps = localTradePropsManager.getTradePropsTFirmById(str1);
      if ((localTradeProps.getMaxHoldQty() != null) && ("-1".equals(localTradeProps.getMaxHoldQty().toString()))) {
        str2 = "2";
      } else {
        str2 = "1";
      }
      paramHttpServletRequest.setAttribute("typeMaxHoldQty", str2);
      BeanUtils.copyProperties(localTradePropsForm, localTradeProps);
      updateFormBean(paramActionMapping, paramHttpServletRequest, localTradePropsForm);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("editTFirm");
  }
  
  public ActionForward saveTFirm(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveTFirm' method");
    }
    TradePropsForm localTradePropsForm = (TradePropsForm)paramActionForm;
    TradeProps localTradeProps = new TradeProps();
    BeanUtils.copyProperties(localTradeProps, localTradePropsForm);
    TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
    try
    {
      localTradePropsManager.saveTradePropsTFirm(localTradeProps);
      addSysLog(paramHttpServletRequest, "修改交易商交易属性");
      updateFormBean(paramActionMapping, paramHttpServletRequest, localTradePropsForm);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("saveTFirm");
  }
  
  public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return null;
  }
  
  private void getSelectAttribute(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
    paramHttpServletRequest.setAttribute("customerGroupSelect", localLookupManager.getSelectLabelValueByTable("T_A_FirmGroup", "GroupName", "GroupID", " order by GroupID", (short)1));
  }
}
