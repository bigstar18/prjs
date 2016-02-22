package gnnt.MEBS.broke.service;

import gnnt.MEBS.broke.dao.ManagerDao;
import gnnt.MEBS.broke.model.Manager;
import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("managerPWService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ManagerPWService
  extends BaseService<Manager>
{
  private final transient Log logger = LogFactory.getLog(ManagerPWService.class);
  @Autowired
  @Qualifier("managerDao")
  private ManagerDao managerDao;
  
  public BaseDao getDao()
  {
    return this.managerDao;
  }
  
  public int update(Manager obj)
  {
    this.logger.debug("enter update");
    int num = 0;
    Manager manager = (Manager)copyObject(obj);
    manager.setPassword(MD5.getMD5(obj.getId(), obj.getPassword()));
    getDao().add(manager);
    num = 3;
    return num;
  }
}
