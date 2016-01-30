package gnnt.MEBS.common.front.webFrame.securityCheck;

import gnnt.MEBS.common.front.model.integrated.User;

public abstract interface IUrlCheck
{
  public abstract UrlCheckResult check(String paramString, User paramUser);
}
