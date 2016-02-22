package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.SpecialMemberTradeSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("specialMemberTradeSearchDao")
public class SpecialMemberTradeSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberTradeSearchDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMemberTradeSearch().getClass();
  }
}
