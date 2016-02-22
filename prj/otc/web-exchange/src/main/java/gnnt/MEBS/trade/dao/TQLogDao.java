package gnnt.MEBS.trade.dao;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.trade.model.TQLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("tqLogDao")
public class TQLogDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(TQLogDao.class);
  
  public Class getEntityClass()
  {
    return new TQLog().getClass();
  }
}
