package gnnt.MEBS.verify.handler;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IsNotEmptyNullVerifyHandle
  extends AbstractVerifyHandle
{
  private final transient Log logger = LogFactory.getLog(IsNotEmptyNullVerifyHandle.class);
  
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
        if ((value == null) || ("".equals(value.trim())))
        {
          result = (String)verifyMap.get("name") + "不能为空";
          break;
        }
      }
    }
    return result;
  }
}
