package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.Trader;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("traderDao")
public class TraderDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(TraderDao.class);
  
  public Class getEntityClass()
  {
    return new Trader().getClass();
  }
}
