package gnnt.MEBS.settlement.service;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.settlement.dao.ClearStatusDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("clearStatusService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ClearStatusService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(ClearStatusService.class);
  @Autowired
  @Qualifier("clearStatusDao")
  private ClearStatusDao clearStatusDao;
  
  public BaseDao getDao()
  {
    return this.clearStatusDao;
  }
}
