package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VCustomerclosesumSimple;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("vCustomerclosesumSimpleDao")
public class VCustomerclosesumSimpleDao
  extends BaseDao
{
  public Class getEntityClass()
  {
    return VCustomerclosesumSimple.class;
  }
  
  public List queryForList(final String sql)
  {
    List list = (List)getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        SQLQuery query = session.createSQLQuery(sql).addEntity(VCustomerclosesumSimpleDao.this.getEntityClass());
        return query.list();
      }
    });
    return list;
  }
}
