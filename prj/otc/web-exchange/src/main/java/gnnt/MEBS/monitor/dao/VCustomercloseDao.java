package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VCustomerclose;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("vCustomercloseDao")
public class VCustomercloseDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(VCustomercloseDao.class);
  
  public Class getEntityClass()
  {
    return VCustomerclose.class;
  }
}
