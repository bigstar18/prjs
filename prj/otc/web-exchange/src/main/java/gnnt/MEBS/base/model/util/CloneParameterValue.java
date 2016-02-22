package gnnt.MEBS.base.model.util;

import gnnt.MEBS.base.model.Clone;
import java.lang.reflect.Method;

public class CloneParameterValue
{
  public static Object getParameter(Clone clone, String parameterName)
  {
    Object value = null;
    if (clone != null)
    {
      Method getMethod = null;
      try
      {
        getMethod = clone.getClass().getMethod("get" + getFunName(parameterName), null);
        if (getMethod != null) {
          value = getMethod.invoke(clone, null);
        }
      }
      catch (Exception localException) {}
    }
    return value;
  }
  
  public static boolean judgeParameter(Clone clone, String parameterName)
  {
    boolean sign = true;
    if (clone != null)
    {
      Method getMethod = null;
      try
      {
        getMethod = clone.getClass().getMethod("get" + getFunName(parameterName), null);
        if (getMethod == null) {
          sign = false;
        }
      }
      catch (Exception localException) {}
    }
    return sign;
  }
  
  public static String getFunName(String objName)
  {
    String funName = objName.substring(0, 1).toUpperCase();
    if (objName.length() > 1) {
      funName = funName + objName.substring(1, objName.length());
    }
    return funName;
  }
}
