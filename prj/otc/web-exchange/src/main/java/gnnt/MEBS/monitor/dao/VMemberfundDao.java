package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VMemberfund;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("vMemberfundDao")
public class VMemberfundDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(VMemberfundDao.class);
  
  public Class getEntityClass()
  {
    return VMemberfund.class;
  }
}
