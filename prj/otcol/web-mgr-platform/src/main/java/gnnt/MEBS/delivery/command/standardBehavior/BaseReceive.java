package gnnt.MEBS.delivery.command.standardBehavior;

import gnnt.MEBS.delivery.command.SettleReceive;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.SettleMatchService;

public abstract class BaseReceive
  implements SettleReceive
{
  protected SettleMatchService settleMatchService;
  
  public void setSettleMatchService(SettleMatchService paramSettleMatchService)
  {
    this.settleMatchService = paramSettleMatchService;
  }
  
  protected boolean judgeStatus(SettleMatch paramSettleMatch)
  {
    boolean bool = false;
    int i = paramSettleMatch.getStatus();
    if (i < 2) {
      bool = true;
    }
    return bool;
  }
}
