package gnnt.MEBS.announcement.dwr;

import gnnt.MEBS.announcement.promptHandler.PromptHandler;
import gnnt.MEBS.announcement.service.TradeService;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class NoticePromptAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(NoticePromptAction.class);
  @Autowired
  @Qualifier("systemStatusHandler")
  private PromptHandler promptHandler;
  @Autowired
  @Qualifier("tradeService")
  private TradeService tradeService;
  @Resource(name="trStatusMap")
  private Map statusMap;
  
  public InService getService()
  {
    return this.tradeService;
  }
  
  public Map promptFun(long oldTradeNo)
  {
    HttpServletRequest request = (HttpServletRequest)ThreadStore.get(ThreadStoreConstant.REQUEST);
    Map map = new HashMap();
    try
    {
      this.promptHandler.handleRequest(map, Long.valueOf(oldTradeNo));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return map;
  }
}
