package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.MemberTradeSearchHis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberTradeSearchHisDao")
public class MemberTradeSearchHisDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberTradeSearchHisDao.class);
  
  public Class getEntityClass()
  {
    return new MemberTradeSearchHis().getClass();
  }
}
