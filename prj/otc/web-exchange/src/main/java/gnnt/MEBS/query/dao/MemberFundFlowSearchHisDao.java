package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.MemberFundFlowSearchHis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberFundFlowSearchHisDao")
public class MemberFundFlowSearchHisDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberFundFlowSearchHisDao.class);
  
  public Class getEntityClass()
  {
    return new MemberFundFlowSearchHis().getClass();
  }
}
