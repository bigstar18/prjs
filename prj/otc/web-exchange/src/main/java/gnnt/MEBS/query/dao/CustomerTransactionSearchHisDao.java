package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.CustomerTransactionSearchHis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerTransactionSearchHisDao")
public class CustomerTransactionSearchHisDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerTransactionSearchHisDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerTransactionSearchHis().getClass();
  }
}
