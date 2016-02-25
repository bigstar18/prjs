package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.DAO;
import gnnt.MEBS.timebargain.manage.service.Manager;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseManager
  implements Manager
{
  protected final Log log = LogFactory.getLog(getClass());
  protected DAO dao = null;
  
  public void setDAO(DAO paramDAO)
  {
    this.dao = paramDAO;
  }
  
  public Object getObject(Class paramClass, Serializable paramSerializable)
  {
    return this.dao.getObject(paramClass, paramSerializable);
  }
  
  public List getObjects(Class paramClass)
  {
    return this.dao.getObjects(paramClass);
  }
  
  public void removeObject(Class paramClass, Serializable paramSerializable)
  {
    this.dao.removeObject(paramClass, paramSerializable);
  }
  
  public void saveObject(Object paramObject)
  {
    this.dao.saveObject(paramObject);
  }
}
