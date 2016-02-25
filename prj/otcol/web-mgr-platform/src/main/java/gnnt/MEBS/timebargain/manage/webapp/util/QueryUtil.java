package gnnt.MEBS.timebargain.manage.webapp.util;

import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import gnnt.MEBS.timebargain.manage.util.Utils;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.util.WebUtils;

public class QueryUtil
{
  private static final transient Log logger = LogFactory.getLog(QueryUtil.class);
  
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
            catch (Exception localException)
            {
              logger.error("Utils.convert() failed!");
            }
          }
          logger.debug("field:" + str1);
          logger.debug("operator:" + str2);
          logger.debug("datatype:" + str3);
          logger.debug("value:" + localObject);
          localQueryConditions.addCondition(str1, str2, localObject);
        }
      }
    }
    if (null == localQueryConditions) {
      localQueryConditions = new QueryConditions();
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
}
