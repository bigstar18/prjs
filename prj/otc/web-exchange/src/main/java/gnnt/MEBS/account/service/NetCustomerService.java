package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.CustomerInfoProDao;
import gnnt.MEBS.account.dao.FirmProDao;
import gnnt.MEBS.account.dao.MemberInfoDao;
import gnnt.MEBS.account.dao.NetCustomerDao;
import gnnt.MEBS.account.model.NetCustomer;
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

@Service("netCustomerService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class NetCustomerService
  extends BaseService<NetCustomer>
{
  private final transient Log logger = LogFactory.getLog(NetCustomerService.class);
  @Autowired
  @Qualifier("netCustomerDao")
  private NetCustomerDao netCustomerDao;
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
    return this.netCustomerDao;
  }
}
