package gnnt.MEBS.delivery.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ToolService
{
  private final transient Log logger = LogFactory.getLog(ToolService.class);
  private static List<String> marginList;
  private Map<String, Map> moduleMap;
  
  public void setModuleMap(Map<String, Map> paramMap)
  {
    this.moduleMap = paramMap;
  }
  
  public void getMarginList()
  {
    if (marginList == null)
    {
      marginList = new ArrayList();
      Iterator localIterator1 = this.moduleMap.entrySet().iterator();
      while (localIterator1.hasNext())
      {
        Map.Entry localEntry1 = (Map.Entry)localIterator1.next();
        Map localMap = (Map)localEntry1.getValue();
        this.logger.debug(Boolean.valueOf(localMap == null));
        Iterator localIterator2 = localMap.entrySet().iterator();
        while (localIterator2.hasNext())
        {
          Map.Entry localEntry2 = (Map.Entry)localIterator2.next();
          String str1 = (String)localEntry2.getValue();
          String str2 = (String)localEntry2.getKey();
          this.logger.debug("key:" + str2);
          if (str2.indexOf("Margin") >= 0)
          {
            marginList.add(str1);
            this.logger.debug("value:" + str1);
          }
        }
      }
    }
  }
  
  public String getOprcode(String paramString1, String paramString2)
  {
    Map localMap = (Map)this.moduleMap.get(paramString1);
    String str = (String)localMap.get(paramString2);
    return str;
  }
  
  public boolean existOrNotMargin(String paramString)
  {
    getMarginList();
    boolean bool = false;
    this.logger.debug(Boolean.valueOf(marginList == null));
    if (marginList.contains(paramString)) {
      bool = true;
    }
    return bool;
  }
  
  public Object rsToObject(Map paramMap, Cloneable paramCloneable)
  {
    Class localClass = paramCloneable.getClass();
    Field[] arrayOfField1 = localClass.getDeclaredFields();
    Set localSet = paramMap.entrySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      this.logger.debug("me:" + localEntry.getKey());
      for (Field localField : arrayOfField1) {
        if ((localEntry.getValue() != null) && (!localEntry.getValue().equals("")) && (localField.getName().toLowerCase().equals(((String)localEntry.getKey()).toLowerCase())))
        {
          localField.setAccessible(true);
          try
          {
            if (localField.getType().equals(String.class)) {
              localField.set(paramCloneable, (String)localEntry.getValue());
            } else if (localField.getType().equals(Double.class)) {
              localField.set(paramCloneable, new Double((String)localEntry.getValue()));
            } else if (localField.getType().equals(Integer.class)) {
              localField.set(paramCloneable, Integer.valueOf(Integer.parseInt((String)localEntry.getValue())));
            } else if (localField.getType().equals(Float.class)) {
              localField.set(paramCloneable, new Float((String)localEntry.getValue()));
            } else if (localField.getType().equals(Long.class)) {
              localField.set(paramCloneable, new Long((String)localEntry.getValue()));
            } else if (localField.getType().equals(Double.TYPE)) {
              localField.set(paramCloneable, Double.valueOf(Double.parseDouble((String)localEntry.getValue())));
            } else if (localField.getType().equals(Integer.TYPE)) {
              localField.set(paramCloneable, Integer.valueOf(Integer.parseInt((String)localEntry.getValue())));
            } else if (localField.getType().equals(Float.TYPE)) {
              localField.set(paramCloneable, Float.valueOf(Float.parseFloat((String)localEntry.getValue())));
            } else if (localField.getType().equals(Long.TYPE)) {
              localField.set(paramCloneable, Long.valueOf(Long.parseLong((String)localEntry.getValue())));
            }
          }
          catch (Exception localException)
          {
            this.logger.error("name: " + localField.getType());
            this.logger.error("fieldName: " + localField.getName());
            localException.printStackTrace();
          }
        }
      }
    }
    return paramCloneable;
  }
  
  public String getXmlNode(String paramString1, String paramString2)
  {
    Pattern localPattern = Pattern.compile("<" + paramString2 + ">.*?</" + paramString2 + ">");
    Matcher localMatcher = localPattern.matcher(paramString1);
    String str = "";
    int i = 0;
    while (localMatcher.find()) {
      str = localMatcher.group().replaceAll("<" + paramString2 + ">", "").replaceAll("</" + paramString2 + ">", "");
    }
    return str;
  }
}
