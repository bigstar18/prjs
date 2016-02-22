package gnnt.MEBS.audit.dao;

import gnnt.MEBS.audit.model.AuditStatus;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("auditStatusDao")
public class AuditStatusDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(AuditStatusDao.class);
  
  public Class getEntityClass()
  {
    return new AuditStatus().getClass();
  }
}
