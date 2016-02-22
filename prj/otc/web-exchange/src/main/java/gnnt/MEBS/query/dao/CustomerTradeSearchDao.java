package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.CustomerTradeSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerTradeSearchDao")
public class CustomerTradeSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerTradeSearchDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerTradeSearch().getClass();
  }
}
