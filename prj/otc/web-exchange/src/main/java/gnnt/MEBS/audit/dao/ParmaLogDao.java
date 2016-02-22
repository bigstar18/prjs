package gnnt.MEBS.audit.dao;

import gnnt.MEBS.audit.model.ParmaLog;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("parmaLogDao")
public class ParmaLogDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(ParmaLogDao.class);
  
  public Class getEntityClass()
  {
    return new ParmaLog().getClass();
  }
}
