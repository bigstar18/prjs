package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VMemberclose;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("vMembercloseDao")
public class VMembercloseDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(VMembercloseDao.class);
  
  public Class getEntityClass()
  {
    return VMemberclose.class;
  }
}
