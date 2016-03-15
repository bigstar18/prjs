package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaPininfoDao;
import cn.com.agree.eteller.afa.persistence.AfaPininfo;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AfaPininfoDao
  extends HibernateDaoSupport
  implements IAfaPininfoDao
{
  public AfaPininfo[] loadAfaPininfo(Map map)
  {
    final List list = new ArrayList();
    
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session.createCriteria(AfaPininfo.class);
        










        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaPininfo[])list.toArray(new AfaPininfo[0]);
  }
  
  public boolean addAfaPininfo(final AfaPininfo ca)
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
  
  public boolean deleteAfaPininfo(final AfaPininfo ca)
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
  
  public boolean updateAfaPininfo(AfaPininfo ca, AfaPininfo ca_old)
  {
    boolean isUpdated = false;
    System.out.println("update:" + ca.toString2());
    try
    {
      deleteAfaPininfo(ca_old);
      addAfaPininfo(ca);
      

      isUpdated = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      isUpdated = false;
    }
    return isUpdated;
  }
  
  public AfaPininfo getAfaPininfoBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (AfaPininfo)list.get(0);
  }
  
  public List<AfaPininfo> loadAfaPininfoByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(AfaPininfo.class);
    if (map.get("sysid") != null) {
      criteria.add(Restrictions.eq("sysid", ((String)map.get("sysid")).trim()));
    }
    if (map.get("channel") != null) {
      criteria.add(Restrictions.eq("channel", ((String)map.get("channel")).trim()));
    }
    criteria.addOrder(Order.asc("sysid"));
    page.setAllRecords(Integer.valueOf(criteria.list().size()));
    criteria.setFirstResult(page.getFirstRecord().intValue());
    criteria.setMaxResults(page.getPerPageRecords().intValue());
    
    return criteria.list();
  }
}
