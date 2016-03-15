package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.ICredCardbinDao;
import cn.com.agree.eteller.afa.persistence.CredCardbin;
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

public class CredCardbinDao
  extends HibernateDaoSupport
  implements ICredCardbinDao
{
  public boolean addCredCardbin(final CredCardbin ca)
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
  
  public boolean deleteCredCardbin(final CredCardbin ca)
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
  
  public CredCardbin[] getAllCredCardbin()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(CredCardbin.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (CredCardbin[])ls.toArray(new CredCardbin[0]);
  }
  
  public CredCardbin[] getCredCardbinByMap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session.createCriteria(CredCardbin.class);
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
        if ((map.get("cardbin") != null) && 
          (map.get("cardbin").toString().length() > 0)) {
          criteria.add(Expression.eq("comp_id.cardbin", map
            .get("cardbin")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (CredCardbin[])list.toArray(new CredCardbin[0]);
  }
  
  public CredCardbin getCredCardbinBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (CredCardbin)list.get(0);
  }
  
  public boolean updateCredCardbin(CredCardbin ca)
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
  
  public List<CredCardbin> getCredCardbinByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(CredCardbin.class);
    if (map.get("sysid") != null) {
      criteria.add(Restrictions.eq("comp_id.sysid", map.get("sysid")));
    }
    if (map.get("unitno") != null) {
      criteria.add(Restrictions.eq("comp_id.unitno", map.get("unitno")));
    }
    if (map.get("subunitno") != null) {
      criteria.add(Restrictions.eq("comp_id.subunitno", map.get("subunitno")));
    }
    if (map.get("cardbin") != null) {
      criteria.add(Restrictions.eq("comp_id.cardbin", map.get("cardbin")));
    }
    criteria.addOrder(Order.asc("comp_id.sysid"));
    criteria.addOrder(Order.asc("comp_id.unitno"));
    criteria.addOrder(Order.asc("comp_id.subunitno"));
    if (page != null)
    {
      page.setAllRecords(Integer.valueOf(criteria.list().size()));
      criteria.setFirstResult(page.getFirstRecord().intValue());
      criteria.setMaxResults(page.getPerPageRecords().intValue());
    }
    return criteria.list();
  }
}
