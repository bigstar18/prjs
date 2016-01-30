package gnnt.MEBS.common.front.dao;

import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository("com_queryDao")
public class QueryDao
  extends AbstractDao
{
  protected final transient Log logger = LogFactory.getLog(getClass());
  
  @Resource(name="sessionFactoryForQuery")
  public void setSuperSessionFactory(SessionFactory paramSessionFactory)
  {
    super.setSessionFactory(paramSessionFactory);
  }
}
