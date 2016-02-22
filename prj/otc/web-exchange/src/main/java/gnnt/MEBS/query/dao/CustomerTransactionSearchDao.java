package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.CustomerTransactionSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerTransactionSearchDao")
public class CustomerTransactionSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerTransactionSearchDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerTransactionSearch().getClass();
  }
}
