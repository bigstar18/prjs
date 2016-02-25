package gnnt.MEBS.base.copy;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

public class CopyObjectParamUtil
{
  public static void bindData(Object paramObject1, Object paramObject2)
  {
    Method[] arrayOfMethod = paramObject2.getClass().getMethods();
    for (int i = 0; i < arrayOfMethod.length; i++)
    {
      Method localMethod1 = arrayOfMethod[i];
      String str1 = localMethod1.getName();
      if ("set".equals(str1.subSequence(0, 3))) {
        try
        {
          String str2 = str1.substring(3);
          Method localMethod2 = null;
          try
          {
            localMethod2 = paramObject1.getClass().getMethod("get" + str2, null);
          }
          catch (Exception localException2) {}
          if (localMethod2 != null)
          {
            Class localClass = localMethod2.getReturnType();
            Object localObject1 = localMethod2.invoke(paramObject1, null);
            if (localObject1 != null)
            {
              Method localMethod3 = paramObject2.getClass().getMethod(str1, new Class[] { localClass });
              if (localClass == String.class)
              {
                localMethod3.invoke(paramObject2, new Object[] { localObject1.toString() });
              }
              else
              {
                Object localObject2;
                Object localObject3;
                if ((localClass == Integer.class) || (localClass == Float.class) || (localClass == Long.class) || (localClass == Double.class) || (localClass == Byte.class) || (localClass == Boolean.class) || (localClass == Character.class))
                {
                  localObject2 = localClass.getMethod("valueOf", new Class[] { String.class });
                  localObject3 = ((Method)localObject2).invoke(localClass, new Object[] { localObject1.toString() });
                  localMethod3.invoke(paramObject2, new Object[] { localObject3 });
                }
                else
                {
                  Object localObject4;
                  if ((localClass == Integer.TYPE) || (localClass == Float.TYPE) || (localClass == Long.TYPE) || (localClass == Double.TYPE))
                  {
                    localObject2 = null;
                    if (localClass == Integer.TYPE) {
                      localObject2 = Integer.class;
                    } else if (localClass == Float.TYPE) {
                      localObject2 = Float.class;
                    } else if (localClass == Long.TYPE) {
                      localObject2 = Long.class;
                    } else if (localClass == Double.TYPE) {
                      localObject2 = Double.class;
                    }
                    localObject3 = ((Class)localObject2).getMethod("valueOf", new Class[] { String.class });
                    localObject4 = ((Method)localObject3).invoke(localObject2, new Object[] { localObject1.toString() });
                    localMethod3.invoke(paramObject2, new Object[] { localObject4 });
                  }
                  else if ((localClass == java.util.Date.class) || (localClass == java.sql.Date.class))
                  {
                    localObject2 = null;
                    localObject3 = null;
                    localObject3 = "yyyy-MM-dd HH:mm:ss";
                    if (localObject3 != null)
                    {
                      localObject2 = new SimpleDateFormat((String)localObject3);
                      localObject4 = ((SimpleDateFormat)localObject2).format(localObject1);
                      java.util.Date localDate = ((SimpleDateFormat)localObject2).parse((String)localObject4);
                      Object localObject5 = localClass.newInstance();
                      Method localMethod4 = localClass.getMethod("setTime", new Class[] { Long.TYPE });
                      localMethod4.invoke(localObject5, new Object[] { Long.valueOf(localDate.getTime()) });
                      localMethod3.invoke(paramObject2, new Object[] { localObject5 });
                    }
                  }
                }
              }
            }
          }
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
        }
      }
    }
  }
}
