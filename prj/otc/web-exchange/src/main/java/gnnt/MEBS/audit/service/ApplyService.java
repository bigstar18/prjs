package gnnt.MEBS.audit.service;

import gnnt.MEBS.audit.dao.ApplyDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("applyService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ApplyService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(ApplyService.class);
  @Autowired
  @Qualifier("applyDao")
  private ApplyDao applyDao;
  
  public BaseDao getDao()
  {
    return this.applyDao;
  }
}
