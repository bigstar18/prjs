package gnnt.MEBS.timebargain.server.trade;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.DelayPrice;
import gnnt.MEBS.timebargain.server.model.Fee_RT;
import gnnt.MEBS.timebargain.server.model.Firm;
import gnnt.MEBS.timebargain.server.model.Margin_RT;
import gnnt.MEBS.timebargain.server.model.Member;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Quotation_RT;
import gnnt.MEBS.timebargain.server.model.QuotePoint_RT;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.util.Arith;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;

public class MarketOrderImpl
  extends AbstractOrder
{
  private static MarketOrderImpl instance;
  
  public static MarketOrderImpl getInstance()
  {
    if (instance == null) {
      instance = new MarketOrderImpl();
    }
    return instance;
  }
  
  public boolean judgeTrade(Order order)
  {
    boolean bRet = false;
    double tradePrice = 0.0D;
    String m_FirmID = order.getOtherFirmID();
    Member member = (Member)ServerInit.getMemberQueue().get(m_FirmID);
    Quotation_RT quotation_RT = (Quotation_RT)member.getQuotationMap().get(
      order.getCommodityID());
    
    order.setOrderPoint(Double.valueOf(Arith.mul(order.getOrderPoint().doubleValue(), 
      ((Commodity)ServerInit.getCommodityMap().get(order.getCommodityID()))
      .getMinPriceMove())));
    double pointUpPrice = 
      Arith.add(order.getPrice().doubleValue(), order.getOrderPoint().doubleValue());
    double pointDownPrice = Arith.sub(order.getPrice().doubleValue(), order
      .getOrderPoint().doubleValue());
    if ((order.isIsslipPoint()) && (order.getDelayTradeTime().longValue() > 0L))
    {
      double quotePoint = 0.0D;
      QuotePoint_RT quotePoint_RT = (QuotePoint_RT)member.getQuotePointMap().get(
        order.getCommodityID());
      if (order.getBuyOrSell().shortValue() == 1) {
        quotePoint = quotePoint_RT.getQuotePoint_B();
      } else {
        quotePoint = quotePoint_RT.getQuotePoint_S();
      }
      if ((this.delayPrice.containsKey(order.getCommodityID())) && (((List)this.delayPrice.get(order.getCommodityID())).size() > 0)) {
        synchronized ((List)this.delayPrice.get(order.getCommodityID()))
        {
          for (DelayPrice dp : (List)this.delayPrice.get(order.getCommodityID()))
          {
            if (System.currentTimeMillis() - dp.getPriceTime() > order.getDelayTradeTime().longValue()) {
              break;
            }
            if ((Arith.add(dp.getPrice().doubleValue(), quotePoint) >= pointDownPrice) && 
              (Arith.add(dp.getPrice().doubleValue(), quotePoint) <= pointUpPrice)) {
              if (tradePrice > 0.0D)
              {
                if (order.getBuyOrSell().shortValue() == 1)
                {
                  if (Arith.add(dp.getPrice().doubleValue(), quotePoint) > tradePrice)
                  {
                    tradePrice = Arith.add(dp.getPrice().doubleValue(), quotePoint);
                    order.setTradePrice(Double.valueOf(tradePrice));
                  }
                }
                else if (Arith.add(dp.getPrice().doubleValue(), quotePoint) < tradePrice)
                {
                  tradePrice = Arith.add(dp.getPrice().doubleValue(), quotePoint);
                  order.setTradePrice(Double.valueOf(tradePrice));
                }
              }
              else
              {
                tradePrice = Arith.add(dp.getPrice().doubleValue(), quotePoint);
                order.setTradePrice(Double.valueOf(tradePrice));
                bRet = true;
              }
            }
          }
        }
      }
    }
    if (order.getBuyOrSell().shortValue() == 1)
    {
      if ((quotation_RT.getCurPrice_B() >= pointDownPrice) && 
        (quotation_RT.getCurPrice_B() <= pointUpPrice))
      {
        if ((tradePrice > 0.0D) && (tradePrice > quotation_RT.getCurPrice_B())) {
          order.setTradePrice(Double.valueOf(tradePrice));
        } else {
          order.setTradePrice(Double.valueOf(quotation_RT.getCurPrice_B()));
        }
        bRet = true;
      }
    }
    else if ((order.getBuyOrSell().shortValue() == 2) && 
      (quotation_RT.getCurPrice_S() >= pointDownPrice) && 
      (quotation_RT.getCurPrice_S() <= pointUpPrice))
    {
      if ((tradePrice > 0.0D) && (tradePrice < quotation_RT.getCurPrice_S())) {
        order.setTradePrice(Double.valueOf(tradePrice));
      } else {
        order.setTradePrice(Double.valueOf(quotation_RT.getCurPrice_S()));
      }
      bRet = true;
    }
    if (!bRet)
    {
      this.log.info("pointUpPrice:" + pointUpPrice + ";pointDownPrice:" + 
        pointDownPrice);
      this.log.info("order.getBuyOrSell():" + order.getBuyOrSell() + 
        ";quotation_RT.getCurPrice_B():" + 
        quotation_RT.getCurPrice_B() + 
        ";quotation_RT.getCurPrice_S():" + 
        quotation_RT.getCurPrice_S());
    }
    this.log.debug("order.getTradePrice:" + order.getTradePrice() + ";bRet:" + 
      bRet);
    return bRet;
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
      if (!judgeTrade(order)) {
        return 60;
      }
      ret = openOrderProcess(order);
      if (ret != 0)
      {
        this.log.info("openOrder,Ret=" + ret + ",order:" + order);
        return ret;
      }
      this.tradeEngine.addTradeOrder(order);
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
  
  public int closeOrder(Order order)
  {
    try
    {
      int ret = validateOrder(order);
      if (ret != 0)
      {
        this.log.info("validateOrder,Ret=" + ret + ",order:" + order);
        return ret;
      }
      ret = validateCommodity(order);
      if (ret != 0)
      {
        this.log.info("closeOrder:validateCommodity,Ret=" + ret + ",order:" + 
          order);
        return ret;
      }
      if (!judgeTrade(order)) {
        return 60;
      }
      if ((order.getCloseMode().shortValue() == 1) && 
        (ServerInit.getMemberQueue().containsKey(
        order.getFirmID())))
      {
        Commodity commodity = 
          (Commodity)((Map)ServerInit.getCommodityQueue().get(Server.getSystemStatus().getSectionID())).get(
          order.getCommodityID());
        ret = (int)Server.getTradeDAO().closeMemberOrder(order, 
          commodity.getContractFactor());
        if (ret < 0)
        {
          this.log.info("closeMemberOrder,Ret=" + ret + ",order:" + order);
          return ret;
        }
      }
      else
      {
        ret = closeOrderProcess(order);
        if (ret != 0)
        {
          this.log.info("closeOrder,Ret=" + ret + ",order:" + order);
          return ret;
        }
        this.tradeEngine.addTradeOrder(order);
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
  
  public int withdrawOrder(Order order)
  {
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
      .getTradePrice().doubleValue(), commodity.getContractFactor());
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
        .longValue(), fee_RT.getFeeRate(), order.getTradePrice().doubleValue(), 
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
}
