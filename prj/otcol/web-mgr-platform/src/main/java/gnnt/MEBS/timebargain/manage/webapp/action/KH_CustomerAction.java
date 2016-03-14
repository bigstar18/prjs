// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 

package gnnt.MEBS.timebargain.manage.webapp.action;

import gnnt.MEBS.timebargain.manage.model.Customer;
import gnnt.MEBS.timebargain.manage.service.CustomerManager;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import gnnt.MEBS.timebargain.manage.webapp.form.CustomerForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.*;
import org.springframework.dao.DataIntegrityViolationException;

// Referenced classes of package gnnt.MEBS.timebargain.manage.webapp.action:
//            BaseAction, CommonDictionary

public class KH_CustomerAction extends BaseAction
{

    public KH_CustomerAction()
    {
    }

    public ActionForward unspecified(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        return search(actionmapping, actionform, httpservletrequest, httpservletresponse);
    }

    public ActionForward top(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'top' method");
        getSelectAttribute(httpservletrequest);
        return actionmapping.findForward("top");
    }

    public ActionForward top1(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'top1' method");
        getSelectAttribute(httpservletrequest);
        return actionmapping.findForward("firstTop");
    }

    public ActionForward search(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'search' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        String s = httpservletrequest.getParameter("isQry") != null ? httpservletrequest.getParameter("isQry") : "1";
        try
        {
            if(s.equals("1"))
            {
                Customer customer = new Customer();
                customer.setCustomerID(httpservletrequest.getParameter("firmID"));
                java.util.List list = customermanager.getKHCustomers(customer);
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
        return actionmapping.findForward("list");
    }

    public ActionForward searchFirst(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'searchFirst' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        String s = httpservletrequest.getParameter("isQry") != null ? httpservletrequest.getParameter("isQry") : "1";
        try
        {
            if(s.equals("1"))
            {
                Customer customer = new Customer();
                String s1 = httpservletrequest.getParameter("groupID");
                Long long1 = s1 != null && !s1.equals("") ? Long.valueOf(s1) : null;
                customer.setGroupID(long1);
                customer.setCustomerID(httpservletrequest.getParameter("firmID"));
                customer.setCustomerName(httpservletrequest.getParameter("firmName"));
                java.util.List list = customermanager.getCustomerCounts(httpservletrequest.getParameter("firmID"));
                httpservletrequest.setAttribute("firstcustomerList", list);
            }
            httpservletrequest.setAttribute("CUSTOMER_STATUS", CommonDictionary.CUSTOMER_STATUS);
            getSelectAttribute(httpservletrequest);
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询Customer表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("firstList");
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
            java.util.List list = customermanager.getKHCustomers(customer);
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

    public ActionForward searchGroupFirm(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'searchGroupFirm' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = new Customer();
        String s = httpservletrequest.getParameter("groupID");
        Long long1 = s != null && !s.equals("") ? Long.valueOf(s) : null;
        customer.setGroupID(long1);
        try
        {
            java.util.List list = customermanager.getFirmGroup(customer);
            httpservletrequest.setAttribute("customerList", list);
            httpservletrequest.setAttribute("CUSTOMER_STATUS", CommonDictionary.CUSTOMER_STATUS);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            log.error((new StringBuilder()).append("查询Customer表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("searchGroupFirm");
    }

    public ActionForward edit(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'edit' method");
        CustomerForm customerform = (CustomerForm)actionform;
        String s = customerform.getCrud();
        String s1 = customerform.getFirmID();
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Object obj = null;
        try
        {
            getSelectAttribute(httpservletrequest);
            Customer customer;
            if(!s.trim().equals("create"))
            {
                customer = customermanager.getKHCustomerById(customerform.getCustomerID());
                log.debug((new StringBuilder()).append("edit Customer.CustomerName:").append(customer.getCustomerName()).toString());
            } else
            {
                customer = new Customer();
            }
            customerform = (CustomerForm)convert(customer);
            customerform.setCrud(s);
            customerform.setFirmID(s1);
            log.debug((new StringBuilder()).append("customerForm.selectedOptionsMarket:").append(customerform.getSelectedOptionsMarket()).toString());
            updateFormBean(actionmapping, httpservletrequest, customerform);
        }
        catch(Exception exception)
        {
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
        String s1 = customerform.getFirmID();
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = (Customer)convert(customerform);
        try
        {
            String as[] = null;
            if(customer.getCode() != null && !"".equals(customer.getCode()))
                as = customer.getCode().split(",");
            int i = 0;
            int j = 0;
            if((customer.getStartCode() != null) & (!"".equals(customer.getStartCode())))
            {
                i = Integer.parseInt(customer.getStartCode());
                j = Integer.parseInt(customer.getEndCode()) + 1;
            }
            String s2 = "";
            int k = 0;
            String s4 = "";
            if(s.trim().equals("create"))
            {
                if(as != null)
                {
                    for(int l = 0; l < as.length;)
                    {
                        if(as[l].trim().length() != 2)
                        {
                            httpservletrequest.setAttribute("prompt", "后缀名格式不正确！");
                            continue;
                        }
                        Customer customer1 = new Customer();
                        customer1.setCode(as[l].trim());
                        customer1.setFirmID(s1);
                        customer1.setStatus(customer.getStatus());
                        try
                        {
                            customermanager.insertKHCustomer(customer1);
                            k++;
                            addSysLog(httpservletrequest, (new StringBuilder()).append("增加交易客户[").append(s1).append(as[l].trim()).append("]").toString());
                            continue;
                        }
                        catch(RuntimeException runtimeexception)
                        {
                            s4 = (new StringBuilder()).append(s4).append(as[l].trim()).append(",").toString();
                            runtimeexception.printStackTrace();
                            l++;
                        }
                    }

                }
                if(i != 0)
                {
                    for(int i1 = i; i1 < j; i1++)
                    {
                        String s3;
                        if(i1 < 10)
                            s3 = (new StringBuilder()).append("0").append(i1).toString();
                        else
                            s3 = (new StringBuilder()).append(i1).append("").toString();
                        Customer customer2 = new Customer();
                        customer2.setFirmID(s1);
                        customer2.setCode(s3);
                        customer2.setStatus(customer.getStatus());
                        try
                        {
                            customermanager.insertKHCustomer(customer2);
                            addSysLog(httpservletrequest, (new StringBuilder()).append("增加交易客户[").append(s1).append(s3).append("]").toString());
                            k++;
                            continue;
                        }
                        catch(RuntimeException runtimeexception1)
                        {
                            runtimeexception1.printStackTrace();
                        }
                        s4 = (new StringBuilder()).append(s4).append(s3).append(",").toString();
                    }

                }
                addSysLog(httpservletrequest, "增加交易客户后缀");
                if(!s4.equals(""))
                {
                    s4 = s4.substring(0, s4.length() - 1);
                    s4 = (new StringBuilder()).append(s4).append("后缀已存在，成功存盘").append(k).append("条交易客户！").toString();
                }
            } else
            if(s.trim().equals("update"))
            {
                customermanager.updateKHCustomer(customer);
                addSysLog(httpservletrequest, (new StringBuilder()).append("修改交易客户[").append(customer.getCustomerID()).append("]").toString());
            }
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("===>save err：").append(exception).toString());
            exception.printStackTrace();
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
        if(as != null)
        {
            String s = "";
            String as1[] = as;
            int j = as1.length;
            for(int k = 0; k < j; k++)
            {
                String s3 = as1[k];
                if("00".equals(s3.substring(s3.length() - 2, s3.length())))
                    s = (new StringBuilder()).append(s).append(s3).append(" ").toString();
            }

            if(!s.equals(""))
            {
                httpservletrequest.setAttribute("prompt", (new StringBuilder()).append(s).append("是默认客户，不能删除请重新选择！").toString());
                as = null;
            }
        }
        int i = 0;
        if(as != null)
        {
            log.debug((new StringBuilder()).append("==ids.length:").append(as.length).toString());
            String s2 = "";
            for(int l = 0; l < as.length; l++)
            {
                String s1 = as[l];
                try
                {
                    customermanager.deleteKHCustomerById(s1);
                    addSysLog(httpservletrequest, (new StringBuilder()).append("删除交易客户[").append(s1).append("]").toString());
                    i++;
                    continue;
                }
                catch(DataIntegrityViolationException dataintegrityviolationexception)
                {
                    dataintegrityviolationexception.printStackTrace();
                    s2 = (new StringBuilder()).append(s2).append(s1).append(",").toString();
                    continue;
                }
                catch(RuntimeException runtimeexception)
                {
                    runtimeexception.printStackTrace();
                }
                s2 = (new StringBuilder()).append(s2).append(s1).append(",").toString();
            }

            if(!s2.equals(""))
            {
                s2 = s2.substring(0, s2.length() - 1);
                s2 = (new StringBuilder()).append(s2).append("交易客户未退市或与其他数据关联，不能删除！").toString();
            }
            s2 = (new StringBuilder()).append(s2).append("成功删除").append(i).append("条纪录！").toString();
            httpservletrequest.setAttribute("prompt", s2);
        }
        httpservletrequest.setAttribute("customer_Type", "delete_KH");
        return actionmapping.findForward("chgGroup");
    }

    public ActionForward updateStatusKH(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        String s = httpservletrequest.getParameter("crud");
        String s1 = httpservletrequest.getParameter("firmID");
        String as[] = httpservletrequest.getParameterValues("itemlist");
        if(as != null)
        {
            log.debug((new StringBuilder()).append("==ids.length:").append(as.length).toString());
            Customer customer = new Customer();
            int i = 0;
            String s3 = "";
            for(int j = 0; j < as.length; j++)
            {
                String s2 = as[j];
                try
                {
                    customer.setCustomerID(s2);
                    if("correct".equals(s))
                        customer.setStatus(Short.valueOf(Short.parseShort("0")));
                    else
                    if("incorrect".equals(s))
                        customer.setStatus(Short.valueOf(Short.parseShort("1")));
                    customermanager.updateStatusCustomer(customer);
                    addSysLog(httpservletrequest, (new StringBuilder()).append("修改状态[").append(s2).append("]").toString());
                    i++;
                    continue;
                }
                catch(DataIntegrityViolationException dataintegrityviolationexception)
                {
                    s3 = (new StringBuilder()).append(s3).append(s2).append(",").toString();
                }
                httpservletrequest.setAttribute("prompt", (new StringBuilder()).append("[").append(s2).append("]修改失败！").toString());
            }

            if(!s3.equals(""))
            {
                s3 = s3.substring(0, s3.length() - 1);
                s3 = (new StringBuilder()).append(s3).append("修改失败！").toString();
            }
            s3 = (new StringBuilder()).append(s3).append("成功修改").append(i).append("条纪录！").toString();
            httpservletrequest.setAttribute("prompt", s3);
        }
        return actionmapping.findForward("update");
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

    public ActionForward customerPrivilege(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'customerPrivilege' method");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        Customer customer = new Customer();
        try
        {
            String s = (String)httpservletrequest.getAttribute("save");
            if("save".equals(s))
            {
                String s1 = (String)httpservletrequest.getAttribute("customerID");
                String s3 = (String)httpservletrequest.getAttribute("firmID");
                customer.setCustomerID(s1);
                java.util.List list = customermanager.getKHCustomerPrivilege(customer);
                httpservletrequest.setAttribute("customerPrivilegeList", list);
                httpservletrequest.setAttribute("customerID", s1);
                httpservletrequest.setAttribute("firmID", s3);
            } else
            {
                String s2 = httpservletrequest.getParameter("customerID");
                String s4 = httpservletrequest.getParameter("firmID");
                customer.setCustomerID(s2);
                java.util.List list1 = customermanager.getKHCustomerPrivilege(customer);
                httpservletrequest.setAttribute("customerPrivilegeList", list1);
                httpservletrequest.setAttribute("customerID", s2);
                httpservletrequest.setAttribute("firmID", s4);
            }
            httpservletrequest.setAttribute("FIRMPRIVILEGE_B", CommonDictionary.FIRMPRIVILEGE_B);
            httpservletrequest.setAttribute("FIRMPRIVILEGE_S", CommonDictionary.FIRMPRIVILEGE_S);
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("查询TradePrivilege表出错：").append(exception.getMessage()).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("customerPrivilege");
    }

    public ActionForward updateCustomerPrivilege(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'updateCustomerPrivilege' method");
        CustomerForm customerform = (CustomerForm)actionform;
        String s = httpservletrequest.getParameter("id");
        String s1 = httpservletrequest.getParameter("crud");
        CustomerManager customermanager = (CustomerManager)getBean("customerManager");
        getSelectAttribute(httpservletrequest);
        Customer customer = null;
        if("update".equals(s1))
        {
            Customer customer1 = new Customer();
            if(s != null && !"".equals(s))
                customer1.setId(Short.valueOf(Short.parseShort(s)));
            customer = customermanager.getCustomerPrivilegeById(customer1);
        } else
        if("create".equals(s1))
        {
            customer = new Customer();
            String s2 = httpservletrequest.getParameter("customerID");
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
        return actionmapping.findForward("updateCustomerPrivilege");
    }

    public ActionForward saveCustomerPrivilege(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'saveCustomerPrivilege' method");
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
                customermanager.insertCustomerPrivilege(customer);
                httpservletrequest.setAttribute("prompt", "操作成功");
                addSysLog(httpservletrequest, (new StringBuilder()).append("增加交易客户权限[").append(customer.getCustomerID()).append("]").toString());
            } else
            if(s.trim().equals("update"))
            {
                customermanager.updateCustomerPrivilege(customer);
                httpservletrequest.setAttribute("prompt", "操作成功");
                addSysLog(httpservletrequest, (new StringBuilder()).append("修改交易客户权限[").append(customer.getCustomerID()).append("]").toString());
            }
            httpservletrequest.setAttribute("save", "save");
            httpservletrequest.setAttribute("customerID", customer.getTypeID());
        }
        catch(Exception exception)
        {
            log.error((new StringBuilder()).append("===>save err：").append(exception).toString());
            httpservletrequest.setAttribute("prompt", exception.getMessage());
        }
        return actionmapping.findForward("saveCustomerPrivilege");
    }

    public ActionForward deleteCustomerPrivilege(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception
    {
        if(log.isDebugEnabled())
            log.debug("Entering 'deleteCustomerPrivilege' method");
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
                    customermanager.deleteCustomerPrivilegeById(s);
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
            String s2 = httpservletrequest.getParameter("customerID");
            String s3 = httpservletrequest.getParameter("firmID");
            httpservletrequest.setAttribute("save", "save");
            httpservletrequest.setAttribute("customerID", s2);
            httpservletrequest.setAttribute("firmID", s3);
        }
        return customerPrivilege(actionmapping, actionform, httpservletrequest, httpservletresponse);
    }

    private void getSelectAttribute(HttpServletRequest httpservletrequest)
        throws Exception
    {
        LookupManager lookupmanager = (LookupManager)getBean("lookupManager");
        httpservletrequest.setAttribute("commoditySelect", lookupmanager.getSelectLabelValueByTable("T_commodity", "commodityID", "commodityID", " order by commodityID "));
    }
}
