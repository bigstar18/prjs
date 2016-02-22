package gnnt.MEBS.base.copy;

import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.util.AnnotationProperty;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ObjectToMap
{
  public static Map bindData(Object bean)
  {
    Method[] methodsBean = bean.getClass().getMethods();
    
    Map map = new HashMap();
    for (int i = 0; i < methodsBean.length; i++)
    {
      Method methodSet = methodsBean[i];
      String methodNameSet = methodSet.getName();
      if ("get".equals(methodNameSet.subSequence(0, 3))) {
        try
        {
          String funName = methodNameSet.substring(3);
          String key = funName.substring(0, 1).toLowerCase() + funName.substring(1);
          Method getMethod = null;
          try
          {
            getMethod = bean.getClass().getMethod("get" + funName, null);
          }
          catch (Exception localException1) {}
          if (getMethod != null) {
            map.put(key, getMethod.invoke(bean, null));
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    return map;
  }
  
  public static Map getMapFromObj(Object bean)
  {
    Method[] methodsBean = bean.getClass().getMethods();
    
    Map map = new HashMap();
    for (int i = 0; i < methodsBean.length; i++)
    {
      Method methodSet = methodsBean[i];
      String methodNameSet = methodSet.getName();
      if ("get".equals(methodNameSet.subSequence(0, 3))) {
        try
        {
          ClassDiscription cl = AnnotationProperty.getAnnotation(methodSet);
          if (cl != null)
          {
            String funName = methodNameSet.substring(3);
            String key = funName.substring(0, 1).toLowerCase() + funName.substring(1);
            Method getMethod = null;
            getMethod = bean.getClass().getMethod("get" + funName, null);
            if (getMethod != null) {
              map.put(key, getMethod.invoke(bean, null));
            }
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    return map;
  }
  
  public static Map getMapFromObjForParam(Object bean)
  {
    Method[] methodsBean = bean.getClass().getMethods();
    
    Map map = new HashMap();
    for (int i = 0; i < methodsBean.length; i++)
    {
      Method methodSet = methodsBean[i];
      String methodNameSet = methodSet.getName();
      if (("get".equals(methodNameSet.subSequence(0, 3))) && (methodNameSet.indexOf("_log") < 0)) {
        try
        {
          String funName = methodNameSet.substring(3);
          String key = funName.substring(0, 1).toLowerCase() + funName.substring(1);
          Method getMethod = null;
          getMethod = bean.getClass().getMethod("get" + funName, null);
          if (getMethod != null) {
            map.put(key, getMethod.invoke(bean, null));
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    return map;
  }
}
