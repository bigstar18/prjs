package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.PerformanceMonitorDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("performanceMonitorService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class PerformanceMonitorService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(PerformanceMonitorService.class);
  @Autowired
  @Qualifier("performanceMonitorDao")
  private PerformanceMonitorDao performanceMonitorDao;
  
  public BaseDao getDao()
  {
    return this.performanceMonitorDao;
  }
}
