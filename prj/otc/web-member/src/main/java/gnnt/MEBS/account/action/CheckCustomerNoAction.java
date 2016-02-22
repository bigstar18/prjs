package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.model.CustomerStatus;
import gnnt.MEBS.account.service.CustomerService;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.service.CustomerHoldQtyService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class CheckCustomerNoAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CheckCustomerNoAction.class);
  @Autowired
  @Qualifier("customerService")
  private CustomerService customerService;
  @Autowired
  @Qualifier("customerHoldQtyService")
  private CustomerHoldQtyService customerHoldQtyService;
  
  public InService getService()
  {
    return this.customerService;
  }
  
  public int checkCustomerNo(String memberNo, String organizationNo, String customerNo)
  {
    int result = 1;
    Customer customer = (Customer)this.customerService.getById(customerNo);
    if ((customer != null) && (customer.getMemberNo().equals(memberNo)))
    {
      QueryConditions qc = new QueryConditions("customerNo", "=", customerNo);
      if ((organizationNo != null) && (!"".equals(organizationNo)))
      {
        String customerNos = "(";
        QueryConditions qConditions = new QueryConditions();
        qConditions.addCondition("primary.organizationNo", "=", organizationNo);
        customerNos = customerNos + "select view.customerNo from gnnt.MEBS.common.model.CustomerRelateOrganization  view where view.organizationNo='" + organizationNo + "')";
        this.logger.debug("organizationNOs:" + customerNos);
        qc.addCondition("customerNo", "in", customerNos);
      }
      List<Customer> list = this.customerService.getList(qc, null);
      if (list.size() == 0) {
        result = -3;
      }
      if (result == 1) {
        if (customer.getCustomerStatus().getStatus().equals("F")) {
          result = 1;
        } else if (customer.getCustomerStatus().getStatus().equals("N")) {
          result = 1;
        } else {
          result = -2;
        }
      }
    }
    else
    {
      result = -1;
    }
    return result;
  }
}
