package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.CustomerDao;
import gnnt.MEBS.account.dao.CustomerInfoProDao;
import gnnt.MEBS.account.dao.FirmProDao;
import gnnt.MEBS.account.dao.MemberInfoDao;
import gnnt.MEBS.account.model.CompMember;
import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.broke.model.CustomerMappingBroker;
import gnnt.MEBS.broke.service.CustomerMappingService;
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
  @Autowired
  @Qualifier("firmProDao")
  private FirmProDao firmProDao;
  @Autowired
  @Qualifier("memberInfoDao")
  private MemberInfoDao memberInfoDao;
  @Autowired
  @Qualifier("customerInfoProDao")
  private CustomerInfoProDao customerInfoProDao;
  @Autowired
  @Qualifier("customerMappingService")
  private CustomerMappingService customerMappingService;
  
  public BaseDao getDao()
  {
    return this.customerDao;
  }
  
  public int add(Customer obj, CustomerMappingBroker customerMappingBroker)
  {
    this.logger.debug("enter add");
    int num = 0;
    String memberNo = obj.getMemberNo();
    MemberInfo memberInfo = (MemberInfo)this.memberInfoDao.getById(memberNo);
    if (("N".equals(memberInfo.getCompMember().getStatus())) || 
      ("F".equals(memberInfo.getCompMember().getStatus())))
    {
      this.customerDao.add(obj);
      this.customerDao.flush();
      this.customerInfoProDao.addCustomerInfoPro(obj.getCustomerNo());
      this.customerInfoProDao.customerAddToPwd(obj);
      this.customerMappingService.update(customerMappingBroker);
      num = 2;
    }
    else
    {
      num = -600;
    }
    return num;
  }
  
  public int update(Customer obj, CustomerMappingBroker customerMappingBroker)
  {
    int num = 1;
    num = this.firmProDao.firmMod(obj.getCustomerNo(), obj.getPapersType().intValue(), obj.getPapersName(), obj.getName());
    if (num > 0)
    {
      Clone objFor = copyObject(obj);
      if (objFor != null) {
        getDao().update((Customer)objFor);
      }
      this.customerMappingService.update(customerMappingBroker);
      num = 3;
    }
    return num;
  }
}
