package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.MemberUser;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("memberUserDao")
public class MemberUserDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberUserDao.class);
  
  public Class getEntityClass()
  {
    return new MemberUser().getClass();
  }
  
  public List<MemberUser> findRoleByProperty(String propertyName, Object value)
  {
    this.logger.debug("finding MemberUser instance with property: " + propertyName + ", value: " + value);
    List<MemberUser> list = null;
    String queryString = "from MemberUser as model where model." + propertyName + "= ?";
    list = getHibernateTemplate().find(queryString, value);
    return list;
  }
}
