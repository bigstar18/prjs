package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.SpecialMemberSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("specialMemberSearchDao")
public class SpecialMemberSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberSearchDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMemberSearch().getClass();
  }
}
