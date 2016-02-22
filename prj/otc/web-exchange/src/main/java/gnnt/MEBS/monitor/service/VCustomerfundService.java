package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.VCustomerfundDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("vCustomerfundService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class VCustomerfundService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(VCustomerfundService.class);
  @Autowired
  @Qualifier("vCustomerfundDao")
  private VCustomerfundDao vCustomerfundDao;
  
  public BaseDao getDao()
  {
    return this.vCustomerfundDao;
  }
}
