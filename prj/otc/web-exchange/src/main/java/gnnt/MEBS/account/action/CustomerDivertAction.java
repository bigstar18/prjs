package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.service.CustomerDivertService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class CustomerDivertAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CustomerDivertAction.class);
  @Autowired
  @Qualifier("customerDivertService")
  private CustomerDivertService customerDivertService;
  
  public InService getService()
  {
    return this.customerDivertService;
  }
  
  public String viewById()
  {
    this.logger.debug("enter viewById");
    this.obj = getService().get(this.obj);
    Customer customer = (Customer)this.obj;
    customer.setMemberNoChange(customer.getMemberNo());
    this.obj = customer;
    return getReturnValue();
  }
}
