package gnnt.MEBS.common.front.webFrame.securityCheck;

import gnnt.MEBS.common.front.model.integrated.User;

public class UrlCheckSession
  implements IUrlCheck
{
  public UrlCheckResult check(String paramString, User paramUser)
  {
    if (paramUser == null) {
      return UrlCheckResult.USERISNULL;
    }
    return UrlCheckResult.SUCCESS;
  }
}
