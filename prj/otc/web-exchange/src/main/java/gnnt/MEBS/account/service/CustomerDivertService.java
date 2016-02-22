package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.CustomerDao;
import gnnt.MEBS.account.dao.CustomerInfoProDao;
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

@Service("customerDivertService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CustomerDivertService
  extends BaseService<Customer>
{
  private final transient Log logger = LogFactory.getLog(CustomerDivertService.class);
  @Autowired
  @Qualifier("customerDao")
  private CustomerDao customerDao;
  @Autowired
  @Qualifier("customerInfoProDao")
  private CustomerInfoProDao customerInfoProDao;
  
  public BaseDao getDao()
  {
    return this.customerDao;
  }
  
  public int update(Customer obj)
  {
    int result = this.customerInfoProDao.checkCustomerDivert(obj);
    this.logger.debug("result:" + result);
    if (result > 0)
    {
      result = this.customerInfoProDao.customerDivert(obj);
      if (result > 0) {
        result = 3;
      }
    }
    return result;
  }
}
