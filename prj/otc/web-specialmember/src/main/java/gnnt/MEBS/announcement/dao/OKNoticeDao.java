package gnnt.MEBS.announcement.dao;

import gnnt.MEBS.announcement.model.OKNotice;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("okNoticeDao")
public class OKNoticeDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(OKNoticeDao.class);
  
  public Class getEntityClass()
  {
    return new OKNotice().getClass();
  }
}
