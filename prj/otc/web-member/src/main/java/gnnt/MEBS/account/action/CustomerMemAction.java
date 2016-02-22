package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.model.CustomerVO;
import gnnt.MEBS.account.service.CustomerService;
import gnnt.MEBS.account.service.CustomerVOService;
import gnnt.MEBS.base.copy.CopyObjectParamUtil;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.broke.model.CustomerMappingBroker;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.broke.service.OrganizationService;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.settlement.model.Firm;
import gnnt.MEBS.settlement.service.FirmService;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("customerAction")
@Scope("request")
public class CustomerMemAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CustomerAction.class);
  @Autowired
  @Qualifier("customerService")
  private CustomerService customerService;
  @Autowired
  @Qualifier("firmService")
  private FirmService firmService;
  @Autowired
  @Qualifier("customerVOService")
  private CustomerVOService customerVOService;
  @Autowired
  @Qualifier("organizationService")
  public OrganizationService organizationService;
  @Resource(name="papersTypeUpdateMap")
  private Map papersTypeUpdateMap;
  @Resource(name="papersTypeMap")
  private Map papersTypeMap;
  @Resource(name="firmStatusMap")
  private Map firmStatusMap;
  
  public InService getService()
  {
    return this.customerService;
  }
  
  public Map getPapersTypeUpdateMap()
  {
    return this.papersTypeUpdateMap;
  }
  
  public Map getPapersTypeMap()
  {
    return this.papersTypeMap;
  }
  
  public FirmService getFirmService()
  {
    return this.firmService;
  }
  
  public Map getFirmStatusMap()
  {
    return this.firmStatusMap;
  }
  
  public String add()
  {
    this.logger.debug("enter add");
    CustomerVO customerVO = (CustomerVO)this.obj;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.papersType", "=", customerVO.getPapersType());
    qc.addCondition("primary.papersName", "=", customerVO.getPapersName());
    qc.addCondition("primary.memberNo", "=", customerVO.getMemberNo());
    List<Customer> list = this.customerService.getList(qc, null);
    System.out.println("list:" + list.size());
    if ((list != null) && (list.size() > 0))
    {
      this.request.setAttribute(ActionConstant.RESULTMSG, "证件号码和证件类型在此会员下已存在，请重新添加！");
      this.request.setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(-1));
    }
    else
    {
      String organizationNoString = this.request.getParameter("objSpecial.organizationNo");
      String brokerageNO = this.request.getParameter("objSpecial.brokerageNo");
      customerVO.setOrganizationNo(organizationNoString);
      customerVO.setBrokerageNo(brokerageNO);
      customerVO.setCreateTime(new Date());
      System.out.println("customerVO.phonePassword:" + customerVO.getPhonePWD());
      System.out.println("customerVO.password:" + customerVO.getPassword());
      Customer customer = new Customer();
      CopyObjectParamUtil.bindData(customerVO, customer);
      CustomerMappingBroker customerMappingBroker = new CustomerMappingBroker();
      CopyObjectParamUtil.bindData(customerVO, customerMappingBroker);
      customerMappingBroker.setCustomerNo(customer.getCustomerNo());
      int resultValue = this.customerService.add(customer, customerMappingBroker);
      addResultMsg(this.request, resultValue);
    }
    return getReturnValue();
  }
  
  public String viewById()
  {
    CustomerVO customerVO = (CustomerVO)this.obj;
    customerVO = (CustomerVO)this.customerVOService.get(customerVO);
    this.obj = customerVO;
    return getReturnValue();
  }
  
  public String update()
  {
    CustomerVO customerVO = (CustomerVO)this.obj;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.papersType", "=", customerVO.getPapersType());
    qc.addCondition("primary.papersName", "=", customerVO.getPapersName());
    qc.addCondition("primary.memberNo", "=", customerVO.getMemberNo());
    qc.addCondition("primary.customerNo", "!=", customerVO.getCustomerNo());
    List<Customer> list = this.customerService.getList(qc, null);
    System.out.println(customerVO.getPapersType() + "   " + "   " + customerVO.getPapersName() + customerVO.getMemberNo());
    System.out.println(list.size());
    if ((list != null) && (list.size() > 0))
    {
      this.request.setAttribute(ActionConstant.RESULTMSG, "证件号码和证件类型在此会员下已存在，请重新修改！");
      this.request.setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(-1));
    }
    else
    {
      String organizationNoString = this.request.getParameter("objSpecial.organizationNo");
      String brokerageNO = this.request.getParameter("objSpecial.brokerageNo");
      customerVO.setOrganizationNo(organizationNoString);
      customerVO.setBrokerageNo(brokerageNO);
      Customer customer = new Customer();
      CopyObjectParamUtil.bindData(customerVO, customer);
      CustomerMappingBroker customerMappingBroker = new CustomerMappingBroker();
      CopyObjectParamUtil.bindData(customerVO, customerMappingBroker);
      Firm firm = (Firm)this.firmService.getById(customer.getCustomerNo());
      customerMappingBroker.setCustomerNo(customer.getCustomerNo());
      int resultValue = this.customerService.update(customer, customerMappingBroker);
      if (resultValue > 0)
      {
        firm.setFirmName(customer.getName());
        resultValue = this.firmService.update(firm);
      }
      addResultMsg(this.request, resultValue);
    }
    return getReturnValue();
  }
  
  public void init()
  {
    this.logger.debug("enter init");
    this.logger.debug("this.name:" + getClass().getName());
    if (getService() != null)
    {
      this.classType = CustomerVO.class;
      this.logger.debug("class.name:" + this.classType.getName());
      try
      {
        this.obj = ((Clone)this.classType.newInstance());
      }
      catch (InstantiationException e)
      {
        e.printStackTrace();
      }
      catch (IllegalAccessException e)
      {
        e.printStackTrace();
      }
      this.logger.debug("end if");
    }
  }
  
  public String forwardAdd()
  {
    String organizationNO = (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID);
    if ((organizationNO != null) && (!"".equals(organizationNO)))
    {
      Organization organization = (Organization)this.organizationService.getById(organizationNO);
      
      this.request.setAttribute("organization", organization);
    }
    this.request.setAttribute("parentOrgNo", organizationNO);
    return getReturnValue();
  }
}
