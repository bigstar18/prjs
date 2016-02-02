package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.common.communicate.IBalanceRMI;
import gnnt.MEBS.common.communicate.model.BalanceVO;
import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BalanceRMIImpl
  extends UnicastRemoteObject
  implements IBalanceRMI
{
  private static final long serialVersionUID = 2917875416226097105L;
  private Log log = LogFactory.getLog(getClass());
  private Server server;
  
  public BalanceRMIImpl(Server paramServer)
    throws RemoteException
  {
    this.server = paramServer;
  }
  
  public boolean noticeSubsystemStatus()
    throws RemoteException
  {
    this.server.getSystemStatus().setStatus(3);
    this.server.statusListener();
    return true;
  }
  
  public BalanceVO checkBalance()
    throws RemoteException
  {
    BalanceVO localBalanceVO = new BalanceVO();
    localBalanceVO.setTradeDate(this.server.getSystemStatus().getTradeDate());
    SystemStatus localSystemStatus = this.server.getSystemStatus();
    if ((10 == localSystemStatus.getStatus()) || (3 == localSystemStatus.getStatus()))
    {
      localBalanceVO.setBalanceStatus(true);
    }
    else
    {
      localBalanceVO.setBalanceStatus(false);
      localBalanceVO.setMessage("当前不是订单结算完成状态，当前状态为: " + gnnt.MEBS.timebargain.server.Constants.SYSTEM_STATUS[localSystemStatus.getStatus()]);
    }
    return localBalanceVO;
  }
}
