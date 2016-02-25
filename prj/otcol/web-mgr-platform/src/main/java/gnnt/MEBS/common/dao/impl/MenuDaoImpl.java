package gnnt.MEBS.common.dao.impl;

import gnnt.MEBS.common.model.Menu;
import java.util.List;
import org.apache.commons.logging.Log;
import org.hibernate.Filter;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class MenuDaoImpl
  extends HibernateDaoSupport
{
  private String className = "gnnt.MEBS.common.model.Menu";
  
  public List getMenuByFilter(int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      String str = "from Menu as model order by model.moduleId,model.seq";
      getHibernateTemplate().enableFilter("menuFilter").setParameter("type1", Integer.valueOf(paramInt1)).setParameter("type2", Integer.valueOf(paramInt2)).setParameter("visible", Integer.valueOf(paramInt3));
      return getHibernateTemplate().find(str);
    }
    catch (RuntimeException localRuntimeException)
    {
      this.logger.error("find by property name failed", localRuntimeException);
      throw localRuntimeException;
    }
  }
  
  public Menu getMenuById(long paramLong, int paramInt1, int paramInt2, int paramInt3)
  {
    Menu localMenu = null;
    try
    {
      String str = "from Menu as model where model.id= ?";
      getHibernateTemplate().enableFilter("menuFilter").setParameter("type1", Integer.valueOf(paramInt1)).setParameter("type2", Integer.valueOf(paramInt2)).setParameter("visible", Integer.valueOf(paramInt3));
      List localList = getHibernateTemplate().find(str, Long.valueOf(paramLong));
      if (localList.size() > 0) {
        localMenu = (Menu)localList.get(0);
      }
    }
    catch (RuntimeException localRuntimeException)
    {
      this.logger.error("get failed", localRuntimeException);
      throw localRuntimeException;
    }
    return localMenu;
  }
}
