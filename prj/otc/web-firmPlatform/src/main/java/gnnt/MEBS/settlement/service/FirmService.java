package gnnt.MEBS.settlement.service;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.settlement.dao.FirmDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("firmService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class FirmService
  extends BaseService
{
  @Autowired
  @Qualifier("firmDao")
  private FirmDao firmDao;
  
  public BaseDao getDao()
  {
    return this.firmDao;
  }
}
