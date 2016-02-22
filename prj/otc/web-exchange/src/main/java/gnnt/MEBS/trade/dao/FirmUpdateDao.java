package gnnt.MEBS.trade.dao;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.trade.model.FirmUpdate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("firmUpdateDao")
public class FirmUpdateDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(FirmUpdateDao.class);
  
  public Class getEntityClass()
  {
    return new FirmUpdate().getClass();
  }
}
