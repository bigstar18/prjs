package gnnt.MEBS.timebargain.server.trade;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.Fee_RT;
import gnnt.MEBS.timebargain.server.model.Firm;
import gnnt.MEBS.timebargain.server.model.Margin_RT;
import gnnt.MEBS.timebargain.server.model.Member;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Quotation_RT;
import gnnt.MEBS.timebargain.server.model.SysLog;
import java.util.Map;
import org.apache.commons.logging.Log;

public class LimitOrderImpl
  extends AbstractOrder
{
  private static LimitOrderImpl instance;
  
  public static LimitOrderImpl getInstance()
  {
    if (instance == null) {
      instance = new LimitOrderImpl();
    }
    return instance;
  }
  
  public int openOrder(Order order)
  {
    try
    {
      int ret = validateOrder(order);
      if (ret != 0)
      {
        this.log.info("validateOrder,Ret=" + ret + ",order:" + order);
        return ret;
      }
      ret = validateOrderQty(order);
      if (ret != 0)
      {
        this.log.info("validateOrderQty,Ret=" + ret + ",order:" + order);
        return ret;
      }
      ret = validateCommodity(order);
      if (ret != 0)
      {
        this.log.info("openOrder:validateCommodity,Ret=" + ret + ",order:" + 
          order);
        return ret;
      }
      boolean bRet = false;
      double curPrice = 0.0D;
      String m_FirmID = order.getOtherFirmID();
      Member member = (Member)ServerInit.getMemberQueue().get(m_FirmID);
      Quotation_RT quotation_RT = (Quotation_RT)member.getQuotationMap().get(
        order.getCommodityID());
      if (order.getBuyOrSell().shortValue() == 1)
      {
        curPrice = quotation_RT.getCurPrice_B();
        if (order.getPrice().doubleValue() == quotation_RT.getCurPrice_B()) {
          bRet = true;
        }
      }
      else if (order.getBuyOrSell().shortValue() == 2)
      {
        curPrice = quotation_RT.getCurPrice_S();
        if (order.getPrice().doubleValue() == quotation_RT.getCurPrice_S()) {
          bRet = true;
        }
      }
      if (bRet) {
        order.setTradePrice(order.getPrice());
      }
      ret = openOrderProcess(order);
      if (ret != 0)
      {
        this.log.info("openOrder,Ret=" + ret + ",order:" + order);
        return ret;
      }
      if (bRet) {
        this.tradeEngine.addTradeOrder(order);
      } else {
        this.tradeEngine.addOrderQueue(order, curPrice);
      }
    }
    catch (Exception e)
    {
      this.log.error("委托失败，" + order.toString());
      e.printStackTrace();
      return 200;
    }
    return 0;
  }
  
  public int closeOrder(Order order)
  {
    try
    {
      int ret = 0;
      boolean bRet = false;
      double curPrice = 0.0D;
      String m_FirmID = order.getOtherFirmID();
      Member member = (Member)ServerInit.getMemberQueue().get(m_FirmID);
      Quotation_RT quotation_RT = (Quotation_RT)member.getQuotationMap().get(
        order.getCommodityID());
      if (order.getRelatedPrice() > 0.0D)
      {
        curPrice = order.getRelatedPrice();
      }
      else if (order.getBuyOrSell().shortValue() == 1)
      {
        curPrice = quotation_RT.getCurPrice_B();
        if (order.getPrice().doubleValue() == quotation_RT.getCurPrice_B()) {
          bRet = true;
        }
      }
      else if (order.getBuyOrSell().shortValue() == 2)
      {
        curPrice = quotation_RT.getCurPrice_S();
        if (order.getPrice().doubleValue() == quotation_RT.getCurPrice_S()) {
          bRet = true;
        }
      }
      if (bRet) {
        order.setTradePrice(order.getPrice());
      }
      ret = closeOrderProcess(order);
      if (ret != 0)
      {
        this.log.info("closeOrder,Ret=" + ret + ",order:" + order);
        return ret;
      }
      if (bRet) {
        this.tradeEngine.addTradeOrder(order);
      } else {
        this.tradeEngine.addOrderQueue(order, curPrice);
      }
    }
    catch (Exception e)
    {
      errorException(e);
      this.log.error("委托失败，" + order.toString());
      e.printStackTrace();
      return 200;
    }
    return 0;
  }
  
  public void errorException(Exception e)
  {
    StackTraceElement[] ste = e.getStackTrace();
    for (int i = 0; i < ste.length; i++) {
      this.log.error(ste[i].toString());
    }
  }
  
  public int withdrawOrder(Order order)
  {
    try
    {
      int ret = validateOrder(order);
      if (ret != 0)
      {
        this.log.info("validateOrder,Ret=" + ret + ",order:" + order);
        return ret;
      }
      ret = withdrawOrderProcess(order);
      if (ret != 0)
      {
        this.log.info("closeOrder,Ret=" + ret + ",order:" + order);
        return ret;
      }
    }
    catch (Exception e)
    {
      this.log.error("委托失败，" + order.toString());
      e.printStackTrace();
      return 200;
    }
    return 0;
  }
  
  private int openOrderProcess(Order order)
  {
    Firm firm = (Firm)ServerInit.getFirmQueue().get(order.getFirmID());
    Margin_RT margin_RT = (Margin_RT)firm.getMarginMap().get(order.getCommodityID());
    Commodity commodity = (Commodity)ServerInit.getCommodityMap().get(
      order.getCommodityID());
    double margin = Function.computeMargin(margin_RT.getMarginAlgr(), order
      .getQuantity().longValue(), margin_RT.getTradeMargin(), order
      .getPrice().doubleValue(), commodity.getContractFactor());
    if (margin < 0.0D) {
      return 40;
    }
    Fee_RT fee_RT = (Fee_RT)firm.getFeeMap().get(order.getCommodityID());
    double fee;
    double fee;
    if (fee_RT.getFeeMode() == 2)
    {
      fee = 0.0D;
    }
    else
    {
      fee = Function.computeFee(fee_RT.getFeeAlgr(), order.getQuantity()
        .longValue(), fee_RT.getFeeRate(), order.getPrice().doubleValue(), 
        commodity.getContractFactor());
      if (fee < 0.0D) {
        return 41;
      }
    }
    order.setMargin(new Double(margin));
    order.setFee(new Double(fee));
    long orderRet = Server.getTradeDAO().openOrder(order);
    this.log.debug("openOrder,orderRet=" + orderRet);
    if (orderRet < 0L) {
      return (int)orderRet;
    }
    order.setOrderNo(new Long(orderRet));
    order.setStatus(new Short((short)1));
    this.log.debug("openOrder,orderno:" + order.getOrderNo() + ";status:" + 
      order.getStatus());
    
    return 0;
  }
  
  private int closeOrderProcess(Order order)
  {
    long orderRet = Server.getTradeDAO().closeOrder(order);
    this.log.debug("closeOrder,orderRet=" + orderRet);
    if (orderRet < 0L) {
      return (int)orderRet;
    }
    order.setOrderNo(new Long(orderRet));
    order.setStatus(new Short((short)1));
    this.log.debug("closeOrder,orderno:" + order.getOrderNo() + ";status:" + 
      order.getStatus());
    
    return 0;
  }
  
  private int withdrawOrderProcess(Order order)
  {
    long withdrawQty = this.tradeEngine.cancelOrder(order.getWithdrawID());
    this.log.debug("withdrawOrder,withdrawQty:" + withdrawQty);
    if (withdrawQty < 0L)
    {
      this.log.error("撤单失败，此委托已成交或已撤单:" + order.getWithdrawID());
      return 42;
    }
    String traderID = order.getTraderID();
    if ((traderID == null) || (traderID.equals(""))) {
      order.setWithdrawerID(order.getConsignerID());
    } else {
      order.setWithdrawerID(traderID);
    }
    order.setWithdrawType(new Short((short)1));
    




    int ret = withdraw(order);
    if (ret == -200) {
      for (int i = 0; i < 3; i++)
      {
        ret = withdraw(order);
        if (ret != -200) {
          break;
        }
      }
    }
    return ret;
  }
  
  private int withdraw(Order order)
  {
    int bRet = 0;
    try
    {
      bRet = Server.getTradeDAO().withdraw(order);
      if (bRet < 0)
      {
        this.log.error("撤单失败：" + order.toString());
        this.log.error("LimitOrderImpl.withdraw ret=" + bRet);
        Server.getServerDAO().insertSysLog(
          new SysLog("撤单失败：" + order.toString()));
      }
      else
      {
        bRet = 0;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.log.error("撤单失败：" + e);
      this.log.error("撤单失败：" + order.toString());
      bRet = -200;
      try
      {
        Thread.sleep(10L);
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
        this.log.error("Thread sleep error:" + e1);
      }
    }
    return bRet;
  }
}
