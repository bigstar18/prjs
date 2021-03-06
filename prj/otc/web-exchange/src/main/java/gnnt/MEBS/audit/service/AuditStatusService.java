package gnnt.MEBS.audit.service;

import gnnt.MEBS.audit.dao.AuditStatusDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("auditStatusService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class AuditStatusService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(AuditStatusService.class);
  @Autowired
  @Qualifier("auditStatusDao")
  private AuditStatusDao auditStatusDao;
  
  public BaseDao getDao()
  {
    return this.auditStatusDao;
  }
}
