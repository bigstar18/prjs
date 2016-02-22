package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.MemberFreezeSearchHis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberFreezeSearchHisDao")
public class MemberFreezeSearchHisDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberFreezeSearchHisDao.class);
  
  public Class getEntityClass()
  {
    return new MemberFreezeSearchHis().getClass();
  }
}
