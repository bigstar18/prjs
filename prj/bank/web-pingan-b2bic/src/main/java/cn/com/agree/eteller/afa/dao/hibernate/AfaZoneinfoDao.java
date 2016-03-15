package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaZoneinfoDao;
import cn.com.agree.eteller.afa.persistence.AfaZoneinfo;
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

public class AfaZoneinfoDao
  extends HibernateDaoSupport
  implements IAfaZoneinfoDao
{
  public boolean addAfaZoneinfo(final AfaZoneinfo ca)
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
  
  public boolean deleteAfaZoneinfo(final AfaZoneinfo ca)
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
  
  public AfaZoneinfo[] getAfaZoneinfoBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(AfaZoneinfo.class);
        if (!map.get("zoneno").toString().equals("00000")) {
          criteria.add(Expression.eq("zoneno", map.get("zoneno")));
        }
        if (!map.get("zonesname").toString().equals("00000"))
        {
          String tmpStr = "%" + map.get("zonesname") + "%";
          criteria.add(Expression.like("zonesname", tmpStr));
        }
        if (!map.get("zonename").toString().equals("00000"))
        {
          String tmpStr = "%" + map.get("zonename") + "%";
          criteria.add(Expression.like("zonename", tmpStr));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaZoneinfo[])list.toArray(new AfaZoneinfo[0]);
  }
  
  public AfaZoneinfo[] getAllAfaZoneinfo()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(AfaZoneinfo.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaZoneinfo[])ls.toArray(new AfaZoneinfo[0]);
  }
  
  public boolean updateAfaZoneinfo(AfaZoneinfo ca)
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
}
