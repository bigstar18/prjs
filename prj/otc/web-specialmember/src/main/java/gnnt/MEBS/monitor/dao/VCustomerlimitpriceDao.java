package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VCustomerlimitprice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("vCustomerlimitpriceDao")
public class VCustomerlimitpriceDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(VCustomerlimitpriceDao.class);
  
  public Class getEntityClass()
  {
    return VCustomerlimitprice.class;
  }
}
