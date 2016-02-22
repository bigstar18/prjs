package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.CustomerFundSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerFundSearchDao")
public class CustomerFundSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerFundSearchDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerFundSearch().getClass();
  }
}
