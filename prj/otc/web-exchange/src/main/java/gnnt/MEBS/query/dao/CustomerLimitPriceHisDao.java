package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.CustomerLimitPriceHis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerLimitPriceHisDao")
public class CustomerLimitPriceHisDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerLimitPriceHisDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerLimitPriceHis().getClass();
  }
}
