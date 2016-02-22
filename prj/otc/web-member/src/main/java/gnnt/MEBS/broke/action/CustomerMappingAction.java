package gnnt.MEBS.broke.action;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.service.CustomerService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.broke.model.CustomerMappingBroker;
import gnnt.MEBS.broke.service.CustomerMappingService;
import gnnt.MEBS.broke.service.OrganizationService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class CustomerMappingAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CustomerMappingAction.class);
  @Autowired
  @Qualifier("customerService")
  private CustomerService customerService;
  @Autowired
  @Qualifier("organizationService")
  private OrganizationService organizationService;
  @Autowired
  @Qualifier("customerMappingService")
  private CustomerMappingService customerMappingService;
  
  public InService getService()
  {
    return this.customerMappingService;
  }
  
  public String list()
  {
    this.logger.debug("enter list");
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "primary.id";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "false";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    Customer customer = (Customer)this.customerService.getList(qc, pageInfo).get(0);
    this.obj = this.customerMappingService.getById(this.request.getParameter("primary.customerNo"));
    if (this.obj == null)
    {
      this.obj = new CustomerMappingBroker();
      ((CustomerMappingBroker)this.obj).setCustomerNo(this.request.getParameter("primary.customerNo"));
    }
    this.logger.debug(Boolean.valueOf(((CustomerMappingBroker)this.obj).getOrganizationNo() == null));
    this.resultList = this.organizationService.getList(new QueryConditions("primary.memberNo", "=", customer.getMemberNo()), pageInfo);
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    this.logger.debug("resultList.size:" + this.resultList.size());
    returnBaseMsg(pageInfo);
    return getReturnValue();
  }
  
  public String update()
  {
    int resultValue = 0;
    if (this.customerMappingService.get((CustomerMappingBroker)this.obj) == null) {
      resultValue = this.customerMappingService.add((CustomerMappingBroker)this.obj);
    } else {
      resultValue = this.customerMappingService.update((CustomerMappingBroker)this.obj);
    }
    addResultMsg(this.request, resultValue);
    this.logger.debug("resultValue:" + resultValue);
    return getReturnValue();
  }
}
