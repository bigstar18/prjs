package gnnt.MEBS.verify.handler;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringDigitVerifyHandle
  extends AbstractVerifyHandle
{
  private final transient Log logger = LogFactory.getLog(StringDigitVerifyHandle.class);
  
  protected String verify(List<Map<String, String>> list, Map<String, String> map)
  {
    String result = null;
    for (Map<String, String> verifyMap : list)
    {
      String field = (String)verifyMap.get("field");
      String maxDigit = (String)verifyMap.get("maxDigit");
      String minDigit = (String)verifyMap.get("minDigit");
      this.logger.debug("verify_field:" + field);
      if (map.containsKey(field))
      {
        String value = (String)map.get(field);
        this.logger.debug("verify_value:" + value);
        if ((Integer.parseInt(maxDigit) < value.length()) || (Integer.parseInt(minDigit) > value.length()))
        {
          if (!maxDigit.equals(minDigit))
          {
            result = (String)verifyMap.get("name") + "位数不正确，应小于" + maxDigit + "位大于" + minDigit; break;
          }
          result = (String)verifyMap.get("name") + "位数不正确，应为" + maxDigit + "位整数";
          
          break;
        }
      }
    }
    return result;
  }
}
