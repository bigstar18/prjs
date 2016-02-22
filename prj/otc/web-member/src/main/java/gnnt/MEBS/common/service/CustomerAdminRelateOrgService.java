package gnnt.MEBS.common.service;

import gnnt.MEBS.common.dao.CustomerAdminRelateOrgDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("customerAdminRelateOrgService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CustomerAdminRelateOrgService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(CustomerAdminRelateOrgService.class);
  @Autowired
  @Qualifier("customerAdminRelateOrgDao")
  private CustomerAdminRelateOrgDao customerAdminRelateOrgDao;
  
  public BaseDao getDao()
  {
    return this.customerAdminRelateOrgDao;
  }
}
