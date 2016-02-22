package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.MemberOrdersSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberOrdersSearchDao")
public class MemberOrdersSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberOrdersSearchDao.class);
  
  public Class getEntityClass()
  {
    return new MemberOrdersSearch().getClass();
  }
}
