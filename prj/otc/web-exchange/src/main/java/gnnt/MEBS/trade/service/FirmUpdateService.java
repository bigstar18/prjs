package gnnt.MEBS.trade.service;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.trade.dao.FirmUpdateDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("firmUpdateService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class FirmUpdateService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(FirmUpdateService.class);
  @Autowired
  @Qualifier("firmUpdateDao")
  private FirmUpdateDao firmUpdateDao;
  
  public BaseDao getDao()
  {
    return this.firmUpdateDao;
  }
}
