package gnnt.MEBS.bill.communicate;

import gnnt.MEBS.bill.core.Server;
import gnnt.MEBS.bill.core.dao.WareHouseStockDAO;
import gnnt.MEBS.bill.core.util.DateUtil;
import gnnt.MEBS.bill.core.util.GnntBeanFactory;
import gnnt.MEBS.bill.core.util.Tool;
import gnnt.MEBS.common.communicate.IBalanceRMI;
import gnnt.MEBS.common.communicate.model.BalanceVO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BalanceRMIImpl
  extends UnicastRemoteObject
  implements IBalanceRMI
{
  private static final long serialVersionUID = -7229303092305102181L;
  private Log log = LogFactory.getLog(getClass());
  
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
      if ("0".equals(GnntBeanFactory.getConfig("HaveWarehouse")))
      {
        int i = Tool.strToInt(GnntBeanFactory.getConfig("moveHistory"), 1);
        Date localDate = DateUtil.GoDate(new Date(), -i);
        this.log.info("转入历史记录的时间为：" + Tool.fmtTime(localDate));
        Server.getInstance().getWareHouseStockDAO().warehouseMoveHistory(localDate);
      }
    }
    catch (Exception localException)
    {
      this.log.error("执行转入历史异常：", localException);
    }
    return true;
  }
}
