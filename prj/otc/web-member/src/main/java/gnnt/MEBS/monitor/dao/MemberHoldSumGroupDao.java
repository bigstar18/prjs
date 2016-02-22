package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.MemberHoldSumGroup;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberHoldSumGroupDao")
public class MemberHoldSumGroupDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberHoldSumGroupDao.class);
  
  public Class getEntityClass()
  {
    return MemberHoldSumGroup.class;
  }
}
