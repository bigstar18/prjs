package gnnt.MEBS.verify.handler;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NumPercentageVerifyHandle
  extends AbstractVerifyHandle
{
  private final transient Log logger = LogFactory.getLog(NumPercentageVerifyHandle.class);
  
  protected String verify(List<Map<String, String>> list, Map<String, String> map)
  {
    String result = null;
    for (Map<String, String> verifyMap : list)
    {
      String field = (String)verifyMap.get("field");
      String name = (String)verifyMap.get("name");
      String percentagePro = (String)verifyMap.get("percentagePro");
      String per = (String)verifyMap.get("per");
      this.logger.debug("verify_field:" + field);
      if (map.containsKey(field))
      {
        String value = (String)map.get(field);
        String frontPer = (String)map.get(percentagePro);
        this.logger.debug("verify_value:" + value);
        this.logger.debug("frontPer:" + frontPer);
        if ((frontPer.equals(per)) && (Double.parseDouble(value) > 100.0D))
        {
          result = (String)verifyMap.get("name") + "应小于100%";
          break;
        }
      }
    }
    return result;
  }
}
