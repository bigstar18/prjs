package gnnt.MEBS.settlement.dwr;

import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.settlement.model.ClearStatus;
import gnnt.MEBS.settlement.service.ClearStatusService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class SettleStatusAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(SettleStatusAction.class);
  @Autowired
  @Qualifier("clearStatusService")
  private ClearStatusService clearStatusService;
  
  public InService getService()
  {
    return this.clearStatusService;
  }
  
  public int getSettleStatus()
  {
    int status = 0;
    int count = 0;
    List<ClearStatus> list = this.clearStatusService.getList(null, null);
    for (ClearStatus clearStatus : list) {
      if ("Y".equals(clearStatus.getStatus())) {
        count++;
      }
    }
    if (count == list.size()) {
      status = 1;
    } else if ((count != 0) && (count < list.size())) {
      status = 2;
    }
    return status;
  }
}
