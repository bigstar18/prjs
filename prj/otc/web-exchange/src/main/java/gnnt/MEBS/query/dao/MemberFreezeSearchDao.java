package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.MemberFreezeSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberFreezeSearchDao")
public class MemberFreezeSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberFreezeSearchDao.class);
  
  public Class getEntityClass()
  {
    return new MemberFreezeSearch().getClass();
  }
}
