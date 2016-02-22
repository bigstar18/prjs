package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.NetCustomerInZSDao;
import gnnt.MEBS.account.model.NetCustomerInZS;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("netCustomerInZSService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class NetCustomerInZSService
  extends BaseService<NetCustomerInZS>
{
  private final transient Log logger = LogFactory.getLog(NetCustomerInZSService.class);
  @Autowired
  @Qualifier("netCustomerInZSDao")
  private NetCustomerInZSDao netCustomerInZSDao;
  
  public BaseDao getDao()
  {
    return this.netCustomerInZSDao;
  }
}
