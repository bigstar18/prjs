package gnnt.MEBS.broke.service;

import gnnt.MEBS.account.dao.CustomerDao;
import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.broke.dao.CustomerMappingDao;
import gnnt.MEBS.broke.model.CustomerMappingBroker;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("customerMappingService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CustomerMappingService
  extends BaseService<CustomerMappingBroker>
{
  private final transient Log logger = LogFactory.getLog(CustomerMappingService.class);
  @Autowired
  @Qualifier("customerMappingDao")
  private CustomerMappingDao customerMappingDao;
  @Autowired
  @Qualifier("customerDao")
  private CustomerDao customerDao;
  
  public BaseDao getDao()
  {
    return this.customerMappingDao;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public CustomerMappingBroker get(CustomerMappingBroker clone)
  {
    this.logger.debug("enter get");
    Serializable id = clone.getId();
    CustomerMappingBroker customerMappingBroker = null;
    if (id == null)
    {
      CustomerMappingBroker cloneCopy = (CustomerMappingBroker)clone.clone();
      customerMappingBroker = (CustomerMappingBroker)getDao().get(cloneCopy);
    }
    else
    {
      customerMappingBroker = (CustomerMappingBroker)getDao().getById(id);
    }
    String customerNo = clone.getCustomerNo();
    Customer customer = (Customer)this.customerDao.getById(customerNo);
    if (customerMappingBroker != null) {
      customerMappingBroker.setMemberNo(customer.getMemberNo());
    }
    this.logger.debug("memberNo:" + customer.getMemberNo());
    return customerMappingBroker;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public CustomerMappingBroker getById(Serializable id)
  {
    this.logger.debug("enter getById");
    CustomerMappingBroker customerMappingBroker = null;
    customerMappingBroker = (CustomerMappingBroker)getDao().getById(id);
    if (customerMappingBroker != null)
    {
      String customerNo = customerMappingBroker.getCustomerNo();
      Customer customer = (Customer)this.customerDao.getById(customerNo);
      customerMappingBroker.setMemberNo(customer.getMemberNo());
    }
    return customerMappingBroker;
  }
}
