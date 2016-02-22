package gnnt.MEBS.broke.service;

import gnnt.MEBS.broke.dao.ManagerDao;
import gnnt.MEBS.broke.dao.ManagerProDao;
import gnnt.MEBS.broke.model.Manager;
import gnnt.MEBS.config.constant.PasswordConstant;
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

@Service("managerService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ManagerService
  extends BaseService<Manager>
{
  private final transient Log logger = LogFactory.getLog(ManagerService.class);
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
  
  public int delete(Manager obj)
  {
    int num = this.managerProDao.managerDeletePro(obj.getManagerNo());
    if (num > 0)
    {
      getDao().delete(obj);
      num = 4;
    }
    return num;
  }
  
  public int add(Manager obj)
  {
    this.logger.debug("enter add");
    int num = 0;
    Manager manager = (Manager)obj.clone();
    manager.setPassword(MD5.getMD5(manager.getId(), PasswordConstant.PASSWORD));
    getDao().add(manager);
    num = 2;
    return num;
  }
}
