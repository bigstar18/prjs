package gnnt.MEBS.trade.dao;

import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.trade.model.SystemStatus;
import java.sql.SQLException;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("systemStatusDao")
public class SystemStatusDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(SystemStatusDao.class);
  
  public Class getEntityClass()
  {
    return new SystemStatus().getClass();
  }
  
  public String getSysdate()
  {
    String sql = "select to_char(sysdate,'yyyy-MM-dd hh24:mi:ss') from dual";
    (String)getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Query query = session.createSQLQuery("select to_char(sysdate,'yyyy-MM-dd hh24:mi:ss') from dual");
        return (String)query.uniqueResult();
      }
    });
  }
  
  public Date getSysdateSimple()
  {
    String sql = "select trunc(sysdate) from dual";
    (Date)getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Query query = session.createSQLQuery("select trunc(sysdate) from dual");
        return (Date)query.uniqueResult();
      }
    });
  }
}
