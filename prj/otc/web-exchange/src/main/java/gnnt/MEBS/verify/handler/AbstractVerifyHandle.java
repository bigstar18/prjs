package gnnt.MEBS.verify.handler;

import gnnt.MEBS.verify.constant.Constant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractVerifyHandle
  implements VerifyHandler
{
  protected VerifyHandler verifyHandler;
  private Map<String, List<Map<String, String>>> verifyMap;
  private final transient Log logger = LogFactory.getLog(AbstractVerifyHandle.class);
  
  public void setVerifyMap(Map<String, List<Map<String, String>>> verifyMap)
  {
    this.verifyMap = verifyMap;
  }
  
  public void setVerifyHandler(VerifyHandler verifyHandler)
  {
    this.verifyHandler = verifyHandler;
  }
  
  protected Map<String, Object> toNextHandle(String key, Map<String, String> map, VerifyHandler verifyHandler)
  {
    Map<String, Object> returnMap = new HashMap();
    if (verifyHandler != null) {
      returnMap = verifyHandler.handle(key, map);
    }
    return returnMap;
  }
  
  public Map<String, Object> handle(String key, Map<String, String> map)
  {
    Map<String, Object> returnMap = new HashMap();
    if (this.verifyMap != null)
    {
      this.logger.debug("key:" + key);
      if (this.verifyMap.containsKey(key))
      {
        List<Map<String, String>> list = (List)this.verifyMap.get(key);
        String value = verify(list, map);
        this.logger.debug("value:" + value);
        if (value != null)
        {
          returnMap.put(Constant.RETURNMSG, value);
          returnMap.put(Constant.RETURNRESULT, Integer.valueOf(-1));
        }
      }
    }
    if (returnMap.size() == 0) {
      returnMap = toNextHandle(key, map, this.verifyHandler);
    }
    return returnMap;
  }
  
  protected String verify(List<Map<String, String>> list, Map<String, String> map)
  {
    return null;
  }
}
