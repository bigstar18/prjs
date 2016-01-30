package gnnt.MEBS.common.front.statictools;

import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.common.QueryConditions;
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
import org.springframework.util.Assert;

public class ActionUtil
{
  private static final transient Log logger = LogFactory.getLog(ActionUtil.class);
  public static final String PARAMETERPREFIX = "gnnt_";
  public static final String PAGESIZE = "pageSize";
  public static final String PAGENUMBER = "pageNo";
  public static final String SORTCOLUMNS = "sortColumns";
  public static final String ISQUERYDB = "isQueryDB";
  public static final String PAGEINFO = "pageInfo";
  public static final String OLDPARAMETER = "oldParams";
  
  public static PageRequest<QueryConditions> getPageRequest(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    PageRequest localPageRequest = new PageRequest();
    localPageRequest.setPageNumber(Tools.strToInt(paramHttpServletRequest.getParameter("pageNo"), 1));
    localPageRequest.setPageSize(Tools.strToInt(paramHttpServletRequest.getParameter("pageSize"), 20));
    localPageRequest.setSortColumns(paramHttpServletRequest.getParameter("sortColumns"));
    localPageRequest.setQueryDB(Tools.strToBoolean(paramHttpServletRequest.getParameter("isQueryDB")));
    paramHttpServletRequest.setAttribute("sortColumns", paramHttpServletRequest.getParameter("sortColumns"));
    localPageRequest.setFilters(getQueryConditionsFromRequest(paramHttpServletRequest));
    return localPageRequest;
  }
  
  public static QueryConditions getQueryConditionsFromRequest(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    QueryConditions localQueryConditions = new QueryConditions();
    logger.debug("enter getQueryConditionsFromReq...");
    Map localMap1 = getParametersStartingWith(paramHttpServletRequest, "gnnt_");
    Map localMap2 = getAttributeStartingWith(paramHttpServletRequest, "gnnt_");
    localMap1.putAll(localMap2);
    if ((localMap1 != null) && (localMap1.size() > 0))
    {
      logger.debug("parameter size:" + localMap1.size());
      Pattern localPattern1 = Pattern.compile("(.+?)\\[(.+)]\\[(.+)]");
      Pattern localPattern2 = Pattern.compile("(.+?)\\[(.+)]");
      Iterator localIterator = localMap1.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str4 = (String)localIterator.next();
        Object localObject = localMap1.get(str4);
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
        if ((str1 != null) && (localObject != null) && (((String)localObject).trim().length() > 0))
        {
          if (localQueryConditions == null) {
            localQueryConditions = new QueryConditions();
          }
          if (str3 != null)
          {
            localObject = localObject.toString().trim();
            localObject = Tools.convert(localObject, str3);
          }
          else if ((localObject != null) && ((localObject instanceof String)))
          {
            localObject = localObject.toString().trim();
          }
          logger.debug("field:" + str1);
          logger.debug("operator:" + str2);
          logger.debug("datatype:" + str3);
          logger.debug("value:" + localObject);
          localQueryConditions.addCondition(str1, str2, localObject);
        }
      }
    }
    return localQueryConditions;
  }
  
  public static Map<String, Object> getParametersStartingWith(ServletRequest paramServletRequest, String paramString)
  {
    Assert.notNull(paramServletRequest, "Request must not be null");
    Enumeration localEnumeration = paramServletRequest.getParameterNames();
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
        String[] arrayOfString = paramServletRequest.getParameterValues(str1);
        if ((arrayOfString != null) && (arrayOfString.length != 0)) {
          if (arrayOfString.length > 1) {
            localTreeMap.put(str2, arrayOfString);
          } else {
            localTreeMap.put(str2, arrayOfString[0].trim());
          }
        }
        logger.debug(str2 + ":" + arrayOfString[0]);
      }
    }
    return localTreeMap;
  }
  
  public static Map<String, String> getAttributeStartingWith(ServletRequest paramServletRequest, String paramString)
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
          localTreeMap.put(str2, str3.trim());
        }
      }
    }
    return localTreeMap;
  }
}
