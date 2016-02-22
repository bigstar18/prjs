package gnnt.MEBS.common.dao;

import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public class RoleDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(RoleDao.class);
  private String entityName = "gnnt.MEBS.common.model.Role";
  
  public Class getEntityClass()
  {
    return new Role().getClass();
  }
  
  public List<Role> findRoleByProperty(String propertyName, Object value)
  {
    this.logger.debug("finding Role instance with property: " + propertyName + 
      ", value: " + value);
    List<Role> list = null;
    try
    {
      String queryString = "from Role as model where model." + 
        propertyName + "= ?";
      
      list = getHibernateTemplate().find(queryString, value);
    }
    catch (Exception re)
    {
      this.logger.error("find by property name failed", re);
    }
    return list;
  }
}
