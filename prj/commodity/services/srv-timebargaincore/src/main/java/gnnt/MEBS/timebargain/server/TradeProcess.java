package gnnt.MEBS.timebargain.server;

import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.engine.TradeCallback;
import gnnt.MEBS.timebargain.server.model.SysLog;
import gnnt.MEBS.timebargain.server.model.Trade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeProcess
  implements TradeCallback
{
  private Log log = LogFactory.getLog(getClass());
  private Server server;
  private TradeDAO tradeDAO;
  private ServerDAO serverDAO;
  
  public TradeProcess(Server paramServer)
  {
    this.server = paramServer;
    this.tradeDAO = paramServer.getTradeDAO();
    this.serverDAO = paramServer.getServerDAO();
  }
  
  public void callback(Trade paramTrade1, Trade paramTrade2)
  {
    this.log.debug("trade:" + paramTrade1.toString());
    this.log.debug("trade_opp" + paramTrade2.toString());
    if ((!updateTrade(paramTrade1, paramTrade2)) && (!updateTrade(paramTrade1, paramTrade2)) && (!updateTrade(paramTrade1, paramTrade2)) && (!updateTrade(paramTrade1, paramTrade2)))
    {
      this.log.error("*******************警告：更新成交到db中失败，timebargain整个应用停止服务，请手工重启此timebargain服务！******************");
      System.exit(1);
      return;
    }
  }
  
  private boolean updateTrade(Trade paramTrade1, Trade paramTrade2)
  {
    boolean bool = true;
    try
    {
      int i = this.tradeDAO.updateTrade(paramTrade1, paramTrade2);
      this.log.debug("TradeProcess.updateTrade ret=" + i);
      if (i < 0)
      {
        this.log.error("更新成交回报失败：" + paramTrade1.toString() + ";" + paramTrade2.toString());
        this.log.error("TradeProcess.updateTrade ret=" + i);
        this.serverDAO.insertSysLog(new SysLog("更新成交回报失败：" + paramTrade1.toString() + ";" + paramTrade2.toString(), 1502, 0));
        bool = false;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("更新成交回报异常失败：" + paramTrade1.toString() + ";" + paramTrade2.toString());
      bool = false;
    }
    return bool;
  }
}
