package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaSystemDao;
import cn.com.agree.eteller.afa.persistence.AfaSystem;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AfaSystemDao
  extends HibernateDaoSupport
  implements IAfaSystemDao
{
  public boolean addAfaSystem(final AfaSystem ca)
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
  
  public boolean deleteAfaSystem(final AfaSystem ca)
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
  
  public AfaSystem[] getAfaSystemBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session.createCriteria(AfaSystem.class);
        if (!map.get("syscname").toString().equals("00000"))
        {
          String tmpStr = "%" + map.get("syscname") + "%";
          criteria.add(Expression.like("syscname", tmpStr));
        }
        if (!map.get("status").toString().equals("00000")) {
          criteria.add(Expression.eq("status", map.get("status")));
        }
        if (!map.get("type").toString().equals("00000")) {
          criteria.add(Expression.eq("type", map.get("type")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaSystem[])list.toArray(new AfaSystem[0]);
  }
  
  public boolean updateAfaSystem(AfaSystem ca)
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
  
  public AfaSystem[] getAllafaSystem()
  {
    List ls = new ArrayList();
    try
    {
      String sql = "from AfaSystem order by sysid asc";
      ls = getHibernateTemplate().find(sql);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaSystem[])ls.toArray(new AfaSystem[0]);
  }
  
  public AfaSystem getAfaSystemBysql(String sql)
  {
    List list = new ArrayList();
    try
    {
      list = getHibernateTemplate().find(sql);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    if (list.size() == 0) {
      return null;
    }
    return (AfaSystem)list.get(0);
  }
  
  public List<AfaSystem> getAfaSystemByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(AfaSystem.class);
    if (map.get("syscname") != null) {
      criteria.add(Restrictions.like("syscname", (String)map.get("syscname"), MatchMode.ANYWHERE));
    }
    if (map.get("status") != null) {
      criteria.add(Restrictions.eq("status", map.get("status")));
    }
    if (map.get("type") != null) {
      criteria.add(Restrictions.eq("type", map.get("type")));
    }
    criteria.addOrder(Order.asc("sysid"));
    page.setAllRecords(Integer.valueOf(criteria.list().size()));
    criteria.setFirstResult(page.getFirstRecord().intValue());
    criteria.setMaxResults(page.getPerPageRecords().intValue());
    
    return criteria.list();
  }
}
