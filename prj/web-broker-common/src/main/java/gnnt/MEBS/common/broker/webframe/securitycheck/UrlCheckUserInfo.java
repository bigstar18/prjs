package gnnt.MEBS.common.broker.webframe.securitycheck;

import gnnt.MEBS.common.broker.common.ActiveUserManager;
import gnnt.MEBS.common.broker.common.Global;
import gnnt.MEBS.common.broker.model.User;
import gnnt.MEBS.logonService.vo.ISLogonResultVO;

public class UrlCheckUserInfo
  implements IUrlCheck
{
  public UrlCheckResult check(String paramString, User paramUser)
  {
    try
    {
      ISLogonResultVO localISLogonResultVO = ActiveUserManager.isLogon(paramUser.getUserId(), paramUser.getSessionId(), Global.getSelfModuleID(), paramUser.getLogonType());
      if (localISLogonResultVO.getResult() == -1)
      {
        if ("-1301".equals(localISLogonResultVO.getRecode().trim()))
          return UrlCheckResult.USERISNULL;
        if ("-1302".equals(localISLogonResultVO.getRecode().trim()))
          return UrlCheckResult.AUOVERTIME;
        if ("-1303".equals(localISLogonResultVO.getRecode().trim()))
          return UrlCheckResult.AUUSERKICK;
        return UrlCheckResult.USERISNULL;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return UrlCheckResult.SUCCESS;
  }
}