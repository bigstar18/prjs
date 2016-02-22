package gnnt.MEBS.verify.handler;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SpecialCharactersVerifyHandle
  extends AbstractVerifyHandle
{
  private final transient Log logger = LogFactory.getLog(SpecialCharactersVerifyHandle.class);
  
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
        boolean check = isChar(value);
        if (!check)
        {
          result = (String)verifyMap.get("name") + "有非字母或数字的字符";
          break;
        }
      }
    }
    return result;
  }
  
  private boolean isChar(String validString)
  {
    byte[] tempbyte = validString.getBytes();
    for (int i = 0; i < validString.length(); i++)
    {
      if (tempbyte[i] >= 48) {
        if ((((tempbyte[i] > 57 ? 1 : 0) & (tempbyte[i] < 65 ? 1 : 0)) == 0) && (tempbyte[i] <= 122)) {
          if (((tempbyte[i] > 90 ? 1 : 0) & (tempbyte[i] < 97 ? 1 : 0)) == 0) {
            continue;
          }
        }
      }
      return false;
    }
    return true;
  }
}
