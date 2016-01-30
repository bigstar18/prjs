package gnnt.MEBS.common.front.callbak;

import gnnt.MEBS.common.front.model.integrated.User;
import javax.servlet.http.HttpServletRequest;

public abstract interface LogonCallbak
{
  public abstract void logonSuccessCallbak(User paramUser, HttpServletRequest paramHttpServletRequest);
}
