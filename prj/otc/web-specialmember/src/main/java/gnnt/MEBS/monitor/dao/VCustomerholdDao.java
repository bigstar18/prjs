package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VCustomerhold;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("vCustomerholdDao")
public class VCustomerholdDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(VCustomerholdDao.class);
  
  public Class getEntityClass()
  {
    return VCustomerhold.class;
  }
}
