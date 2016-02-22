package gnnt.MEBS.base.copy;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CopyObjectParamUtil
{
  public static void bindData(Object obj, Object bean)
  {
    Method[] methodsBean = bean.getClass().getMethods();
    for (int i = 0; i < methodsBean.length; i++)
    {
      Method methodSet = methodsBean[i];
      String methodNameSet = methodSet.getName();
      if (("set".equals(methodNameSet.subSequence(0, 3))) && (methodNameSet.indexOf("_v") < 0) && (methodNameSet.indexOf("_log") < 0)) {
        try
        {
          String funName = methodNameSet.substring(3);
          Method getMethod = null;
          try
          {
            getMethod = obj.getClass().getMethod("get" + funName, null);
          }
          catch (Exception localException1) {}
          if (getMethod != null)
          {
            Class cl = getMethod.getReturnType();
            Object object = getMethod.invoke(obj, null);
            if (object != null)
            {
              Method setMethod = bean.getClass().getMethod(methodNameSet, new Class[] { cl });
              if (cl == String.class)
              {
                setMethod.invoke(bean, new Object[] { object.toString() });
              }
              else if ((cl == Integer.class) || (cl == Float.class) || (cl == Long.class) || (cl == Double.class) || (cl == Byte.class) || (cl == Boolean.class) || (cl == Character.class))
              {
                Method valueOf = cl.getMethod("valueOf", new Class[] { String.class });
                Object valueObj = valueOf.invoke(cl, new Object[] { object.toString() });
                setMethod.invoke(bean, new Object[] { valueObj });
              }
              else if ((cl == Integer.TYPE) || (cl == Float.TYPE) || (cl == Long.TYPE) || (cl == Double.TYPE))
              {
                Class c2 = null;
                if (cl == Integer.TYPE) {
                  c2 = Integer.class;
                } else if (cl == Float.TYPE) {
                  c2 = Float.class;
                } else if (cl == Long.TYPE) {
                  c2 = Long.class;
                } else if (cl == Double.TYPE) {
                  c2 = Double.class;
                }
                Method valueOf = c2.getMethod("valueOf", new Class[] { String.class });
                Object valueObj = valueOf.invoke(c2, new Object[] { object.toString() });
                setMethod.invoke(bean, new Object[] { valueObj });
              }
              else if (cl == Date.class)
              {
                SimpleDateFormat simpleDateFormat = null;
                String formatChar = null;
                formatChar = "yyyy-MM-dd HH:mm:ss";
                if (formatChar != null)
                {
                  simpleDateFormat = new SimpleDateFormat(formatChar);
                  String str = simpleDateFormat.format(object);
                  Date date = simpleDateFormat.parse(str);
                  Object dateObj = cl.newInstance();
                  Method setTime = cl.getMethod("setTime", new Class[] { Long.TYPE });
                  setTime.invoke(dateObj, new Object[] { Long.valueOf(date.getTime()) });
                  setMethod.invoke(bean, new Object[] { dateObj });
                }
              }
              else if (cl == BigDecimal.class)
              {
                setMethod.invoke(bean, new Object[] { object });
              }
              else if (cl == Timestamp.class)
              {
                setMethod.invoke(bean, new Object[] { object });
              }
            }
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
  }
}
