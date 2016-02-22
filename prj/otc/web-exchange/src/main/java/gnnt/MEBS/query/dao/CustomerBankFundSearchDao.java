package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.CustomerBankFundSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerBankFundSearchDao")
public class CustomerBankFundSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerBankFundSearchDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerBankFundSearch().getClass();
  }
}
