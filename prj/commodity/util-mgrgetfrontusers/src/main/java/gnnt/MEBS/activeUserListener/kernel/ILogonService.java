package gnnt.MEBS.activeUserListener.kernel;

import gnnt.MEBS.logonService.vo.CompulsoryLogoffVO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public abstract interface ILogonService
  extends Remote
{
  public abstract List<String> getOnLineUserList(String paramString1, Integer paramInteger, String paramString2, String paramString3)
    throws RemoteException;
  
  public abstract int compulsoryLogoff(String paramString, CompulsoryLogoffVO paramCompulsoryLogoffVO)
    throws RemoteException;
}
