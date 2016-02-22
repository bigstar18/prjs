package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.SpecialMemberUser;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("specialMemberUserDao")
public class SpecialMemberUserDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberUserDao.class);
  
  public Class getEntityClass()
  {
    return new SpecialMemberUser().getClass();
  }
  
  public List<SpecialMemberUser> findRoleByProperty(String propertyName, Object value)
  {
    this.logger.debug("finding SpecialMemberUser instance with property: " + propertyName + 
      ", value: " + value);
    List<SpecialMemberUser> list = null;
    try
    {
      String queryString = "from SpecialMemberUser as model where model." + 
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
