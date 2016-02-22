package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.MemberTradeSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberTradeSearchDao")
public class MemberTradeSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberTradeSearchDao.class);
  
  public Class getEntityClass()
  {
    return new MemberTradeSearch().getClass();
  }
}
