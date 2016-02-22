package gnnt.MEBS.common.dao;

import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Filter;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("rightDao")
public class RightDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(RightDao.class);
  private String entityName = "gnnt.MEBS.common.model.Right";
  
  public Class getEntityClass()
  {
    return new Right().getClass();
  }
  
  public Right getRightByFilter(long id, int type1, int type2)
  {
    Right right = null;
    try
    {
      String queryString = "from Right as model where model.id= ?";
      
      getHibernateTemplate().enableFilter("rightFilter").setParameter(
        "type1", Integer.valueOf(type1)).setParameter("type2", Integer.valueOf(type2));
      List<Right> list = getHibernateTemplate().find(queryString, Long.valueOf(id));
      if (list.size() > 0) {
        right = (Right)list.get(0);
      }
    }
    catch (Exception re)
    {
      this.logger.error("find by property name failed", re);
    }
    return right;
  }
  
  public Right loadTreeRight(long id, int type, int visible)
  {
    Right right = null;
    try
    {
      String queryString = "from Right as model where model.id= ?";
      
      getHibernateTemplate().enableFilter("rightTreeFilter")
        .setParameter("type", Integer.valueOf(type))
        .setParameter("visible", Integer.valueOf(visible));
      List<Right> list = getHibernateTemplate().find(queryString, Long.valueOf(id));
      if (list.size() > 0) {
        right = (Right)list.get(0);
      }
    }
    catch (Exception re)
    {
      this.logger.error("find by property name failed", re);
    }
    return right;
  }
}
