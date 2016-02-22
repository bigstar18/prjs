package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.MemberFundFlowSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberFundFlowSearchDao")
public class MemberFundFlowSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberFundFlowSearchDao.class);
  
  public Class getEntityClass()
  {
    return new MemberFundFlowSearch().getClass();
  }
}
