package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.DAO;
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
  
  protected String convertStringNull2Blank(String paramString)
  {
    return paramString == null ? "" : paramString;
  }
  
  protected Short convertShortNull2Zero(Short paramShort)
  {
    return paramShort == null ? new Short((short)0) : paramShort;
  }
  
  protected Double convertDoubleNull2Zero(Double paramDouble)
  {
    return paramDouble == null ? new Double(0.0D) : paramDouble;
  }
  
  protected Long convertLongNull2Zero(Long paramLong)
  {
    return paramLong == null ? new Long(0L) : paramLong;
  }
}
