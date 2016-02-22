package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.MonitorProDao;
import gnnt.MEBS.monitor.dao.VOnlinemonitorDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("vOnlinemonitorService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class VOnlinemonitorService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(VOnlinemonitorService.class);
  @Autowired
  @Qualifier("vOnlinemonitorDao")
  private VOnlinemonitorDao vOnlinemonitorDao;
  @Autowired
  @Qualifier("monitorProDao")
  private MonitorProDao monitorProDao;
  
  public BaseDao getDao()
  {
    return this.vOnlinemonitorDao;
  }
  
  public int logkick(String operator, String operator_type, String operatorIp, String operatorContent, int operatorResult)
  {
    return this.monitorProDao.logkick(operator, operator_type, operatorIp, operatorContent, operatorResult);
  }
}
