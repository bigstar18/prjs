package gnnt.MEBS.globalLog.dao;

import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.TimestampType;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("operateLogDao")
public class OperateLogDao
  extends BaseDao<OperateLog>
{
  private final transient Log logger = LogFactory.getLog(OperateLogDao.class);
  
  public Class getEntityClass()
  {
    return new OperateLog().getClass();
  }
  
  public Date getSysDate()
  {
    (Date)getHibernateTemplate().execute(
      new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws SQLException, HibernateException
        {
          Date sysdate = null;
          SQLQuery query = session.createSQLQuery("select sysdate  CRTDATE from dual");
          query.addScalar("CRTDATE", new TimestampType());
          List children = query.list();
          sysdate = (Date)children.iterator().next();
          OperateLogDao.this.releaseSession(session);
          return sysdate;
        }
      });
  }
}
