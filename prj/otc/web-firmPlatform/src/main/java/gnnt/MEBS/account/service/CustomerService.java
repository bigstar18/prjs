package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.CustomerDao;
import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("customerService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CustomerService
  extends BaseService<Customer>
{
  private final transient Log logger = LogFactory.getLog(CustomerService.class);
  @Autowired
  @Qualifier("customerDao")
  private CustomerDao customerDao;
  
  public BaseDao getDao()
  {
    return this.customerDao;
  }
}
