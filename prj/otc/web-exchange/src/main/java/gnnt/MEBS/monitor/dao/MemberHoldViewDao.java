package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.MemberHoldView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberHoldViewDao")
public class MemberHoldViewDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberHoldViewDao.class);
  
  public Class getEntityClass()
  {
    return MemberHoldView.class;
  }
}
