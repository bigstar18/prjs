package gnnt.MEBS.base.copy;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

public class MapToObject
{
  public static void bindData(Map<String, String> map, Object bean)
  {
    Method[] methodsBean = bean.getClass().getMethods();
    for (int i = 0; i < methodsBean.length; i++)
    {
      Method methodSet = methodsBean[i];
      String methodNameSet = methodSet.getName();
      if ("set".equals(methodNameSet.subSequence(0, 3))) {
        try
        {
          String funName = methodNameSet.substring(3);
          String key = funName.substring(0, 1).toLowerCase() + funName.substring(1);
          String value = (String)map.get(key);
          Method getMethod = null;
          try
          {
            getMethod = bean.getClass().getMethod("get" + funName, null);
          }
          catch (Exception localException1) {}
          if ((getMethod != null) && (value != null))
          {
            Class cl = getMethod.getReturnType();
            
            Method setMethod = bean.getClass().getMethod(methodNameSet, new Class[] { cl });
            if (cl == String.class)
            {
              setMethod.invoke(bean, new Object[] { value });
            }
            else if ((cl == Integer.class) || (cl == Float.class) || (cl == Long.class) || (cl == Double.class) || (cl == Byte.class) || (cl == Boolean.class) || (cl == Character.class))
            {
              Method valueOf = cl.getMethod("valueOf", new Class[] { String.class });
              Object valueObj = valueOf.invoke(cl, new Object[] { value });
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
              Object valueObj = valueOf.invoke(c2, new Object[] { value });
              setMethod.invoke(bean, new Object[] { valueObj });
            }
            else if ((cl == java.util.Date.class) || (cl == java.sql.Date.class))
            {
              SimpleDateFormat simpleDateFormat = null;
              String formatChar = null;
              formatChar = "yyyy-MM-dd";
              if (formatChar != null)
              {
                simpleDateFormat = new SimpleDateFormat(formatChar);
                java.util.Date date = simpleDateFormat.parse(value);
                Object dateObj = cl.newInstance();
                Method setTime = cl.getMethod("setTime", new Class[] { Long.TYPE });
                setTime.invoke(dateObj, new Object[] { Long.valueOf(date.getTime()) });
                setMethod.invoke(bean, new Object[] { dateObj });
              }
            }
            else if (cl == BigDecimal.class)
            {
              setMethod.invoke(bean, new Object[] { new BigDecimal(value) });
            }
            else if (cl == Timestamp.class)
            {
              SimpleDateFormat simpleDateFormat = null;
              String formatChar = null;
              formatChar = "yyyy-MM-dd HH:mm:ss";
              if (formatChar != null)
              {
                simpleDateFormat = new SimpleDateFormat(formatChar);
                java.util.Date date = simpleDateFormat.parse(value);
                


                Object dateObj = new Timestamp(date.getTime());
                setMethod.invoke(bean, new Object[] { dateObj });
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
