package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.VMemberdetailDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("vMemberdetailService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class VMemberdetailService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(VMemberdetailService.class);
  @Autowired
  @Qualifier("vMemberdetailDao")
  private VMemberdetailDao vMemberdetailDao;
  
  public BaseDao getDao()
  {
    return this.vMemberdetailDao;
  }
}
