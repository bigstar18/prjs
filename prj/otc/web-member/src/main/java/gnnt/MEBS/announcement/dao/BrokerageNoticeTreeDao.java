package gnnt.MEBS.announcement.dao;

import gnnt.MEBS.announcement.model.BrokerageNoticeTree;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("brokerageNoticeTreeDao")
public class BrokerageNoticeTreeDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(BrokerageNoticeTreeDao.class);
  
  public Class getEntityClass()
  {
    return new BrokerageNoticeTree().getClass();
  }
}
