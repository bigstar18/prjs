package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfaPinzoneinfoDao;
import cn.com.agree.eteller.afa.persistence.AfaPinzoneinfo;
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

public class AfaPinzoneinfoDao
  extends HibernateDaoSupport
  implements IAfaPinzoneinfoDao
{
  public AfaPinzoneinfo[] loadAfaPinzoneinfo(final Map map)
  {
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(AfaPinzoneinfo.class);
        if (!map.get("sysid").equals("xxxxx")) {
          criteria.add(Expression.eq("comp_id.sysid", (String)map.get("sysid")));
        }
        if (!((String)map.get("zoneno")).equals("xxxxx")) {
          criteria.add(Expression.eq("comp_id.zoneno", (String)map.get("zoneno")));
        }
        if (!((String)map.get("tremid")).equals("xxxxx")) {
          criteria.add(Expression.eq("comp_id.tremid", (String)map.get("tremid")));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    return (AfaPinzoneinfo[])list.toArray(new AfaPinzoneinfo[0]);
  }
  
  public boolean addAfaPinzoneinfo(final AfaPinzoneinfo ca)
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
  
  public boolean deleteAfaPinzoneinfo(final AfaPinzoneinfo ca)
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
  
  public boolean updateAfaPinzoneinfo(AfaPinzoneinfo ca)
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
  
  public AfaPinzoneinfo getAfaPinzoneinfoBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (AfaPinzoneinfo)list.get(0);
  }
}
