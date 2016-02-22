package gnnt.MEBS.common.service;

import gnnt.MEBS.common.dao.CustomerRelateOrgDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("customerRelateOrgService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CustomerRelateOrgService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(CustomerRelateOrgService.class);
  @Autowired
  @Qualifier("customerRelateOrgDao")
  private CustomerRelateOrgDao customerRelateOrgDao;
  
  public BaseDao getDao()
  {
    return this.customerRelateOrgDao;
  }
}
