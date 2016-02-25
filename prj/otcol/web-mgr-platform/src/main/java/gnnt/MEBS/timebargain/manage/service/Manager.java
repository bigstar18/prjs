package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.dao.DAO;
import java.io.Serializable;
import java.util.List;

public abstract interface Manager
{
  public abstract void setDAO(DAO paramDAO);
  
  public abstract List getObjects(Class paramClass);
  
  public abstract Object getObject(Class paramClass, Serializable paramSerializable);
  
  public abstract void saveObject(Object paramObject);
  
  public abstract void removeObject(Class paramClass, Serializable paramSerializable);
}
