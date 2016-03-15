package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaZhinfoDao;
import cn.com.agree.eteller.afa.persistence.AfaZhinfo;
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

public class AfaZhinfoDao
  extends HibernateDaoSupport
  implements IAfaZhinfoDao
{
  public boolean addAfaZhinfo(final AfaZhinfo ca)
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
  
  public boolean deleteAfaZhinfo(final AfaZhinfo ca)
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
  
  public AfaZhinfo[] getAfaZhinfoBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(AfaZhinfo.class);
        if (!map.get("upzoneno").toString().equals("00000")) {
          criteria.add(Expression.eq("upzoneno", map.get("upzoneno")));
        }
        if (!map.get("zhno").toString().equals("00000")) {
          criteria.add(Expression.eq("zhno", map.get("zhno")));
        }
        if (!map.get("name").toString().equals("00000"))
        {
          String tmpStr = "%" + map.get("name") + "%";
          criteria.add(Expression.like("name", tmpStr));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaZhinfo[])list.toArray(new AfaZhinfo[0]);
  }
  
  public AfaZhinfo[] getAllAfaZhinfo()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(AfaZhinfo.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaZhinfo[])ls.toArray(new AfaZhinfo[0]);
  }
  
  public boolean updateAfaZhinfo(AfaZhinfo ca)
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
