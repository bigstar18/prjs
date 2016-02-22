package gnnt.MEBS.timebargain.server.trade;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.util.DateUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WithDrawSPOrderThread
  extends Thread
{
  private Log log = LogFactory.getLog(getClass());
  private long timespace;
  private boolean threadEnd = false;
  private TradeEngine tradeEngine;
  private Date updateTime;
  
  public void init(TradeEngine tradeEngine, long timespace)
  {
    this.tradeEngine = tradeEngine;
    this.timespace = timespace;
    this.updateTime = DateUtil.GoDate(Server.getServerDAO().getCurDbDate(), -1);
  }
  
  public void run()
  {
    break label189;
    try
    {
      do
      {
        if (TradeEngine.getInstance().getTraderOrderStatus() == 0) {
          try
          {
            List withDrawList = Server.getTradeDAO()
              .getWithdrawOrderList(this.updateTime);
            if ((withDrawList != null) && (withDrawList.size() != 0)) {
              for (int i = 0; i < withDrawList.size(); i++)
              {
                Map map = (Map)withDrawList.get(i);
                this.tradeEngine.withdrawOrderProcess(
                
                  Long.valueOf(((BigDecimal)map.get("orderNO")).longValue()), null);
                if (i == withDrawList.size() - 1) {
                  this.updateTime = ((Date)map.get("modifytime"));
                }
              }
            }
          }
          catch (Exception e)
          {
            this.log.error("撤销 无效止损止盈单线程，原因：" + e);
          }
        }
        try
        {
          sleep(this.timespace);
        }
        catch (InterruptedException localInterruptedException) {}
      } while (!this.threadEnd);
    }
    catch (Exception e)
    {
      this.log.error("撤销 无效止损止盈单线程失败，原因：" + e);
    }
    label189:
  }
  
  public void close()
  {
    this.log.info("正在关闭撤销 无效止损止盈单线程！");
    this.threadEnd = true;
    try
    {
      interrupt();
    }
    catch (Exception localException) {}
    this.log.info("成功关闭撤销 无效止损止盈单线程！");
  }
}
