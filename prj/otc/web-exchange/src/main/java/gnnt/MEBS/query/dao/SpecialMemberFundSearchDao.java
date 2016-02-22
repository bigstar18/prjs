package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.SpecialMemberFundSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("specialMemberFundSearchDao")
public class SpecialMemberFundSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberFundSearchDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMemberFundSearch().getClass();
  }
}
