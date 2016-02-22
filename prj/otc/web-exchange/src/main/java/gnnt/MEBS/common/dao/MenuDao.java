package gnnt.MEBS.common.dao;

import gnnt.MEBS.common.model.Menu;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Filter;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("menuDao")
public class MenuDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MenuDao.class);
  private String entityName = "gnnt.MEBS.common.model.Menu";
  
  public Class getEntityClass()
  {
    return new Menu().getClass();
  }
  
  public List getMenuByFilter(int type1, int type2, int visible)
  {
    try
    {
      String queryString = "from Menu as model order by model.moduleId,model.seq";
      
      getHibernateTemplate().enableFilter("menuFilter").setParameter(
        "type1", Integer.valueOf(type1)).setParameter("type2", Integer.valueOf(type2)).setParameter(
        "visible", Integer.valueOf(visible));
      return getHibernateTemplate().find(queryString);
    }
    catch (RuntimeException re)
    {
      this.logger.error("find by property name failed", re);
      throw re;
    }
  }
  
  public Menu getMenuById(long id, int type1, int type2, int visible)
  {
    Menu menu = null;
    try
    {
      String queryString = "from Menu as model where model.id= ?";
      getHibernateTemplate().enableFilter("menuFilter").setParameter(
        "type1", Integer.valueOf(type1)).setParameter("type2", Integer.valueOf(type2)).setParameter(
        "visible", Integer.valueOf(visible));
      List<Menu> list = getHibernateTemplate().find(queryString, Long.valueOf(id));
      if (list.size() > 0) {
        menu = (Menu)list.get(0);
      }
    }
    catch (RuntimeException re)
    {
      this.logger.error("get failed", re);
      throw re;
    }
    return menu;
  }
}
