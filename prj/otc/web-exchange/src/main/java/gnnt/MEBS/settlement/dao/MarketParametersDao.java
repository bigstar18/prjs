package gnnt.MEBS.settlement.dao;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.settlement.model.MarketParameters;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("marketParametersDao")
public class MarketParametersDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MarketParametersDao.class);
  
  public Class getEntityClass()
  {
    return new MarketParameters().getClass();
  }
}
