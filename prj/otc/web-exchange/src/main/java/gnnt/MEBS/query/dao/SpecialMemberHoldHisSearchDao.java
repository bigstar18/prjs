package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.SpecialMemberHoldHisSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("specialMemberHoldHisSearchDao")
public class SpecialMemberHoldHisSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberHoldHisSearchDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMemberHoldHisSearch().getClass();
  }
}
