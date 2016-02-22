package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.VCustomerfundTipDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("vCustomerfundTipService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class VCustomerfundTipService
  extends BaseService
{
  @Autowired
  @Qualifier("vCustomerfundTipDao")
  private VCustomerfundTipDao vCustomerfundTipDao;
  
  public BaseDao getDao()
  {
    return this.vCustomerfundTipDao;
  }
}
