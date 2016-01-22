package gnnt.MEBS.logonService.kernel;

import gnnt.MEBS.logonService.vo.CheckUserResultVO;
import gnnt.MEBS.logonService.vo.CheckUserVO;
import gnnt.MEBS.logonService.vo.CompulsoryLogoffResultVO;
import gnnt.MEBS.logonService.vo.CompulsoryLogoffVO;
import gnnt.MEBS.logonService.vo.GetUserResultVO;
import gnnt.MEBS.logonService.vo.GetUserVO;
import gnnt.MEBS.logonService.vo.ISLogonResultVO;
import gnnt.MEBS.logonService.vo.ISLogonVO;
import gnnt.MEBS.logonService.vo.LogoffResultVO;
import gnnt.MEBS.logonService.vo.LogoffVO;
import gnnt.MEBS.logonService.vo.LogonOrLogoffUserVO;
import gnnt.MEBS.logonService.vo.LogonResultVO;
import gnnt.MEBS.logonService.vo.LogonVO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public abstract interface ILogonService extends Remote
{
  public abstract LogonResultVO logon(LogonVO paramLogonVO)
    throws RemoteException;

  public abstract LogoffResultVO logoff(LogoffVO paramLogoffVO)
    throws RemoteException;

  public abstract CheckUserResultVO checkUser(CheckUserVO paramCheckUserVO, int paramInt, String paramString)
    throws RemoteException;

  public abstract GetUserResultVO getUserBySessionID(GetUserVO paramGetUserVO)
    throws RemoteException;

  public abstract ISLogonResultVO isLogon(ISLogonVO paramISLogonVO)
    throws RemoteException;

  public abstract CompulsoryLogoffResultVO compulsoryLogoff(CompulsoryLogoffVO paramCompulsoryLogoffVO)
    throws RemoteException;

  public abstract List<LogonOrLogoffUserVO> getLogonOrLogoffUserList()
    throws RemoteException;

  public abstract List<LogonOrLogoffUserVO> getAllLogonUserList()
    throws RemoteException;

  public abstract List<LogonOrLogoffUserVO> onlyGetAllLogonUserList()
    throws RemoteException;

  public abstract int isRestartStartRMI()
    throws RemoteException;
}