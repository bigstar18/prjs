package gnnt.MEBS.logonService.po.relect;

import gnnt.MEBS.logonService.po.Clone;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResultSetToBeanByField
  implements IResultSetToBean
{
  private Log logger = LogFactory.getLog(ResultSetToBeanByField.class);

  public Clone resultSetToBean(Clone paramClone, ResultSet paramResultSet)
  {
    Class localClass = paramClone.getClass();
    Clone localClone = (Clone)paramClone.clone();
    Field[] arrayOfField1 = localClass.getDeclaredFields();
    for (Field localField : arrayOfField1)
    {
      try
      {
        paramResultSet.findColumn(localField.getName());
      }
      catch (Exception localException)
      {
        continue;
      }
      boolean bool = localField.isAccessible();
      localField.setAccessible(true);
      setFieldValue(localClone, localField, paramResultSet);
      localField.setAccessible(bool);
    }
    return localClone;
  }

  public void setFieldValue(Object paramObject, Field paramField, ResultSet paramResultSet)
  {
    try
    {
      if (paramField.getType().equals(String.class))
        paramField.set(paramObject, paramResultSet.getString(paramField.getName()));
      else if (paramField.getType().equals(Double.class))
        paramField.set(paramObject, new Double(paramResultSet.getDouble(paramField.getName())));
      else if (paramField.getType().equals(Integer.class))
        paramField.set(paramObject, Integer.valueOf(paramResultSet.getInt(paramField.getName())));
      else if (paramField.getType().equals(Float.class))
        paramField.set(paramObject, new Float(paramResultSet.getFloat(paramField.getName())));
      else if (paramField.getType().equals(java.util.Date.class))
        paramField.set(paramObject, paramResultSet.getTimestamp(paramField.getName()));
      else if (paramField.getType().equals(java.sql.Date.class))
      {
        if (paramResultSet.getTimestamp(paramField.getName()) != null)
          paramField.set(paramObject, new java.sql.Date(paramResultSet.getTimestamp(paramField.getName()).getTime()));
      }
      else if (paramField.getType().equals(Double.TYPE))
        paramField.set(paramObject, Double.valueOf(paramResultSet.getDouble(paramField.getName())));
      else if (paramField.getType().equals(Integer.TYPE))
        paramField.set(paramObject, Integer.valueOf(paramResultSet.getInt(paramField.getName())));
      else if (paramField.getType().equals(Float.TYPE))
        paramField.set(paramObject, Float.valueOf(paramResultSet.getFloat(paramField.getName())));
      else if (paramField.getType().equals(Long.TYPE))
        paramField.set(paramObject, Long.valueOf(paramResultSet.getLong(paramField.getName())));
      else
        paramField.set(paramObject, paramResultSet.getObject(paramField.getName()));
      this.logger.debug(paramField.getName() + ":" + paramField.get(paramObject));
    }
    catch (Exception localException)
    {
      this.logger.error("name:" + paramField + " " + paramField.getType());
      this.logger.error(localException);
    }
  }

  public static void setFieldValue(Object paramObject, Field paramField, String paramString)
  {
    String str = paramField.getType().toString();
    if ((str.indexOf("boolean") != -1) || (str.indexOf("Boolean") != -1))
      try
      {
        paramField.set(paramObject, Boolean.valueOf(paramString));
      }
      catch (IllegalAccessException localIllegalAccessException1)
      {
        localIllegalAccessException1.printStackTrace();
      }
    else if ((str.indexOf("byte") != -1) || (str.indexOf("Byte") != -1))
      try
      {
        paramField.set(paramObject, Byte.valueOf(paramString));
      }
      catch (IllegalAccessException localIllegalAccessException2)
      {
        localIllegalAccessException2.printStackTrace();
      }
    else if ((str.indexOf("char") != -1) || (str.indexOf("Character") != -1))
      try
      {
        paramField.set(paramObject, Character.valueOf(paramString.charAt(0)));
      }
      catch (IllegalAccessException localIllegalAccessException3)
      {
        localIllegalAccessException3.printStackTrace();
      }
    else if ((str.indexOf("double") != -1) || (str.indexOf("Double") != -1))
      try
      {
        paramField.set(paramObject, Double.valueOf(paramString));
      }
      catch (IllegalAccessException localIllegalAccessException4)
      {
        localIllegalAccessException4.printStackTrace();
      }
    else if ((str.indexOf("float") != -1) || (str.indexOf("Float") != -1))
      try
      {
        paramField.set(paramObject, Float.valueOf(paramString));
      }
      catch (IllegalAccessException localIllegalAccessException5)
      {
        localIllegalAccessException5.printStackTrace();
      }
    else if ((str.indexOf("int") != -1) || (str.indexOf("Integer") != -1))
      try
      {
        paramField.set(paramObject, Integer.valueOf(paramString));
      }
      catch (IllegalAccessException localIllegalAccessException6)
      {
        localIllegalAccessException6.printStackTrace();
      }
    else if ((str.indexOf("long") != -1) || (str.indexOf("Long") != -1))
      try
      {
        paramField.set(paramObject, Long.valueOf(paramString));
      }
      catch (IllegalAccessException localIllegalAccessException7)
      {
        localIllegalAccessException7.printStackTrace();
      }
    else if ((str.indexOf("short") != -1) || (str.indexOf("Short") != -1))
      try
      {
        paramField.set(paramObject, Short.valueOf(paramString));
      }
      catch (IllegalAccessException localIllegalAccessException8)
      {
        localIllegalAccessException8.printStackTrace();
      }
    else
      try
      {
        paramField.set(paramObject, paramString);
      }
      catch (IllegalAccessException localIllegalAccessException9)
      {
        localIllegalAccessException9.printStackTrace();
      }
  }

  public static void setFieldValueBySet(Object paramObject, Field paramField, String paramString)
  {
    Class localClass = paramField.getType();
    Method[] arrayOfMethod = paramObject.getClass().getMethods();
    for (int i = 0; i < arrayOfMethod.length; i++)
    {
      Method localMethod1 = arrayOfMethod[i];
      String str = localMethod1.getName();
      if ("set".equals(str.subSequence(0, 3)))
        try
        {
          Method localMethod2 = paramObject.getClass().getMethod(str, new Class[] { localClass });
          if (localClass == String.class)
          {
            localMethod2.invoke(paramObject, new Object[] { paramString });
          }
          else
          {
            Object localObject1;
            Object localObject2;
            if ((localClass == Integer.class) || (localClass == Float.class) || (localClass == Long.class) || (localClass == Double.class) || (localClass == Byte.class) || (localClass == Boolean.class) || (localClass == Character.class))
            {
              localObject1 = localClass.getMethod("valueOf", new Class[] { String.class });
              localObject2 = ((Method)localObject1).invoke(localClass, new Object[] { paramString });
              localMethod2.invoke(paramObject, new Object[] { localObject2 });
            }
            else
            {
              Object localObject3;
              if ((localClass == Integer.TYPE) || (localClass == Float.TYPE) || (localClass == Long.TYPE) || (localClass == Double.TYPE))
              {
                localObject1 = null;
                if (localClass == Integer.TYPE)
                  localObject1 = Integer.class;
                else if (localClass == Float.TYPE)
                  localObject1 = Float.class;
                else if (localClass == Long.TYPE)
                  localObject1 = Long.class;
                else if (localClass == Double.TYPE)
                  localObject1 = Double.class;
                localObject2 = ((Class)localObject1).getMethod("valueOf", new Class[] { String.class });
                localObject3 = ((Method)localObject2).invoke(localObject1, new Object[] { paramString });
                localMethod2.invoke(paramObject, new Object[] { localObject3 });
              }
              else if ((localClass == java.util.Date.class) || (localClass == java.sql.Date.class))
              {
                localObject1 = null;
                localObject2 = null;
                localObject2 = "yyyy-MM-dd";
                if (localObject2 != null)
                {
                  localObject1 = new SimpleDateFormat((String)localObject2);
                  localObject3 = ((SimpleDateFormat)localObject1).format(paramString);
                  java.util.Date localDate = ((SimpleDateFormat)localObject1).parse((String)localObject3);
                  Object localObject4 = localClass.newInstance();
                  Method localMethod3 = localClass.getMethod("setTime", new Class[] { Long.TYPE });
                  localMethod3.invoke(localObject4, new Object[] { Long.valueOf(localDate.getTime()) });
                  localMethod2.invoke(paramObject, new Object[] { localObject4 });
                }
              }
            }
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
    }
  }
}