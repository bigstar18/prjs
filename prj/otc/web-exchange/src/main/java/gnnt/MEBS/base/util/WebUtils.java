package gnnt.MEBS.base.util;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletRequest;

public class WebUtils
{
  public static Map getParametersStartingWith(ServletRequest request, String prefix)
  {
    Enumeration paramNames = request.getParameterNames();
    Map params = new TreeMap();
    if (prefix == null) {
      prefix = "";
    }
    while ((paramNames != null) && (paramNames.hasMoreElements()))
    {
      String paramName = (String)paramNames.nextElement();
      if (("".equals(prefix)) || (paramName.startsWith(prefix)))
      {
        String unprefixed = paramName.substring(prefix.length());
        String[] values = request.getParameterValues(paramName);
        if ((values != null) && 
          (values.length != 0)) {
          if (values.length > 1) {
            params.put(unprefixed, values);
          } else {
            params.put(unprefixed, values[0].trim());
          }
        }
      }
    }
    return params;
  }
  
  public static Map getAttributeStartingWith(ServletRequest request, String prefix)
  {
    Enumeration paramNames = request.getAttributeNames();
    Map params = new TreeMap();
    if (prefix == null) {
      prefix = "";
    }
    while ((paramNames != null) && (paramNames.hasMoreElements()))
    {
      String paramName = (String)paramNames.nextElement();
      if (("".equals(prefix)) || (paramName.startsWith(prefix)))
      {
        String unprefixed = paramName.substring(prefix.length());
        String value = (String)request.getAttribute(paramName);
        if (value != null) {
          params.put(unprefixed, value.trim());
        }
      }
    }
    return params;
  }
}
