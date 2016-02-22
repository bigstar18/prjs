package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.MonitorProDao;
import gnnt.MEBS.monitor.dao.VCustomerfundtryDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("vCustomerfundtryService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class VCustomerfundtryService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(VCustomerfundtryService.class);
  @Autowired
  @Qualifier("vCustomerfundtryDao")
  private VCustomerfundtryDao vCustomerfundtryDao;
  @Autowired
  @Qualifier("monitorProDao")
  private MonitorProDao monitorProDao;
  
  public BaseDao getDao()
  {
    return this.vCustomerfundtryDao;
  }
  
  public void updateprice(String commodity, double price)
  {
    this.monitorProDao.updateprice(commodity, price);
  }
}
