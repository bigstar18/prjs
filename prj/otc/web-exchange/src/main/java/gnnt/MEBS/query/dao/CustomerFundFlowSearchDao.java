package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.CustomerFundFlowSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerFundFlowSearchDao")
public class CustomerFundFlowSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerFundFlowSearchDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerFundFlowSearch().getClass();
  }
}
