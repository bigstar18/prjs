package gnnt.MEBS.announcement.promptHandler;

import gnnt.MEBS.announcement.service.SystemStatusPromptService;
import gnnt.MEBS.trade.model.SystemStatus;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SystemStatusHandler
  extends AbstractTypeHandler
{
  @Resource(name="trStatusMap")
  private Map statusMap;
  @Autowired
  @Qualifier("systemStatusPromptService")
  private SystemStatusPromptService systemStatusPromptService;
  
  public int handle(Map map, Long oldTradeNo)
  {
    int result = 0;
    map.put("systemStatus", this.statusMap.get(((SystemStatus)this.systemStatusPromptService.getList(null, null).get(0)).getStatus()));
    return result;
  }
}
