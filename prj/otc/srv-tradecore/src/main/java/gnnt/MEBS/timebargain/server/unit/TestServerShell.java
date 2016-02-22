package gnnt.MEBS.timebargain.server.unit;

import gnnt.MEBS.member.ActiveUser.TraderInfo;
import gnnt.MEBS.timebargain.server.model.LimitOrder;
import gnnt.MEBS.timebargain.server.model.MarketOrder;
import gnnt.MEBS.timebargain.server.model.Trader;
import gnnt.MEBS.timebargain.server.model.WithdrawOrder;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestServerShell
{
  static Log log = LogFactory.getLog(TestServerShell.class);
  
  public static void main(String[] args)
  {
    String rmiIP = "172.16.2.10";
    int rmiPort = 6097;
    try
    {
      TradeRMI tradeRMI = (TradeRMI)Naming.lookup("rmi://" + rmiIP + ":" + rmiPort + "/TradeRMI");
      
      TraderInfo traderInfo = logon(tradeRMI);
      if (traderInfo.auSessionId <= 0L)
      {
        log.error("logon failure!");
        return;
      }
      marketOrder(traderInfo.auSessionId, tradeRMI);
      
      limitOrder(traderInfo.auSessionId, tradeRMI);
      
      withdrawOrder(traderInfo.auSessionId, tradeRMI);
    }
    catch (MalformedURLException e)
    {
      e.printStackTrace();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    catch (NotBoundException e)
    {
      e.printStackTrace();
    }
  }
  
  private static TraderInfo logon(TradeRMI tradeRMI)
    throws RemoteException
  {
    Trader trader = new Trader();
    trader.setTraderID("");
    trader.setPassword("");
    return tradeRMI.logon(trader);
  }
  
  private static void marketOrder(long sessionID, TradeRMI tradeRMI)
    throws RemoteException
  {
    MarketOrder marketOrder = new MarketOrder();
    marketOrder.setBuyOrSell(Short.valueOf((short)1));
    marketOrder.setCommodityID("AU");
    marketOrder.setOC_Flag('O');
    marketOrder.setOrderPoint(Double.valueOf(2.0D));
    marketOrder.setOtherFirmID("001");
    marketOrder.setPrice(Double.valueOf(300.0D));
    marketOrder.setQuantity(Long.valueOf(1L));
    marketOrder.setTraderID("1001");
    



    int ret = tradeRMI.marketOrder(sessionID, marketOrder);
    if (ret == 0) {
      log.info("marketOrder success");
    } else {
      log.info("marketOrder failure,ret=" + ret);
    }
  }
  
  private static void limitOrder(long sessionID, TradeRMI tradeRMI)
    throws RemoteException
  {
    LimitOrder limitOrder = new LimitOrder();
    limitOrder.setBuyOrSell(Short.valueOf((short)1));
    limitOrder.setCommodityID("AU");
    limitOrder.setStopLossPrice(Double.valueOf(280.0D));
    limitOrder.setStopProfitPrice(Double.valueOf(320.0D));
    limitOrder.setOtherFirmID("001");
    limitOrder.setPrice(Double.valueOf(300.0D));
    limitOrder.setQuantity(Long.valueOf(1L));
    limitOrder.setTraderID("1001");
    int ret = tradeRMI.limitOrder(sessionID, limitOrder);
    if (ret == 0) {
      log.info("limitOrder success");
    } else {
      log.info("limitOrder failure,ret=" + ret);
    }
  }
  
  private static void withdrawOrder(long sessionID, TradeRMI tradeRMI)
    throws RemoteException
  {
    WithdrawOrder withdrawOrder = new WithdrawOrder();
    withdrawOrder.setWithdrawID(Long.valueOf(1L));
    withdrawOrder.setTraderID("1001");
    int ret = tradeRMI.withdrawOrder(sessionID, withdrawOrder);
    if (ret == 0) {
      log.info("withdrawOrder success");
    } else {
      log.info("withdrawOrder failure,ret=" + ret);
    }
  }
}
