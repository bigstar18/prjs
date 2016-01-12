package gnnt.MEBS.timebargain.mgr.service.impl;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.dao.CustomerFundsDao;
import gnnt.MEBS.timebargain.mgr.service.CustomerFundsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("customerFundsService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CustomerFundsServiceImpl extends StandardService
  implements CustomerFundsService
{

  @Autowired
  @Qualifier("customerFundsDao")
  private CustomerFundsDao customerFundsDao;

  public CustomerFundsDao getCustomerFundsDao()
  {
    return this.customerFundsDao;
  }

  public List customerFundsTable(String firmId) {
    List list = null;
    list = this.customerFundsDao.customerFundsTable(firmId);
    return list;
  }
}