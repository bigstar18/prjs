package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.CustomerHoldSearchHis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerHoldSearchHisDao")
public class CustomerHoldSearchHisDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerHoldSearchHisDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerHoldSearchHis().getClass();
  }
}
