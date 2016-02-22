package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.SpecialMemberOrderHisSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("specialMemberOrderHisSearchDao")
public class SpecialMemberOrderHisSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberOrderHisSearchDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMemberOrderHisSearch().getClass();
  }
}
