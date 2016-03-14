// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 

package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.timebargain.manage.model.Customer;
import gnnt.MEBS.timebargain.manage.service.*;
import gnnt.MEBS.timebargain.manage.util.StringUtil;
import gnnt.MEBS.timebargain.manage.webapp.form.CustomerForm;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.*;
import org.springframework.dao.DataIntegrityViolationException;

// Referenced classes of package gnnt.MEBS.timebargain.manage.webapp.action:
//            BaseAction, CommonDictionary

public class CustomerAction extends BaseAction
{

    public CustomerAction()
    {
    }

    public ActionForward top(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'top' method");
        getSelectAttribute(httpservletrequest);
        return actionmapping.findForward("top");
    }

    public ActionForward edit(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'edit' method");
        CustomerForm customerform = (CustomerForm)actionform;
        String s = customerform.getCrud();
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = null;
        try
        {
            getSelectAttribute(httpservletrequest);
            if(s.trim().equals("update"))
            {
                customer = customermanager.getCustomerById(customerform.getCustomerID());
                log.debug((new StringBuilder()).append("edit Customer.CustomerName:").append(customer.getCustomerName()).toString());
            } else
            if(s.trim().equals("create"))
                customer = new Customer();
            String s1 = "";
            String s3 = "";
            String s5 = "";
            if(customer != null && customer.getStatus() != null && customer.getStatus().shortValue() == 2)
            {
                String s2 = "2";
                String s6 = "2";
                String s4 = "退市";
                httpservletrequest.setAttribute("type9", s2);
                httpservletrequest.setAttribute("status", s6);
                httpservletrequest.setAttribute("name", s4);
            } else
            {
                httpservletrequest.setAttribute("type9", "");
                httpservletrequest.setAttribute("status", "");
                httpservletrequest.setAttribute("name", "");
            }
            customerform = (CustomerForm)convert(customer);
            customerform.setCrud(s);
            log.debug((new StringBuilder()).append("customerForm.selectedOptionsMarket:").append(customerform.getSelectedOptionsMarket()).toString());
            updateFormBean(actionmapping, httpservletrequest, customerform);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            log.error((new StringBuilder()).append("==err:").append(exception).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
            return search(actionmapping, actionform, httpservletrequest, httpservletresponse);
        }
        return actionmapping.findForward("edit");
    }

    public ActionForward save(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'save' method");
        CustomerForm customerform = (CustomerForm)actionform;
        String s = customerform.getCrud();
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = (Customer)convert(customerform);
        try
        {
            if(s.trim().equals("create"))
            {
                customer.setPassword(StringUtil.encodePassword(customerform.getPassword(), "MD5"));
                if(httpservletrequest.getParameter("mkName") == null)
                {
                    customermanager.insertCustomer(customer);
                    addSysLog(httpservletrequest, (new StringBuilder()).append("增加交易客户[").append(customer.getCustomerID()).append("]").toString());
                }
            } else
            if(s.trim().equals("update"))
            {
                customermanager.updateCustomer(customer);
                addSysLog(httpservletrequest, (new StringBuilder()).append("修改交易客户[").append(customer.getCustomerID()).append("]").toString());
            }
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("===>save err：").append(exception).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("save");
    }

    public ActionForward delete(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'delete' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        String as[] = httpservletrequest.getParameterValues("itemlist");
        int i = 0;
        if(as != null)
        {
            log.debug((new StringBuilder()).append("==ids.length:").append(as.length).toString());
            String s1 = "";
            for(int j = 0; j < as.length; j++)
            {
                String s = as[j];
                try
                {
                    customermanager.deleteCustomerById(s);
                    addSysLog(httpservletrequest, (new StringBuilder()).append("删除交易客户[").append(s).append("]").toString());
                    i++;
                    continue;
                }
                catch(DataIntegrityViolationException dataintegrityviolationexception)
                {
                    dataintegrityviolationexception.printStackTrace();
                    s1 = (new StringBuilder()).append(s1).append(s).append(",").toString();
                    continue;
                }
                catch(RuntimeException runtimeexception)
                {
                    runtimeexception.printStackTrace();
                }
                s1 = (new StringBuilder()).append(s1).append(s).append(",").toString();
            }

            if(!s1.equals(""))
            {
                s1 = s1.substring(0, s1.length() - 1);
                s1 = (new StringBuilder()).append(s1).append("交易客户未退市或与其他数据关联，不能删除！").toString();
            }
            s1 = (new StringBuilder()).append(s1).append("成功删除").append(i).append("条纪录！").toString();
            httpservletrequest.setAttribute("prompt", s1);
        }
        return actionmapping.findForward("chgGroup");
    }

    public ActionForward updateStatusF(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        String s = httpservletrequest.getParameter("crud");
        String as[] = httpservletrequest.getParameterValues("itemlist");
        if(as != null)
        {
            log.debug((new StringBuilder()).append("==ids.length:").append(as.length).toString());
            Customer customer = new Customer();
            int i = 0;
            String s2 = "";
            for(int j = 0; j < as.length;)
            {
                String s1 = as[j];
                try
                {
                    customer.setFirmID(s1);
                    if("correct".equals(s))
                        customer.setStatus(Short.valueOf(Short.parseShort("0")));
                    else
                    if("incorrect".equals(s))
                        customer.setStatus(Short.valueOf(Short.parseShort("1")));
                    else
                    if("out".equals(s))
                        customer.setStatus(Short.valueOf(Short.parseShort("2")));
                    customermanager.updateStatusFirm(customer);
                    httpservletrequest.setAttribute("ifSave", "save");
                    addSysLog(httpservletrequest, (new StringBuilder()).append("修改状态[").append(s1).append("]").toString());
                    i++;
                    continue;
                }
                catch(DataIntegrityViolationException dataintegrityviolationexception)
                {
                    s2 = (new StringBuilder()).append(s2).append(s1).append(",").toString();
                    httpservletrequest.setAttribute("prompt", (new StringBuilder()).append("[").append(s1).append("]修改失败！").toString());
                    continue;
                }
                catch(Exception exception)
                {
                    log.debug((new StringBuilder()).append("error message: ").append(exception.getMessage()).toString());
                    httpservletrequest.setAttribute("prompt", exception.getMessage());
                    j++;
                }
            }

            if(!s2.equals(""))
            {
                s2 = s2.substring(0, s2.length() - 1);
                s2 = (new StringBuilder()).append(s2).append("修改失败！").toString();
            } else
            {
                s2 = (new StringBuilder()).append(s2).append("成功修改").append(i).append("条纪录！").toString();
            }
            httpservletrequest.setAttribute("prompt", s2);
        }
        return search(actionmapping, actionform, httpservletrequest, httpservletresponse);
    }

    public ActionForward chgGroup(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'chgGroup' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        String as[] = httpservletrequest.getParameterValues("itemlist");
        String s = httpservletrequest.getParameter("groupID");
        int i = 0;
        if(as != null)
        {
            log.debug((new StringBuilder()).append("==ids.length:").append(as.length).toString());
            String s2 = "";
            for(int j = 0; j < as.length; j++)
            {
                String s1 = as[j];
                try
                {
                    customermanager.chgGroupById(new Long(s), s1);
                    addSysLog(httpservletrequest, (new StringBuilder()).append("调整交易客户[").append(s1).append("]至组[").append(s).append("]").toString());
                    i++;
                }
                catch(RuntimeException runtimeexception)
                {
                    s2 = (new StringBuilder()).append(s2).append(s1).append(",").toString();
                }
            }

            if(!s2.equals(""))
            {
                s2 = s2.substring(0, s2.length() - 1);
                s2 = (new StringBuilder()).append(s2).append("调整失败！").toString();
            }
            s2 = (new StringBuilder()).append(s2).append("成功调整").append(i).append("条纪录！").toString();
            httpservletrequest.setAttribute("prompt", s2);
        }
        return actionmapping.findForward("chgGroup");
    }

    public ActionForward search(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'search' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        try
        {
            Customer customer = new Customer();
            customer.setCustomerID(httpservletrequest.getParameter("firmID"));
            customer.setCustomerName(httpservletrequest.getParameter("firmName"));
            List list = customermanager.getCustomers(customer);
            httpservletrequest.setAttribute("customerList", list);
            httpservletrequest.setAttribute("CUSTOMER_STATUS", CommonDictionary.CUSTOMER_STATUS);
            getSelectAttribute(httpservletrequest);
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询Customer表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("list");
    }

    public ActionForward searchGroupCustomer(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'searchGroupCustomer' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = new Customer();
        String s = httpservletrequest.getParameter("groupID");
        Long long1 = s != null && !s.equals("") ? Long.valueOf(s) : null;
        customer.setGroupID(long1);
        try
        {
            List list = customermanager.getCustomers(customer);
            httpservletrequest.setAttribute("customerList", list);
            httpservletrequest.setAttribute("CUSTOMER_STATUS", CommonDictionary.CUSTOMER_STATUS);
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询Customer表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("searchGroupCustomer");
    }

    public ActionForward back(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'back' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = new Customer();
        String s = httpservletrequest.getParameter("customerID");
        customer.setCustomerID(s);
        customer.setStatus(Short.valueOf(Short.parseShort("2")));
        try
        {
            customermanager.updateBack(customer);
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("back");
    }

    public ActionForward goback(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'goback' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = new Customer();
        String s = httpservletrequest.getParameter("customerID");
        customer.setCustomerID(s);
        customer.setStatus(Short.valueOf(Short.parseShort("0")));
        try
        {
            customermanager.updateGoBack(customer);
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询Customer表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("goback");
    }

    public ActionForward unspecified(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        return search(actionmapping, actionform, httpservletrequest, httpservletresponse);
    }

    public ActionForward searchKH(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'searchKH' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        String s = httpservletrequest.getParameter("isQry") != null ? httpservletrequest.getParameter("isQry") : "1";
        try
        {
            if(s.equals("1"))
            {
                Customer customer = new Customer();
                customer.setCustomerID(httpservletrequest.getParameter("firmID"));
                List list = customermanager.getKHCustomers(customer);
                httpservletrequest.setAttribute("customerList", list);
            }
            httpservletrequest.setAttribute("CUSTOMER_STATUS", CommonDictionary.CUSTOMER_STATUS);
            getSelectAttribute(httpservletrequest);
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询Customer表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("listKH");
    }

    public ActionForward customerQuery(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'customerQuery' method");
        CustomerForm customerform = (CustomerForm)actionform;
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = new Customer();
        try
        {
            customer.setCustomerID(customerform.getCustomerID());
            customer.setCustomerName(customerform.getCustomerName());
            List list = customermanager.customerQuery(customer);
            httpservletrequest.setAttribute("customerList", list);
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询Customer表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("list");
    }

    public ActionForward customerChg(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'customerChg' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        TradePropsManager tradepropsmanager = (TradePropsManager)getBean("tradePropsManager");
        String s = "";
        String s1 = httpservletrequest.getParameter("forwardName") != null ? httpservletrequest.getParameter("forwardName").trim() : "";
        String s2 = httpservletrequest.getParameter("customerID") != null ? httpservletrequest.getParameter("customerID").trim() : "";
        try
        {
            if(s2.contains(","))
            {
                ArrayList arraylist = new ArrayList();
                ArrayList arraylist1 = new ArrayList();
                httpservletrequest.setAttribute("customerID", s2);
                String as[] = s2.split(",");
                int i = as.length;
                int j = 0;
                do
                {
                    if(j >= i)
                        break;
                    String s3 = as[j];
                    Customer customer1 = customermanager.getCustomerById(s3);
                    if(customer1 == null || customer1.getCustomerID() == null || "".equals(customer1.getCustomerID()))
                    {
                        httpservletrequest.setAttribute("prompt", (new StringBuilder()).append("交易商代码").append(s3).append("不存在！").toString());
                        httpservletrequest.setAttribute("customerID", s);
                        break;
                    }
                    arraylist.add(customer1);
                    arraylist1.add(customermanager.getCustomerFundsById(s3));
                    s = (new StringBuilder()).append(s).append(s3).append(",").toString();
                    j++;
                } while(true);
                httpservletrequest.setAttribute("customerList", arraylist);
                httpservletrequest.setAttribute("customerFundsList", arraylist1);
            } else
            {
                httpservletrequest.setAttribute("customer", customermanager.getCustomerById(s2));
                if(httpservletrequest.getAttribute("customer") != null)
                {
                    Customer customer = (Customer)httpservletrequest.getAttribute("customer");
                    if(customer == null || customer.getCustomerID() == null || "".equals(customer.getCustomerID()))
                        httpservletrequest.setAttribute("prompt", "交易商代码不存在！");
                }
                httpservletrequest.setAttribute("customerFunds", customermanager.getCustomerFundsById(s2));
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            log.error((new StringBuilder()).append("查询Customer或CustomerFunds表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
            return actionmapping.findForward(s1);
        }
        return actionmapping.findForward(s1);
    }

    public ActionForward saveVirtualFund(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'saveVirtualFund' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        String s = httpservletrequest.getParameter("customerID");
        String s1 = httpservletrequest.getParameter("addVirtualFunds");
        if(s1 == null || s1.trim().equals(""))
            s1 = "0";
        try
        {
            customermanager.addCustomerVirtualFunds(s, Double.valueOf(s1));
            addSysLog(httpservletrequest, "02", (new StringBuilder()).append("为客户代码").append(s).append("入虚拟资金").append(s1).toString());
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("===>save err：").append(exception).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("saveVirtualFund");
    }

    public ActionForward searchVirtualFund(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'searchVirtualFund' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        try
        {
            List list = customermanager.customerQuery(null);
            httpservletrequest.setAttribute("virtualFundList", list);
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询CustomerFunds表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("listVirtualFund");
    }

    public ActionForward applyWaitList(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'applyWaitList' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = new Customer();
        try
        {
            String s = httpservletrequest.getParameter("firmID");
            String s1 = httpservletrequest.getParameter("applyID");
            String s2 = "";
            customer.setFirmID(s);
            if(s1 != null && !"".equals(s1))
                customer.setApplyID(Long.valueOf(Long.parseLong(s1)));
            List list = customermanager.getApplyWaitList(customer);
            httpservletrequest.setAttribute("applyWaitList", list);
            httpservletrequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询CustomerFunds表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("virtualFundAppWaitList");
    }

    public ActionForward applyAlreadyList(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'applyAlreadyList' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = new Customer();
        try
        {
            String s = httpservletrequest.getParameter("firmID");
            String s1 = httpservletrequest.getParameter("applyID");
            String s2 = "";
            customer.setFirmID(s);
            if(s1 != null && !"".equals(s1))
                customer.setApplyID(Long.valueOf(Long.parseLong(s1)));
            List list = customermanager.getApplyAlreadyList(customer);
            httpservletrequest.setAttribute("applyAlreadyList", list);
            httpservletrequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询CustomerFunds表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("virtualFundAppAlreadyList");
    }

    public ActionForward saveNewApp(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'saveNewApp' method");
        CustomerForm customerform = (CustomerForm)actionform;
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = new Customer();
        BeanUtils.copyProperties(customer, customerform);
        try
        {
            String s = AclCtrl.getLogonID(httpservletrequest);
            if(s != null && !"".equals(s))
                customer.setCreator(s);
            customermanager.insertNewApp(customer);
            addSysLog(httpservletrequest, "增加虚拟资金申请！");
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询virtualfundsapply表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("saveNewApp");
    }

    public ActionForward applyWaitCheList(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'applyWaitCheList' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = new Customer();
        String s = (String)httpservletrequest.getAttribute("save");
        try
        {
            if("save".equals(s))
            {
                List list = customermanager.getApplyWaitList(customer);
                httpservletrequest.setAttribute("applyWaitCheList", list);
                httpservletrequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
            } else
            {
                String s1 = httpservletrequest.getParameter("firmID");
                String s2 = httpservletrequest.getParameter("applyID");
                customer.setFirmID(s1);
                if(s2 != null && !"".equals(s2))
                    customer.setApplyID(Long.valueOf(Long.parseLong(s2)));
                List list1 = customermanager.getApplyWaitList(customer);
                httpservletrequest.setAttribute("applyWaitCheList", list1);
                httpservletrequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
            }
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询CustomerFunds表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("virtualFundApplyWaitCheList");
    }

    public ActionForward applyCheAlreadyList(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'applyCheAlreadyList' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = new Customer();
        try
        {
            String s = httpservletrequest.getParameter("firmID");
            String s1 = httpservletrequest.getParameter("applyID");
            customer.setFirmID(s);
            if(s1 != null && !"".equals(s1))
                customer.setApplyID(Long.valueOf(Long.parseLong(s1)));
            List list = customermanager.getApplyAlreadyList(customer);
            httpservletrequest.setAttribute("applyCheAlreadyList", list);
            httpservletrequest.setAttribute("PRESENTSTATUS", CommonDictionary.PRESENTSTATUS);
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询CustomerFunds表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("virtualFundApplyCheAlreadyList");
    }

    public ActionForward waitSuccessCheck(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'waitSuccessCheck' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = new Customer();
        try
        {
            String s = httpservletrequest.getParameter("type");
            if("1".equals(s))
                customer.setStatus(Short.valueOf(Short.parseShort("2")));
            else
            if("2".equals(s))
                customer.setStatus(Short.valueOf(Short.parseShort("3")));
            String s1 = httpservletrequest.getParameter("remark2");
            String s2 = httpservletrequest.getParameter("applyID");
            if(s2 != null && !"".equals(s2))
                customer.setApplyID(Long.valueOf(Long.parseLong(s2)));
            customer.setRemark2(s1);
            String s3 = AclCtrl.getLogonID(httpservletrequest);
            customer.setCreator(s3);
            String s4 = httpservletrequest.getParameter("virtualFunds");
            String s5 = httpservletrequest.getParameter("firmID");
            Double double1 = null;
            if(s4 != null && !"".equals(s4))
                double1 = Double.valueOf(Double.parseDouble(s4));
            else
                double1 = Double.valueOf(Double.parseDouble("0"));
            if("1".equals(s))
                try
                {
                    customermanager.addCustomerVirtualFunds(s5, double1);
                    httpservletrequest.setAttribute("prompt", "追加虚拟资金成功！");
                    httpservletrequest.setAttribute("save", "save");
                }
                catch(Exception exception1)
                {
                    throw new RuntimeException("追加虚拟资金失败！");
                }
            try
            {
                customermanager.CheckVirtual(customer);
                httpservletrequest.setAttribute("prompt", "审核成功！");
                httpservletrequest.setAttribute("save", "save");
            }
            catch(Exception exception2)
            {
                throw new RuntimeException("审核失败！");
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            log.error((new StringBuilder()).append("查询CustomerFunds表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return applyWaitCheList(actionmapping, actionform, httpservletrequest, httpservletresponse);
    }

    public ActionForward firmPrivilege(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'firmPrivilege' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = new Customer();
        try
        {
            String s = (String)httpservletrequest.getAttribute("save");
            if("save".equals(s))
            {
                String s1 = (String)httpservletrequest.getAttribute("firmID");
                if(s1 != null && !"".equals(s1))
                    customer.setFirmID(s1);
                List list = customermanager.getFirmPrivilege(customer);
                httpservletrequest.setAttribute("privilegeList", list);
                httpservletrequest.setAttribute("firmID", s1);
            } else
            {
                String s2 = httpservletrequest.getParameter("firmID");
                if(s2 != null && !"".equals(s2))
                    customer.setFirmID(s2);
                List list1 = customermanager.getFirmPrivilege(customer);
                httpservletrequest.setAttribute("privilegeList", list1);
                httpservletrequest.setAttribute("firmID", s2);
            }
            httpservletrequest.setAttribute("FIRMPRIVILEGE_B", CommonDictionary.FIRMPRIVILEGE_B);
            httpservletrequest.setAttribute("FIRMPRIVILEGE_S", CommonDictionary.FIRMPRIVILEGE_S);
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询TradePrivilege表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("firmPrivilegeList");
    }

    public ActionForward updateFirmPrivilege(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'updateFirmPrivilege' method");
        CustomerForm customerform = (CustomerForm)actionform;
        String s = httpservletrequest.getParameter("id");
        String s1 = httpservletrequest.getParameter("crud");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = null;
        if("update".equals(s1))
        {
            Customer customer1 = new Customer();
            if(s != null && !"".equals(s))
                customer1.setId(Short.valueOf(Short.parseShort(s)));
            customer = customermanager.getFirmPrivilegeById(customer1);
        } else
        if("create".equals(s1))
        {
            customer = new Customer();
            String s2 = httpservletrequest.getParameter("firmID");
            customer.setTypeID(s2);
        }
        BeanUtils.copyProperties(customerform, customer);
        String s3 = "";
        if(customer.getKind() != null)
        {
            if("1".equals(customer.getKind().toString()))
            {
                s3 = "1";
                customerform.setKindID(customer.getKindID());
            }
            if("2".equals(customer.getKind().toString()))
            {
                s3 = "2";
                customerform.setCommodityID(customer.getKindID());
            }
            customerform.setKind(customer.getKind().toString());
            if("".equals(customer.getKind().toString()))
                s3 = " ";
        }
        httpservletrequest.setAttribute("type", s3);
        customerform.setCrud(s1);
        updateFormBean(actionmapping, httpservletrequest, customerform);
        return actionmapping.findForward("updateFirmPrivilege");
    }

    public ActionForward saveFirmPrivilege(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'saveFirmPrivilege' method");
        CustomerForm customerform = (CustomerForm)actionform;
        String s = customerform.getCrud();
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = (Customer)convert(customerform);
        if(customerform.getKind() != null && !"".equals(customerform.getKind()))
        {
            if("1".equals(customerform.getKind()))
                customer.setKindID(customerform.getKindID());
            if("2".equals(customerform.getKind()))
                customer.setKindID(customerform.getCommodityID());
        }
        try
        {
            if(s.trim().equals("create"))
            {
                customermanager.insertFirmPrivilege(customer);
                httpservletrequest.setAttribute("prompt", "操作成功");
                addSysLog(httpservletrequest, (new StringBuilder()).append("增加交易商权限[").append(customer.getTypeID()).append("]").toString());
            } else
            if(s.trim().equals("update"))
            {
                customermanager.updateFirmPrivilege(customer);
                httpservletrequest.setAttribute("prompt", "操作成功");
                addSysLog(httpservletrequest, (new StringBuilder()).append("修改交易商权限[").append(customer.getTypeID()).append("]").toString());
            }
            httpservletrequest.setAttribute("save", "save");
            httpservletrequest.setAttribute("firmID", customer.getTypeID());
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            log.error((new StringBuilder()).append("===>save err：").append(exception).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("saveFirmPrivilege");
    }

    public ActionForward batchSetSaveFirmPrivilege(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'batchSstSaveFirmPrivilege' method");
        String s = httpservletrequest.getParameter("Code") != null ? httpservletrequest.getParameter("Code").trim() : "";
        String s1 = httpservletrequest.getParameter("codes") != null ? httpservletrequest.getParameter("codes").trim() : "";
        String s2 = httpservletrequest.getParameter("goods") != null ? httpservletrequest.getParameter("goods").trim() : "";
        Object obj = null;
        Object obj1 = null;
        String s3 = "";
        if("1".equals(s2))
        {
            String as[] = (String[])(httpservletrequest.getParameterValues("breed").length != 0 ? httpservletrequest.getParameterValues("breed") : "");
            for(int i = 0; i < as.length; i++)
                if(i == as.length)
                    s3 = (new StringBuilder()).append(s3).append(as[i]).toString();
                else
                    s3 = (new StringBuilder()).append(s3).append(as[i]).append(",").toString();

        } else
        {
            String as1[] = (String[])(httpservletrequest.getParameterValues("commodity").length != 0 ? httpservletrequest.getParameterValues("commodity") : "");
            for(int j = 0; j < as1.length; j++)
                if(j == as1.length)
                    s3 = (new StringBuilder()).append(s3).append(as1[j]).toString();
                else
                    s3 = (new StringBuilder()).append(s3).append(as1[j]).append(",").toString();

        }
        int k = Integer.parseInt(s);
        int l = Integer.parseInt(s2);
        CustomerForm customerform = (CustomerForm)actionform;
        String s4 = customerform.getCrud();
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        if(s4.trim().equals("save"))
        {
            int i1 = Integer.parseInt(customerform.getPrivilegeCode_B());
            int j1 = Integer.parseInt(customerform.getPrivilegeCode_S());
            customermanager.batchEmptyDeleteFirmPrivilege(k, s1, l, s3);
            customermanager.batchSetInsertFirmPrivilege(k, s1, l, s3, i1, j1);
        } else
        if(s4.trim().equals("empty"))
            customermanager.batchEmptyDeleteFirmPrivilege(k, s1, l, s3);
        httpservletrequest.setAttribute("prompt", "操作成功");
        addSysLog(httpservletrequest, (new StringBuilder()).append("批量").append(s4).append("交易商权限[").append("typeIdString").append("]").toString());
        return actionmapping.findForward("batchSstSaveFirmPrivilege");
    }

    public ActionForward deleteFirmPrivilege(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'deleteFirmPrivilege' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        String as[] = httpservletrequest.getParameterValues("itemlist");
        int i = 0;
        if(as != null)
        {
            log.debug((new StringBuilder()).append("==ids.length:").append(as.length).toString());
            String s1 = "";
            for(int j = 0; j < as.length; j++)
            {
                String s = as[j];
                try
                {
                    customermanager.deleteFirmPrivilegeById(s);
                    addSysLog(httpservletrequest, (new StringBuilder()).append("删除交易商权限[").append(s).append("]").toString());
                    i++;
                    continue;
                }
                catch(DataIntegrityViolationException dataintegrityviolationexception)
                {
                    dataintegrityviolationexception.printStackTrace();
                    s1 = (new StringBuilder()).append(s1).append(s).append(",").toString();
                    continue;
                }
                catch(RuntimeException runtimeexception)
                {
                    runtimeexception.printStackTrace();
                }
                s1 = (new StringBuilder()).append(s1).append(s).append(",").toString();
            }

            s1 = (new StringBuilder()).append(s1).append("成功删除").append(i).append("条纪录！").toString();
            httpservletrequest.setAttribute("prompt", s1);
            String s2 = httpservletrequest.getParameter("firmID");
            httpservletrequest.setAttribute("save", "save");
            httpservletrequest.setAttribute("firmID", s2);
        }
        return firmPrivilege(actionmapping, actionform, httpservletrequest, httpservletresponse);
    }

    public ActionForward typePrivilegeList(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'typePrivilegeList' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = new Customer();
        try
        {
            String s = httpservletrequest.getParameter("kindID");
            String s1 = httpservletrequest.getParameter("typeID");
            customer.setKindID(s);
            customer.setTypeID(s1);
            List list = customermanager.getTypePrivilege(customer);
            httpservletrequest.setAttribute("typePrivilegeList", list);
            httpservletrequest.setAttribute("FIRMPRIVILEGE_B", CommonDictionary.FIRMPRIVILEGE_B);
            httpservletrequest.setAttribute("FIRMPRIVILEGE_S", CommonDictionary.FIRMPRIVILEGE_S);
            httpservletrequest.setAttribute("KIND", CommonDictionary.KIND);
            httpservletrequest.setAttribute("TYPE", CommonDictionary.TYPE);
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询TradePrivilege表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("typePrivilegeList");
    }

    private void getSelectAttribute(HttpServletRequest httpservletrequest)
        throws Exception
    {
        LookupManager lookupmanager = (LookupManager)getBean("lookupManager");
        httpservletrequest.setAttribute("commoditySelect", lookupmanager.getSelectLabelValueByTable("T_commodity", "commodityID", "commodityID", " order by commodityID "));
    }

    private void getSelectAttributeBreed(HttpServletRequest httpservletrequest)
        throws Exception
    {
        LookupManager lookupmanager = (LookupManager)getBean("lookupManager");
    }

    private void getMarketSelectAttribute(HttpServletRequest httpservletrequest)
        throws Exception
    {
        LookupManager lookupmanager = (LookupManager)getBean("lookupManager");
        httpservletrequest.setAttribute("marketSelect", lookupmanager.getSelectLabelValueByTable("Market", "MarketName", "MarketCode", " where Status=1 order by MarketCode"));
    }
}
