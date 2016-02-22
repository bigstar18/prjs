package gnnt.MEBS.trade.dao;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.trade.model.TradeTime;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("tradeTimeDao")
public class TradeTimeDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(TradeTimeDao.class);
  
  public Class getEntityClass()
  {
    return new TradeTime().getClass();
  }
}
