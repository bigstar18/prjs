package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.WxCustomerDao;
import gnnt.MEBS.account.model.WxCustomer;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("wxCustomerService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class WxCustomerService
  extends BaseService<WxCustomer>
{
  private final transient Log logger = LogFactory.getLog(WxCustomerService.class);
  @Autowired
  @Qualifier("wxCustomerDao")
  private WxCustomerDao wxCustomerDao;
  
  public BaseDao getDao()
  {
    return this.wxCustomerDao;
  }
}
