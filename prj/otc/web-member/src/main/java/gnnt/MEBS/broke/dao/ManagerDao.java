package gnnt.MEBS.broke.dao;

import gnnt.MEBS.broke.model.Manager;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("managerDao")
public class ManagerDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(ManagerDao.class);
  
  public Class getEntityClass()
  {
    return new Manager().getClass();
  }
}
