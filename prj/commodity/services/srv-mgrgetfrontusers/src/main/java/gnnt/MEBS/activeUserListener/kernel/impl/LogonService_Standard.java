package gnnt.MEBS.activeUserListener.kernel.impl;

import gnnt.MEBS.activeUserListener.actualize.LogonUserActualize;
import gnnt.MEBS.activeUserListener.kernel.ILogonService;
import gnnt.MEBS.logonService.vo.CompulsoryLogoffVO;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class LogonService_Standard
  extends UnicastRemoteObject
  implements ILogonService, Serializable
{
  private static final long serialVersionUID = 1754818691702243522L;
  
  public LogonService_Standard()
    throws RemoteException
  {}
  
  public List<String> getOnLineUserList(String paramString1, Integer paramInteger, String paramString2, String paramString3)
  {
    return LogonUserActualize.getInstance(paramString1).getOnLineUserList(paramInteger, paramString2, paramString3);
  }
  
  public int compulsoryLogoff(String paramString, CompulsoryLogoffVO paramCompulsoryLogoffVO)
  {
    return LogonUserActualize.getInstance(paramString).compulsoryLogoff(paramCompulsoryLogoffVO);
  }
}
