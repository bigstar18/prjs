package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.VCustomercloseDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("vCustomercloseService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class VCustomercloseService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(VCustomercloseService.class);
  @Autowired
  @Qualifier("vCustomercloseDao")
  private VCustomercloseDao vCustomercloseDao;
  
  public BaseDao getDao()
  {
    return this.vCustomercloseDao;
  }
}
