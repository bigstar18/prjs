package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.MemberHoldSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberHoldSearchDao")
public class MemberHoldSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberHoldSearchDao.class);
  
  public Class getEntityClass()
  {
    return new MemberHoldSearch().getClass();
  }
}
