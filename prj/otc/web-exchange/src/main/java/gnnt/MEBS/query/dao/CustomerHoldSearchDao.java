package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.CustomerHoldSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerHoldSearchDao")
public class CustomerHoldSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerHoldSearchDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerHoldSearch().getClass();
  }
}
