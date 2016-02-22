package gnnt.MEBS.broke.service;

import gnnt.MEBS.broke.dao.BrokerageDao;
import gnnt.MEBS.broke.dao.BrokerageProDao;
import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("brokerageChangeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BrokerageChangeService
  extends BaseService<Brokerage>
{
  private final transient Log logger = LogFactory.getLog(BrokerageChangeService.class);
  @Autowired
  @Qualifier("brokerageDao")
  private BrokerageDao brokerageDao;
  @Autowired
  @Qualifier("brokerageProDao")
  private BrokerageProDao brokerageProDao;
  
  public BaseDao getDao()
  {
    return this.brokerageDao;
  }
  
  public int update(Brokerage org)
  {
    int result = this.brokerageProDao.brokerageChangePro(org.getBrokerageNo(), org.getBrokerageNoChange());
    if (result > 0) {
      result = 3;
    }
    return result;
  }
}
