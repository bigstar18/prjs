package gnnt.MEBS.common.dao;

import gnnt.MEBS.common.model.User;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(UserDao.class);
  
  public Class getEntityClass()
  {
    return new User().getClass();
  }
  
  public List<User> findRoleByProperty(String propertyName, Object value)
  {
    this.logger.debug("finding User instance with property: " + propertyName + ", value: " + value);
    List<User> list = null;
    String queryString = "from User as model where model." + propertyName + "= ?";
    list = getHibernateTemplate().find(queryString, value);
    return list;
  }
}
