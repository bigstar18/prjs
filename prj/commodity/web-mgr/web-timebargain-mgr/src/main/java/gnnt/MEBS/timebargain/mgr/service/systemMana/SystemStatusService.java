package gnnt.MEBS.timebargain.mgr.service.systemMana;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.dao.systemMana.SystemStatusDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("systemStatusService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class SystemStatusService extends StandardService
{

  @Autowired
  @Qualifier("systemStatusDao")
  private SystemStatusDao systemStatusDao;

  public String getSystemStatus()
  {
    return this.systemStatusDao.getSystemStatus();
  }
}