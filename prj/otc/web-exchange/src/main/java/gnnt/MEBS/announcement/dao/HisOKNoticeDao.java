package gnnt.MEBS.announcement.dao;

import gnnt.MEBS.announcement.model.HisOKNotice;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("hisOkNoticeDao")
public class HisOKNoticeDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(HisOKNoticeDao.class);
  
  public Class getEntityClass()
  {
    return new HisOKNotice().getClass();
  }
}
