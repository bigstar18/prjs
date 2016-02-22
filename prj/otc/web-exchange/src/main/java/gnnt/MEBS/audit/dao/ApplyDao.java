package gnnt.MEBS.audit.dao;

import gnnt.MEBS.audit.model.Apply;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("applyDao")
public class ApplyDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(ApplyDao.class);
  
  public Class getEntityClass()
  {
    return new Apply().getClass();
  }
}
