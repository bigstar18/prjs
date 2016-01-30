package gnnt.MEBS.common.broker.dao;

import gnnt.MEBS.common.broker.model.Right;
import java.util.List;
import org.hibernate.Filter;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("com_rightDao")
public class RightDao extends StandardDao
{
  public Right getRightBySubFilter(long paramLong, int paramInt1, int paramInt2)
  {
    String str = "from Right as model where model.id= ?";
    getHibernateTemplate().enableFilter("rightFilter").setParameter("type1", Integer.valueOf(paramInt1)).setParameter("type2", Integer.valueOf(paramInt2));
    List localList = getHibernateTemplate().find(str, Long.valueOf(paramLong));
    if (localList.size() > 0)
      return (Right)localList.get(0);
    return null;
  }

  public Right loadRightTree(long paramLong, int paramInt1, int paramInt2)
  {
    String str = "from Right as model where model.id= ?";
    getHibernateTemplate().enableFilter("rightTreeFilter").setParameter("type", Integer.valueOf(paramInt1)).setParameter("visible", Integer.valueOf(paramInt2));
    List localList = getHibernateTemplate().find(str, Long.valueOf(paramLong));
    if (localList.size() > 0)
      return (Right)localList.get(0);
    return null;
  }
}