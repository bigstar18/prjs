package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.MemberFundSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberFundSearchDao")
public class MemberFundSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberFundSearchDao.class);
  
  public Class getEntityClass()
  {
    return new MemberFundSearch().getClass();
  }
}
