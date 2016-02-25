package gnnt.MEBS.delivery.command.settleBehavior;

import gnnt.MEBS.delivery.command.model.SettleObject;

public abstract interface Behaviour
{
  public abstract void deal(SettleObject paramSettleObject);
}
