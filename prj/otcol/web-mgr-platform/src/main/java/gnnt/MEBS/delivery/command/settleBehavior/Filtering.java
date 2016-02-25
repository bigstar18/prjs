package gnnt.MEBS.delivery.command.settleBehavior;

import gnnt.MEBS.delivery.command.model.SettleObject;

public abstract interface Filtering
{
  public abstract int checkFilter(SettleObject paramSettleObject);
}
