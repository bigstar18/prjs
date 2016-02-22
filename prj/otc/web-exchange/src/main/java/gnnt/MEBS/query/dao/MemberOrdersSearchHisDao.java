package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.MemberOrdersSearchHis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberOrdersSearchHisDao")
public class MemberOrdersSearchHisDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberOrdersSearchHisDao.class);
  
  public Class getEntityClass()
  {
    return new MemberOrdersSearchHis().getClass();
  }
}
