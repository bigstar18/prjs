package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.MemberHoldSearchHis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberHoldSearchHisDao")
public class MemberHoldSearchHisDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberHoldSearchHisDao.class);
  
  public Class getEntityClass()
  {
    return new MemberHoldSearchHis().getClass();
  }
}
