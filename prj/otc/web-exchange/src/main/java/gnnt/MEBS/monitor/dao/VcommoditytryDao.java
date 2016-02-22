package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VCommoditytry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("vCommoditytryDao")
public class VcommoditytryDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(VcommoditytryDao.class);
  
  public Class getEntityClass()
  {
    return VCommoditytry.class;
  }
}
