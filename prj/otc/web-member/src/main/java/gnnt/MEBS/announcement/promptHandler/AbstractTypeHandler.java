package gnnt.MEBS.announcement.promptHandler;

import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractTypeHandler
  implements PromptHandler
{
  private final transient Log logger = LogFactory.getLog(AbstractTypeHandler.class);
  protected PromptHandler nextHandle;
  
  public void setNextHandle(PromptHandler nextHandle)
  {
    this.nextHandle = nextHandle;
  }
  
  public abstract int handle(Map paramMap, Long paramLong);
  
  public int handleRequest(Map map, Long oldTradeNo)
  {
    int result = -1;
    result = handle(map, oldTradeNo);
    if ((result != 1) && 
      (this.nextHandle != null)) {
      result = this.nextHandle.handleRequest(map, oldTradeNo);
    }
    return result;
  }
}
