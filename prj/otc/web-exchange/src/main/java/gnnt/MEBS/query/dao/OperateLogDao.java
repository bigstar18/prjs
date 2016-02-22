package gnnt.MEBS.query.dao;

import gnnt.MEBS.globalLog.model.OperateLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("operateLogSearchDao")
public class OperateLogDao
  extends QueryBaseDao
{
  private final transient Log logger = LogFactory.getLog(OperateLogDao.class);
  
  public Class getEntityClass()
  {
    return new OperateLog().getClass();
  }
}
