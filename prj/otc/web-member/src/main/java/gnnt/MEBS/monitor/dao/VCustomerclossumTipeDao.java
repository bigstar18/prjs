package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.VCustomerclosesumTip;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("vCustomerclossumTipeDao")
public class VCustomerclossumTipeDao
  extends BaseDao
{
  public Class getEntityClass()
  {
    return VCustomerclosesumTip.class;
  }
  
  public List queryForList(final String sql)
  {
    List list = (List)getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        SQLQuery query = session.createSQLQuery(sql).addEntity(VCustomerclossumTipeDao.this.getEntityClass());
        return query.list();
      }
    });
    return list;
  }
}
