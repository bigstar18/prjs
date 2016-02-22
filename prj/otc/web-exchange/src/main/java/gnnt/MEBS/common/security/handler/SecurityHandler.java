package gnnt.MEBS.common.security.handler;

import gnnt.MEBS.common.model.User;

public abstract interface SecurityHandler
{
  public abstract int handleRequest(String paramString, User paramUser);
}
