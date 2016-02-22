package gnnt.MEBS.monitor.dao;

import javax.annotation.Resource;
import org.hibernate.SessionFactory;

public abstract class BaseDao
  extends gnnt.MEBS.packaging.dao.BaseDao
{
  @Resource(name="sessionFactoryForMonitor")
  public void setSuperSessionFactory(SessionFactory sessionFactory)
  {
    super.setSessionFactory(sessionFactory);
  }
}
