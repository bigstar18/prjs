package gnnt.MEBS.broke.service;

import gnnt.MEBS.broke.dao.ManagerDao;
import gnnt.MEBS.broke.dao.ManagerProDao;
import gnnt.MEBS.broke.model.Manager;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("managerChangeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ManagerChangeService
  extends BaseService<Manager>
{
  private final transient Log logger = LogFactory.getLog(ManagerChangeService.class);
  @Autowired
  @Qualifier("managerDao")
  private ManagerDao managerDao;
  @Autowired
  @Qualifier("managerProDao")
  private ManagerProDao managerProDao;
  
  public BaseDao getDao()
  {
    return this.managerDao;
  }
  
  public int update(Manager org)
  {
    int result = this.managerProDao.managerChangePro(org.getManagerNo(), org.getParentOrganizationNO());
    if (result > 0) {
      result = 3;
    }
    return result;
  }
}
