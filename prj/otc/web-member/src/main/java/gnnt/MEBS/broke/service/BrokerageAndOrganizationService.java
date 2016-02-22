package gnnt.MEBS.broke.service;

import gnnt.MEBS.broke.dao.BrokerageAndOrganizationDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("brokerageAndOrganizationService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BrokerageAndOrganizationService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(BrokerageAndOrganizationService.class);
  @Autowired
  @Qualifier("brokerageAndOrganizationDao")
  private BrokerageAndOrganizationDao brokerageAndOrganizationDao;
  
  public BaseDao getDao()
  {
    return this.brokerageAndOrganizationDao;
  }
}
