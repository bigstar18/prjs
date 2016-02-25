package gnnt.MEBS.common.util.query;

import gnnt.MEBS.common.util.query.exception.BaseException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    if ((localMap != null) && (localMap.size() > 0))
    {
      logger.debug("parameter size:" + localMap.size());
      Pattern localPattern1 = Pattern.compile("(.+?)\\[(.+)]\\[(.+)]");
      Pattern localPattern2 = Pattern.compile("(.+?)\\[(.+)]");
      Iterator localIterator = localMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str4 = (String)localIterator.next();
        Object localObject = localMap.get(str4);
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
    int i = Utils.parseInt(paramHttpServletRequest.getParameter("pageSize"));
    int j = Utils.parseInt(paramHttpServletRequest.getParameter("pageNo"), 1);
    if (i > 0)
    {
      localPageInfo = new PageInfo();
      localPageInfo.setPageSize(i);
      localPageInfo.setPageNo(j);
      String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("orderField");
      String[] arrayOfString2 = paramHttpServletRequest.getParameterValues("orderDesc");
      String str2;
      boolean bool;
      if ((arrayOfString1 != null) && (arrayOfString2 != null))
      {
        for (int k = 0; k < arrayOfString1.length; k++)
        {
          logger.debug("orderField " + k + "=" + arrayOfString1[k]);
          logger.debug("orderDesc " + k + "=" + arrayOfString2[k]);
          str2 = arrayOfString1[k];
          bool = Utils.parseBoolean(arrayOfString2[k]);
          localPageInfo.addOrderField(str2, bool);
        }
      }
      else
      {
        String str1 = paramHttpServletRequest.getParameter("orderField");
        str2 = paramHttpServletRequest.getParameter("orderDesc");
        logger.debug("orderField =" + str1);
        logger.debug("orderDesc =" + str2);
        if ((str1 != null) && (str1.length() > 0))
        {
          bool = Utils.parseBoolean(str2);
          localPageInfo.addOrderField(str1, bool);
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
}
