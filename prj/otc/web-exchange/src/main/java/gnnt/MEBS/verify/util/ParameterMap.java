package gnnt.MEBS.verify.util;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletRequest;

public class ParameterMap
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
            params.put(unprefixed, values[0]);
          }
        }
      }
    }
    return params;
  }
}
