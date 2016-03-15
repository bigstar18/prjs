package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaHostfuncmapDao;
import cn.com.agree.eteller.afa.persistence.AfaHostfuncmap;
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

public class AfaHostfuncmapDao
  extends HibernateDaoSupport
  implements IAfaHostfuncmapDao
{
  public boolean addAfaHostfuncmap(final AfaHostfuncmap ca)
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
  
  public boolean deleteAfaHostfuncmap(final AfaHostfuncmap ca)
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
  
  public AfaHostfuncmap[] getAfaHostfuncmapBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(AfaHostfuncmap.class);
        if (!map.get("hosttype").toString().equals("00000")) {
          criteria.add(Expression.eq("hosttype", map.get("hosttype")));
        }
        if (!map.get("agentflag").toString().equals("00000")) {
          criteria.add(Expression.eq("agentflag", map
            .get("agentflag")));
        }
        if (!map.get("channelcode").toString().equals("00000")) {
          criteria.add(Expression.eq("channelcode", map
            .get("channelcode")));
        }
        if (!map.get("revtranf").toString().equals("00000")) {
          criteria.add(Expression.eq("revtranf", map.get("revtranf")));
        }
        if (!map.get("acctype").toString().equals("00000")) {
          criteria.add(Expression.eq("acctype", map.get("acctype")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaHostfuncmap[])list.toArray(new AfaHostfuncmap[0]);
  }
  
  public boolean updateAfaHostfuncmap(AfaHostfuncmap ca, AfaHostfuncmap ca_old)
  {
    boolean isUpdated = false;
    try
    {
      deleteAfaHostfuncmap(ca_old);
      addAfaHostfuncmap(ca);
      isUpdated = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      isUpdated = false;
    }
    return isUpdated;
  }
  
  public AfaHostfuncmap[] getAllafaHostfuncmap()
  {
    List ls = new ArrayList();
    try
    {
      String sql = "from AfaHostfuncmap order by hosttype asc";
      ls = getHibernateTemplate().find(sql);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaHostfuncmap[])ls.toArray(new AfaHostfuncmap[0]);
  }
  
  public AfaHostfuncmap getAfaHostfuncmapBysql(String sql)
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
    return (AfaHostfuncmap)list.get(0);
  }
}
