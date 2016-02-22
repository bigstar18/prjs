package gnnt.MEBS.query.dao;

import gnnt.MEBS.query.model.MemberThSearchHis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberThSearchHisDao")
public class MemberThSearchHisDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberThSearchHisDao.class);
  
  public Class getEntityClass()
  {
    return new MemberThSearchHis().getClass();
  }
}
