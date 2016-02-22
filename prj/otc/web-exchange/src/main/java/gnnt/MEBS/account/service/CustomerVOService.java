package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.CustomerVODao;
import gnnt.MEBS.account.model.CustomerVO;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("customerVOService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CustomerVOService
  extends BaseService<CustomerVO>
{
  private final transient Log logger = LogFactory.getLog(CustomerVOService.class);
  @Autowired
  @Qualifier("customerVODao")
  private CustomerVODao customerVODao;
  
  public BaseDao getDao()
  {
    return this.customerVODao;
  }
  
  public List<CustomerVO> getCustomerList(QueryConditions conditions, PageInfo pageInfo)
  {
    return this.customerVODao.getCustomerList(conditions, pageInfo);
  }
}
