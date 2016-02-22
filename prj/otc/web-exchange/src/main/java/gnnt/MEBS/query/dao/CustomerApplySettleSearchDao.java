package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.CustomerApplySettleSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerApplySettleSearchDao")
public class CustomerApplySettleSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerApplySettleSearchDao.class);
  
  public Class getEntityClass()
  {
    return new CustomerApplySettleSearch().getClass();
  }
}
