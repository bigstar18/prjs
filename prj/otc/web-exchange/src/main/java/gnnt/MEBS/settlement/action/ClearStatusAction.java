package gnnt.MEBS.settlement.action;

import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.settlement.service.ClearStatusService;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class ClearStatusAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(ClearStatusAction.class);
  @Autowired
  @Qualifier("clearStatusService")
  private ClearStatusService clearStatusService;
  @Resource(name="clearStatusMap")
  private Map clearStatusMap;
  
  public Map getClearStatusMap()
  {
    return this.clearStatusMap;
  }
  
  public InService getService()
  {
    return this.clearStatusService;
  }
}
