package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IGdbAcctDao;
import cn.com.agree.eteller.afa.persistence.GdbAcct;
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

public class GdbAcctDao
  extends HibernateDaoSupport
  implements IGdbAcctDao
{
  public boolean addGdbAcct(final GdbAcct ca)
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
  
  public boolean deleteGdbAcct(final GdbAcct ca)
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
  
  public boolean updateGdbAcct(final GdbAcct ca_old, final GdbAcct ca)
  {
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        session.delete(ca_old);
        session.save(ca);
        return null;
      }
    });
    return true;
  }
  
  public GdbAcct[] getGdbAcctBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(GdbAcct.class);
        if ((map.get("acctype") != null) && (map.get("acctype").toString().length() > 0)) {
          criteria.add(Expression.eq("acctype", map.get("acctype")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (GdbAcct[])list.toArray(new GdbAcct[0]);
  }
  
  public GdbAcct getGdbAcctBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (GdbAcct)list.get(0);
  }
  
  public List<GdbAcct> getGdbAcctByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(GdbAcct.class);
    if (map.get("acctype") != null) {
      criteria.add(Restrictions.eq("acctype", map.get("acctype")));
    }
    if (map.get("mapname") != null) {
      criteria.add(Restrictions.eq("mapname", map.get("mapname")));
    }
    if (map.get("hosttype") != null) {
      criteria.add(Restrictions.eq("hosttype", map.get("hosttype")));
    }
    criteria.addOrder(Order.asc("acctype"));
    page.setAllRecords(Integer.valueOf(criteria.list().size()));
    criteria.setFirstResult(page.getFirstRecord().intValue());
    criteria.setMaxResults(page.getPerPageRecords().intValue());
    
    return criteria.list();
  }
}
