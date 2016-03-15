package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.ICisCommdataDao;
import cn.com.agree.eteller.afa.persistence.CisCommdata;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class CisCommdataDao
  extends HibernateDaoSupport
  implements ICisCommdataDao
{
  public boolean addCisCommdata(final CisCommdata ca)
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
  
  public boolean deleteCisCommdata(final CisCommdata ca)
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
  
  public CisCommdata[] getCisCommdataBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(CisCommdata.class);
        if (!map.get("datacode").toString().equals("xxxxxx")) {
          criteria.add(Expression.eq("datacode", map.get("datacode")));
        }
        if (!map.get("dataname").toString().equals("xxxxxx"))
        {
          String tmpStr = "%" + map.get("dataname") + "%";
          criteria.add(Expression.like("dataname", tmpStr));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (CisCommdata[])list.toArray(new CisCommdata[0]);
  }
  
  public CisCommdata[] getAllCisCommdata()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(CisCommdata.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (CisCommdata[])ls.toArray(new CisCommdata[0]);
  }
  
  public boolean updateCisCommdata(CisCommdata ca)
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
  
  public CisCommdata getCisCommdataBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (CisCommdata)list.get(0);
  }
}
