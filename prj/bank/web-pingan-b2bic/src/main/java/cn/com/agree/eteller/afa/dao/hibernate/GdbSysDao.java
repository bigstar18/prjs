package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IGdbSysDao;
import cn.com.agree.eteller.afa.persistence.GdbSys;
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

public class GdbSysDao
  extends HibernateDaoSupport
  implements IGdbSysDao
{
  public boolean addGdbsys(final GdbSys ca)
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
  
  public boolean deleteGdbsys(final GdbSys ca)
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
  
  public boolean updateGdbsys(final GdbSys ca_old, final GdbSys ca)
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
  
  public GdbSys[] getGdbSyBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(GdbSys.class);
        if ((map.get("bankunitno") != null) && (map.get("bankunitno").toString().length() > 0)) {
          criteria.add(Expression.eq("comp_id.bankunitno", map.get("bankunitno")));
        }
        if ((map.get("onlineflag") != null) && (map.get("onlineflag").toString().length() > 0) && (!map.get("onlineflag").equals("00000"))) {
          criteria.add(Expression.eq("comp_id.onlineflag", map.get("onlineflag")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (GdbSys[])list.toArray(new GdbSys[0]);
  }
  
  public GdbSys getGdbSysBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (GdbSys)list.get(0);
  }
  
  public List<GdbSys> getGdbSysByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(GdbSys.class);
    if (map.get("bankunitno") != null) {
      criteria.add(Restrictions.eq("comp_id.bankunitno", map.get("bankunitno")));
    }
    if (map.get("onlineflag") != null) {
      criteria.add(Restrictions.eq("comp_id.onlineflag", map.get("onlineflag")));
    }
    if (map.get("dataqueue") != null) {
      criteria.add(Restrictions.eq("dataqueue", map.get("dataqueue")));
    }
    criteria.addOrder(Order.asc("comp_id.bankunitno"));
    page.setAllRecords(Integer.valueOf(criteria.list().size()));
    criteria.setFirstResult(page.getFirstRecord().intValue());
    criteria.setMaxResults(page.getPerPageRecords().intValue());
    
    return criteria.list();
  }
}
