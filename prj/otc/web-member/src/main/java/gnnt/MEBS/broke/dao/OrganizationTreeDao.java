package gnnt.MEBS.broke.dao;

import gnnt.MEBS.broke.model.OrganizationTree;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("organizationTreeDao")
public class OrganizationTreeDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(OrganizationTreeDao.class);
  
  public Class getEntityClass()
  {
    return new OrganizationTree().getClass();
  }
}
