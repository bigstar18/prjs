package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.SpecialMemberOrderSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("specialMemberOrderSearchDao")
public class SpecialMemberOrderSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberOrderSearchDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMemberOrderSearch().getClass();
  }
}
