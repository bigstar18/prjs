package gnnt.MEBS.globalLog.util;

import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.util.AnnotationProperty;
import gnnt.MEBS.globalLog.model.ExecuteObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Compare
{
  private static final transient Log logger = LogFactory.getLog(Compare.class);
  
  public static ExecuteObject getDifferent(Object oldObject, Object newObject)
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
  {
    ExecuteObject executeObject = null;
    

    Method[] methods = newObject.getClass().getMethods();
    if ((methods != null) && (methods.length > 0))
    {
      executeObject = new ExecuteObject();
      for (Method method : methods) {
        if (method.getName().startsWith("get"))
        {
          ClassDiscription me = AnnotationProperty.getAnnotation(method);
          if (me != null)
          {
            if (me.keyWord())
            {
              Map<String, String> map = new HashMap();
              map.put("name", me.name());
              logger.debug("key:" + me.name());
              logger.debug("methodName:" + method.getName());
              if (oldObject != null)
              {
                logger.debug("value:" + method.invoke(oldObject, new Object[0]).toString());
                if (me.isStatus())
                {
                  Map<String, String> statusMap = AnnotationProperty.getStatusDescription(method);
                  String value = method.invoke(oldObject, new Object[0]).toString();
                  String v = (String)statusMap.get(value);
                  if (v == null) {
                    v = value;
                  }
                  map.put("value", v);
                }
                else
                {
                  map.put("value", method.invoke(oldObject, new Object[0]).toString());
                }
              }
              else if (method.invoke(newObject, new Object[0]) != null)
              {
                logger.debug("value:" + method.invoke(newObject, new Object[0]).toString());
                if (me.isStatus())
                {
                  Map<String, String> statusMap = AnnotationProperty.getStatusDescription(method);
                  String value = method.invoke(newObject, new Object[0]).toString();
                  String v = (String)statusMap.get(value);
                  if (v == null) {
                    v = value;
                  }
                  map.put("value", v);
                }
                else
                {
                  map.put("value", method.invoke(newObject, new Object[0]).toString());
                }
              }
              executeObject.getKeyList().add(map);
            }
            if (newObject != null)
            {
              String oldValue = null;
              if (oldObject != null) {
                oldValue = analysis(method.invoke(oldObject, new Object[0]));
              }
              String newValue = analysis(method.invoke(newObject, new Object[0]));
              logger.debug(me.name());
              if (me.isStatus())
              {
                Map<String, String> statusMap = AnnotationProperty.getStatusDescription(method);
                String oldValueForMap = (String)statusMap.get(oldValue);
                String newValueForMap = (String)statusMap.get(newValue);
                if (oldValueForMap != null) {
                  oldValue = oldValueForMap;
                }
                if (newValueForMap != null) {
                  newValue = newValueForMap;
                }
              }
              logger.debug("oldValue:" + oldValue);
              logger.debug("newValue:" + newValue);
              if (newValue != null) {
                if (oldValue == null)
                {
                  Map<String, String> map = new HashMap();
                  if (!"空".equals(newValue))
                  {
                    String value = me.name() + "为" + newValue + ";";
                    map.put("value", value);
                    executeObject.getPropertyList().add(map);
                  }
                }
                else if (!oldValue.equals(newValue))
                {
                  Map<String, String> map = new HashMap();
                  
                  String value = me.name() + "从" + oldValue + "改为" + newValue + ";";
                  map.put("value", value);
                  executeObject.getPropertyList().add(map);
                }
              }
            }
          }
        }
      }
    }
    return executeObject;
  }
  
  public static String analysis(Object object)
  {
    logger.debug("object:" + object);
    String result = "";
    if (object != null)
    {
      if (("".equals(object.toString().trim())) || ("null".equals(object.toString())))
      {
        result = "空";
      }
      else if (object.getClass() == Date.class)
      {
        DateFormat df = new SimpleDateFormat(
          "yyyy-MM-dd");
        result = df.format(object);
      }
      else if (object.getClass() == Timestamp.class)
      {
        DateFormat df = new SimpleDateFormat(
          "yyyy-MM-dd HH:mm:ss");
        result = df.format(object);
      }
      else if (object.getClass() == BigDecimal.class)
      {
        BigDecimal value = (BigDecimal)object;
        String valueString = value.toString();
        String valueDecimals = "";
        if (valueString.indexOf(".") > 0) {
          valueDecimals = valueString.substring(valueString.indexOf(".") + 1, valueString.length());
        }
        int length = valueDecimals.length();
        String format = "#,##0";
        if (length > 0)
        {
          format = format + ".";
          for (int i = 0; i < length; i++) {
            format = format + "0";
          }
        }
        DecimalFormat df = new DecimalFormat(format);
        result = df.format(object);
      }
      else
      {
        result = object.toString();
      }
    }
    else {
      result = null;
    }
    return result;
  }
  
  public static String translate(ExecuteObject executeObject)
  {
    String result = null;
    List<Map<String, String>> keyList = executeObject.getKeyList();
    String keys = "";
    for (Map<String, String> map : keyList) {
      keys = keys + (String)map.get("name") + (String)map.get("value") + ",";
    }
    if (keys.length() > 0) {
      keys = keys.substring(0, keys.length() - 1);
    }
    List<Map<String, String>> propertyList = executeObject.getPropertyList();
    String propertys = "";
    for (Map<String, String> map : propertyList) {
      propertys = propertys + (String)map.get("value");
    }
    result = "将" + keys + "中的" + propertys;
    return result;
  }
  
  public static String translateForParmaLog(ExecuteObject executeObject)
  {
    String result = null;
    List<Map<String, String>> keyList = executeObject.getKeyList();
    String keys = "";
    for (Map<String, String> map : keyList) {
      keys = keys + (String)map.get("name") + (String)map.get("value") + ",";
    }
    if (keys.length() > 0) {
      keys = keys.substring(0, keys.length() - 1);
    }
    List<Map<String, String>> propertyList = executeObject.getPropertyList();
    String propertys = "";
    for (Map<String, String> map : propertyList) {
      propertys = propertys + (String)map.get("value");
    }
    result = propertys;
    return result;
  }
}
