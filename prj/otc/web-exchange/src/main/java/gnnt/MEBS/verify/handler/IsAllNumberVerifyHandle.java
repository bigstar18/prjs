package gnnt.MEBS.verify.handler;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IsAllNumberVerifyHandle
  extends AbstractVerifyHandle
{
  private final transient Log logger = LogFactory.getLog(IsAllNumberVerifyHandle.class);
  
  protected String verify(List<Map<String, String>> list, Map<String, String> map)
  {
    String result = null;
    for (Map<String, String> verifyMap : list)
    {
      String field = (String)verifyMap.get("field");
      this.logger.debug("verify_field:" + field);
      if (map.containsKey(field))
      {
        String value = (String)map.get(field);
        this.logger.debug("verify_value:" + value);
        boolean check = isAllNumber(value);
        if (!check)
        {
          result = (String)verifyMap.get("name") + "有非整数的字符";
          break;
        }
      }
    }
    return result;
  }
  
  private boolean isAllNumber(String str)
  {
    boolean sign = Pattern.matches("(\\-|\\+)?\\d*", str);
    return sign;
  }
}
