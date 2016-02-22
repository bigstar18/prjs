package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VSmemberfund;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("vSmemberfundDao")
public class VSmemberfundDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(VMemberfundDao.class);
  
  public Class getEntityClass()
  {
    return VSmemberfund.class;
  }
}
