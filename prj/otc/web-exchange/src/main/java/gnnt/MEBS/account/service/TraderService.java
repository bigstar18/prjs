package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.TraderDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("traderService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class TraderService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(TraderService.class);
  @Autowired
  @Qualifier("traderDao")
  private TraderDao traderDao;
  
  public BaseDao getDao()
  {
    return this.traderDao;
  }
}
