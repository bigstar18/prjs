package gnnt.MEBS.query.service;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.query.dao.LogCataLogSearchDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("logCataLogSearchService")
public class LogCataLogSearchService
  extends BaseService
{
  @Autowired
  @Qualifier("logCataLogSearchDao")
  private LogCataLogSearchDao logCataLogSearchDao;
  
  public BaseDao getDao()
  {
    return this.logCataLogSearchDao;
  }
}
