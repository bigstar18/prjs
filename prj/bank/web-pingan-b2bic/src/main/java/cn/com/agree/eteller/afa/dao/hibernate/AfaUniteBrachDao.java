package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaUnitebrachDao;
import cn.com.agree.eteller.afa.persistence.AfaUnitebrach;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AfaUniteBrachDao
  extends HibernateDaoSupport
  implements IAfaUnitebrachDao
{
  public boolean addAfaUnitebrach(final AfaUnitebrach ca)
  {
    boolean isAdded = false;
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException
        {
          session.save(ca);
          return null;
        }
      });
      isAdded = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      isAdded = false;
    }
    return isAdded;
  }
  
  public boolean deleteAfaUnitebrach(final AfaUnitebrach ca)
  {
    boolean isDeleted = false;
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException, SQLException
        {
          session.delete(ca);
          return null;
        }
      });
      isDeleted = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      isDeleted = false;
    }
    return isDeleted;
  }
  
  public AfaUnitebrach[] getAfaUnitebrachByMap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(AfaUnitebrach.class);
        if (!map.get("branchno").toString().equals("xxxxx")) {
          criteria.add(Expression.eq("branchno", map.get("branchno")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaUnitebrach[])list.toArray(new AfaUnitebrach[0]);
  }
  
  public AfaUnitebrach[] getAllAfaUnitebrach()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(AfaUnitebrach.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaUnitebrach[])ls.toArray(new AfaUnitebrach[0]);
  }
  
  public boolean updateAfaUnitebrach(AfaUnitebrach ca)
  {
    boolean isUpdated = false;
    try
    {
      getHibernateTemplate().update(ca);
      isUpdated = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      isUpdated = false;
    }
    return isUpdated;
  }
  
  public AfaUnitebrach getAfaUnitebrachBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (AfaUnitebrach)list.get(0);
  }
  
  public List<AfaUnitebrach> getAfaUnitebrachByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(AfaUnitebrach.class);
    if (map.get("branchno") != null) {
      criteria.add(Restrictions.eq("branchno", map.get("branchno")));
    }
    if (map.get("newbranchno") != null) {
      criteria.add(Restrictions.eq("newbranchno", map.get("newbranchno")));
    }
    if (map.get("brdate") != null) {
      criteria.add(Restrictions.eq("brdate", map.get("brdate")));
    }
    criteria.addOrder(Order.asc("branchno"));
    page.setAllRecords(Integer.valueOf(criteria.list().size()));
    criteria.setFirstResult(page.getFirstRecord().intValue());
    criteria.setMaxResults(page.getPerPageRecords().intValue());
    
    return criteria.list();
  }
}
