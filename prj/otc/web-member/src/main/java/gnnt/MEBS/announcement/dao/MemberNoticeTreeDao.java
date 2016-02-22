package gnnt.MEBS.announcement.dao;

import gnnt.MEBS.announcement.model.MemberNoticeTree;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberNoticeTreeDao")
public class MemberNoticeTreeDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberNoticeTreeDao.class);
  
  public Class getEntityClass()
  {
    return new MemberNoticeTree().getClass();
  }
}
