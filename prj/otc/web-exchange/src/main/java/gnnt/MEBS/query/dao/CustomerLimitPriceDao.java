package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.CustomerLimitPrice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerLimitPriceDao")
public class CustomerLimitPriceDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerLimitPriceDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerLimitPrice().getClass();
  }
}
