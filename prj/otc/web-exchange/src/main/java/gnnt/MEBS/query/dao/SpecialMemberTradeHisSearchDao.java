package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.SpecialMemberTradeHisSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("specialMemberTradeHisSearchDao")
public class SpecialMemberTradeHisSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberTradeHisSearchDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMemberTradeHisSearch().getClass();
  }
}
