package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.MemberThSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberThSearchDao")
public class MemberThSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberThSearchDao.class);
  
  public Class getEntityClass()
  {
    return new MemberThSearch().getClass();
  }
}
