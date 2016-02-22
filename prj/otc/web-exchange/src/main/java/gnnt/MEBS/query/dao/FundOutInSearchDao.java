package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.FundOutInSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("fundOutInSearchDao")
public class FundOutInSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(FundOutInSearchDao.class);
  
  public Class getEntityClass()
  {
    return new FundOutInSearch().getClass();
  }
}
