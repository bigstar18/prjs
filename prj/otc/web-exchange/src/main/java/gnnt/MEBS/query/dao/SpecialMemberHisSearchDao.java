package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.SpecialMemberHisSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("specialMemberHisSearchDao")
public class SpecialMemberHisSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberHisSearchDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMemberHisSearch().getClass();
  }
}
