package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.ICredRecktradecodeDao;
import cn.com.agree.eteller.afa.persistence.CredRecktradecode;
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

public class CredRecktradecodeDao
  extends HibernateDaoSupport
  implements ICredRecktradecodeDao
{
  public boolean addCredRecktradecode(final CredRecktradecode ca)
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
  
  public boolean deleteCredRecktradecode(final CredRecktradecode ca)
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
  
  public CredRecktradecode[] getAllCredRecktradecode()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(CredRecktradecode.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (CredRecktradecode[])ls.toArray(new CredRecktradecode[0]);
  }
  
  public CredRecktradecode[] getCredRecktradecodeByMap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session.createCriteria(CredRecktradecode.class);
        if ((map.get("sysid") != null) && 
          (map.get("sysid").toString().length() > 0)) {
          criteria.add(Expression.eq("comp_id.sysid", map.get("sysid")));
        }
        if ((map.get("unitno") != null) && 
          (map.get("unitno").toString().length() > 0)) {
          criteria.add(Expression.eq("comp_id.unitno", map.get("unitno")));
        }
        if ((map.get("subunitno") != null) && 
          (map.get("subunitno").toString().length() > 0)) {
          criteria.add(Expression.eq("comp_id.subunitno", map
            .get("subunitno")));
        }
        if ((map.get("tradecode") != null) && 
          (map.get("tradecode").toString().length() > 0)) {
          criteria.add(Expression.eq("comp_id.tradecode", map
            .get("tradecode")));
        }
        if ((map.get("recktradecode") != null) && 
          (map.get("recktradecode").toString().length() > 0)) {
          criteria.add(Expression.eq("recktradecode", map
            .get("recktradecode")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (CredRecktradecode[])list.toArray(new CredRecktradecode[0]);
  }
  
  public CredRecktradecode getCredRecktradecodeBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (CredRecktradecode)list.get(0);
  }
  
  public boolean updateCredRecktradecode(CredRecktradecode ca)
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
  
  public List<CredRecktradecode> getCredRecktradecodeByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(CredRecktradecode.class);
    if (map.get("sysid") != null) {
      criteria.add(Restrictions.eq("comp_id.sysid", map.get("sysid")));
    }
    if (map.get("unitno") != null) {
      criteria.add(Restrictions.eq("comp_id.unitno", map.get("unitno")));
    }
    if (map.get("subunitno") != null) {
      criteria.add(Restrictions.eq("comp_id.subunitno", map.get("subunitno")));
    }
    if (map.get("tradecode") != null) {
      criteria.add(Restrictions.eq("comp_id.tradecode", map.get("tradecode")));
    }
    if (map.get("recktradecode") != null) {
      criteria.add(Restrictions.eq("recktradecode", map.get("recktradecode")));
    }
    criteria.addOrder(Order.asc("comp_id.sysid"));
    criteria.addOrder(Order.asc("comp_id.unitno"));
    criteria.addOrder(Order.asc("comp_id.subunitno"));
    criteria.addOrder(Order.asc("comp_id.tradecode"));
    if (page != null)
    {
      page.setAllRecords(Integer.valueOf(criteria.list().size()));
      criteria.setFirstResult(page.getFirstRecord().intValue());
      criteria.setMaxResults(page.getPerPageRecords().intValue());
    }
    return criteria.list();
  }
}
