package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.VCustomercloseSimpleDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("vCustomercloseSimpleService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class VCustomercloseSimpleService
  extends BaseService
{
  @Autowired
  @Qualifier("vCustomercloseSimpleDao")
  private VCustomercloseSimpleDao vCustomercloseSimpleDao;
  
  public BaseDao getDao()
  {
    return this.vCustomercloseSimpleDao;
  }
}
