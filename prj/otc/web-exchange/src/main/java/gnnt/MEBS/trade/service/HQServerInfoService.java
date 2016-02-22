package gnnt.MEBS.trade.service;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.trade.dao.HQServerInfoDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("hqServerInfoService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class HQServerInfoService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(HQServerInfoService.class);
  @Autowired
  @Qualifier("hqServerInfoDao")
  private HQServerInfoDao hqServerInfoDao;
  
  public BaseDao getDao()
  {
    return this.hqServerInfoDao;
  }
}
