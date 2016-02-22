package gnnt.MEBS.base.copy;

import gnnt.MEBS.base.model.Clone;
import java.lang.reflect.Field;
import java.math.BigDecimal;
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
  
  public Object mapRow(ResultSet rs, int arg1)
    throws SQLException
  {
    return rsToObject(rs);
  }
  
  private Object rsToObject(ResultSet rs)
    throws SQLException
  {
    Class objClass = this.object.getClass();
    Clone obj = (Clone)this.object.clone();
    Field[] fields = objClass.getDeclaredFields();
    for (int i = 0; i < fields.length; i++)
    {
      fields[i].setAccessible(true);
      String methodname = "set" + fields[i].getName().substring(0, 1).toUpperCase() + fields[i].getName().substring(1, fields[i].getName().length());
      try
      {
        objClass.getDeclaredMethod(methodname, new Class[] { fields[i].getType() });
      }
      catch (SecurityException e)
      {
        e.printStackTrace();
      }
      catch (NoSuchMethodException e)
      {
        continue;
      }
      try
      {
        if (fields[i].getType().equals(String.class))
        {
          this.logger.debug(fields[i].getName());
          fields[i].set(obj, rs.getString(fields[i].getName()));
          this.logger.debug(fields[i].getName() + ":" + fields[i].get(obj));
        }
        else if (fields[i].getType().equals(Double.class))
        {
          fields[i].set(obj, new Double(rs.getDouble(fields[i].getName())));
        }
        else if (fields[i].getType().equals(Integer.class))
        {
          fields[i].set(obj, Integer.valueOf(rs.getInt(fields[i].getName())));
        }
        else if (fields[i].getType().equals(Float.class))
        {
          fields[i].set(obj, new Float(rs.getFloat(fields[i].getName())));
        }
        else if (fields[i].getType().equals(Long.class))
        {
          fields[i].set(obj, new Long(rs.getLong(fields[i].getName())));
        }
        else if (fields[i].getType().equals(Date.class))
        {
          fields[i].set(obj, rs.getTimestamp(fields[i].getName()));
        }
        else if (fields[i].getType().equals(Double.TYPE))
        {
          fields[i].set(obj, Double.valueOf(rs.getDouble(fields[i].getName())));
        }
        else if (fields[i].getType().equals(Integer.TYPE))
        {
          fields[i].set(obj, Integer.valueOf(rs.getInt(fields[i].getName())));
        }
        else if (fields[i].getType().equals(Float.TYPE))
        {
          fields[i].set(obj, Float.valueOf(rs.getFloat(fields[i].getName())));
        }
        else if (fields[i].getType().equals(Long.TYPE))
        {
          fields[i].set(obj, Long.valueOf(rs.getLong(fields[i].getName())));
        }
        else if (fields[i].getType().equals(BigDecimal.class))
        {
          fields[i].set(obj, rs.getBigDecimal(fields[i].getName()));
        }
        else
        {
          fields[i].set(obj, rs.getObject(fields[i].getName()));
        }
      }
      catch (Exception e)
      {
        this.logger.error("name:" + fields[i] + " " + fields[i].getType());
      }
    }
    return obj;
  }
  
  public CommonRowMapper(Clone o)
  {
    this.object = o;
  }
}
