package gnnt.MEBS.query.service;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.query.dao.SysLogSearchDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("sysLogSearchService")
public class SysLogSearchService
  extends BaseService
{
  @Autowired
  @Qualifier("sysLogSearchDao")
  private SysLogSearchDao sysLogSearchDao;
  
  public BaseDao getDao()
  {
    return this.sysLogSearchDao;
  }
}
