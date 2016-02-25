package gnnt.MEBS.finance.base.web;

import javax.servlet.http.HttpServletRequest;

public class Utility
{
  public static Long[] getLongParameters(HttpServletRequest paramHttpServletRequest, String paramString)
  {
    String[] arrayOfString = paramHttpServletRequest.getParameterValues(paramString);
    Long[] arrayOfLong = new Long[arrayOfString.length];
    for (int i = 0; i < arrayOfString.length; i++) {
      if ((arrayOfString[i] == null) || ("".equals(arrayOfString[i]))) {
        arrayOfLong[i] = null;
      } else {
        try
        {
          arrayOfLong[i] = new Long(arrayOfString[i]);
        }
        catch (Exception localException) {}
      }
    }
    return arrayOfLong;
  }
}
