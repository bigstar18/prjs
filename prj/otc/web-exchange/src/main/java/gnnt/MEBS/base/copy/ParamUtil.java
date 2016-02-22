package gnnt.MEBS.base.copy;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ParamUtil
{
  public static void bindData(HttpServletRequest request, Object bean)
  {
    Enumeration<String> em = request.getParameterNames();
    
    List<String> names = getParamNames(bean, em);
    for (int i = 0; i < names.size(); i++) {
      try
      {
        String name = (String)names.get(i);
        String value = request.getParameter(name);
        String nameFor = name;
        if (name.split("\\.").length > 1) {
          nameFor = name.split("\\.")[1];
        }
        bindSubObject(bean, nameFor, value);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public static void bindSubObject(Object bean, String objName, String value)
    throws SecurityException, Exception
  {
    String funName = getFunName(objName);
    Method getMethod = bean.getClass().getMethod("get" + funName, null);
    Class cl = getMethod.getReturnType();
    if ("".equals(value.trim()))
    {
      if (cl == String.class)
      {
        Method setMethod = bean.getClass().getMethod("set" + funName, new Class[] { cl });
        setMethod.invoke(bean, new Object[] { "" });
      }
      return;
    }
    Method setMethod = bean.getClass().getMethod("set" + funName, new Class[] { cl });
    if (cl == String.class)
    {
      setMethod.invoke(bean, new Object[] { value });
    }
    else if ((cl == Integer.class) || (cl == Float.class) || (cl == Long.class) || 
      (cl == Double.class) || (cl == Byte.class) || 
      (cl == Boolean.class) || (cl == Character.class) || 
      (cl == Short.class))
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
    else if (cl == Date.class)
    {
      SimpleDateFormat simpleDateFormat = null;
      String formatChar = null;
      formatChar = "yyyy-MM-dd";
      if (formatChar != null)
      {
        simpleDateFormat = new SimpleDateFormat(formatChar);
        Date date = simpleDateFormat.parse(value);
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
        Date date = simpleDateFormat.parse(value);
        


        Object dateObj = new Timestamp(date.getTime());
        setMethod.invoke(bean, new Object[] { dateObj });
      }
    }
  }
  
  private static List<String> getParamNames(Object bean, Enumeration<String> em)
  {
    Class objClass = bean.getClass();
    Method[] methods = objClass.getMethods();
    List<String> nameList = new ArrayList();
    String methodname = "";
    String name = "";
    String nameFor = "";
    int i;
    for (; em.hasMoreElements(); i < methods.length)
    {
      name = (String)em.nextElement();
      if (name.split("\\.").length > 1) {
        nameFor = name.split("\\.")[1];
      } else {
        nameFor = name;
      }
      methodname = "set" + getFunName(nameFor);
      i = 0; continue;
      if (methodname.equals(methods[i].getName())) {
        nameList.add(name);
      }
      i++;
    }
    return nameList;
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
