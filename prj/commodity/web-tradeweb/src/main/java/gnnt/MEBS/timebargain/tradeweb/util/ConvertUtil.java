package gnnt.MEBS.timebargain.tradeweb.util;

import gnnt.MEBS.timebargain.tradeweb.model.BaseObject;
import gnnt.MEBS.timebargain.tradeweb.model.LabelValue;
import java.beans.PropertyDescriptor;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.support.AopUtils;

public final class ConvertUtil
{
  private static Log log = LogFactory.getLog(ConvertUtil.class);
  
  public static Map convertBundleToMap(ResourceBundle paramResourceBundle)
  {
    HashMap localHashMap = new HashMap();
    Enumeration localEnumeration = paramResourceBundle.getKeys();
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      localHashMap.put(str, paramResourceBundle.getString(str));
    }
    return localHashMap;
  }
  
  public static Map convertListToMap(List paramList)
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      LabelValue localLabelValue = (LabelValue)localIterator.next();
      localLinkedHashMap.put(localLabelValue.getLabel(), localLabelValue.getValue());
    }
    return localLinkedHashMap;
  }
  
  public static Properties convertBundleToProperties(ResourceBundle paramResourceBundle)
  {
    Properties localProperties = new Properties();
    Enumeration localEnumeration = paramResourceBundle.getKeys();
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      localProperties.put(str, paramResourceBundle.getString(str));
    }
    return localProperties;
  }
  
  public static Object populateObject(Object paramObject, ResourceBundle paramResourceBundle)
  {
    try
    {
      Map localMap = convertBundleToMap(paramResourceBundle);
      BeanUtils.copyProperties(paramObject, localMap);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      log.error("Exception occured populating object: " + localException.getMessage());
    }
    return paramObject;
  }
  
  public static Object getOpposingObject(Object paramObject)
    throws ClassNotFoundException, InstantiationException, IllegalAccessException
  {
    String str = paramObject.getClass().getName();
    if ((paramObject instanceof BaseObject))
    {
      if (log.isDebugEnabled()) {
        log.debug("getting form equivalent of pojo...");
      }
      str = StringUtils.replace(str, ".model.", ".webapp.form.");
      if (AopUtils.isCglibProxy(paramObject)) {
        str = str.substring(0, str.indexOf("$$"));
      }
      str = str + "Form";
    }
    else
    {
      if (log.isDebugEnabled()) {
        log.debug("getting pojo equivalent of form...");
      }
      str = StringUtils.replace(str, ".webapp.form.", ".model.");
      str = str.substring(0, str.lastIndexOf("Form"));
    }
    Class localClass = Class.forName(str);
    if (log.isDebugEnabled()) {
      log.debug("returning className: " + localClass.getName());
    }
    return localClass.newInstance();
  }
  
  public static Object convert(Object paramObject)
    throws Exception
  {
    if (paramObject == null) {
      return null;
    }
    Object localObject = getOpposingObject(paramObject);
    BeanUtils.copyProperties(localObject, paramObject);
    return localObject;
  }
  
  public static Object convertLists(Object paramObject)
    throws Exception
  {
    if (paramObject == null) {
      return null;
    }
    Object localObject1 = null;
    PropertyDescriptor[] arrayOfPropertyDescriptor = PropertyUtils.getPropertyDescriptors(paramObject);
    for (int i = 0; i < arrayOfPropertyDescriptor.length; i++)
    {
      String str = arrayOfPropertyDescriptor[i].getName();
      if (arrayOfPropertyDescriptor[i].getPropertyType().equals(List.class))
      {
        List localList = (List)PropertyUtils.getProperty(paramObject, str);
        for (int j = 0; j < localList.size(); j++)
        {
          Object localObject2 = localList.get(j);
          localObject1 = convert(localObject2);
          localList.set(j, localObject1);
        }
        PropertyUtils.setProperty(paramObject, str, localList);
      }
    }
    return paramObject;
  }
}
