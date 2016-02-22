package gnnt.MEBS.audit.service;

import gnnt.MEBS.audit.dao.ParmaLogDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("pramaLogService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ParmaLogService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(ParmaLogService.class);
  @Autowired
  @Qualifier("parmaLogDao")
  private ParmaLogDao parmaLogDao;
  
  public BaseDao getDao()
  {
    return this.parmaLogDao;
  }
}
