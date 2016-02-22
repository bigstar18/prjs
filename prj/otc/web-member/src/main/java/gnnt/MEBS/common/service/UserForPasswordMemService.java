package gnnt.MEBS.common.service;

import gnnt.MEBS.account.dao.TraderDao;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.common.dao.TraderProDao;
import gnnt.MEBS.common.dao.UserForPasswordDao;
import gnnt.MEBS.common.model.UserForPassword;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userForPasswordMemService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class UserForPasswordMemService
  extends BaseService<UserForPassword>
{
  private final transient Log logger = LogFactory.getLog(UserForPasswordService.class);
  @Autowired
  @Qualifier("traderDao")
  private TraderDao traderDao;
  @Autowired
  @Qualifier("userForPasswordDao")
  private UserForPasswordDao userForPasswordDao;
  @Autowired
  @Qualifier("traderProDao")
  private TraderProDao traderProDao;
  
  public BaseDao getDao()
  {
    return this.userForPasswordDao;
  }
  
  public int update(UserForPassword obj)
  {
    int num = 0;
    this.logger.debug("enter update");
    Clone objFor = copyObject(obj);
    if (objFor != null) {
      getDao().update(objFor);
    }
    getDao().flush();
    this.traderProDao.traderUpdate(obj.getUserId());
    num = 3;
    return num;
  }
}
