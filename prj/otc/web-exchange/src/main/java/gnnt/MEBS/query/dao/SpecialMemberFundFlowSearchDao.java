package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.SpecialMemberFundFlowSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("specialMemberFundFlowSearchDao")
public class SpecialMemberFundFlowSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberFundFlowSearchDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMemberFundFlowSearch().getClass();
  }
}
