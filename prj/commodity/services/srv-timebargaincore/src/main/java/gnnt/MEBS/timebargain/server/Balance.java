package gnnt.MEBS.timebargain.server;

import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.SysLog;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Balance
{
  private Log log = LogFactory.getLog(getClass());
  private Server server;
  private ServerDAO serverDAO;
  private TradeDAO tradeDAO;
  private static Balance instance;
  
  public static Balance getInstance()
  {
    if (instance == null) {
      instance = new Balance();
    }
    return instance;
  }
  
  public void init(Server paramServer)
  {
    this.server = paramServer;
    this.serverDAO = ((ServerDAO)DAOBeanFactory.getBean("serverDAO"));
    this.tradeDAO = ((TradeDAO)DAOBeanFactory.getBean("tradeDAO"));
  }
  
  public int checkFrozenQtyAtBalance()
  {
    long l = this.tradeDAO.getCustomerHoldSumFrozenQty();
    if (l != 0L)
    {
      this.log.info("闭市结算时检查冻结数量还存在[" + l + "]！");
      return 2;
    }
    this.log.info("闭市结算时检查委托冻结数量通过！");
    return 0;
  }
  
  public int balance()
    throws Exception
  {
    SystemStatus localSystemStatus = this.server.getSystemStatus();
    if ((localSystemStatus.getStatus() != 3) && (localSystemStatus.getStatus() != 1) && (localSystemStatus.getStatus() != 10)) {
      throw new Exception("交易服务器没有闭市操作，不能结算！");
    }
    this.server.getServerInit().getAdjustMargin().adjustMargin(0);
    int i = localSystemStatus.getStatus();
    localSystemStatus.setStatus(2);
    this.server.statusListener();
    int j = this.tradeDAO.balance();
    if (j == 1)
    {
      localSystemStatus.setStatus(10);
      localSystemStatus.setSectionID(null);
      localSystemStatus.setNote(null);
      localSystemStatus.setRecoverTime(null);
    }
    else
    {
      localSystemStatus.setStatus(i);
    }
    this.server.statusListener();
    return j;
  }
  
  public void autoWithdraw()
  {
    this.log.debug("正在自动撤单...");
    List localList = this.tradeDAO.getNotTradeOrders();
    int i = 0;
    int j = 1;
    for (int k = 0; k < localList.size(); k++)
    {
      Order localOrder1 = (Order)localList.get(k);
      Order localOrder2 = new Order();
      localOrder2.setWithdrawerID(null);
      localOrder2.setWithdrawID(localOrder1.getOrderNo());
      localOrder2.setWithdrawType(new Short((short)4));
      localOrder2.setQuantity(null);
      i = this.tradeDAO.withdraw(localOrder2);
      if (i == -100)
      {
        this.log.info("闭市时自动撤委托号为" + localOrder1.getOrderNo() + "的单时执行撤单存储失败！");
        j = 0;
      }
    }
    if (j == 0)
    {
      this.log.debug("自动撤单失败");
      this.serverDAO.insertSysLog(new SysLog("自动撤单失败", 1502, 0));
    }
    else
    {
      this.log.debug("自动撤单成功");
    }
  }
}
