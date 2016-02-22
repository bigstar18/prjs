package gnnt.MEBS.packaging.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.util.ThreadStore;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcExportInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(JdbcExportInterceptor.class);
  private List<String> listProperty;
  private String classFullName;
  
  public void setClassFullName(String classFullName)
  {
    this.classFullName = classFullName;
  }
  
  public void setListProperty(List<String> listProperty)
  {
    this.listProperty = listProperty;
  }
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    ThreadStore.put("reloadExport", "Y");
    String result = invocation.invoke();
    this.logger.debug("exportAll:" + ThreadStore.get("exportAll"));
    if (ThreadStore.get("exportAll") != null)
    {
      SqlRowSet sqlRowSet = (SqlRowSet)ThreadStore.get("sqlRowSet");
      

      PrintWriter out = (PrintWriter)ThreadStore.get("beforeBody_out");
      











      Clone clone = getObject();
      try
      {
        int i = 1;
        StringBuffer a = null;
        while (sqlRowSet.next())
        {
          if (this.listProperty != null)
          {
            a = new StringBuffer();
            for (String key : this.listProperty) {
              if ("_0".equals(key))
              {
                a.append(i + "\t,");
              }
              else
              {
                String value = "";
                Class cl = getReturnType(clone, key);
                if (cl.equals(String.class))
                {
                  value = "\"" + sqlRowSet.getString(key) + "\t\"";
                }
                else if ((cl.equals(Double.class)) || (cl.equals(Double.TYPE)))
                {
                  double v = sqlRowSet.getDouble(key);
                  value = formatDecimals(new BigDecimal(Double.valueOf(v).doubleValue()), 2).toString() + " ";
                }
                else if ((cl.equals(Integer.class)) || (cl.equals(Integer.TYPE)))
                {
                  int v = sqlRowSet.getInt(key);
                  value = v + " ";
                }
                else if ((cl.equals(Float.class)) || (cl.equals(Float.TYPE)))
                {
                  float v = sqlRowSet.getFloat(key);
                  value = v + " ";
                }
                else if ((cl.equals(Long.class)) || (cl.equals(Long.TYPE)))
                {
                  long v = sqlRowSet.getLong(key);
                  value = v + " ";
                }
                else if (cl.equals(Date.class))
                {
                  Date v = sqlRowSet.getDate(key);
                  DateFormat df = new SimpleDateFormat(
                    "yyyy-MM-dd");
                  value = df.format(v) + "\t";
                }
                else if (cl.equals(BigDecimal.class))
                {
                  BigDecimal v = sqlRowSet.getBigDecimal(key);
                  value = v.toString();
                }
                else if (cl.equals(Timestamp.class))
                {
                  Timestamp v = sqlRowSet.getTimestamp(key);
                  DateFormat df = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
                  value = df.format(v) + "\t";
                }
                if (value != null) {
                  a.append(value.toString() + ",");
                } else {
                  a.append(" ,");
                }
              }
            }
            if ((this.listProperty.size() > 0) && (a.length() > 0)) {
              out.print(a.substring(0, a.length() - 1) + "\r\n");
            }
            i++;
          }
          clone = null;
          a = null;
          if ((this.logger.isDebugEnabled()) && 
            (i % 10000 == 0)) {
            this.logger.debug(i + "万条");
          }
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      finally
      {
        out.flush();
        out.close();
      }
    }
    return result;
  }
  
  private static BigDecimal formatDecimals(BigDecimal value, int scale)
  {
    BigDecimal valueFormat = null;
    if (value != null) {
      valueFormat = value.setScale(scale, 4);
    }
    return valueFormat;
  }
  
  private Clone getObject()
  {
    Clone clone = null;
    Class classType = null;
    try
    {
      classType = Class.forName(this.classFullName);
      clone = (Clone)classType.newInstance();
    }
    catch (InstantiationException e)
    {
      e.printStackTrace();
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    return clone;
  }
  
  private Class getReturnType(Clone clone, String key)
  {
    Class cl = null;
    try
    {
      Method getMethod = clone.getClass().getMethod("get" + key, null);
      cl = getMethod.getReturnType();
    }
    catch (SecurityException e)
    {
      e.printStackTrace();
    }
    catch (NoSuchMethodException e)
    {
      e.printStackTrace();
    }
    return cl;
  }
}
