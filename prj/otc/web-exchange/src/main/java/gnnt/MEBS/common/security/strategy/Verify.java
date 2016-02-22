package gnnt.MEBS.common.security.strategy;

import gnnt.MEBS.common.model.User;

public abstract interface Verify
{
  public abstract int checkUserRight(User paramUser, long paramLong);
}
