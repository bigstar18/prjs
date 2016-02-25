package gnnt.MEBS.delivery.command.settleBehavior;

import gnnt.MEBS.delivery.command.model.SettleObject;

public abstract interface Condition
{
  public abstract boolean check(SettleObject paramSettleObject);
}
