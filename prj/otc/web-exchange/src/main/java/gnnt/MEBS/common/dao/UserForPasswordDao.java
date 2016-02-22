package gnnt.MEBS.common.dao;

import gnnt.MEBS.common.model.UserForPassword;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("userForPasswordDao")
public class UserForPasswordDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(UserForPasswordDao.class);
  
  public Class getEntityClass()
  {
    return new UserForPassword().getClass();
  }
}
