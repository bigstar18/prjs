package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.timebargain.manage.model.Customer;
import gnnt.MEBS.timebargain.manage.service.CustomerManager;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.service.TradePropsManager;
import gnnt.MEBS.timebargain.manage.util.StringUtil;
import gnnt.MEBS.timebargain.manage.webapp.form.CustomerForm;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.dao.DataIntegrityViolationException;

public class CustomerAction
  extends BaseAction
{
  public ActionForward top(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'top' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    return paramActionMapping.findForward("top");
  }
  
  public ActionForward edit(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'edit' method");
    }
    CustomerForm localCustomerForm = (CustomerForm)paramActionForm;
    String str1 = localCustomerForm.getCrud();
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = null;
    try
    {
      getSelectAttribute(paramHttpServletRequest);
      if (str1.trim().equals("update"))
      {
        localCustomer = localCustomerManager.getCustomerById(localCustomerForm.getCustomerID());
        this.log.debug("edit Customer.CustomerName:" + localCustomer.getCustomerName());
      }
      else if (str1.trim().equals("create"))
      {
        localCustomer = new Customer();
      }
      String str2 = "";
      String str3 = "";
      String str4 = "";
      if ((localCustomer != null) && (localCustomer.getStatus() != null) && (localCustomer.getStatus().shortValue() == 2))
      {
        str2 = "2";
        str4 = "2";
        str3 = "退市";
        paramHttpServletRequest.setAttribute("type9", str2);
        paramHttpServletRequest.setAttribute("status", str4);
        paramHttpServletRequest.setAttribute("name", str3);
      }
      else
      {
        paramHttpServletRequest.setAttribute("type9", "");
        paramHttpServletRequest.setAttribute("status", "");
        paramHttpServletRequest.setAttribute("name", "");
      }
      localCustomerForm = (CustomerForm)convert(localCustomer);
      localCustomerForm.setCrud(str1);
      this.log.debug("customerForm.selectedOptionsMarket:" + localCustomerForm.getSelectedOptionsMarket());
      updateFormBean(paramActionMapping, paramHttpServletRequest, localCustomerForm);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("==err:" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
    }
    return paramActionMapping.findForward("edit");
  }
  
  public ActionForward save(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'save' method");
    }
    CustomerForm localCustomerForm = (CustomerForm)paramActionForm;
    String str = localCustomerForm.getCrud();
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = (Customer)convert(localCustomerForm);
    try
    {
      if (str.trim().equals("create"))
      {
        localCustomer.setPassword(StringUtil.encodePassword(localCustomerForm.getPassword(), "MD5"));
        if (paramHttpServletRequest.getParameter("mkName") == null)
        {
          localCustomerManager.insertCustomer(localCustomer);
          addSysLog(paramHttpServletRequest, "增加交易客户[" + localCustomer.getCustomerID() + "]");
        }
      }
      else if (str.trim().equals("update"))
      {
        localCustomerManager.updateCustomer(localCustomer);
        addSysLog(paramHttpServletRequest, "修改交易客户[" + localCustomer.getCustomerID() + "]");
      }
    }
    catch (Exception localException)
    {
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("save");
  }
  
  public ActionForward delete(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'delete' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
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
          localCustomerManager.deleteCustomerById(str1);
          addSysLog(paramHttpServletRequest, "删除交易客户[" + str1 + "]");
          i++;
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
      if (!str2.equals(""))
      {
        str2 = str2.substring(0, str2.length() - 1);
        str2 = str2 + "交易客户未退市或与其他数据关联，不能删除！";
      }
      str2 = str2 + "成功删除" + i + "条纪录！";
      paramHttpServletRequest.setAttribute("prompt", str2);
    }
    return paramActionMapping.findForward("chgGroup");
  }
  
  public ActionForward updateStatusF(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    String str1 = paramHttpServletRequest.getParameter("crud");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("itemlist");
    if (arrayOfString != null)
    {
      this.log.debug("==ids.length:" + arrayOfString.length);
      Customer localCustomer = new Customer();
      int i = 0;
      String str3 = "";
      for (int j = 0; j < arrayOfString.length; j++)
      {
        String str2 = arrayOfString[j];
        try
        {
          localCustomer.setFirmID(str2);
          if ("correct".equals(str1)) {
            localCustomer.setStatus(Short.valueOf(Short.parseShort("0")));
          } else if ("incorrect".equals(str1)) {
            localCustomer.setStatus(Short.valueOf(Short.parseShort("1")));
          } else if ("out".equals(str1)) {
            localCustomer.setStatus(Short.valueOf(Short.parseShort("2")));
          }
          localCustomerManager.updateStatusFirm(localCustomer);
          paramHttpServletRequest.setAttribute("ifSave", "save");
          addSysLog(paramHttpServletRequest, "修改状态[" + str2 + "]");
          i++;
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          str3 = str3 + str2 + ",";
          paramHttpServletRequest.setAttribute("prompt", "[" + str2 + "]修改失败！");
        }
        catch (Exception localException)
        {
          this.log.debug("error message: " + localException.getMessage());
          paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
        }
      }
      if (!str3.equals(""))
      {
        str3 = str3.substring(0, str3.length() - 1);
        str3 = str3 + "修改失败！";
      }
      else
      {
        str3 = str3 + "成功修改" + i + "条纪录！";
      }
      paramHttpServletRequest.setAttribute("prompt", str3);
    }
    return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward chgGroup(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'chgGroup' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("itemlist");
    String str1 = paramHttpServletRequest.getParameter("groupID");
    int i = 0;
    if (arrayOfString != null)
    {
      this.log.debug("==ids.length:" + arrayOfString.length);
      String str3 = "";
      for (int j = 0; j < arrayOfString.length; j++)
      {
        String str2 = arrayOfString[j];
        try
        {
          localCustomerManager.chgGroupById(new Long(str1), str2);
          addSysLog(paramHttpServletRequest, "调整交易客户[" + str2 + "]至组[" + str1 + "]");
          i++;
        }
        catch (RuntimeException localRuntimeException)
        {
          str3 = str3 + str2 + ",";
        }
      }
      if (!str3.equals(""))
      {
        str3 = str3.substring(0, str3.length() - 1);
        str3 = str3 + "调整失败！";
      }
      str3 = str3 + "成功调整" + i + "条纪录！";
      paramHttpServletRequest.setAttribute("prompt", str3);
    }
    return paramActionMapping.findForward("chgGroup");
  }
  
  public ActionForward search(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'search' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    try
    {
      Customer localCustomer = new Customer();
      localCustomer.setCustomerID(paramHttpServletRequest.getParameter("firmID"));
      localCustomer.setCustomerName(paramHttpServletRequest.getParameter("firmName"));
      List localList = localCustomerManager.getCustomers(localCustomer);
      paramHttpServletRequest.setAttribute("customerList", localList);
      paramHttpServletRequest.setAttribute("CUSTOMER_STATUS", CommonDictionary.CUSTOMER_STATUS);
      getSelectAttribute(paramHttpServletRequest);
    }
    catch (Exception localException)
    {
      this.log.error("查询Customer表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("list");
  }
  
  public ActionForward searchGroupCustomer(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchGroupCustomer' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = new Customer();
    String str = paramHttpServletRequest.getParameter("groupID");
    Long localLong = (str == null) || (str.equals("")) ? null : Long.valueOf(str);
    localCustomer.setGroupID(localLong);
    try
    {
      List localList = localCustomerManager.getCustomers(localCustomer);
      paramHttpServletRequest.setAttribute("customerList", localList);
      paramHttpServletRequest.setAttribute("CUSTOMER_STATUS", CommonDictionary.CUSTOMER_STATUS);
    }
    catch (Exception localException)
    {
      this.log.error("查询Customer表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("searchGroupCustomer");
  }
  
  public ActionForward back(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'back' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = new Customer();
    String str = paramHttpServletRequest.getParameter("customerID");
    localCustomer.setCustomerID(str);
    localCustomer.setStatus(Short.valueOf(Short.parseShort("2")));
    try
    {
      localCustomerManager.updateBack(localCustomer);
    }
    catch (Exception localException)
    {
      this.log.error("查询出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("back");
  }
  
  public ActionForward goback(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'goback' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = new Customer();
    String str = paramHttpServletRequest.getParameter("customerID");
    localCustomer.setCustomerID(str);
    localCustomer.setStatus(Short.valueOf(Short.parseShort("0")));
    try
    {
      localCustomerManager.updateGoBack(localCustomer);
    }
    catch (Exception localException)
    {
      this.log.error("查询Customer表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("goback");
  }
  
  public ActionForward unspecified(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    return search(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward searchKH(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchKH' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    String str = paramHttpServletRequest.getParameter("isQry") == null ? "1" : paramHttpServletRequest.getParameter("isQry");
    try
    {
      if (str.equals("1"))
      {
        Customer localCustomer = new Customer();
        localCustomer.setCustomerID(paramHttpServletRequest.getParameter("firmID"));
        List localList = localCustomerManager.getKHCustomers(localCustomer);
        paramHttpServletRequest.setAttribute("customerList", localList);
      }
      paramHttpServletRequest.setAttribute("CUSTOMER_STATUS", CommonDictionary.CUSTOMER_STATUS);
      getSelectAttribute(paramHttpServletRequest);
    }
    catch (Exception localException)
    {
      this.log.error("查询Customer表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listKH");
  }
  
  public ActionForward customerQuery(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'customerQuery' method");
    }
    CustomerForm localCustomerForm = (CustomerForm)paramActionForm;
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = new Customer();
    try
    {
      localCustomer.setCustomerID(localCustomerForm.getCustomerID());
      localCustomer.setCustomerName(localCustomerForm.getCustomerName());
      List localList = localCustomerManager.customerQuery(localCustomer);
      paramHttpServletRequest.setAttribute("customerList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询Customer表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("list");
  }
  
  public ActionForward customerChg(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'customerChg' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    TradePropsManager localTradePropsManager = (TradePropsManager)getBean("tradePropsManager");
    String str1 = "";
    String str2 = paramHttpServletRequest.getParameter("forwardName") == null ? "" : paramHttpServletRequest.getParameter("forwardName").trim();
    String str3 = paramHttpServletRequest.getParameter("customerID") == null ? "" : paramHttpServletRequest.getParameter("customerID").trim();
    try
    {
      if (str3.contains(","))
      {
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        paramHttpServletRequest.setAttribute("customerID", str3);
        for (String str4 : str3.split(","))
        {
          Customer localCustomer = localCustomerManager.getCustomerById(str4);
          if ((localCustomer == null) || (localCustomer.getCustomerID() == null) || ("".equals(localCustomer.getCustomerID())))
          {
            paramHttpServletRequest.setAttribute("prompt", "交易商代码" + str4 + "不存在！");
            paramHttpServletRequest.setAttribute("customerID", str1);
            break;
          }
          localArrayList1.add(localCustomer);
          localArrayList2.add(localCustomerManager.getCustomerFundsById(str4));
          str1 = str1 + str4 + ",";
        }
        paramHttpServletRequest.setAttribute("customerList", localArrayList1);
        paramHttpServletRequest.setAttribute("customerFundsList", localArrayList2);
      }
      else
      {
        paramHttpServletRequest.setAttribute("customer", localCustomerManager.getCustomerById(str3));
        if (paramHttpServletRequest.getAttribute("customer") != null)
        {
          ??? = (Customer)paramHttpServletRequest.getAttribute("customer");
          if ((??? == null) || (((Customer)???).getCustomerID() == null) || ("".equals(((Customer)???).getCustomerID()))) {
            paramHttpServletRequest.setAttribute("prompt", "交易商代码不存在！");
          }
        }
        paramHttpServletRequest.setAttribute("customerFunds", localCustomerManager.getCustomerFundsById(str3));
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("查询Customer或CustomerFunds表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
      return paramActionMapping.findForward(str2);
    }
    return paramActionMapping.findForward(str2);
  }
  
  public ActionForward saveVirtualFund(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveVirtualFund' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    String str1 = paramHttpServletRequest.getParameter("customerID");
    String str2 = paramHttpServletRequest.getParameter("addVirtualFunds");
    if ((str2 == null) || (str2.trim().equals(""))) {
      str2 = "0";
    }
    try
    {
      localCustomerManager.addCustomerVirtualFunds(str1, Double.valueOf(str2));
      addSysLog(paramHttpServletRequest, "02", "为客户代码" + str1 + "入虚拟资金" + str2);
    }
    catch (Exception localException)
    {
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("saveVirtualFund");
  }
  
  public ActionForward searchVirtualFund(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchVirtualFund' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    try
    {
      List localList = localCustomerManager.customerQuery(null);
      paramHttpServletRequest.setAttribute("virtualFundList", localList);
    }
    catch (Exception localException)
    {
      this.log.error("查询CustomerFunds表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("listVirtualFund");
  }
  
  public ActionForward applyWaitList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyWaitList' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = new Customer();
    try
    {
      String str1 = paramHttpServletRequest.getParameter("firmID");
      String str2 = paramHttpServletRequest.getParameter("applyID");
      String str3 = "";
      localCustomer.setFirmID(str1);
      if ((str2 != null) && (!"".equals(str2))) {
        localCustomer.setApplyID(Long.valueOf(Long.parseLong(str2)));
      }
      List localList = localCustomerManager.getApplyWaitList(localCustomer);
      paramHttpServletRequest.setAttribute("applyWaitList", localList);
      paramHttpServletRequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
    }
    catch (Exception localException)
    {
      this.log.error("查询CustomerFunds表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("virtualFundAppWaitList");
  }
  
  public ActionForward applyAlreadyList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyAlreadyList' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = new Customer();
    try
    {
      String str1 = paramHttpServletRequest.getParameter("firmID");
      String str2 = paramHttpServletRequest.getParameter("applyID");
      String str3 = "";
      localCustomer.setFirmID(str1);
      if ((str2 != null) && (!"".equals(str2))) {
        localCustomer.setApplyID(Long.valueOf(Long.parseLong(str2)));
      }
      List localList = localCustomerManager.getApplyAlreadyList(localCustomer);
      paramHttpServletRequest.setAttribute("applyAlreadyList", localList);
      paramHttpServletRequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
    }
    catch (Exception localException)
    {
      this.log.error("查询CustomerFunds表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("virtualFundAppAlreadyList");
  }
  
  public ActionForward saveNewApp(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveNewApp' method");
    }
    CustomerForm localCustomerForm = (CustomerForm)paramActionForm;
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = new Customer();
    BeanUtils.copyProperties(localCustomer, localCustomerForm);
    try
    {
      String str = AclCtrl.getLogonID(paramHttpServletRequest);
      if ((str != null) && (!"".equals(str))) {
        localCustomer.setCreator(str);
      }
      localCustomerManager.insertNewApp(localCustomer);
      addSysLog(paramHttpServletRequest, "增加虚拟资金申请！");
    }
    catch (Exception localException)
    {
      this.log.error("查询virtualfundsapply表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("saveNewApp");
  }
  
  public ActionForward applyWaitCheList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyWaitCheList' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = new Customer();
    String str1 = (String)paramHttpServletRequest.getAttribute("save");
    try
    {
      Object localObject;
      if ("save".equals(str1))
      {
        localObject = localCustomerManager.getApplyWaitList(localCustomer);
        paramHttpServletRequest.setAttribute("applyWaitCheList", localObject);
        paramHttpServletRequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
      }
      else
      {
        localObject = paramHttpServletRequest.getParameter("firmID");
        String str2 = paramHttpServletRequest.getParameter("applyID");
        localCustomer.setFirmID((String)localObject);
        if ((str2 != null) && (!"".equals(str2))) {
          localCustomer.setApplyID(Long.valueOf(Long.parseLong(str2)));
        }
        List localList = localCustomerManager.getApplyWaitList(localCustomer);
        paramHttpServletRequest.setAttribute("applyWaitCheList", localList);
        paramHttpServletRequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
      }
    }
    catch (Exception localException)
    {
      this.log.error("查询CustomerFunds表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("virtualFundApplyWaitCheList");
  }
  
  public ActionForward applyCheAlreadyList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'applyCheAlreadyList' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = new Customer();
    try
    {
      String str1 = paramHttpServletRequest.getParameter("firmID");
      String str2 = paramHttpServletRequest.getParameter("applyID");
      localCustomer.setFirmID(str1);
      if ((str2 != null) && (!"".equals(str2))) {
        localCustomer.setApplyID(Long.valueOf(Long.parseLong(str2)));
      }
      List localList = localCustomerManager.getApplyAlreadyList(localCustomer);
      paramHttpServletRequest.setAttribute("applyCheAlreadyList", localList);
      paramHttpServletRequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
    }
    catch (Exception localException)
    {
      this.log.error("查询CustomerFunds表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("virtualFundApplyCheAlreadyList");
  }
  
  public ActionForward waitSuccessCheck(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'waitSuccessCheck' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = new Customer();
    try
    {
      String str1 = paramHttpServletRequest.getParameter("type");
      if ("1".equals(str1)) {
        localCustomer.setStatus(Short.valueOf(Short.parseShort("2")));
      } else if ("2".equals(str1)) {
        localCustomer.setStatus(Short.valueOf(Short.parseShort("3")));
      }
      String str2 = paramHttpServletRequest.getParameter("remark2");
      String str3 = paramHttpServletRequest.getParameter("applyID");
      if ((str3 != null) && (!"".equals(str3))) {
        localCustomer.setApplyID(Long.valueOf(Long.parseLong(str3)));
      }
      localCustomer.setRemark2(str2);
      String str4 = AclCtrl.getLogonID(paramHttpServletRequest);
      localCustomer.setCreator(str4);
      String str5 = paramHttpServletRequest.getParameter("virtualFunds");
      String str6 = paramHttpServletRequest.getParameter("firmID");
      Double localDouble = null;
      if ((str5 != null) && (!"".equals(str5))) {
        localDouble = Double.valueOf(Double.parseDouble(str5));
      } else {
        localDouble = Double.valueOf(Double.parseDouble("0"));
      }
      if ("1".equals(str1)) {
        try
        {
          localCustomerManager.addCustomerVirtualFunds(str6, localDouble);
          paramHttpServletRequest.setAttribute("prompt", "追加虚拟资金成功！");
          paramHttpServletRequest.setAttribute("save", "save");
        }
        catch (Exception localException2)
        {
          throw new RuntimeException("追加虚拟资金失败！");
        }
      }
      try
      {
        localCustomerManager.CheckVirtual(localCustomer);
        paramHttpServletRequest.setAttribute("prompt", "审核成功！");
        paramHttpServletRequest.setAttribute("save", "save");
      }
      catch (Exception localException3)
      {
        throw new RuntimeException("审核失败！");
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      this.log.error("查询CustomerFunds表出错：" + localException1.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException1.getMessage());
    }
    return applyWaitCheList(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward firmPrivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'firmPrivilege' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = new Customer();
    try
    {
      String str1 = (String)paramHttpServletRequest.getAttribute("save");
      String str2;
      List localList;
      if ("save".equals(str1))
      {
        str2 = (String)paramHttpServletRequest.getAttribute("firmID");
        if ((str2 != null) && (!"".equals(str2))) {
          localCustomer.setFirmID(str2);
        }
        localList = localCustomerManager.getFirmPrivilege(localCustomer);
        paramHttpServletRequest.setAttribute("privilegeList", localList);
        paramHttpServletRequest.setAttribute("firmID", str2);
      }
      else
      {
        str2 = paramHttpServletRequest.getParameter("firmID");
        if ((str2 != null) && (!"".equals(str2))) {
          localCustomer.setFirmID(str2);
        }
        localList = localCustomerManager.getFirmPrivilege(localCustomer);
        paramHttpServletRequest.setAttribute("privilegeList", localList);
        paramHttpServletRequest.setAttribute("firmID", str2);
      }
      paramHttpServletRequest.setAttribute("FIRMPRIVILEGE_B", CommonDictionary.FIRMPRIVILEGE_B);
      paramHttpServletRequest.setAttribute("FIRMPRIVILEGE_S", CommonDictionary.FIRMPRIVILEGE_S);
    }
    catch (Exception localException)
    {
      this.log.error("查询TradePrivilege表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("firmPrivilegeList");
  }
  
  public ActionForward updateFirmPrivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'updateFirmPrivilege' method");
    }
    CustomerForm localCustomerForm = (CustomerForm)paramActionForm;
    String str1 = paramHttpServletRequest.getParameter("id");
    String str2 = paramHttpServletRequest.getParameter("crud");
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = null;
    if ("update".equals(str2))
    {
      localObject = new Customer();
      if ((str1 != null) && (!"".equals(str1))) {
        ((Customer)localObject).setId(Short.valueOf(Short.parseShort(str1)));
      }
      localCustomer = localCustomerManager.getFirmPrivilegeById((Customer)localObject);
    }
    else if ("create".equals(str2))
    {
      localCustomer = new Customer();
      localObject = paramHttpServletRequest.getParameter("firmID");
      localCustomer.setTypeID((String)localObject);
    }
    BeanUtils.copyProperties(localCustomerForm, localCustomer);
    Object localObject = "";
    if (localCustomer.getKind() != null)
    {
      if ("1".equals(localCustomer.getKind().toString()))
      {
        localObject = "1";
        localCustomerForm.setKindID(localCustomer.getKindID());
      }
      if ("2".equals(localCustomer.getKind().toString()))
      {
        localObject = "2";
        localCustomerForm.setCommodityID(localCustomer.getKindID());
      }
      localCustomerForm.setKind(localCustomer.getKind().toString());
      if ("".equals(localCustomer.getKind().toString())) {
        localObject = " ";
      }
    }
    paramHttpServletRequest.setAttribute("type", localObject);
    localCustomerForm.setCrud(str2);
    updateFormBean(paramActionMapping, paramHttpServletRequest, localCustomerForm);
    return paramActionMapping.findForward("updateFirmPrivilege");
  }
  
  public ActionForward saveFirmPrivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveFirmPrivilege' method");
    }
    CustomerForm localCustomerForm = (CustomerForm)paramActionForm;
    String str = localCustomerForm.getCrud();
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = (Customer)convert(localCustomerForm);
    if ((localCustomerForm.getKind() != null) && (!"".equals(localCustomerForm.getKind())))
    {
      if ("1".equals(localCustomerForm.getKind())) {
        localCustomer.setKindID(localCustomerForm.getKindID());
      }
      if ("2".equals(localCustomerForm.getKind())) {
        localCustomer.setKindID(localCustomerForm.getCommodityID());
      }
    }
    try
    {
      if (str.trim().equals("create"))
      {
        localCustomerManager.insertFirmPrivilege(localCustomer);
        paramHttpServletRequest.setAttribute("prompt", "操作成功");
        addSysLog(paramHttpServletRequest, "增加交易商权限[" + localCustomer.getTypeID() + "]");
      }
      else if (str.trim().equals("update"))
      {
        localCustomerManager.updateFirmPrivilege(localCustomer);
        paramHttpServletRequest.setAttribute("prompt", "操作成功");
        addSysLog(paramHttpServletRequest, "修改交易商权限[" + localCustomer.getTypeID() + "]");
      }
      paramHttpServletRequest.setAttribute("save", "save");
      paramHttpServletRequest.setAttribute("firmID", localCustomer.getTypeID());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("saveFirmPrivilege");
  }
  
  public ActionForward batchSetSaveFirmPrivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'batchSstSaveFirmPrivilege' method");
    }
    String str1 = paramHttpServletRequest.getParameter("Code") == null ? "" : paramHttpServletRequest.getParameter("Code").trim();
    String str2 = paramHttpServletRequest.getParameter("codes") == null ? "" : paramHttpServletRequest.getParameter("codes").trim();
    String str3 = paramHttpServletRequest.getParameter("goods") == null ? "" : paramHttpServletRequest.getParameter("goods").trim();
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    String str4 = "";
    if ("1".equals(str3))
    {
      arrayOfString1 = (String[])(paramHttpServletRequest.getParameterValues("breed").length == 0 ? "" : paramHttpServletRequest.getParameterValues("breed"));
      for (i = 0; i < arrayOfString1.length; i++) {
        if (i == arrayOfString1.length) {
          str4 = str4 + arrayOfString1[i];
        } else {
          str4 = str4 + arrayOfString1[i] + ",";
        }
      }
    }
    else
    {
      arrayOfString2 = (String[])(paramHttpServletRequest.getParameterValues("commodity").length == 0 ? "" : paramHttpServletRequest.getParameterValues("commodity"));
      for (i = 0; i < arrayOfString2.length; i++) {
        if (i == arrayOfString2.length) {
          str4 = str4 + arrayOfString2[i];
        } else {
          str4 = str4 + arrayOfString2[i] + ",";
        }
      }
    }
    int i = Integer.parseInt(str1);
    int j = Integer.parseInt(str3);
    CustomerForm localCustomerForm = (CustomerForm)paramActionForm;
    String str5 = localCustomerForm.getCrud();
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    if (str5.trim().equals("save"))
    {
      int k = Integer.parseInt(localCustomerForm.getPrivilegeCode_B());
      int m = Integer.parseInt(localCustomerForm.getPrivilegeCode_S());
      localCustomerManager.batchEmptyDeleteFirmPrivilege(i, str2, j, str4);
      localCustomerManager.batchSetInsertFirmPrivilege(i, str2, j, str4, k, m);
    }
    else if (str5.trim().equals("empty"))
    {
      localCustomerManager.batchEmptyDeleteFirmPrivilege(i, str2, j, str4);
    }
    paramHttpServletRequest.setAttribute("prompt", "操作成功");
    addSysLog(paramHttpServletRequest, "批量" + str5 + "交易商权限[" + "typeIdString" + "]");
    return paramActionMapping.findForward("batchSstSaveFirmPrivilege");
  }
  
  public ActionForward deleteFirmPrivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deleteFirmPrivilege' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
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
          localCustomerManager.deleteFirmPrivilegeById(str1);
          addSysLog(paramHttpServletRequest, "删除交易商权限[" + str1 + "]");
          i++;
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
      String str3 = paramHttpServletRequest.getParameter("firmID");
      paramHttpServletRequest.setAttribute("save", "save");
      paramHttpServletRequest.setAttribute("firmID", str3);
    }
    return firmPrivilege(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ActionForward typePrivilegeList(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'typePrivilegeList' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = new Customer();
    try
    {
      String str1 = paramHttpServletRequest.getParameter("kindID");
      String str2 = paramHttpServletRequest.getParameter("typeID");
      localCustomer.setKindID(str1);
      localCustomer.setTypeID(str2);
      List localList = localCustomerManager.getTypePrivilege(localCustomer);
      paramHttpServletRequest.setAttribute("typePrivilegeList", localList);
      paramHttpServletRequest.setAttribute("FIRMPRIVILEGE_B", CommonDictionary.FIRMPRIVILEGE_B);
      paramHttpServletRequest.setAttribute("FIRMPRIVILEGE_S", CommonDictionary.FIRMPRIVILEGE_S);
      paramHttpServletRequest.setAttribute("KIND", CommonDictionary.KIND);
      paramHttpServletRequest.setAttribute("TYPE", CommonDictionary.TYPE);
    }
    catch (Exception localException)
    {
      this.log.error("查询TradePrivilege表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("typePrivilegeList");
  }
  
  private void getSelectAttribute(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
    paramHttpServletRequest.setAttribute("commoditySelect", localLookupManager.getSelectLabelValueByTable("T_commodity", "commodityID", "commodityID", " order by commodityID "));
  }
  
  private void getSelectAttributeBreed(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
  }
  
  private void getMarketSelectAttribute(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
    paramHttpServletRequest.setAttribute("marketSelect", localLookupManager.getSelectLabelValueByTable("Market", "MarketName", "MarketCode", " where Status=1 order by MarketCode"));
  }
}
