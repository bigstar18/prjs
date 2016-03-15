package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaZhbrnoinfoDao;
import cn.com.agree.eteller.afa.persistence.AfaZhbrnoinfo;
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

public class AfaZhbrnoinfoDao
  extends HibernateDaoSupport
  implements IAfaZhbrnoinfoDao
{
  public boolean addAfaZhbrnoinfo(final AfaZhbrnoinfo ca)
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
  
  public boolean deleteAfaZhbrnoinfo(final AfaZhbrnoinfo ca)
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
  
  public AfaZhbrnoinfo[] getAfaZhbrnoinfoBymap(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(AfaZhbrnoinfo.class);
        if (!map.get("zhno").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.zhno", map.get("zhno")));
        }
        if (!map.get("brno").toString().equals("00000")) {
          criteria.add(Expression.eq("comp_id.brno", map.get("brno")));
        }
        if (!map.get("brname").toString().equals("00000"))
        {
          String tmpStr = "%" + map.get("brname") + "%";
          criteria.add(Expression.like("brname", tmpStr));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaZhbrnoinfo[])list.toArray(new AfaZhbrnoinfo[0]);
  }
  
  public AfaZhbrnoinfo[] getAllAfaZhbrnoinfo()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(AfaZhbrnoinfo.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfaZhbrnoinfo[])ls.toArray(new AfaZhbrnoinfo[0]);
  }
  
  public boolean updateAfaZhbrnoinfo(AfaZhbrnoinfo ca)
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
