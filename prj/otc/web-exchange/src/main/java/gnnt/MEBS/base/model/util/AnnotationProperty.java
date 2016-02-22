package gnnt.MEBS.base.model.util;

import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import gnnt.MEBS.base.model.inner.StatusDiscription;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AnnotationProperty
{
  public static ClassDiscription getAnnotation(Method method)
  {
    ClassDiscription me = (ClassDiscription)method.getAnnotation(ClassDiscription.class);
    return me;
  }
  
  public static Map<String, String> getStatusDescription(Method method)
  {
    Map<String, String> map = null;
    ClassStatus status = (ClassStatus)method.getAnnotation(ClassStatus.class);
    if (status != null)
    {
      StatusDiscription[] Clas = status.status();
      map = new HashMap();
      for (StatusDiscription cla : Clas) {
        map.put(cla.key(), cla.value());
      }
    }
    return map;
  }
  
  public static String getStatusDescription(Class cla, String methodName, Serializable type)
    throws Exception
  {
    String statusName = "";
    String StrType = type;
    Method method = cla.getDeclaredMethod(methodName, null);
    Map<String, String> map = getStatusDescription(method);
    statusName = (String)map.get(StrType);
    return statusName;
  }
}
