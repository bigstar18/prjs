package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IGdbHostabsinfoDao;
import cn.com.agree.eteller.afa.persistence.GdbHostabsinfo;
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

public class GdbHostabsinfoDao
  extends HibernateDaoSupport
  implements IGdbHostabsinfoDao
{
  public boolean addGdbHostabsinfo(final GdbHostabsinfo ca)
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
  
  public boolean deleteGdbHostabsinfo(final GdbHostabsinfo ca)
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
  
  public boolean updateGdbHostabsinfo(final GdbHostabsinfo ca_old, final GdbHostabsinfo ca)
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
  
  public GdbHostabsinfo[] getGdbHostabsinfoBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session.createCriteria(GdbHostabsinfo.class);
        if ((map.get("hosttype") != null) && (map.get("hosttype").toString().length() > 0)) {
          criteria.add(Expression.eq("comp_id.hosttype", map.get("hosttype")));
        }
        if ((map.get("bankunitno") != null) && (map.get("bankunitno").toString().length() > 0)) {
          criteria.add(Expression.eq("comp_id.bankunitno", map.get("bankunitno")));
        }
        if ((map.get("saveflag") != null) && (map.get("saveflag").toString().length() > 0)) {
          criteria.add(Expression.eq("comp_id.saveflag", map.get("saveflag")));
        }
        if ((map.get("agentflag") != null) && (map.get("agentflag").toString().length() > 0)) {
          criteria.add(Expression.eq("comp_id.agentflag", map.get("agentflag")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (GdbHostabsinfo[])list.toArray(new GdbHostabsinfo[0]);
  }
  
  public GdbHostabsinfo getGdbHostabsinfoBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (GdbHostabsinfo)list.get(0);
  }
  
  public List<GdbHostabsinfo> getGdbHostabsinfoByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(GdbHostabsinfo.class);
    if (map.get("hosttype") != null) {
      criteria.add(Restrictions.eq("comp_id.hosttype", map.get("hosttype")));
    }
    if (map.get("bankunitno") != null) {
      criteria.add(Restrictions.eq("comp_id.bankunitno", map.get("bankunitno")));
    }
    if (map.get("saveflag") != null) {
      criteria.add(Restrictions.eq("comp_id.saveflag", map.get("saveflag")));
    }
    if (map.get("agentflag") != null) {
      criteria.add(Restrictions.eq("comp_id.agentflag", map.get("agentflag")));
    }
    criteria.addOrder(Order.asc("comp_id.bankunitno"));
    page.setAllRecords(Integer.valueOf(criteria.list().size()));
    criteria.setFirstResult(page.getFirstRecord().intValue());
    criteria.setMaxResults(page.getPerPageRecords().intValue());
    
    return criteria.list();
  }
}
