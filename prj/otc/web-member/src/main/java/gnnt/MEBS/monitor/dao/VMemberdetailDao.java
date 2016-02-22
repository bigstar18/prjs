package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VMemberdetail;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("vMemberdetailDao")
public class VMemberdetailDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(VMemberdetailDao.class);
  
  public Class getEntityClass()
  {
    return VMemberdetail.class;
  }
}
