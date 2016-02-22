package gnnt.MEBS.verify.handler;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RegularVerifyHandle
  extends AbstractVerifyHandle
{
  private final transient Log logger = LogFactory.getLog(RegularVerifyHandle.class);
  @Resource(name="regularMap")
  private Map regularMap;
  
  protected String verify(List<Map<String, String>> list, Map<String, String> map)
  {
    String result = null;
    for (Map<String, String> verifyMap : list)
    {
      String field = (String)verifyMap.get("field");
      this.logger.debug("verify_field:" + field);
      if (map.containsKey(field))
      {
        String regularName = (String)verifyMap.get("regular");
        this.logger.debug("regularName:" + regularName);
        Map<String, String> regularDesc = (Map)this.regularMap.get(regularName);
        String regular = (String)regularDesc.get("regular");
        this.logger.debug("regular:" + regular);
        String value = (String)map.get(field);
        this.logger.debug("verify_value:" + value);
        if ((value != null) && (!"".equals(value)) && 
          (!value.matches(regular)))
        {
          this.logger.debug("description:" + (String)regularDesc.get("description"));
          result = (String)verifyMap.get("name") + "格式不正确,应为" + (String)regularDesc.get("description");
          break;
        }
      }
    }
    return result;
  }
}
