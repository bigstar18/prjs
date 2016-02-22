package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VCustomerfundtry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("vCustomerfundtryDao")
public class VCustomerfundtryDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(VCustomerfundDao.class);
  
  public Class getEntityClass()
  {
    return VCustomerfundtry.class;
  }
}
