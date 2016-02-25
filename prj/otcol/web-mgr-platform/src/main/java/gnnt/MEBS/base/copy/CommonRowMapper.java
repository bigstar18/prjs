package gnnt.MEBS.base.copy;

import gnnt.MEBS.base.model.Clone;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;

public class CommonRowMapper
  implements RowMapper
{
  private final transient Log logger = LogFactory.getLog(CommonRowMapper.class);
  private Clone object;
  
  public Object mapRow(ResultSet paramResultSet, int paramInt)
    throws SQLException
  {
    return rsToObject(paramResultSet);
  }
  
  private Object rsToObject(ResultSet paramResultSet)
    throws SQLException
  {
    Class localClass = this.object.getClass();
    Clone localClone = (Clone)this.object.clone();
    Field[] arrayOfField = localClass.getDeclaredFields();
    for (int i = 0; i < arrayOfField.length; i++)
    {
      arrayOfField[i].setAccessible(true);
      String str = "set" + arrayOfField[i].getName().substring(0, 1).toUpperCase() + arrayOfField[i].getName().substring(1, arrayOfField[i].getName().length());
      try
      {
        localClass.getDeclaredMethod(str, new Class[] { arrayOfField[i].getType() });
      }
      catch (SecurityException localSecurityException)
      {
        localSecurityException.printStackTrace();
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        continue;
      }
      try
      {
        if (arrayOfField[i].getType().equals(String.class))
        {
          this.logger.debug(arrayOfField[i].getName());
          arrayOfField[i].set(localClone, paramResultSet.getString(arrayOfField[i].getName()));
          this.logger.debug(arrayOfField[i].getName() + ":" + arrayOfField[i].get(localClone));
        }
        else if (arrayOfField[i].getType().equals(Double.class))
        {
          arrayOfField[i].set(localClone, new Double(paramResultSet.getDouble(arrayOfField[i].getName())));
        }
        else if (arrayOfField[i].getType().equals(Integer.class))
        {
          arrayOfField[i].set(localClone, Integer.valueOf(paramResultSet.getInt(arrayOfField[i].getName())));
        }
        else if (arrayOfField[i].getType().equals(Float.class))
        {
          arrayOfField[i].set(localClone, new Float(paramResultSet.getFloat(arrayOfField[i].getName())));
        }
        else if (arrayOfField[i].getType().equals(Date.class))
        {
          arrayOfField[i].set(localClone, paramResultSet.getTimestamp(arrayOfField[i].getName()));
        }
        else if (arrayOfField[i].getType().equals(Double.TYPE))
        {
          arrayOfField[i].set(localClone, Double.valueOf(paramResultSet.getDouble(arrayOfField[i].getName())));
        }
        else if (arrayOfField[i].getType().equals(Integer.TYPE))
        {
          arrayOfField[i].set(localClone, Integer.valueOf(paramResultSet.getInt(arrayOfField[i].getName())));
        }
        else if (arrayOfField[i].getType().equals(Float.TYPE))
        {
          arrayOfField[i].set(localClone, Float.valueOf(paramResultSet.getFloat(arrayOfField[i].getName())));
        }
        else if (arrayOfField[i].getType().equals(Long.TYPE))
        {
          arrayOfField[i].set(localClone, Long.valueOf(paramResultSet.getLong(arrayOfField[i].getName())));
        }
        else
        {
          arrayOfField[i].set(localClone, paramResultSet.getObject(arrayOfField[i].getName()));
        }
      }
      catch (Exception localException)
      {
        this.logger.error("name:" + arrayOfField[i] + " " + arrayOfField[i].getType());
      }
    }
    return localClone;
  }
  
  public CommonRowMapper(Clone paramClone)
  {
    this.object = paramClone;
  }
}
