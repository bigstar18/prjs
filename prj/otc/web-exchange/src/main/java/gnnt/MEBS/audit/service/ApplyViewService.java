package gnnt.MEBS.audit.service;

import gnnt.MEBS.audit.dao.ApplyViewDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("applyViewService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ApplyViewService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(ApplyViewService.class);
  @Autowired
  @Qualifier("applyViewDao")
  private ApplyViewDao applyDao;
  
  public BaseDao getDao()
  {
    return this.applyDao;
  }
}
