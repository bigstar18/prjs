package gnnt.MEBS.common.front.dao;

import gnnt.MEBS.common.front.model.front.Menu;
import java.util.List;
import org.hibernate.Filter;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("com_menuDao")
public class MenuDao
  extends StandardDao
{
  public List<Menu> getMenuBySubFilter(int paramInt1, int paramInt2, int paramInt3)
  {
    String str = "from Menu as model where model.parentID!=-1 order by model.moduleId,model.seq ";
    getHibernateTemplate().enableFilter("menuFilter").setParameter("type1", Integer.valueOf(paramInt1)).setParameter("type2", Integer.valueOf(paramInt2)).setParameter("visible", Integer.valueOf(paramInt3));
    return getHibernateTemplate().find(str);
  }
  
  public Menu getMenuById(long paramLong, List<Integer> paramList, int paramInt1, int paramInt2, int paramInt3)
  {
    String str = "from Menu as model where model.id= ? order by seq ";
    getHibernateTemplate().enableFilter("menuFilter").setParameter("type1", Integer.valueOf(paramInt1)).setParameter("type2", Integer.valueOf(paramInt2)).setParameterList("type3", paramList).setParameter("visible", Integer.valueOf(paramInt3));
    List localList = getHibernateTemplate().find(str, Long.valueOf(paramLong));
    if (localList.size() > 0) {
      return (Menu)localList.get(0);
    }
    return null;
  }
}
