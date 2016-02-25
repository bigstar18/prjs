package gnnt.MEBS.delivery.command.standardBehavior;

import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.Receive;
import gnnt.MEBS.delivery.services.SettleMatchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SettleBalance
  implements Receive
{
  private final transient Log logger = LogFactory.getLog(SettleBalance.class);
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  
  public int deal(Information paramInformation)
  {
    int i = this.settleMatchService.balance();
    return i;
  }
}
