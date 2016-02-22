package gnnt.MEBS.report.dao;

import gnnt.MEBS.packaging.dao.BaseDao;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;

public abstract class QueryReportDao
  extends BaseDao
{
  @Resource(name="sessionFactoryForQuery")
  public void setSuperSessionFactory(SessionFactory sessionFactory)
  {
    super.setSessionFactory(sessionFactory);
  }
}
