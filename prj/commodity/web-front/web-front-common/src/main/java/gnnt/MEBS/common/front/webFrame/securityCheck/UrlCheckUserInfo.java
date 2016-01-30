package gnnt.MEBS.common.front.webFrame.securityCheck;

import gnnt.MEBS.common.front.common.ActiveUserManager;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.logonService.vo.ISLogonResultVO;

public class UrlCheckUserInfo
  implements IUrlCheck
{
  public UrlCheckResult check(String paramString, User paramUser)
  {
    ISLogonResultVO localISLogonResultVO = null;
    try
    {
      localISLogonResultVO = ActiveUserManager.isLogon(paramUser.getTraderID(), paramUser.getSessionId().longValue(), paramUser.getLogonType(), paramUser.getModuleID().intValue());
      int i = localISLogonResultVO.getResult();
      String str = localISLogonResultVO.getRecode();
      if (i == -1)
      {
        if (str.equals("-1303")) {
          return UrlCheckResult.AUUSERKICK;
        }
        if (str.equals("-1302")) {
          return UrlCheckResult.AUOVERTIME;
        }
        return UrlCheckResult.AUOVERTIME;
      }
      if (!ActiveUserManager.checkModuleRight(paramUser.getTraderID())) {
        return UrlCheckResult.NOMODULEPURVIEW;
      }
    }
    catch (Exception localException)
    {
      return UrlCheckResult.AUOVERTIME;
    }
    return UrlCheckResult.SUCCESS;
  }
}
