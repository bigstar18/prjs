package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VMemberholddetail;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("vMemberholddetailDao")
public class VMemberholddetailDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(VMemberholddetailDao.class);
  
  public Class getEntityClass()
  {
    return VMemberholddetail.class;
  }
}
