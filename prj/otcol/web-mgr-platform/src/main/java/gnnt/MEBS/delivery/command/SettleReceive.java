package gnnt.MEBS.delivery.command;

import gnnt.MEBS.delivery.command.settleExtra.SettleBalanceCheck;
import gnnt.MEBS.delivery.command.settleExtra.SettleLegalExamine;

public abstract interface SettleReceive
  extends Receive
{
  public abstract SettleLegalExamine getSettleLegalExamine();
  
  public abstract SettleBalanceCheck getSettleBalanceCheck();
}
