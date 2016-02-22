package gnnt.MEBS.broke.dao;

import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("brokerageDao")
public class BrokerageDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(BrokerageDao.class);
  
  public Class getEntityClass()
  {
    return new Brokerage().getClass();
  }
}
