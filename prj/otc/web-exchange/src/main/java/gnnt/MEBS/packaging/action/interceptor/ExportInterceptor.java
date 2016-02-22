package gnnt.MEBS.packaging.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.util.AnnotationProperty;
import gnnt.MEBS.base.model.util.CloneParameterValue;
import gnnt.MEBS.base.util.ThreadStore;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

public class ExportInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(ExportInterceptor.class);
  private List<String> listProperty;
  
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
      ScrollableResults cusor = (ScrollableResults)ThreadStore.get("ScrollableResults");
      Session session = (Session)ThreadStore.get("hibernateSession");
      


      PrintWriter out = (PrintWriter)ThreadStore.get("beforeBody_out");
      try
      {
        int i = 1;
        

        StringBuffer a = null;
        if (cusor != null) {
          while (cusor.next())
          {
            Object[] oArray = cusor.get();
            Clone clone = (Clone)oArray[0];
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
                  Object value = show(clone, key);
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
            oArray = (Object[])null;
            clone = null;
            a = null;
            if (i % 1000 == 0)
            {
              session.flush();
              session.clear();
            }
            if ((this.logger.isDebugEnabled()) && 
              (i % 10000 == 0)) {
              this.logger.debug(i + "万条");
            }
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
  
  private String show(Clone clone, String parameterName)
  {
    Method getMethod = null;
    String v = "";
    try
    {
      getMethod = clone.getClass().getMethod("get" + CloneParameterValue.getFunName(parameterName), null);
      Object value = getMethod.invoke(clone, new Object[0]);
      ClassDiscription me = AnnotationProperty.getAnnotation(getMethod);
      if (me != null) {
        if (me.isStatus())
        {
          Map<String, String> statusMap = AnnotationProperty.getStatusDescription(getMethod);
          String value1 = "";
          value1 = (String)statusMap.get(value.toString());
          if (value1 != null) {
            value = value1;
          }
        }
      }
      v = analysis(value);
    }
    catch (Exception localException) {}
    return v;
  }
  
  public static String analysis(Object object)
  {
    String result = "";
    if (object != null)
    {
      if (("".equals(object.toString().trim())) || ("null".equals(object.toString())))
      {
        result = " \t";
      }
      else if (object.getClass() == String.class)
      {
        result = "\"" + object.toString() + "\t\"";
      }
      else if (object.getClass() == Date.class)
      {
        DateFormat df = new SimpleDateFormat(
          "yyyy-MM-dd");
        result = df.format(object) + "\t";
      }
      else if (object.getClass() == Timestamp.class)
      {
        DateFormat df = new SimpleDateFormat(
          "yyyy-MM-dd HH:mm:ss");
        result = df.format(object) + "\t";
      }
      else if (object.getClass() == BigDecimal.class)
      {
        BigDecimal value = (BigDecimal)object;
        result = value.toString();
      }
      else if ((object.getClass() == Double.class) || (object.getClass() == Double.TYPE))
      {
        BigDecimal value = formatDecimals(new BigDecimal(((Double)object).doubleValue()), 2);
        result = value.toString() + " ";
      }
      else
      {
        result = object.toString();
      }
    }
    else {
      result = null;
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
}
