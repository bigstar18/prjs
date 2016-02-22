package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.SpecialMemberHoldSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("specialMemberHoldSearchDao")
public class SpecialMemberHoldSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberHoldSearchDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMemberHoldSearch().getClass();
  }
}
