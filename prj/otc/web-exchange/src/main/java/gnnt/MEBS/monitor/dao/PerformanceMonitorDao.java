package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.monitor.model.PerformanceMonitor;
import gnnt.MEBS.monitor.model.QOrganizationVO;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("performanceMonitorDao")
public class PerformanceMonitorDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(PerformanceMonitorDao.class);
  
  public Class getEntityClass()
  {
    return PerformanceMonitor.class;
  }
  
  public List queryForList(final String sql)
  {
    List list = (List)getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        SQLQuery query = session.createSQLQuery(sql).addEntity(PerformanceMonitorDao.this.getEntityClass());
        return query.list();
      }
    });
    return list;
  }
  
  public List queryForOrganizationList(final String sql)
  {
    List list = (List)getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        SQLQuery query = session.createSQLQuery(sql).addEntity(QOrganizationVO.class);
        return query.list();
      }
    });
    return list;
  }
}
