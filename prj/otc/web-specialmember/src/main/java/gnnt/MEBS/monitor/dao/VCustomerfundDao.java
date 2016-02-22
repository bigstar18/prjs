package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VCustomerfund;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("vCustomerfundDao")
public class VCustomerfundDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(VCustomerfundDao.class);
  
  public Class getEntityClass()
  {
    return VCustomerfund.class;
  }
}
