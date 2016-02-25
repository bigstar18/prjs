package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.timebargain.manage.model.Customer;
import gnnt.MEBS.timebargain.manage.service.CustomerManager;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.webapp.form.CustomerForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.dao.DataIntegrityViolationException;

public class KH_CustomerAction
  extends BaseAction
{
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
  
  public ActionForward top1(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'top1' method");
    }
    getSelectAttribute(paramHttpServletRequest);
    return paramActionMapping.findForward("firstTop");
  }
  
  public ActionForward search(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'search' method");
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
    return paramActionMapping.findForward("list");
  }
  
  public ActionForward searchFirst(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchFirst' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    String str1 = paramHttpServletRequest.getParameter("isQry") == null ? "1" : paramHttpServletRequest.getParameter("isQry");
    try
    {
      if (str1.equals("1"))
      {
        Customer localCustomer = new Customer();
        String str2 = paramHttpServletRequest.getParameter("groupID");
        Long localLong = (str2 == null) || (str2.equals("")) ? null : Long.valueOf(str2);
        localCustomer.setGroupID(localLong);
        localCustomer.setCustomerID(paramHttpServletRequest.getParameter("firmID"));
        localCustomer.setCustomerName(paramHttpServletRequest.getParameter("firmName"));
        List localList = localCustomerManager.getCustomerCounts(paramHttpServletRequest.getParameter("firmID"));
        paramHttpServletRequest.setAttribute("firstcustomerList", localList);
      }
      paramHttpServletRequest.setAttribute("CUSTOMER_STATUS", CommonDictionary.CUSTOMER_STATUS);
      getSelectAttribute(paramHttpServletRequest);
    }
    catch (Exception localException)
    {
      this.log.error("查询Customer表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("firstList");
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
      List localList = localCustomerManager.getKHCustomers(localCustomer);
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
  
  public ActionForward searchGroupFirm(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'searchGroupFirm' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = new Customer();
    String str = paramHttpServletRequest.getParameter("groupID");
    Long localLong = (str == null) || (str.equals("")) ? null : Long.valueOf(str);
    localCustomer.setGroupID(localLong);
    try
    {
      List localList = localCustomerManager.getFirmGroup(localCustomer);
      paramHttpServletRequest.setAttribute("customerList", localList);
      paramHttpServletRequest.setAttribute("CUSTOMER_STATUS", CommonDictionary.CUSTOMER_STATUS);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("查询Customer表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("searchGroupFirm");
  }
  
  public ActionForward edit(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'edit' method");
    }
    CustomerForm localCustomerForm = (CustomerForm)paramActionForm;
    String str1 = localCustomerForm.getCrud();
    String str2 = localCustomerForm.getFirmID();
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = null;
    try
    {
      getSelectAttribute(paramHttpServletRequest);
      if (!str1.trim().equals("create"))
      {
        localCustomer = localCustomerManager.getKHCustomerById(localCustomerForm.getCustomerID());
        this.log.debug("edit Customer.CustomerName:" + localCustomer.getCustomerName());
      }
      else
      {
        localCustomer = new Customer();
      }
      localCustomerForm = (CustomerForm)convert(localCustomer);
      localCustomerForm.setCrud(str1);
      localCustomerForm.setFirmID(str2);
      this.log.debug("customerForm.selectedOptionsMarket:" + localCustomerForm.getSelectedOptionsMarket());
      updateFormBean(paramActionMapping, paramHttpServletRequest, localCustomerForm);
    }
    catch (Exception localException)
    {
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
    String str1 = localCustomerForm.getCrud();
    String str2 = localCustomerForm.getFirmID();
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer1 = (Customer)convert(localCustomerForm);
    try
    {
      String[] arrayOfString = null;
      if ((localCustomer1.getCode() != null) && (!"".equals(localCustomer1.getCode()))) {
        arrayOfString = localCustomer1.getCode().split(",");
      }
      int i = 0;
      int j = 0;
      if (((localCustomer1.getStartCode() != null ? 1 : 0) & (!"".equals(localCustomer1.getStartCode()) ? 1 : 0)) != 0)
      {
        i = Integer.parseInt(localCustomer1.getStartCode());
        j = Integer.parseInt(localCustomer1.getEndCode()) + 1;
      }
      String str3 = "";
      int k = 0;
      String str4 = "";
      if (str1.trim().equals("create"))
      {
        int m;
        Customer localCustomer2;
        if (arrayOfString != null) {
          for (m = 0; m < arrayOfString.length; m++) {
            if (arrayOfString[m].trim().length() != 2)
            {
              paramHttpServletRequest.setAttribute("prompt", "后缀名格式不正确！");
            }
            else
            {
              localCustomer2 = new Customer();
              localCustomer2.setCode(arrayOfString[m].trim());
              localCustomer2.setFirmID(str2);
              localCustomer2.setStatus(localCustomer1.getStatus());
              try
              {
                localCustomerManager.insertKHCustomer(localCustomer2);
                k++;
                addSysLog(paramHttpServletRequest, "增加交易客户[" + str2 + arrayOfString[m].trim() + "]");
              }
              catch (RuntimeException localRuntimeException1)
              {
                str4 = str4 + arrayOfString[m].trim() + ",";
                localRuntimeException1.printStackTrace();
              }
            }
          }
        }
        if (i != 0) {
          for (m = i; m < j; m++)
          {
            if (m < 10) {
              str3 = "0" + m;
            } else {
              str3 = m + "";
            }
            localCustomer2 = new Customer();
            localCustomer2.setFirmID(str2);
            localCustomer2.setCode(str3);
            localCustomer2.setStatus(localCustomer1.getStatus());
            try
            {
              localCustomerManager.insertKHCustomer(localCustomer2);
              addSysLog(paramHttpServletRequest, "增加交易客户[" + str2 + str3 + "]");
              k++;
            }
            catch (RuntimeException localRuntimeException2)
            {
              localRuntimeException2.printStackTrace();
              str4 = str4 + str3 + ",";
            }
          }
        }
        addSysLog(paramHttpServletRequest, "增加交易客户后缀");
        if (!str4.equals(""))
        {
          str4 = str4.substring(0, str4.length() - 1);
          str4 = str4 + "后缀已存在，成功存盘" + k + "条交易客户！";
        }
      }
      else if (str1.trim().equals("update"))
      {
        localCustomerManager.updateKHCustomer(localCustomer1);
        addSysLog(paramHttpServletRequest, "修改交易客户[" + localCustomer1.getCustomerID() + "]");
      }
    }
    catch (Exception localException)
    {
      this.log.error("===>save err：" + localException);
      localException.printStackTrace();
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
    if (arrayOfString != null)
    {
      String str1 = "";
      for (String str3 : arrayOfString) {
        if ("00".equals(str3.substring(str3.length() - 2, str3.length()))) {
          str1 = str1 + str3 + " ";
        }
      }
      if (!str1.equals(""))
      {
        paramHttpServletRequest.setAttribute("prompt", str1 + "是默认客户，不能删除请重新选择！");
        arrayOfString = null;
      }
    }
    int i = 0;
    if (arrayOfString != null)
    {
      this.log.debug("==ids.length:" + arrayOfString.length);
      String str2 = "";
      for (??? = 0; ??? < arrayOfString.length; ???++)
      {
        ??? = arrayOfString[???];
        try
        {
          localCustomerManager.deleteKHCustomerById((String)???);
          addSysLog(paramHttpServletRequest, "删除交易客户[" + (String)??? + "]");
          i++;
        }
        catch (DataIntegrityViolationException localDataIntegrityViolationException)
        {
          localDataIntegrityViolationException.printStackTrace();
          str2 = str2 + (String)??? + ",";
        }
        catch (RuntimeException localRuntimeException)
        {
          localRuntimeException.printStackTrace();
          str2 = str2 + (String)??? + ",";
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
    paramHttpServletRequest.setAttribute("customer_Type", "delete_KH");
    return paramActionMapping.findForward("chgGroup");
  }
  
  public ActionForward updateStatusKH(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    String str1 = paramHttpServletRequest.getParameter("crud");
    String str2 = paramHttpServletRequest.getParameter("firmID");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("itemlist");
    if (arrayOfString != null)
    {
      this.log.debug("==ids.length:" + arrayOfString.length);
      Customer localCustomer = new Customer();
      int i = 0;
      String str4 = "";
      for (int j = 0; j < arrayOfString.length; j++)
      {
        String str3 = arrayOfString[j];
        try
        {
          localCustomer.setCustomerID(str3);
          if ("correct".equals(str1)) {
            localCustomer.setStatus(Short.valueOf(Short.parseShort("0")));
          } else if ("incorrect".equals(str1)) {
            localCustomer.setStatus(Short.valueOf(Short.parseShort("1")));
          }
          localCustomerManager.updateStatusCustomer(localCustomer);
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
  
  public ActionForward customerPrivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'customerPrivilege' method");
    }
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    Customer localCustomer = new Customer();
    try
    {
      String str1 = (String)paramHttpServletRequest.getAttribute("save");
      String str2;
      String str3;
      List localList;
      if ("save".equals(str1))
      {
        str2 = (String)paramHttpServletRequest.getAttribute("customerID");
        str3 = (String)paramHttpServletRequest.getAttribute("firmID");
        localCustomer.setCustomerID(str2);
        localList = localCustomerManager.getKHCustomerPrivilege(localCustomer);
        paramHttpServletRequest.setAttribute("customerPrivilegeList", localList);
        paramHttpServletRequest.setAttribute("customerID", str2);
        paramHttpServletRequest.setAttribute("firmID", str3);
      }
      else
      {
        str2 = paramHttpServletRequest.getParameter("customerID");
        str3 = paramHttpServletRequest.getParameter("firmID");
        localCustomer.setCustomerID(str2);
        localList = localCustomerManager.getKHCustomerPrivilege(localCustomer);
        paramHttpServletRequest.setAttribute("customerPrivilegeList", localList);
        paramHttpServletRequest.setAttribute("customerID", str2);
        paramHttpServletRequest.setAttribute("firmID", str3);
      }
      paramHttpServletRequest.setAttribute("FIRMPRIVILEGE_B", CommonDictionary.FIRMPRIVILEGE_B);
      paramHttpServletRequest.setAttribute("FIRMPRIVILEGE_S", CommonDictionary.FIRMPRIVILEGE_S);
    }
    catch (Exception localException)
    {
      this.log.error("查询TradePrivilege表出错：" + localException.getMessage());
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("customerPrivilege");
  }
  
  public ActionForward updateCustomerPrivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'updateCustomerPrivilege' method");
    }
    CustomerForm localCustomerForm = (CustomerForm)paramActionForm;
    String str1 = paramHttpServletRequest.getParameter("id");
    String str2 = paramHttpServletRequest.getParameter("crud");
    CustomerManager localCustomerManager = (CustomerManager)getBean("customerManager");
    getSelectAttribute(paramHttpServletRequest);
    Customer localCustomer = null;
    if ("update".equals(str2))
    {
      localObject = new Customer();
      if ((str1 != null) && (!"".equals(str1))) {
        ((Customer)localObject).setId(Short.valueOf(Short.parseShort(str1)));
      }
      localCustomer = localCustomerManager.getCustomerPrivilegeById((Customer)localObject);
    }
    else if ("create".equals(str2))
    {
      localCustomer = new Customer();
      localObject = paramHttpServletRequest.getParameter("customerID");
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
    return paramActionMapping.findForward("updateCustomerPrivilege");
  }
  
  public ActionForward saveCustomerPrivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'saveCustomerPrivilege' method");
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
        localCustomerManager.insertCustomerPrivilege(localCustomer);
        paramHttpServletRequest.setAttribute("prompt", "操作成功");
        addSysLog(paramHttpServletRequest, "增加交易客户权限[" + localCustomer.getCustomerID() + "]");
      }
      else if (str.trim().equals("update"))
      {
        localCustomerManager.updateCustomerPrivilege(localCustomer);
        paramHttpServletRequest.setAttribute("prompt", "操作成功");
        addSysLog(paramHttpServletRequest, "修改交易客户权限[" + localCustomer.getCustomerID() + "]");
      }
      paramHttpServletRequest.setAttribute("save", "save");
      paramHttpServletRequest.setAttribute("customerID", localCustomer.getTypeID());
    }
    catch (Exception localException)
    {
      this.log.error("===>save err：" + localException);
      paramHttpServletRequest.setAttribute("prompt", localException.getMessage());
    }
    return paramActionMapping.findForward("saveCustomerPrivilege");
  }
  
  public ActionForward deleteCustomerPrivilege(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'deleteCustomerPrivilege' method");
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
          localCustomerManager.deleteCustomerPrivilegeById(str1);
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
      String str3 = paramHttpServletRequest.getParameter("customerID");
      String str4 = paramHttpServletRequest.getParameter("firmID");
      paramHttpServletRequest.setAttribute("save", "save");
      paramHttpServletRequest.setAttribute("customerID", str3);
      paramHttpServletRequest.setAttribute("firmID", str4);
    }
    return customerPrivilege(paramActionMapping, paramActionForm, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  private void getSelectAttribute(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    LookupManager localLookupManager = (LookupManager)getBean("lookupManager");
    paramHttpServletRequest.setAttribute("commoditySelect", localLookupManager.getSelectLabelValueByTable("T_commodity", "commodityID", "commodityID", " order by commodityID "));
  }
}
