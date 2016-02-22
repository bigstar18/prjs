package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VOnlineMonitor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("vOnlinemonitorDao")
public class VOnlinemonitorDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(VOnlinemonitorDao.class);
  
  public Class getEntityClass()
  {
    return VOnlineMonitor.class;
  }
}
