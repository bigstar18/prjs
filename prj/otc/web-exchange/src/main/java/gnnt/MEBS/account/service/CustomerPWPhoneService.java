package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.CustomerDao;
import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("customerPWPhoneService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CustomerPWPhoneService
  extends BaseService<Customer>
{
  private final transient Log logger = LogFactory.getLog(CustomerPWPhoneService.class);
  @Autowired
  @Qualifier("customerDao")
  private CustomerDao customerDao;
  
  public BaseDao getDao()
  {
    return this.customerDao;
  }
  
  public int update(Customer obj)
  {
    int num = 0;
    Customer objFor = (Customer)copyObject(obj);
    objFor.setPhonePWD(MD5.getMD5(obj.getId(), obj.getPhonePWD()));
    this.logger.debug("phonepwd:" + objFor.getPhonePWD());
    getDao().update(objFor);
    num = 1;
    return num;
  }
}
