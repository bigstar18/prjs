package gnnt.MEBS.common.broker.webframe.securitycheck;

import gnnt.MEBS.common.broker.model.Broker;
import gnnt.MEBS.common.broker.model.BrokerAge;
import gnnt.MEBS.common.broker.model.Right;
import gnnt.MEBS.common.broker.model.User;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlCheckUserPurview
  implements IUrlCheck
{
  public UrlCheckResult check(String paramString, User paramUser)
  {
    Map localMap = null;
    if (paramUser.getType().equals("0"))
      localMap = paramUser.getBroker().getRightMap();
    else
      localMap = paramUser.getBrokerAge().getRightMap();
    if (localMap == null)
      return UrlCheckResult.NOPURVIEW;
    Iterator localIterator = localMap.values().iterator();
    while (localIterator.hasNext())
    {
      Right localRight = (Right)localIterator.next();
      if ((localRight.getUrl() != null) && (localRight.getUrl().length() != 0))
      {
        if (localRight.getUrl().equals(paramString))
          return UrlCheckResult.SUCCESS;
        if (localRight.getUrl().endsWith("*"))
          if (localRight.getType().intValue() == 0)
          {
            if (localRight.getVisiturl().contains(paramString))
              return UrlCheckResult.SUCCESS;
          }
          else
          {
            String str = localRight.getUrl().substring(0, localRight.getUrl().length() - 1);
            if (paramString.contains(str))
              return UrlCheckResult.SUCCESS;
          }
      }
    }
    return UrlCheckResult.NOPURVIEW;
  }

  public static void main(String[] paramArrayOfString)
  {
    Pattern localPattern = Pattern.compile("/user/add.*");
    Matcher localMatcher = localPattern.matcher("/user/add.action");
    if (localMatcher.matches())
      System.out.println("匹配成功！");
  }
}