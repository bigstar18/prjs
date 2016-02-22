package gnnt.MEBS.timebargain.tradeweb.dao.jdbc;

import gnnt.MEBS.timebargain.tradeweb.dao.DAO;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class BaseDAOJdbc
  extends JdbcDaoSupport
  implements DAO
{
  protected final Log log = LogFactory.getLog(getClass());
  
  public void saveObject(Object o) {}
  
  public Object getObject(Class clazz, Serializable id)
  {
    return null;
  }
  
  public List getObjects(Class clazz)
  {
    return null;
  }
  
  public void removeObject(Class clazz, Serializable id) {}
}
