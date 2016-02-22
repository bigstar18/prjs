package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.CustomerTradeSearchHis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerTradeSearchHisDao")
public class CustomerTradeSearchHisDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerTradeSearchHisDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerTradeSearchHis().getClass();
  }
}
