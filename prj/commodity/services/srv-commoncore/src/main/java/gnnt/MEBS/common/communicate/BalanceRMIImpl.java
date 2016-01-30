package gnnt.MEBS.common.communicate;

import gnnt.MEBS.common.communicate.model.BalanceVO;
import gnnt.MEBS.common.core.Server;
import gnnt.MEBS.common.core.sysscheduler.ISysScheduler;
import gnnt.MEBS.common.core.util.DateUtil;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BalanceRMIImpl
  extends UnicastRemoteObject
  implements IBalanceRMI
{
  private Log log = LogFactory.getLog(getClass());
  private static final long serialVersionUID = -5073809856715023630L;
  
  protected BalanceRMIImpl()
    throws RemoteException
  {}
  
  public BalanceVO checkBalance()
    throws RemoteException
  {
    BalanceVO localBalanceVO = new BalanceVO();
    localBalanceVO.setBalanceStatus(true);
    localBalanceVO.setTradeDate(null);
    return localBalanceVO;
  }
  
  public boolean noticeSubsystemStatus()
    throws RemoteException
  {
    try
    {
      Date localDate = new Date(System.currentTimeMillis() + Server.getInstance().sysScheduler.getDiffTime());
      this.log.info("moveHistory Date=" + DateUtil.convertDateToString(localDate));
      Server.getInstance().sysScheduler.moveHistory(localDate);
    }
    catch (Exception localException)
    {
      this.log.error("执行转入历史异常：", localException);
    }
    return true;
  }
}
