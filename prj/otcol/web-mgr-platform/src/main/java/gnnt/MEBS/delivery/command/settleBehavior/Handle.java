package gnnt.MEBS.delivery.command.settleBehavior;

import gnnt.MEBS.delivery.command.model.SettleObject;

public abstract interface Handle
{
  public abstract void doDeal(SettleObject paramSettleObject);
  
  public abstract int checkFilterList(SettleObject paramSettleObject);
  
  public abstract boolean checkCondition(SettleObject paramSettleObject);
}
