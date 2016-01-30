package gnnt.MEBS.timebargain.tradeweb.query;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.util.WebUtils;

public class QueryHelper
{
  private static final transient Log logger = LogFactory.getLog(QueryHelper.class);
  
  public static QueryConditions getQueryConditionsFromRequest(HttpServletRequest paramHttpServletRequest)
  {
    logger.debug("enter getQueryConditionsFromReq...");
    QueryConditions localQueryConditions = null;
    Map localMap1 = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    Map localMap2 = getAttributeStartingWith(paramHttpServletRequest, "_");
    localMap1.putAll(localMap2);
    localQueryConditions = getQueryConditionsForMap(localMap1);
    return localQueryConditions;
  }
  
  public static QueryConditions createQueryConditionsFromRequest(HttpServletRequest paramHttpServletRequest)
  {
    QueryConditions localQueryConditions = getQueryConditionsFromRequest(paramHttpServletRequest);
    if (localQueryConditions == null) {
      localQueryConditions = new QueryConditions();
    }
    return localQueryConditions;
  }
  
  public static PageInfo getPageInfoFromRequest(HttpServletRequest paramHttpServletRequest)
  {
    logger.debug("enter getPageInfoFromRequest...");
    PageInfo localPageInfo = null;
    String str1 = paramHttpServletRequest.getParameter("pageSize");
    if (str1 == null) {
      str1 = (String)paramHttpServletRequest.getAttribute("pageSize");
    }
    int i = Utils.parseInt(str1);
    String str2 = paramHttpServletRequest.getParameter("pageNo");
    if (str2 == null) {
      str2 = (String)paramHttpServletRequest.getAttribute("pageNo");
    }
    int j = Utils.parseInt(str2, 1);
    if (i > 0)
    {
      localPageInfo = new PageInfo();
      localPageInfo.setPageSize(i);
      localPageInfo.setPageNo(j);
      String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("orderField");
      if (arrayOfString1 == null)
      {
        arrayOfString1 = (String[])paramHttpServletRequest.getAttribute("orderField");
        logger.debug("orderFields:" + arrayOfString1);
      }
      String[] arrayOfString2 = paramHttpServletRequest.getParameterValues("orderDesc");
      if (arrayOfString2 == null) {
        arrayOfString2 = (String[])paramHttpServletRequest.getAttribute("orderDesc");
      }
      String str4;
      boolean bool;
      if ((arrayOfString1 != null) && (arrayOfString2 != null))
      {
        for (int k = 0; k < arrayOfString1.length; k++)
        {
          logger.debug("orderField " + k + "=" + arrayOfString1[k]);
          logger.debug("orderDesc " + k + "=" + arrayOfString2[k]);
          str4 = arrayOfString1[k];
          bool = Utils.parseBoolean(arrayOfString2[k]);
          localPageInfo.addOrderField(str4, bool);
        }
      }
      else
      {
        String str3 = paramHttpServletRequest.getParameter("orderField");
        str4 = paramHttpServletRequest.getParameter("orderDesc");
        logger.debug("orderField =" + str3);
        logger.debug("orderDesc =" + str4);
        if ((str3 != null) && (str3.length() > 0))
        {
          bool = Utils.parseBoolean(str4);
          localPageInfo.addOrderField(str3, bool);
        }
        else
        {
          return null;
        }
      }
      logger.debug("pageInfo: pageNo = " + localPageInfo.getPageNo() + ", pageSize = " + localPageInfo.getPageSize());
    }
    return localPageInfo;
  }
  
  public static String getTargetView(HttpServletRequest paramHttpServletRequest, String paramString)
  {
    String str = paramHttpServletRequest.getParameter("targetView");
    if ((str == null) || (str.length() == 0)) {
      str = paramString;
    }
    return str;
  }
  
  public static Map getAttributeStartingWith(ServletRequest paramServletRequest, String paramString)
  {
    Enumeration localEnumeration = paramServletRequest.getAttributeNames();
    TreeMap localTreeMap = new TreeMap();
    if (paramString == null) {
      paramString = "";
    }
    while ((localEnumeration != null) && (localEnumeration.hasMoreElements()))
    {
      String str1 = (String)localEnumeration.nextElement();
      if (("".equals(paramString)) || (str1.startsWith(paramString)))
      {
        String str2 = str1.substring(paramString.length());
        String str3 = (String)paramServletRequest.getAttribute(str1);
        if (str3 != null) {
          localTreeMap.put(str2, str3);
        }
      }
    }
    return localTreeMap;
  }
  
  public static QueryConditions getQueryConditionsForMap(Map paramMap)
  {
    QueryConditions localQueryConditions = null;
    if ((paramMap != null) && (paramMap.size() > 0))
    {
      logger.debug("parameter size:" + paramMap.size());
      Pattern localPattern1 = Pattern.compile("(.+?)\\[(.+)]\\[(.+)]");
      Pattern localPattern2 = Pattern.compile("(.+?)\\[(.+)]");
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str4 = (String)localIterator.next();
        Object localObject = paramMap.get(str4);
        logger.debug("parameter:" + str4);
        Matcher localMatcher = localPattern1.matcher(str4);
        String str1;
        String str2;
        String str3;
        if (localMatcher.matches())
        {
          str1 = localMatcher.group(1);
          str2 = localMatcher.group(2);
          str3 = localMatcher.group(3);
        }
        else
        {
          localMatcher = localPattern2.matcher(str4);
          if (localMatcher.matches())
          {
            str1 = localMatcher.group(1);
            str2 = localMatcher.group(2);
            str3 = null;
          }
          else
          {
            str1 = null;
            str2 = null;
            str3 = null;
          }
        }
        if ((str1 != null) && (localObject != null) && (((String)localObject).length() > 0))
        {
          if (localQueryConditions == null) {
            localQueryConditions = new QueryConditions();
          }
          if (str3 != null) {
            try
            {
              localObject = Utils.convert(localObject, str3);
            }
            catch (BaseException localBaseException)
            {
              logger.error("Utils.convert() failed!");
            }
          }
          logger.debug("parameter:" + str1);
          logger.debug("operator:" + str2);
          logger.debug("datatype:" + str3);
          logger.debug("value:" + localObject);
          localQueryConditions.addCondition(str1, str2, localObject);
        }
      }
    }
    return localQueryConditions;
  }
  
  public static QueryConditions getQueryConditionsFromRequestAttribute(HttpServletRequest paramHttpServletRequest, String paramString)
  {
    Map localMap = getAttributeStartingWith(paramHttpServletRequest, paramString);
    QueryConditions localQueryConditions = getQueryConditionsForMap(localMap);
    return localQueryConditions;
  }
  
  public static Map getMapFromRequest(HttpServletRequest paramHttpServletRequest)
  {
    logger.debug("enter getQueryConditionsFromReq...");
    Map localMap1 = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    Map localMap2 = getAttributeStartingWith(paramHttpServletRequest, "_");
    localMap1.putAll(localMap2);
    return localMap1;
  }
}
