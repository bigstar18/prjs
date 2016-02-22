package gnnt.MEBS.timebargain.tradeweb.service.impl;

import gnnt.MEBS.timebargain.tradeweb.dao.DAO;
import gnnt.MEBS.timebargain.tradeweb.service.Manager;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseManager
  implements Manager
{
  protected final Log log = LogFactory.getLog(getClass());
  protected DAO dao = null;
  
  public void setDAO(DAO dao)
  {
    this.dao = dao;
  }
  
  public Object getObject(Class clazz, Serializable id)
  {
    return this.dao.getObject(clazz, id);
  }
  
  public List getObjects(Class clazz)
  {
    return this.dao.getObjects(clazz);
  }
  
  public void removeObject(Class clazz, Serializable id)
  {
    this.dao.removeObject(clazz, id);
  }
  
  public void saveObject(Object o)
  {
    this.dao.saveObject(o);
  }
}
