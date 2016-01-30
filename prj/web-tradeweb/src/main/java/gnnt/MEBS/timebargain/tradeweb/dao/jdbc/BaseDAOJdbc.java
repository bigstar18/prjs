package gnnt.MEBS.timebargain.tradeweb.dao.jdbc;

import gnnt.MEBS.timebargain.tradeweb.dao.DAO;
import gnnt.MEBS.timebargain.tradeweb.dao.DaoHelper;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseDAOJdbc
  extends DaoHelper
  implements DAO
{
  protected final Log log = LogFactory.getLog(getClass());
  
  public void saveObject(Object paramObject) {}
  
  public Object getObject(Class paramClass, Serializable paramSerializable)
  {
    return null;
  }
  
  public List getObjects(Class paramClass)
  {
    return null;
  }
  
  public void removeObject(Class paramClass, Serializable paramSerializable) {}
}
