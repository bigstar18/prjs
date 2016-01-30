package gnnt.MEBS.common.broker.webframe.securitycheck;

import gnnt.MEBS.common.broker.model.User;

public abstract interface IUrlCheck
{
  public abstract UrlCheckResult check(String paramString, User paramUser);
}