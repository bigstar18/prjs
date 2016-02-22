package gnnt.MEBS.common.service;

import gnnt.MEBS.common.dao.UserForPasswordDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userForPasswordService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class UserForPasswordService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(UserForPasswordService.class);
  @Autowired
  @Qualifier("userForPasswordDao")
  private UserForPasswordDao userForPasswordDao;
  
  public BaseDao getDao()
  {
    return this.userForPasswordDao;
  }
}
