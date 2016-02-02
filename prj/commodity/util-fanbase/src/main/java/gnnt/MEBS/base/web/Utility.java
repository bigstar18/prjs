package gnnt.MEBS.base.web;

import javax.servlet.http.HttpServletRequest;

public class Utility
{
  public static Long[] getLongParameters(HttpServletRequest request, String name)
  {
    String[] strs = request.getParameterValues(name);
    Long[] longs = new Long[strs.length];
    for (int i = 0; i < strs.length; i++) {
      if ((strs[i] == null) || ("".equals(strs[i]))) {
        longs[i] = null;
      } else {
        try
        {
          longs[i] = new Long(strs[i]);
        }
        catch (Exception localException) {}
      }
    }
    return longs;
  }
}
