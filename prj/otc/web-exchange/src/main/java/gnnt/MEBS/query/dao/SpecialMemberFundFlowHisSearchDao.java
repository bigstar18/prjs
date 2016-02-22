package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.SpecialMemberFundFlowHisSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("specialMemberFundFlowHisSearchDao")
public class SpecialMemberFundFlowHisSearchDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberFundFlowHisSearchDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMemberFundFlowHisSearch().getClass();
  }
}
