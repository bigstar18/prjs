package gnnt.MEBS.query.dao;

import gnnt.MEBS.packaging.dao.BaseDao;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;

public abstract class QueryBaseDao
  extends BaseDao
{
  @Resource(name="sessionFactoryForQuery")
  public void setSuperSessionFactory(SessionFactory sessionFactory)
  {
    super.setSessionFactory(sessionFactory);
  }
}
